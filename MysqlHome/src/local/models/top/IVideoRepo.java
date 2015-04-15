package local.models.top;

import java.sql.ResultSet;
import java.util.List;

import local.dto.Video;

public interface IVideoRepo
{

	List<Video> getVideos();
	
	List<Video> searchVideos( String search );

	void writeVideos( List<Video> videos );
	
	void updateVideos( List<Video> videos );
	
}
