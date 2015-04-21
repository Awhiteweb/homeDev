package local.models.top;

import java.sql.ResultSet;
import java.util.List;

import local.dto.Video;

public interface IVideoRepo
{

	List<Video> getVideos( int amount );

	Video getVideoByID( int id );
	
	List<Video> searchVideos( String searchTitle, String searchCat );

	void writeVideos( List<Video> videos );
	
	void updateVideos( List<Video> videos );

	List<Video> getVideos();
	
}
