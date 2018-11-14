package application.objects;

import java.util.Map;

import application.objects.structure.PearsonComparable;

public class Blog implements PearsonComparable {
	private String blogName;
	private Map<String, Double> wordsToAmountUsed;

	public Blog(String blogName, Map<String, Double> wordsToAmountUsed){
		this.blogName = blogName;
		this.wordsToAmountUsed = wordsToAmountUsed;
	}

	public String getBlogName() {
		return blogName;
	}

	@Override
	public Map<String, Double> getWordsToAmountUsed() {
		return wordsToAmountUsed;
	}
}
