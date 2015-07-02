package local.video.app;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import local.dto.Video;
import local.dto.VideoProvider;
import local.models.top.CoreData;
import local.models.top.Repos;
import local.models.top.Types;
import local.video.model.ItemToUpdate;
import local.video.model.Pair;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

@SuppressWarnings( { "rawtypes", "unchecked" } )
public class VideoMainFrame
{

	private JFrame frame;
	private JButton btnPlay, btnRefresh, btnUpdateShow, btnRefreshDatabase;
	private JLabel lblTitle, lblGenre, lblGroup, lblSeriesNumber, lblSeasonNumber, lblCopying;
	private JList videos, genres, groups, episode_numbers, season_numbers;
	private JCheckBox movieCB, tvCB;
	private DefaultListModel<String> titleList, genreList, groupList;
	private DefaultListModel<Integer> episodeList, seasonList;
	private ArrayList<String> locationList, titleArray, genresArray, groupsArray;
	private ArrayList<Integer> episodesArray, seasonArray, idArray;
	private Task task;
	private List<Video> videoList, updateList;
	private Color fcolour, bcolour;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				CoreData loadUserData = new CoreData();
				loadUserData.loadUser();
				try {
					VideoMainFrame window = new VideoMainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VideoMainFrame() {
		VideoProvider controller = new VideoProvider( Repos.XML );
		try 
		{
			titleList = new DefaultListModel<String>();
			genreList = new DefaultListModel<String>();
			groupList = new DefaultListModel<String>();
			episodeList = new DefaultListModel<Integer>();
			seasonList = new DefaultListModel<Integer>();
			locationList = new ArrayList<String>();
			titleArray = new ArrayList<String>();
			genresArray = new ArrayList<String>();
			groupsArray = new ArrayList<String>();
			episodesArray = new ArrayList<Integer>();
			seasonArray = new ArrayList<Integer>();
			idArray = new ArrayList<Integer>();
			this.videoList = controller.returnVideos();
			updateAll();
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Initialize the contents of the frame.
	 * @param videoList 
	 */
//	@SuppressWarnings ( "unchecked" )
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(150, 0, 1100, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[350px:n,grow][20px][150px:n,grow][20px][150px:n,grow][20px][100px:n:250px,grow]", "[30px][5px][30px][grow][30px][grow][30px]"));
	
/* Labels */
		
		lblTitle = new JLabel( "Select a show" );
		lblTitle.setVerticalAlignment( SwingConstants.TOP );
		lblGenre = new JLabel( "Genre" );
		lblGroup = new JLabel( "Group" );		
		lblSeriesNumber = new JLabel( "Episode Number" );
		lblSeasonNumber = new JLabel( "Season Number" );
		lblCopying = new JLabel( "Copying Video" );
		fcolour = lblCopying.getForeground();
		bcolour = lblCopying.getBackground();
		lblCopying.setForeground( bcolour );

/* Buttons */
		
		btnPlay = new JButton( "PLAY" );
		btnPlay.setMinimumSize( new Dimension(300,25 ) );
		btnPlay.setEnabled( false );
		btnPlay.setToolTipText( "This will copy to the desktop and then play the movie" );
		btnRefresh = new JButton( "Refresh Lists" );
		btnUpdateShow = new JButton( "Make updates" );
		btnUpdateShow.setEnabled( false );
		btnRefreshDatabase = new JButton( "Refresh database" );
		
/* Lists */

		videos = new JList( titleList );
		genres = new JList( genreList );
		groups = new JList( groupList );
		episode_numbers = new JList( episodeList );
		season_numbers = new JList( seasonList );
		
		videos.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		genres.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		groups.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		episode_numbers.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
	  	season_numbers.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
		
		videos.setLayoutOrientation( JList.VERTICAL );
		genres.setLayoutOrientation( JList.VERTICAL );
		groups.setLayoutOrientation( JList.VERTICAL );
		episode_numbers.setLayoutOrientation( JList.VERTICAL );
	  	season_numbers.setLayoutOrientation( JList.VERTICAL );

		JScrollPane scrollVideoPane = new JScrollPane( videos, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		JScrollPane scrollGenrePane = new JScrollPane( genres, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		JScrollPane scrollGroupPane = new JScrollPane( groups, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		JScrollPane scrollSeriesPane = new JScrollPane( episode_numbers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		JScrollPane scrollSeasonPane = new JScrollPane( season_numbers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		
/* Check Boxes */	
		
		movieCB = new JCheckBox( "Movies" );
		tvCB = new JCheckBox( "TV Shows" );
		
/* Add components to frame */

		frame.getContentPane().add( lblTitle, "cell 0 0,alignx left,aligny bottom" );
		frame.getContentPane().add( lblGenre, "cell 2 2,aligny bottom" );
		frame.getContentPane().add( lblGroup, "cell 4 2,aligny bottom" );
		frame.getContentPane().add( lblSeriesNumber, "cell 6 2,aligny bottom" );
		frame.getContentPane().add( lblSeasonNumber, "cell 6 4,aligny bottom" );
		frame.getContentPane().add( btnPlay, "cell 0 6,alignx left,aligny center" );
		frame.getContentPane().add( btnRefresh, "cell 2 6,alignx left,aligny bottom" );
		frame.getContentPane().add( btnUpdateShow, "cell 4 6,alignx right,aligny bottom" );
		frame.getContentPane().add( btnRefreshDatabase, "cell 6 6,alignx left,aligny bottom" );
		frame.getContentPane().add( scrollVideoPane, "cell 0 2 1 4,grow" );
		frame.getContentPane().add( scrollGenrePane, "cell 2 3 1 3,grow" );
		frame.getContentPane().add( scrollGroupPane, "cell 4 3 1 3,grow" );
		frame.getContentPane().add( scrollSeriesPane, "cell 6 3,grow" );
		frame.getContentPane().add( scrollSeasonPane, "cell 6 5,grow" );
		frame.getContentPane().add( movieCB, "cell 4 0,alignx right,aligny bottom" );
		frame.getContentPane().add( tvCB, "cell 6 0,alignx left,aligny bottom" );
		frame.getContentPane().add( lblCopying, "cell 2 0,growx,aligny center" );
		
/* Actions */

		videos.addListSelectionListener( new ListFilter() );
		genres.addListSelectionListener( new ListFilter() );
		groups.addListSelectionListener( new ListFilter() );
		episode_numbers.addListSelectionListener( new ListFilter() );
		season_numbers.addListSelectionListener( new ListFilter() );
		movieCB.addActionListener( new ActionEventHandler() );
		tvCB.addActionListener( new ActionEventHandler() );

		btnPlay.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) 
			{
				String chosen = videos.getSelectedValue().toString();
				playAction( chosen );
			}
		});

		btnRefresh.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) 
			{
//				System.out.println( "refresh click" );
				movieCB.setSelected( false );
				tvCB.setSelected( false );
				btnPlay.setEnabled( false );
				btnUpdateShow.setEnabled( false );
				updateAll();
			}
		});
		
		btnUpdateShow.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) 
			{
//				System.out.println( "update a show click" );
				if ( videos.getSelectedIndex() >= 0 )
				{
					ItemToUpdate item = new ItemToUpdate();
					int i = videos.getSelectedIndex();
					String[] split;
					genresArray.clear();
					groupsArray.clear();
					episodesArray.clear();
					seasonArray.clear();
					for ( Video v : videoList )
					{
						if ( v.getID() == idArray.get( i ) )
						{
							item.setID( v.getID() );
							item.setTitle( v.getTitle() );
							item.setGenre( v.getGenre() );
							item.setGroup( v.getGroup() );
							item.setEpisode( v.getEpisodeN() );
							item.setSeason( v.getSeasonN() );
							item.setLocation( v.getLocation() );
							item.setType( v.getType() );
						}

						if ( v.getGenre().contains( "," ) )
						{
							split = v.getGenre().split( "," );
							for ( int j = 0; j < split.length; j++ )
							{
								if ( !genresArray.contains( split[j] ) )
								{
									genresArray.add( split[j] );
								}
							}
						}
						else
						{
							if ( !genresArray.contains( v.getGenre() ) )
							{
								genresArray.add( v.getGenre() );
							}
						}
						if ( v.getGroup().contains( "," ) )
						{
							split = v.getGroup().split( "," );
							for ( int j = 0; j < split.length; j++ )
							{
								if ( !groupsArray.contains( split[j] ) )
								{
									groupsArray.add( split[j] );
								}
							}
						}
						else
						{
							if ( !groupsArray.contains( v.getGroup() ) )
							{
								groupsArray.add( v.getGroup() );
							}
						}
						if ( !episodesArray.contains( v.getEpisodeN() ) )
						{
							episodesArray.add( v.getEpisodeN() );
						}
						if ( !seasonArray.contains( v.getSeasonN() ) )
						{
							seasonArray.add( v.getSeasonN() );				
						}
					}
					sortString( genresArray );
					sortString( groupsArray );
					sortInteger( episodesArray );
					sortInteger( seasonArray );
					item.setGenreList( genresArray );
					item.setGroupList( groupsArray );
					item.setEpisodeList( episodesArray );
					item.setSeasonList( seasonArray );

					EditorWindow editor = new EditorWindow( item );
					editor.setLocationRelativeTo( frame );
					editor.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
					editor.setModalityType( Dialog.ModalityType.APPLICATION_MODAL );
					editor.setVisible( true );
				}
			}
		});

		btnRefreshDatabase.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) 
			{
				System.out.println( "refresh DB click" );
				movieCB.setSelected( false );
				tvCB.setSelected( false );
				btnPlay.setEnabled( false );
				btnUpdateShow.setEnabled( false );
				VideoProvider controller = new VideoProvider( Repos.MYSQL );
				try
				{
					List<Video> data = controller.returnVideos();
					VideoProvider writer = new VideoProvider( Repos.XML );
					writer.writeVideos( data );
				}
				catch ( Exception ex )
				{
					ex.printStackTrace();
				}
				updateAll();
			}
		});
	}
	
	private void updateAll() 
	{
//		System.out.println( "new full list" );
		setLists( videoList, 0 );
	}

	private void updateLists()
	{
		if ( videos.getSelectedIndex() >= 0 )
		{
			btnPlay.setEnabled( true );
			btnUpdateShow.setEnabled( true );
			return;
		}
		
		updateList = new ArrayList<Video>();
		List<Pair> pair = new ArrayList<Pair>();

		if ( ( tvCB.isSelected() && movieCB.isSelected() ) || ( !tvCB.isSelected() && !movieCB.isSelected() ) )
		{
			Pair m = new Pair( Types.TYPE, "movie");
			Pair t = new Pair( Types.TYPE, "tv" );
			if ( pair.contains( m ) )
					pair.remove( m );
			if ( pair.contains( t ) )
				pair.remove( t );
		}

		if ( !tvCB.isSelected() && movieCB.isSelected() )
			pair.add( new Pair( Types.TYPE, "movie" ) );

		if ( tvCB.isSelected() && !movieCB.isSelected() )
			pair.add( new Pair( Types.TYPE, "tv" ) );

		if ( genres.getSelectedIndex() >= 0 )				
			pair.add( new Pair( Types.GENRE, genres.getSelectedValue().toString() ) );
		
		if ( groups.getSelectedIndex() >= 0 )
			pair.add( new Pair( Types.GROUP, groups.getSelectedValue().toString() ) );

		if ( episode_numbers.getSelectedIndex() >= 0 )
			pair.add( new Pair( Types.EPISODE, episode_numbers.getSelectedValue().toString() ) );

		if ( season_numbers.getSelectedIndex() >= 0 )
			pair.add( new Pair( Types.SEASON, season_numbers.getSelectedValue().toString() ) );
		
		createTmpList( videoList, pair, pair.size() );
		setLists( updateList, 1 );
	}
	
	private void createTmpList ( List<Video> videos, List<Pair> pairs, int run )
	{
		if ( run < 1 )
		{
			updateList = videos;
			return;
		}
		run--;
		List<Video> tmpList = new ArrayList<Video>();
		Types type = pairs.get( run ).getType();
		String name = pairs.get( run ).getName();
		for ( Video video : videos )
		{		
			switch ( type )
			{
				case TYPE:
					if( video.getType().equals( name ) )
					{
						tmpList.add( createTempVideoList( video ) );
					}
					break;

				case GENRE:
					if( video.getGenre().contains( name ) )
					{
						tmpList.add( createTempVideoList( video ) );
					}
					break;

				case GROUP:
					if( video.getGroup().contains( name ) )
					{
						tmpList.add( createTempVideoList( video ) );
					}
					break;

				case EPISODE:
					if( video.getEpisodeN() == Integer.parseInt( name ) )
					{
						tmpList.add( createTempVideoList( video ) );
					}
					break;

				case SEASON:
					if( video.getSeasonN() == Integer.parseInt( name ) )
					{
						tmpList.add( createTempVideoList( video ) );
					}
					break;

				default:
					break;
			}
		}
		createTmpList( tmpList, pairs, run );
	}
		
	private void setLists( List<Video> videos, int n  )
	{
		String[] split;
		titleList.clear();
		titleArray.clear();
		idArray.clear();
		locationList.clear();
		genresArray.clear();
		groupsArray.clear();
		episodesArray.clear();
		seasonArray.clear();
		for( Video video : videos )
		{
			idArray.add( video.getID() );
			titleArray.add( video.getTitle() );
			locationList.add( video.getLocation() );

			// checks for videos with multiple tags
			if ( video.getGenre().contains( "," ) )
			{
				split = video.getGenre().split( "," );
				for ( int i = 0; i < split.length; i++ )
				{
					if ( !genresArray.contains( split[i] ) )
					{
						genresArray.add( split[i] );
					}
				}
			}
			else
			{
				if ( !genresArray.contains( video.getGenre() ) )
				{
					genresArray.add( video.getGenre() );
				}
			}

			// checks for videos with multiple tags
			if ( video.getGroup().contains( "," ) )
			{
				split = video.getGroup().split( "," );
				for ( int i = 0; i < split.length; i++ )
				{
					if ( !groupsArray.contains( split[i] ) )
					{
						groupsArray.add( split[i] );
					}
				}
			}
			else
			{
				if ( !groupsArray.contains( video.getGroup() ) )
				{
					groupsArray.add( video.getGroup() );
				}
			}
			
			if ( !episodesArray.contains( video.getEpisodeN() ) && video.getEpisodeN() != 0 )
			{
				episodesArray.add( video.getEpisodeN() );
			}
			
			if ( !seasonArray.contains( video.getSeasonN() ) && video.getSeasonN() != 0  )
			{
				seasonArray.add( video.getSeasonN() );				
			}
		}
		
		switch ( n )
		{
			case 1:
				if ( genres.getSelectedValue() == null )
				{
					genreList.clear();
					Collections.sort( genresArray );
					for (String s : genresArray ) 
					{
						genreList.addElement( s );
					}
				}
				if ( groups.getSelectedValue() == null )
				{
					groupList.clear();
					Collections.sort( groupsArray );
					for (String s : groupsArray ) 
					{
						groupList.addElement( s );
					}
				}
				if ( episode_numbers.getSelectedValue() == null )
				{
					episodeList.clear();
					Collections.sort( episodesArray );
					for (Integer i : episodesArray ) 
					{
						episodeList.addElement( i );
					}
				}
				if ( season_numbers.getSelectedValue() == null )
				{
					seasonList.clear();
					Collections.sort( seasonArray );
					for (Integer i : seasonArray ) 
					{
						seasonList.addElement( i );
					}
				}
				break;
				
			default:
				genreList.clear();
				sortString( genresArray );
				for (String s : genresArray ) 
				{
					genreList.addElement( s );
				}
				groupList.clear();
				sortString( groupsArray );
				for (String s : groupsArray ) 
				{
					groupList.addElement( s );
				}
				episodeList.clear();
				sortInteger( episodesArray );
				for (Integer i : episodesArray ) 
				{
					episodeList.addElement( i );
				}
				seasonList.clear();
				sortInteger( seasonArray );
				for (Integer i : seasonArray ) 
				{
					seasonList.addElement( i );
				}
				break;
		}

		for ( String s : titleArray )
		{
			titleList.addElement( s );
		}
	}

	private Video createTempVideoList( Video video )
	{
		Video v = new Video();
		v.setID( video.getID() );
		v.setType( video.getType() );
		v.setLocation( video.getLocation() );
		v.setGenre( video.getGenre() );
		v.setGroup( video.getGroup() );
		v.setEpisodeN( video.getEpisodeN() );
		v.setSeasonN( video.getSeasonN() );
		v.setTitle( video.getTitle() );
		return v;
	}
	
	private void sortString(List<String> list )
	{
		Collections.sort( list );
		if ( list.get( 0 ).equals( "" ) )
		{
			list.remove( 0 );
		}
	}
	
	private void sortInteger(List<Integer> list )
	{
		Collections.sort( list );
		if ( list.get( 0 ).equals( "" ) )
		{
			list.remove( 0 );
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
		try
		{
			String copyFrom =  videoList.get( 0 ).getLocation();
			String copyTo;
			Pattern p = Pattern.compile( "(?:.*\\/)(.*\\.\\w{3,4}$)" );
			Matcher m = p.matcher( copyFrom );
			if ( m.find() )
			{
				copyTo = "/Users/Alex/Desktop/" + m.group( 1 );
			}
			else
			{
				copyTo = "/Users/Alex/Desktop/";
			}
					
			btnPlay.setEnabled( false );
			lblCopying.setForeground( fcolour );
			task = new Task();
			task.source = copyFrom;
			task.dest = copyTo;
	        task.execute();
		}
		catch ( Exception e )
		{
			System.err.println( "could not get video" );
		}
	}

	private class ListFilter implements ListSelectionListener
	{
		
		public void valueChanged( ListSelectionEvent e )
		{
			if ( e.getValueIsAdjusting() )
				return;
			
			updateLists();
		}	
	}
	
	private class ActionEventHandler implements ActionListener
	{
		public void actionPerformed( ActionEvent e )
		{
			updateLists();
		}
	}
	
	class Task extends SwingWorker<Void, Void>
	{
		public String source;
		public String dest;
		public boolean check;
		
		@Override
		protected Void doInBackground() throws Exception
		{
			FTPClient ftp = new FTPClient();
			FTPClientConfig config = new FTPClientConfig();
			ftp.configure( config );
			try 
			{
				int reply;
				ftp.connect( "192.168.1.48" );
				ftp.login( "alex", "married" );
				System.out.println( "Connected" );
				System.out.print( ftp.getReplyString() );
				
//				ftp.rename( source, dest );
				
				// After connection attempt, you should check the reply code to verify
				// success.
				reply = ftp.getReplyCode();

				if( !FTPReply.isPositiveCompletion( reply ) )
				{
					ftp.disconnect();
					System.err.println( "FTP server refused connection." );
					System.exit(1);
				}
				// transfer files
				ftp.logout();
			} 
			catch( IOException e )
			{
				e.printStackTrace();
			} 
			finally 
			{
				if( ftp.isConnected() )
				{
					try 
					{
						ftp.disconnect();
					} 
					catch( IOException ioe )
					{
						// do nothing
					}
				}
			}
            return null;
		}

		/*
         * Executed in event dispatching thread
         */
        @Override
        public void done() 
        {
//        	progressBar.setStringPainted( false );
            btnPlay.setEnabled( true );
            btnPlay.setText( "PLAY" );
            lblCopying.setForeground( bcolour );
            setProgress( 0 );
            
            if ( Desktop.isDesktopSupported() ) 
            {
    			try 
    			{
    		        Desktop.getDesktop().open( new File( this.dest ) );
    		    }
    			catch ( IOException ex )
    			{
    				JOptionPane.showMessageDialog( frame,
    					    "Error opening video",
    					    "Error",
    					    JOptionPane.ERROR_MESSAGE );
    		    	System.err.println( "unable to open video" );
    		    }
    		}
        }
	}
}
