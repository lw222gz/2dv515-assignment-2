package application.clustering;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import application.dataset.handler.DatasetHandler;
import application.objects.Blog;
import application.objects.Centroid;
import application.objects.ClusterDto;
import application.objects.Word;
import application.pearson.PearsonCorrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KMeansClusteringService {

	private Random rand = new Random();

	@Autowired
	private DatasetHandler datasetHandler;

	@Autowired
	private PearsonCorrelationService pearsonCorrelationService;

	public List<ClusterDto> cluster(int amountOfClusters){
		List<Centroid> centroids = generateCentroids(amountOfClusters);

		List<Blog> blogs = datasetHandler.getBlogs();

		boolean assignmentsUpdated = true;

		while(assignmentsUpdated){
			centroids.forEach(Centroid::clearAssignments);

			blogs.forEach(b -> assignToClosestCentroid(b, centroids));

			centroids.forEach(Centroid::centerBetweenAssignedBlogs);

			assignmentsUpdated = centroids.stream().anyMatch(Centroid::hasNewAssignments);
		}

		return centroids.stream().map(ClusterDto::new).collect(Collectors.toList());
	}

	private void assignToClosestCentroid(Blog b, List<Centroid> centroids) {
		double distance = -Double.MAX_VALUE;
		Centroid closest = null;
		for(Centroid c : centroids){
			double cDist = pearsonCorrelationService.pearson(c, b);
			if(cDist > distance){
				closest = c;
				distance = cDist;
			}
		}

		ofNullable(closest).ifPresent(centroid -> centroid.assign(b));
	}


	private List<Centroid> generateCentroids(int amountOfClusters){
		List<Word> datasetWords = datasetHandler.getWords();

		List<Centroid> centroids = new ArrayList<>();
		for(int i = 1; i <= amountOfClusters; i++){
			centroids.add(new Centroid(i, generateRandomWordMap(datasetWords)));
		}

		return centroids;
	}

	private Map<String, Double> generateRandomWordMap(List<Word> datasetWords) {
		return datasetWords.stream().collect(toMap(Word::getWord, word -> (double)rand.nextInt(word.getMax() - word.getMin()) + word.getMin()));
	}
}
