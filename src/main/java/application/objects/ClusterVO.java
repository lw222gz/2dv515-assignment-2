package application.objects;

import static java.util.stream.Collectors.toList;

import java.util.List;

public class ClusterVO {
	List<String> blogs;
	String title;


	public ClusterVO(Centroid centroid){
		this.blogs = centroid.getAssignedBlogs().stream().map(Blog::getBlogName).collect(toList());
		this.title = "Centroid - " + centroid.hashCode();
	}

	public String getTitle() {
		return title;
	}

	public List<String> getBlogs() {
		return blogs;
	}
}