package local.dto;

import java.util.List;

import local.database.mysql.MysqlConnect;
import local.database.mysql.MysqlTransfer;
import local.database.mysql.MysqlUOW;
import local.database.mysql.MysqlVideoRepo;
import local.database.xml.XmlUOW;
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
							
	
	private IUnitOfWork uow = null;
	
	public VideoProvider()
	{
//		this.uow = new MysqlUOW();
		this.uow = new XmlUOW();
	}
	
	
	public void getVideos() throws Exception {
		List<Video> videos = this.uow.VideoRepo().getVideos();
				
		for( Video video : videos)
		{
			System.out.println( video.getType() );
			System.out.println( video.getTitle() );
		}
	}
	
	public List<Video> returnVideos() throws Exception {
		List<Video> videos = this.uow.VideoRepo().getVideos();
				
		return videos;
	}
	
	
}
