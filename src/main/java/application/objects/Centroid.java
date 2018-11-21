package application.objects;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import application.objects.structure.PearsonComparable;

public class Centroid implements PearsonComparable {

	private Map<String, Double> wordsToAmountUsed;
	private Set<Blog> assignedBlogs;
	private Set<Blog> previousAssignments;
	private int id;

	public Centroid(int id, Map<String, Double> wordsToAmountUsed){
		this.id = id;
		this.wordsToAmountUsed = wordsToAmountUsed;
		assignedBlogs = new HashSet<>();
	}

	@Override
	public Map<String, Double> getWordsToAmountUsed() {
		return wordsToAmountUsed;
	}

	public Set<Blog> getAssignedBlogs() {
		return assignedBlogs;
	}

	public int getId() {
		return id;
	}

	public void clearAssignments(){
		previousAssignments = assignedBlogs;
		assignedBlogs = new HashSet<>();
	}

	public void assign(Blog blog){
		assignedBlogs.add(blog);
	}

	public void centerBetweenAssignedBlogs() {
		if(assignedBlogs.size() > 0){
			wordsToAmountUsed.entrySet().forEach(this::updateToAverageWordCountOfAssignedBlogs);
		}
	}

	private void updateToAverageWordCountOfAssignedBlogs(Map.Entry<String, Double> entry){
		double totalUsage = assignedBlogs.stream().mapToDouble(b -> b.getWordsToAmountUsed().get(entry.getKey())).sum();
		entry.setValue(totalUsage / assignedBlogs.size());
	}

	public boolean hasNewAssignments() {
		return !(previousAssignments.containsAll(assignedBlogs) && previousAssignments.size() == assignedBlogs.size());
	}
}
