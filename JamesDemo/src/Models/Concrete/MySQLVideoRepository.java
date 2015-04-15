package Models.Concrete;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.Video;
import Models.Abstract.IVideoRepository;

public class MySQLVideoRepository implements IVideoRepository{

	private MySQLDbContext context;
	
	public MySQLVideoRepository( MySQLDbContext ctx )
	{
		this.context = ctx;
	}
	
	@Override
	public ArrayList<Video> GetVideos() {
		ResultSet resultSet = context.Query( "SELECT * FROM videos" );
		
		ArrayList<Video> videos = null;
		
		try
		{
			while( resultSet.next() )
			{
				Video video = new Video();
				video.Title = resultSet.getString("title");
				videos.add(video);
			}
		}
		catch( Exception e ){}
		
		return videos;
	}

}
