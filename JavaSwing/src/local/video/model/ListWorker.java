package local.video.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import local.dto.Video;
import local.dto.VideoProvider;
import local.models.top.Finals;
import local.models.top.Repos;
import local.video.constants.*;


public class ListWorker {
	
	private DefaultListModel<String> titleList;
	private ArrayList<String> locationList;
	private DefaultListModel<String> genreList;
	private DefaultListModel<String> groupList;
	private DefaultListModel<Integer> episodeList;
	private DefaultListModel<Integer> seasonList;
	private List<Video> videos;

	public ListWorker()
	{
	}
	
	public ListWorker( List<Video> videos )
	{
		this.videos = videos;
		setLists();
	}
	
	public void setLists()
	{
		ArrayList<String> genres = new ArrayList<String>();
		ArrayList<String> groups = new ArrayList<String>();
		ArrayList<Integer> episodes = new ArrayList<Integer>();
		ArrayList<Integer> season = new ArrayList<Integer>();
		
		titleList = new DefaultListModel<String>();
		locationList = new ArrayList<String>();
		genreList = new DefaultListModel<String>();
		groupList = new DefaultListModel<String>();
		episodeList = new DefaultListModel<Integer>();
		seasonList = new DefaultListModel<Integer>();

		for( Video video : videos )
		{
			titleList.addElement( video.getTitle() );
			locationList.add( video.getLocation() );
			
			if ( !genres.contains( video.getGenre() ) )
			{
				genres.add( video.getGenre() );
			}
			
			if ( !groups.contains( video.getGroup() ) )
			{
				groups.add( video.getGroup() );
			}

			if ( !episodes.contains( video.getSeriesN() ) )
			{
				episodes.add( video.getSeriesN() );
			}

			if ( !season.contains( video.getSeasonN() ) )
			{
				season.add( video.getSeasonN() );				
			}

		}
		
		Collections.sort( genres );
		for (String s : genres ) {
			genreList.addElement( s );
		}
		
		Collections.sort( groups );
		for (String s : groups ) {
			groupList.addElement( s );
		}

		Collections.sort( episodes );
		for (Integer i : episodes ) {
			episodeList.addElement( i );
		}

		Collections.sort( season );
		for (Integer i : season ) {
			seasonList.addElement( i );
		}
		
	}
	
	public DefaultListModel<String> sortTitles()
	{
		ArrayList<String> arrayList = new ArrayList<String>();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for (Video video : videos )
		{
			arrayList.add( video.getTitle() );
		}
		
		Collections.sort( arrayList );
		for ( String s : arrayList )
		{
			listModel.addElement( s );
		}

		return listModel;
	}

	public DefaultListModel<String> sortGenres()
	{
		ArrayList<String> arrayList = new ArrayList<String>();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for (Video video : videos )
		{
			if ( !arrayList.contains( video.getGenre() ) )
			{
				arrayList.add( video.getGenre() );
			}
		}
		
		Collections.sort( arrayList );
		for ( String s : arrayList )
		{
			listModel.addElement( s );
		}

		return listModel;
		
	}
	
	public DefaultListModel<String> sortGroups()
	{
		ArrayList<String> arrayList = new ArrayList<String>();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for (Video video : videos )
		{
			if ( !arrayList.contains( video.getGroup() ) )
			{
				arrayList.add( video.getGroup() );
			}
		}
		
		Collections.sort( arrayList );
		for ( String s : arrayList )
		{
			listModel.addElement( s );
		}

		return listModel;
	}
	
	public DefaultListModel<String> sortEpisodes()
	{
		ArrayList<String> arrayList = new ArrayList<String>();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for (Video video : videos )
		{
			if ( !arrayList.contains( video.getSeriesN() ) )
			{
				arrayList.add( Integer.toString( video.getSeriesN() ) );
			}
		}
		
		Collections.sort( arrayList );
		for ( String s : arrayList )
		{
			listModel.addElement( s );
		}

		return listModel;
	}
	
	public DefaultListModel<String> sortSeasons()
	{
		ArrayList<String> arrayList = new ArrayList<String>();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for (Video video : videos )
		{
			if ( !arrayList.contains( video.getSeasonN() ) )
			{
				arrayList.add( Integer.toString( video.getSeasonN() ) );
			}
		}
		
		Collections.sort( arrayList );
		for ( String s : arrayList )
		{
			listModel.addElement( s );
		}

		return listModel;
	}
	
	public static List<Video> chooseType( Types type )
	{
		switch ( type )
		{
		case ALL:
			return updateAll();
			
		case MOVIE:
			return updateLists( "movie" , Finals.TYPE );
			
		case TV:
			return updateLists( "tv" , Finals.TYPE );
			
		default:
			return updateAll();
		}
	}
	
	public static List<Video> updateLists( String type, String finalsValue ) 
	{
		VideoProvider controller = new VideoProvider( Repos.XML );

		try 
		{
			List<Video> videoList = controller.returnVideos( type, finalsValue );
		
			return videoList;
			
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		return null;		
	}
	
	public static List<Video> updateAll() 
	{
		VideoProvider controller = new VideoProvider( Repos.XML );
		
		try 
		{
			List<Video> videoList = controller.returnVideos();
			
			return videoList;
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public DefaultListModel<String> getTitles()
	{
		return this.titleList;
	}

	public DefaultListModel<String> getGenres()
	{
		return this.genreList;
	}
	
	public DefaultListModel<String> getGroups()
	{
		return this.groupList;
	}
	
	public DefaultListModel<Integer> getEpisodes()
	{
		return this.episodeList;
	}
	
	public DefaultListModel<Integer> getSeasons()
	{
		return this.seasonList;
	}
	
	public ArrayList<String> getLocations()
	{
		return this.locationList;
	}
	
	
}
