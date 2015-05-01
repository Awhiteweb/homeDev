package local.video.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import local.dto.Video;
import local.dto.VideoProvider;
import local.models.top.Repos;
import local.video.constants.*;


public class ListWorker {
	
	private ArrayList<String> arrayList;
	private DefaultListModel<String> listModel;

	private DefaultListModel<String> setLists( List<Video> videos, Types type )
	{
		switch ( type )
		{
			case TITLE:
				return sortTitles( videos );
				
			case GENRE:
				return sortGenres( videos );
				
			case GROUP:
				return sortGroups( videos );

			case EPISODE:
				return sortEpisodes( videos );

			case SEASON:
				return sortSeasons( videos );

			default:
				return listModel;
		}
		
	}
	
	private DefaultListModel<String> sortTitles( List<Video> videos )
	{
		arrayList = new ArrayList<String>();
		
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

	private DefaultListModel<String> sortGenres( List<Video> videos )
	{
		arrayList = new ArrayList<String>();
		
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
	
	private DefaultListModel<String> sortGroups( List<Video> videos )
	{
		arrayList = new ArrayList<String>();
		
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
	
	private DefaultListModel<String> sortEpisodes( List<Video> videos )
	{
		arrayList = new ArrayList<String>();
		
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
	
	private DefaultListModel<String> sortSeasons( List<Video> videos )
	{
		arrayList = new ArrayList<String>();
		
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
	


	private void updateLists(JList searchCat, String finalsValue, Types type) 
	{
		if ( searchCat.getSelectedIndex() > 0 )
		{
			VideoProvider controller = new VideoProvider( Repos.XML );

			try 
			{
				List<Video> videoList = controller.returnVideos( searchCat.getSelectedValue().toString(), finalsValue );

				setLists( videoList, type );
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	private void updateAll() 
	{
		VideoProvider controller = new VideoProvider( Repos.XML );
		try 
		{
			List<Video> videoList = controller.returnVideos();
			
			setLists(videoList);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * plays the movie after copying it to local directory.
	 * will modify so that it streams it to a player instead as copying can take over 10mins
	 * @param chosen 
	 * @throws Exception 
	 * 
	 */
	private void playAction ( String chosen )
	{
		
		VideoProvider video = new VideoProvider( Repos.XML );
		
		try
		{
			List<Video> videos = video.returnVideos( chosen, VideoProvider.TITLE );

			File copyFrom =  new File( videos.get(0).getLocation() );
			
			File copyTo = new File( "/Users/Alex/Desktop/" + copyFrom.getName() );
		
			btnPlay.setEnabled( false );
			progressBar.setStringPainted( true );
			task = new Task();

			task.source = copyFrom;
			task.dest = copyTo;
	        
			task.addPropertyChangeListener( this );
	        task.execute();
	        
		}
		catch ( Exception e )
		{
			System.err.println( "could not get video" );
		}
	}


}
