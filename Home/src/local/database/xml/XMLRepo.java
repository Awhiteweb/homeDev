package local.database.xml;

import java.util.List;

import local.dto.Video;
import local.models.top.IVideoRepo;

public class XMLRepo implements IVideoRepo
{

	@Override
	public List<Video> getVideos()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Video> searchVideos( String search, String category )
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeVideos( List<Video> videos )
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateVideos( List<Video> videos )
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Video> getVideos( int amount ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Video getVideoByID( int id ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendPreparedStatement ( String statement )
	{
		// TODO Auto-generated method stub
		
	}

}
