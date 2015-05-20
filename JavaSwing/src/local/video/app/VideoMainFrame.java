package local.video.app;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import local.dto.Video;
import local.dto.VideoProvider;
import local.models.top.Finals;
import local.models.top.Repos;
import local.video.constants.ListStore;
import local.video.constants.Types;
import local.video.model.ListWorker;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings( { "rawtypes", "unchecked" } )
public class VideoMainFrame implements PropertyChangeListener {

	private JFrame frame;
	private JButton btnPlay, btnRefresh, btnUpdateShow, btnRefreshDatabase;
	private JLabel lblTitle, lblGenre, lblGroup, lblSeriesNumber, lblSeasonNumber;
	private JList videos, genres, groups, episode_numbers, season_numbers;
	private JCheckBox movieCB, tvCB;
	private JProgressBar progressBar;
	private DefaultListModel<String> titleList, genreList, groupList;
	private DefaultListModel<Integer> episodeList, seasonList;
	private ArrayList<String> locationList, genresArray, groupsArray;
	private ArrayList<Integer> episodesArray, seasonArray, idArray;
	private Task task;
	private List<Video> videoList;
	
	
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
		frame.setBounds(150, 50, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[400px:n,grow][20px][250px:n,grow][20px][150px:n,grow][20px][100px:n:250px,grow]", "[30px][5px][30px][grow][30px][grow][30px]"));
		
		try 
		{
	        frame.setIconImage(ImageIO.read(new File("files/icon.png")));
	    }
		catch (IOException e) 
		{
	     System.out.println( "failed to load icon" );
	    }
	
/* Labels */
		
		lblTitle = new JLabel("Select a show");
		lblTitle.setVerticalAlignment(SwingConstants.TOP);
		lblGenre = new JLabel("Genre");
		lblGroup = new JLabel("Group");		
		lblSeriesNumber = new JLabel("Episode Number");
		lblSeasonNumber = new JLabel("Season Number");

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
		videos.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		videos.setLayoutOrientation( JList.VERTICAL );
		JScrollPane scrollVideoPane = new JScrollPane( videos, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		
		genres = new JList( genreList );
		genres.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		genres.setLayoutOrientation( JList.VERTICAL );
		JScrollPane scrollGenrePane = new JScrollPane( genres, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		
		groups = new JList( groupList );
		groups.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		groups.setLayoutOrientation( JList.VERTICAL );
		JScrollPane scrollGroupPane = new JScrollPane( groups, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		
		episode_numbers = new JList( episodeList );
		episode_numbers.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
		episode_numbers.setLayoutOrientation( JList.VERTICAL );
		JScrollPane scrollSeriesPane = new JScrollPane( episode_numbers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
				
		season_numbers = new JList( seasonList );
	  	season_numbers.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
	  	season_numbers.setLayoutOrientation( JList.VERTICAL );
		JScrollPane scrollSeasonPane = new JScrollPane( season_numbers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		
/* Check Boxes */	
		
		movieCB = new JCheckBox( "Movies" );
		tvCB = new JCheckBox( "TV Shows" );
		
/* Progress Bar */
		
		progressBar = new JProgressBar();
		
		
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
		frame.getContentPane().add( progressBar, "cell 2 0,growx,aligny center" );
		
		
/* Actions */

		videos.addListSelectionListener( new ListSelectionListener() {
			public void valueChanged( ListSelectionEvent e ) 
			{
				System.out.println( "video click" );
				if ( videos.getSelectedIndex() > 0 )
				{
					btnPlay.setEnabled( true );
				}
				else
				{
					btnPlay.setEnabled( false );
				}
			}
		});

		genres.addListSelectionListener( new ListSelectionListener() {
			public void valueChanged( ListSelectionEvent e) 
			{
				System.out.println( "genre click" );
				if ( genres.getSelectedIndex() > 0 )
				{
					updateLists( genres.getSelectedValue().toString(), Finals.GENRE );
				}
			}
		});

		groups.addListSelectionListener( new ListSelectionListener() {
			public void valueChanged( ListSelectionEvent e ) 
			{
				System.out.println( "group click" );
				if ( groups.getSelectedIndex() > 0 )
				{
					updateLists( groups.getSelectedValue().toString(), Finals.GROUP );
				}
			}
		});

		episode_numbers.addListSelectionListener( new ListSelectionListener() {
			public void valueChanged( ListSelectionEvent e ) 
			{
				System.out.println( "episode click" );
				if ( episode_numbers.getSelectedIndex() > 0 )
				{
					updateLists( episode_numbers.getSelectedValue().toString(), Finals.SERIES_N );
				}
			}
		});

		season_numbers.addListSelectionListener( new ListSelectionListener() {
			public void valueChanged( ListSelectionEvent e ) 
			{
				System.out.println( "season click" );
				if ( season_numbers.getSelectedIndex() > 0 )
				{
					updateLists( season_numbers.getSelectedValue().toString(), Finals.SEASON_N );
				}
			}

		});

		movieCB.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) 
			{
				System.out.println( "movie click" );
				typeSelector();
			}
		});

		tvCB.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) 
			{
				System.out.println( "tv click" );
				typeSelector();
			}

		});

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
				updateAll();
			}

		});
		
		btnUpdateShow.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) 
			{
				System.out.println( "update a show click" );
				if ( videos.getSelectedIndex() > 0 || genres.getSelectedIndex() > 0 || groups.getSelectedIndex() > 0 )
				{
					UpdateWindow update = UpdateWindow( idArray ); //launch update window with with idArray
				}
			}
		});

		btnRefreshDatabase.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) 
			{
				System.out.println( "refresh DB click" );
				movieCB.setSelected( false );
				tvCB.setSelected( false );
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
	
	
	private void setLists ( List<Video> videos, int n )
	{
		ArrayList<Integer> tmpArray = new ArrayList<Integer>();
		
		for ( int id : idArray )
		{
			tmpArray.add( id );
		}
		
		titleList.clear();
		genreList.clear();
		groupList.clear();
		episodeList.clear();
		seasonList.clear();
		locationList.clear();
		genresArray.clear();
		groupsArray.clear();
		episodesArray.clear();
		seasonArray.clear();
		idArray.clear();
		
		switch ( n )
		{
			case 0:
				for( Video video : videos )
				{
					idArray.add( video.getID() );
					titleList.addElement( video.getTitle() );
					locationList.add( video.getLocation() );
					
					if ( !genresArray.contains( video.getGenre() ) )
					{
						genresArray.add( video.getGenre() );
					}
					if ( !groupsArray.contains( video.getGroup() ) )
					{
						groupsArray.add( video.getGroup() );
					}
					if ( !episodesArray.contains( video.getSeriesN() ) )
					{
						episodesArray.add( video.getSeriesN() );
					}
					if ( !seasonArray.contains( video.getSeasonN() ) )
					{
						seasonArray.add( video.getSeasonN() );				
					}
				}
				break;
				
			default:
				for( Video video : videos )
				{
					if ( tmpArray.contains( video.getID() ) )
					{
						idArray.add( video.getID() );
						titleList.addElement( video.getTitle() );
						locationList.add( video.getLocation() );
						
						if ( !genresArray.contains( video.getGenre() ) )
						{
							genresArray.add( video.getGenre() );
						}
						if ( !groupsArray.contains( video.getGroup() ) )
						{
							groupsArray.add( video.getGroup() );
						}
						if ( !episodesArray.contains( video.getSeriesN() ) )
						{
							episodesArray.add( video.getSeriesN() );
						}
						if ( !seasonArray.contains( video.getSeasonN() ) )
						{
							seasonArray.add( video.getSeasonN() );				
						}
					}
				}
				break;
		}
			
		Collections.sort( genresArray );
		for (String s : genresArray ) 
		{
			genreList.addElement( s );
		}
		Collections.sort( groupsArray );
		for (String s : groupsArray ) 
		{
			groupList.addElement( s );
		}
		Collections.sort( episodesArray );
		for (Integer i : episodesArray ) 
		{
			episodeList.addElement( i );
		}
		Collections.sort( seasonArray );
		for (Integer i : seasonArray ) 
		{
			seasonList.addElement( i );
		}
	}

	private void updateLists( String searchCat, String finalsValue ) 
	{
		VideoProvider controller = new VideoProvider( Repos.XML );
		try 
		{
			System.out.println( "new edit list" );
			setLists( controller.returnVideos( searchCat, finalsValue ), 1 );
		} catch ( Exception e1 ) {
			e1.printStackTrace();
		}
	}
	
	private void updateAll() 
	{
		VideoProvider controller = new VideoProvider( Repos.XML );
		try 
		{
			System.out.println( "new full list" );
			setLists( controller.returnVideos(), 0 );
		} catch ( Exception e1 ) {
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
			videoList = video.returnVideos( chosen, VideoProvider.TITLE );

			File copyFrom =  new File( videoList.get( 0 ).getLocation() );
			
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

	
	/**
	 * class for progress bar updating when copying files from network drive to local.
	 * @author Alex
	 *
	 */
	class Task extends SwingWorker<Void, Void>
	{

		public File source;
		public File dest;
		public boolean check;
		
		@Override
		protected Void doInBackground() throws Exception
		{
			this.check = false;
			InputStream is = null;
		    OutputStream os = null;

		    btnPlay.setText( "Copying file" );
			progressBar.setString( "Copying file" );

			int progress = 0;
			int length;
	        int fileSize = ( int ) source.length();
	        int bufferSize = 1024;
	        byte[] buffer = new byte[ bufferSize ];

			//Initialize progress property.
			setProgress(0);
            

		    try {
		    	
		        is = new FileInputStream( this.source );
		        os = new FileOutputStream( this.dest );
		        
		        while (( length = is.read( buffer ) ) > 0) {
		            os.write( buffer, 0, length );
		            progress += bufferSize;
		            double percent = ( ( double ) progress / fileSize ) * 100;
		            if ( ( int ) percent % 5 == 0 )
		            {
		            	setProgress( Math.min( ( int ) percent , 100 ) );
		            }
		        }
		    } finally {
		        is.close();
		        os.close();
		        this.check = true;
		    }

            return null;
		}

		/*
         * Executed in event dispatching thread
         */
        @Override
        public void done() 
        {
        	progressBar.setStringPainted( false );
            btnPlay.setEnabled( true );
            btnPlay.setText( "PLAY" );
            setProgress( 0 );
            
            if (Desktop.isDesktopSupported()) {
    			
    			try 
    			{
    		        Desktop.getDesktop().open( this.dest );
    		    }
    			catch (IOException ex)
    			{
    				JOptionPane.showMessageDialog(frame,
    					    "Error opening video",
    					    "Error",
    					    JOptionPane.ERROR_MESSAGE);
    		    	System.err.println( "unable to open video" );
    		    }
    		}
            
        }
	}
		
	/**
     * Invoked when Task's progress property changes.
     */
    public void propertyChange( PropertyChangeEvent evt ) {
        if ( "progress" == evt.getPropertyName() ) {
            int progress = ( Integer ) evt.getNewValue();
            progressBar.setValue( progress );
        } 
    }
    
	private void typeSelector()
	{
		System.out.println( "type selector called");
		if ( ( tvCB.isSelected() && movieCB.isSelected() ) || ( !tvCB.isSelected() && !movieCB.isSelected() ) )
		{
			chooseType( Types.ALL );
		}
		else if ( !tvCB.isSelected() && movieCB.isSelected() )
		{
			chooseType( Types.MOVIE );
		}
		else if ( tvCB.isSelected() && !movieCB.isSelected() )
		{
			chooseType( Types.TV );
		}
		else
		{
			chooseType( Types.ALL );
		}
	}
	
	private void chooseType( Types type )
	{
		System.out.println( "choose type called");
		// xml is not returning a list for tv or movie selection.
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

    	
	private void errorPopup ( String file )
	{
		JOptionPane.showMessageDialog(frame,
			    "Error moving the file" + file + ".",
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
	}

	
}
