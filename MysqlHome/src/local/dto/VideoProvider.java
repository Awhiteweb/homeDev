package local.dto;

import java.util.List;

import local.database.mysql.MysqlConnect;
import local.database.mysql.MysqlTransfer;

public class VideoProvider {
	
	public static final String ID = "id",
							 TYPE = "type",
					 	    TITLE = "title",
						 LOCATION = "location",
							GENRE = "genre",
							GROUP = "group",
						 SERIES_N = "series_number",
						 SEASON_N = "season_number";
							
	
	private static List<Video> videos;

	public static List<Video> getVideos() throws Exception {
		
		videos = MysqlTransfer.returnAll();
		
		MysqlConnect.close();
		
		return videos;
	}
}
