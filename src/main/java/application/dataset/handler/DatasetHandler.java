package application.dataset.handler;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import application.objects.Blog;
import application.objects.Word;
import org.springframework.stereotype.Component;

@Component
public class DatasetHandler {

	private List<Word> words;
	private List<Blog> blogs;

	@PostConstruct
	void setup() {
		ClassLoader classLoader = getClass().getClassLoader();
		blogs = new ArrayList<>();
		words = new ArrayList<>();

		File blogDataFile = new File(classLoader.getResource("blogdata.txt").getFile());
		try {
			BufferedReader reader = new BufferedReader(new FileReader(blogDataFile));
			String line = reader.readLine();

			List<String> allWords = new ArrayList<>(asList(line.split("\\t")));
			//Remove the "Blog" column on the first row
			allWords.remove(0);
			words = allWords.stream().map(Word::new).collect(toList());

			while (nonNull(line = reader.readLine())){
				List<String> vals = new ArrayList<>(asList(line.split("\\t")));
				String blogName = vals.remove(0);

				blogs.add(new Blog(blogName, mapWordsToCount(vals)));
			}
		} catch (IOException ex){
			throw new RuntimeException("Could not read dataset file.");
		}
	}

	private Map<String, Double> mapWordsToCount(List<String> wordCounts) {
		Map<String, Double> wordsToAmountUsed = new HashMap<>();

		range(0, words.size())
				.forEach(i -> {
					int wordUsage = parseInt(wordCounts.get(i));
					//Add word to map
					wordsToAmountUsed.put(words.get(i).getWord(), (double)wordUsage);
					//Update min/max usage of the word overall
					words.get(i).updateWordMinMaxUsage(wordUsage);

				});

		return wordsToAmountUsed;
	}

	public List<Word> getWords(){
		return words;
	}

	public List<Blog> getBlogs(){
		return blogs;
	}
}
