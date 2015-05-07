package local.video.controller;

import java.util.List;

import javax.swing.JList;

import local.dto.Video;
import local.dto.VideoProvider;
import local.models.top.Repos;
import local.video.app.VideoMainFrame;
import local.video.constants.Types;
import local.video.model.ListWorker;

@SuppressWarnings( "rawtypes" )
public class Updater {

	public static void typeSelector ( Types type )
	{
		List<Video> videoList = ListWorker.chooseType( type );
		ListWorker resetLists = new ListWorker( videoList );
		
		VideoMainFrame.titleList = resetLists.getTitles();
		VideoMainFrame.genreList = resetLists.getGenres();
		VideoMainFrame.groupList = resetLists.getGroups();
		VideoMainFrame.episodeList = resetLists.getEpisodes();
		VideoMainFrame.seasonList = resetLists.getSeasons();
		VideoMainFrame.locationList = resetLists.getLocations();
	}

	public static void updateLists( JList searchCat, String finalsValue ) 
	{
		if ( searchCat.getSelectedIndex() > 0 )
		{
			VideoProvider controller = new VideoProvider( Repos.XML );

			try 
			{
				List<Video> videoList = controller.returnVideos( searchCat.getSelectedValue().toString(), finalsValue );
				ListWorker resetLists = new ListWorker( videoList );
								
				VideoMainFrame.titleList = resetLists.getTitles();
				VideoMainFrame.genreList = resetLists.getGenres();
				VideoMainFrame.groupList = resetLists.getGroups();
				VideoMainFrame.episodeList = resetLists.getEpisodes();
				VideoMainFrame.seasonList = resetLists.getSeasons();
				VideoMainFrame.locationList = resetLists.getLocations();
				
			} catch ( Exception e1 ) {
				e1.printStackTrace();
			}
			
		}
	}

	public static void updateAll() 
	{
		VideoProvider controller = new VideoProvider( Repos.XML );
		
		try 
		{
			List<Video> videoList = controller.returnVideos();
			ListWorker resetLists = new ListWorker( videoList );
			
			VideoMainFrame.titleList = resetLists.getTitles();
			VideoMainFrame.genreList = resetLists.getGenres();
			VideoMainFrame.groupList = resetLists.getGroups();
			VideoMainFrame.episodeList = resetLists.getEpisodes();
			VideoMainFrame.seasonList = resetLists.getSeasons();
			VideoMainFrame.locationList = resetLists.getLocations();
			
		} catch ( Exception e1 ) {
			e1.printStackTrace();
		}
	}

}
