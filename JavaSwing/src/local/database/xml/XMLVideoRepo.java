package local.database.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import local.dto.Video;
import local.dto.VideoProvider;
import local.models.top.CoreData;
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
			File in = new File( CoreData.XMLFILE );
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
		System.out.println( "get database" );
		try 
		{
			XMLWriter creator = new XMLWriter();
			
			System.out.println( "writeVideos data size " + videos.size() );
			videos = concatVideos( videos, 0 );
			System.out.println( "writeVideos filtered size " + videos.size() );
			
			creator.createDocument( videos , CoreData.XMLFILE ); 
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}


	}

	@Override
	public List<Video> getVideos()
	{
		List<Video> videos = new ArrayList<Video>();
		
		XMLReader handler = new XMLReader();
		
		try
		{
			File in = new File( CoreData.XMLFILE );
			if ( in.exists() )
			{
				videos = handler.readXML( in );
			}
			else
			{
				VideoProvider controller = new VideoProvider( Repos.MYSQL );
				try
				{
					List<Video> data = controller.returnVideos();
					VideoProvider writer = new VideoProvider( Repos.XML );
					writer.writeVideos( data );
					videos = handler.readXML( in );
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

	@Override
	public boolean updateVideo( Video video )
	{
		// not for XML use
		return false;
	}
	
	private List<Video> concatVideos( List<Video> data, int index )
	{
		if ( data.size() <= index )
			return data;

		List<Video> filter = data;
		List<Integer> remove = new ArrayList<Integer>();
		String updateGenre = filter.get( index ).getGenre();
		String updateGroup = filter.get( index ).getGroup();
		for ( Video first : data )
		{
			boolean ge = false, gr = false;
			if ( index != data.indexOf( first ) )
			{
//				System.out.println( index + " : " + data.indexOf( first ) );
				if ( filter.get( index ).getID() == first.getID() )
				{
//					System.out.println( index + " : " + data.indexOf( first ) );
					System.out.println( "match ID: " + first.getID() );
					
					if ( filter.get( index ).getGenre() == null && first.getGenre() == null ) { /* do nothing */ }
					else if ( ( filter.get( index ).getGenre() == null && first.getGenre() != null ) || !filter.get( index ).getGenre().equals( first.getGenre() ) )
					{
						ge = true;
						updateGenre += "," + first.getGenre();
						filter.get( index ).setGenre( updateGenre );
//						System.out.println( updateGenre );
					}
					
					if ( filter.get( index ).getGroup() == null && first.getGroup() == null ) { /* do nothing */ }
					else if ( ( filter.get( index ).getGroup() == null && first.getGroup() != null ) || !filter.get( index ).getGroup().equals( first.getGroup() ) )
					{
						gr = true;
						updateGroup += "," + first.getGroup();
						filter.get( index ).setGroup( updateGroup );
//						System.out.println( updateGroup );
					}
				}
				if ( ge || gr )
				{
					int i = data.indexOf( first );
					remove.add( i );
				}
			}
		}
		for ( Integer i : remove )
		{
//			System.out.println( "remove " + i );
			filter.remove( i.intValue() );
		}
		index++;
		return concatVideos( data, index );
	}

}
