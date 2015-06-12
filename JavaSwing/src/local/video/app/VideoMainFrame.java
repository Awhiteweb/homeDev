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
import local.models.top.Repos;
import local.video.constants.Types;
import local.video.model.ItemToUpdate;
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
	private List<Video> videoList;
	private List<List<Video>> blockbuster = new ArrayList<List<Video>>();
	private int selections;
	private Color fcolour, bcolour;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
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
		
		try 
		{
	        frame.setIconImage(ImageIO.read(new File("files/icon.png")));
	    }
		catch (IOException e) 
		{
			System.out.println( "failed to load icon" );
	    }
	
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

		videos.addListSelectionListener( new ListFilter( Types.ALL ) );
		genres.addListSelectionListener( new ListFilter( Types.GENRE ) );
		groups.addListSelectionListener( new ListFilter( Types.GROUP ) );
		episode_numbers.addListSelectionListener( new ListFilter( Types.EPISODE ) );
		season_numbers.addListSelectionListener( new ListFilter( Types.SEASON ) );
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
				System.out.println( "refresh click" );
				VideoProvider controller = new VideoProvider( Repos.XML );
				videoList = controller.returnVideos();
				updateAll();
			}
		});
		
		btnUpdateShow.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) 
			{
				System.out.println( "update a show click" );
				if ( videos.getSelectedIndex() >= 0 )
				{
					ItemToUpdate item = new ItemToUpdate();
					int i = videos.getSelectedIndex();
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

						if ( !genresArray.contains( v.getGenre() ) )
						{
							genresArray.add( v.getGenre() );
						}
						if ( !groupsArray.contains( v.getGroup() ))
						{
							groupsArray.add( v.getGroup() );
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
				System.out.println( "\nrefresh DB click" );
				movieCB.setSelected( false );
				tvCB.setSelected( false );
				VideoProvider controller = new VideoProvider( Repos.MYSQL );
				try
				{
					List<Video> data = controller.returnVideos();
					VideoProvider writer = new VideoProvider( Repos.XML );
					writer.writeVideos( data );
					videoList = data;
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
		blockbuster.clear();
		blockbuster.add( 0, videoList );
		selections = 0;
		System.out.println( "new full list" );
		setLists( videoList, 0 );
	}

	private void typeSelector()
	{
		System.out.println( "type selector called");
		if ( ( tvCB.isSelected() && movieCB.isSelected() ) || ( !tvCB.isSelected() && !movieCB.isSelected() ) )
		{
			updateAll();
		}
		else if ( !tvCB.isSelected() && movieCB.isSelected() )
		{
			updateLists( "movie" , Types.TYPE );
		}
		else if ( tvCB.isSelected() && !movieCB.isSelected() )
		{
			updateLists( "tv" , Types.TYPE );
		}
		else
		{
			updateAll();
		}
	}
	
	// TODO update for better searching, make searchCat a String array and iterate over each one for video
	private void updateLists( String searchCat, Types type )
	{
		List<Video> getter = blockbuster.get( selections );
		List<Video> tmpList = new ArrayList<Video>();
		switch ( type )
		{
			case TYPE:
				for ( Video video : getter )
				{
					if( video.getType().equals( searchCat ) )
					{
						tmpList.add( createTempVideoList( video ) );
					}
				}
				break;

			case GENRE:
				for ( Video video : getter )
				{
					if( video.getGenre().equals( searchCat ) )
					{
						tmpList.add( createTempVideoList( video ) );
					}
				}
				break;

			case GROUP:
				for ( Video video : getter )
				{
					if( video.getGroup().equals( searchCat ) )
					{
						tmpList.add( createTempVideoList( video ) );
					}
				}
				break;

			case EPISODE:
				for ( Video video : getter )
				{
					if( video.getEpisodeN() == Integer.parseInt( searchCat ) )
					{
						tmpList.add( createTempVideoList( video ) );
					}
				}
				break;

			case SEASON:
				for ( Video video : getter )
				{
					if( video.getSeasonN() == Integer.parseInt( searchCat ) )
					{
						tmpList.add( createTempVideoList( video ) );
					}
				}
				break;

			default:
				break;
		}
		
		selections++;
		blockbuster.add( selections, tmpList );
		int bSize = blockbuster.size() - 1;
		setLists( blockbuster.get( bSize ), 1 );
	}
	
	private void setLists( List<Video> videos, int n  )
	{
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
			
			if ( !genresArray.contains( video.getGenre() ) )
			{
				genresArray.add( video.getGenre() );
			}
			if ( !groupsArray.contains( video.getGroup() ) )
			{
				groupsArray.add( video.getGroup() );
			}
			if ( !episodesArray.contains( video.getEpisodeN() ) )
			{
				episodesArray.add( video.getEpisodeN() );
			}
			if ( !seasonArray.contains( video.getSeasonN() ) )
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

	private void clearVideoSelection()
	{
		if ( videos.getSelectedIndex() >= 0 )
			videos.clearSelection();
	}
	
	private class ListFilter implements ListSelectionListener
	{
		private Types type;

		
		ListFilter( Types type )
		{
			this.type = type;
		}
		
		public void valueChanged( ListSelectionEvent e )
		{
			if ( e.getValueIsAdjusting() )
				return;
			
			switch ( type )
			{
				case ALL:
//					System.out.println( "Videos" );
//					System.out.println( "selected video: " + videos.getSelectedIndex() );
					if ( videos.getSelectedIndex() >= 0 )
					{
						btnPlay.setEnabled( true );
						btnUpdateShow.setEnabled( true );					
					}
					else
					{
						btnPlay.setEnabled( false );
						btnUpdateShow.setEnabled( false );
					}
					break;
				
				case GENRE:
					clearVideoSelection();
//					System.out.println( "Genre" );
//					System.out.println( genres.getSelectedIndex() );
					if ( genres.getSelectedIndex() >= 0 )
						updateLists( genres.getSelectedValue().toString(), Types.GENRE );
					break;
					
				case GROUP:
					clearVideoSelection();
//					System.out.println( "Group" );
//					System.out.println( groups.getSelectedIndex() );
					if ( groups.getSelectedIndex() >= 0 )
						updateLists( groups.getSelectedValue().toString(), Types.GROUP );
					break;
					
				case EPISODE:
					clearVideoSelection();
//					System.out.println( "Episode" );
//					System.out.println( episode_numbers.getSelectedIndex() );
					if ( episode_numbers.getSelectedIndex() >= 0 )
						updateLists( episode_numbers.getSelectedValue().toString(), Types.EPISODE );
					break;
					
				case SEASON:
					clearVideoSelection();
//					System.out.println( "Season" );
//					System.out.println( season_numbers.getSelectedIndex() );
					if ( season_numbers.getSelectedIndex() >= 0 )
						updateLists( season_numbers.getSelectedValue().toString(), Types.SEASON );
					break;
					
				default:
					break;
			}
		}	
	}
	
	private class ActionEventHandler implements ActionListener
	{
		public void actionPerformed( ActionEvent e )
		{
			typeSelector();
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
