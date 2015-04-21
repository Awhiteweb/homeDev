package local.database.xml;

import java.util.ArrayList;
import java.util.List;

import local.dto.Video;
import local.dto.VideoProvider;
import local.models.top.IVideoRepo;
import local.models.top.Repos;

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
		
		XMLReader handler = new XMLReader();
		
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
		VideoProvider video = new VideoProvider( Repos.MYSQL );

		try 
		{

			List<Video> data = video.returnVideos();

			XMLWriter creator = new XMLWriter();
		
			creator.createDocument( data , "./files/file.xml" );
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}


	}

	@Override
	public void updateVideos( List<Video> videos )
	{		
	}

	@Override
	public List<Video> getVideos()
	{
		List<Video> videos = new ArrayList<Video>();
		
		XMLReader handler = new XMLReader();
		
		try
		{
			videos = handler.readXML( "./files/file.xml" );
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
