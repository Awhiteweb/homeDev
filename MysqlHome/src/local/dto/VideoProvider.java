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
						 SEASON_N = "season_number",
						 	 TYPE = "type";
							
	
	private IUnitOfWork uow = null;
	
	public VideoProvider()
	{
		this.uow = new MysqlUOW();
	}
	
	
	public void getVideos() throws Exception {
		List<Video> videos = this.uow.VideoRepo().getVideos( 4 );
		
		for( Video video : videos)
		{
			System.out.println( video.getTitle() );
		}
	}
}
