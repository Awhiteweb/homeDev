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

@SuppressWarnings( { "rawtypes", "unchecked" } )
public class ListWorker {
	
	
	private DefaultListModel titleList, genreList, groupList, episodeList, seasonList;
	private ArrayList<String> locationList;
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
		
		titleList = new DefaultListModel();
		locationList = new ArrayList<String>();
		genreList = new DefaultListModel();
		groupList = new DefaultListModel();
		episodeList = new DefaultListModel();
		seasonList = new DefaultListModel();

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
	
	public void chooseType( Types type )
	{
		switch ( type )
		{
		case ALL:
			updateAll();
			break;
			
		case MOVIE:
			updateLists( "movie" , Finals.TYPE );
			break;
			
		case TV:
			updateLists( "tv" , Finals.TYPE );
			break;
			
		default:
			updateAll();
			break;
		}
	}
	
	public void updateLists( String type, String finalsValue ) 
	{
		VideoProvider controller = new VideoProvider( Repos.XML );

		try 
		{
			videos = controller.returnVideos( type, finalsValue );
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}		
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
