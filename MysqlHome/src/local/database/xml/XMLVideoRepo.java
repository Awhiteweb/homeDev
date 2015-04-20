package local.database.xml;

import java.util.ArrayList;
import java.util.List;

import local.dto.Video;
import local.models.top.IVideoRepo;

public class XMLVideoRepo implements IVideoRepo
{

	@Override
	public List<Video> getVideos( int amount )
	{
		return null;
	}

	@Override
	public List<Video> searchVideos( String search )
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
	public List<Video> getVideos()
	{
		List<Video> videos = new ArrayList<Video>();
		
		XMLHandler handler = new XMLHandler();
		
		try
		{
			videos = handler.readXML( "files\\file.xml" );
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return videos;
	}

	@Override
	public Video getVideoByID( int id )
	{
		// TODO Auto-generated method stub
		return null;
	}

}
