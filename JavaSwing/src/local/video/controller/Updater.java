package local.video.controller;

import java.util.List;

import javax.swing.JList;
import javax.swing.SwingUtilities;

import local.dto.Video;
import local.dto.VideoProvider;
import local.models.top.Repos;
import local.video.app.VideoMainFrame;
import local.video.constants.Types;
import local.video.model.ListWorker;

@SuppressWarnings( "rawtypes" )
public class Updater {
	
	public ListWorker typeSelector ( Types type )
	{
		ListWorker resetLists = new ListWorker();
		resetLists.chooseType( type );
		return resetLists;
	}

	public ListWorker updateLists( final JList searchCat, final String finalsValue ) 
	{
		ListWorker resetLists = new ListWorker();
		if ( searchCat.getSelectedIndex() > 0 )
		{
			VideoProvider controller = new VideoProvider( Repos.XML );
			try 
			{
				List<Video> videoList = controller.returnVideos( searchCat.getSelectedValue().toString(), finalsValue );
				resetLists = new ListWorker( videoList );
			} catch ( Exception e1 ) {
				e1.printStackTrace();
			}
		}
		return resetLists;
	}
	
	public ListWorker updateAll() 
	{
		ListWorker resetLists = new ListWorker();
		VideoProvider controller = new VideoProvider( Repos.XML );
		try 
		{
			List<Video> videoList = controller.returnVideos();
			resetLists = new ListWorker( videoList );
		} catch ( Exception e1 ) {
			e1.printStackTrace();
		}
		return resetLists;
	}

}
