package application;

import java.util.List;

import application.clustering.KMeansClusteringService;
import application.objects.ClusterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BlogClusteringApplication {

	public static void main(String[] args){
		SpringApplication.run(BlogClusteringApplication.class, args);
	}

	@Autowired
	KMeansClusteringService kMeansClusteringService;

	@GetMapping("/kmeans/{amountOfClusters}")
	List<ClusterDto> kMeansClustering(@PathVariable Integer amountOfClusters){
		return kMeansClusteringService.cluster(amountOfClusters);
	}
}
