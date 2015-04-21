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
	public List<Video> searchVideos(String searchTitle, String searchCat) {

		List<Video> videos = new ArrayList<Video>();
		
		XMLHandler handler = new XMLHandler();
		
		handler.searchTitle = searchTitle;
		handler.searchCat = searchCat;
		
		try
		{
			videos = handler.readXML( "files/file.xml" );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return videos;
	}

	@Override
	public void writeVideos( List<Video> videos )
	{
	}

	@Override
	public void updateVideos( List<Video> videos )
	{		
	}

	@Override
	public List<Video> getVideos()
	{
		List<Video> videos = new ArrayList<Video>();
		
		XMLHandler handler = new XMLHandler();
		
		try
		{
			videos = handler.readXML( "files/file.xml" );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return videos;
	}

	@Override
	public Video getVideoByID( int id )
	{
		return null;
	}


}
