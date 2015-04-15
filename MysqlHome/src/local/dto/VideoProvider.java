package local.dto;

import java.util.List;

import local.database.mysql.MysqlConnect;
import local.database.mysql.MysqlTransfer;
import local.database.mysql.MysqlUOW;
import local.database.mysql.MysqlVideoRepo;
import local.models.top.IUnitOfWork;

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
	private IUnitOfWork uow = null;
	
	public VideoProvider()
	{
		this.uow = new MysqlUOW();
	}
	
	
	public List<Video> getVideos() throws Exception {
		
		videos = this.uow.VideoRepo().getVideos();
		
		return videos;
	}
}
