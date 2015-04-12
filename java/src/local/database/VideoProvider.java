package local.database;

import java.util.List;

public class VideoProvider {
	
	public static final String ID = "id",
					 	    TITLE = "title",
						 LOCATION = "location",
							GENRE = "genre",
							GROUP = "group",
						  SERIESN = "seriesN";
							
	
	private static List<Movie> videos;

	public static List<Movie> getVideos() {
		
		return videos;
	}
}
