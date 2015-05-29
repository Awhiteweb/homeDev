package local.database.xml;

import java.io.File;
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
			File in = new File("/Users/Alex/HomeVideo/videos.xml");
			videos = handler.readXML( in );
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
		
			creator.createDocument( data , "/Users/Alex/HomeVideo/videos.xml" );
			
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
			File in = new File("/Users/Alex/HomeVideo/videos.xml");
			if ( in.exists() )
			{
				videos = handler.readXML( in ); // "files/file.xml"
			}
			else
			{
				VideoProvider controller = new VideoProvider( Repos.MYSQL );
				try
				{
					List<Video> data = controller.returnVideos();
					VideoProvider writer = new VideoProvider( Repos.XML );
					writer.writeVideos( data );
					videos = handler.readXML( in ); // "files/file.xml"
				}
				catch ( Exception ex )
				{
					ex.printStackTrace();
				}
				
			}
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

	@Override
	public void sendPreparedStatement ( String statement )
	{
		// not for XML use
		
	}


}
