package local.video.app;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
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
import net.miginfocom.swing.MigLayout;

@SuppressWarnings( "rawtypes" )
public class VideoMainFrame implements PropertyChangeListener {

	private JFrame frame;
	private JButton btnPlay;
	private JButton btnRefresh;
	private JButton btnUpdateShow;
	private JButton btnRefreshDatabase;
	private JLabel lblTitle;
	private JLabel lblGenre;
	private JLabel lblGroup;		
	private JLabel lblSeriesNumber;
	private JLabel lblSeasonNumber;
	private JList videos;
	private JList genres;
	private JList groups;
	private JList series_numbers;
	private JList season_numbers;
	private JCheckBox movieCB;
	private JCheckBox tvCB;
	private JProgressBar progressBar;
	private DefaultListModel<String> titleList = new DefaultListModel<String>();
	private ArrayList<String> locationList = new ArrayList<String>();
	private DefaultListModel<String> genreList = new DefaultListModel<String>();
	private DefaultListModel<String> groupList = new DefaultListModel<String>();
	private DefaultListModel<Integer> seriesList = new DefaultListModel<Integer>();
	private DefaultListModel<Integer> seasonList = new DefaultListModel<Integer>();
	private Task task;
	
	
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
//			controller.getVideos();
		
			List<Video> videoList = controller.returnVideos();
		
			initialize(videoList);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * Initialize the contents of the frame.
	 * @param videoList 
	 */
	@SuppressWarnings ( { "unchecked" } )
	private void initialize( List<Video> videoList ) {
		
		setLists( videoList );		
		
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
		
		btnPlay = new JButton("PLAY");
		btnPlay.setMinimumSize(new Dimension(300,25));
		btnPlay.setEnabled(false);
		btnPlay.setToolTipText("This will copy to the desktop and then play the movie");
		
		btnRefresh = new JButton( "" );
		try ( InputStream is = VideoMainFrame.class.getResourceAsStream("fontawesome-webfont.ttf") )
		{		
			if ( is != null )
			{
				Font font;
				font = Font.createFont(Font.TRUETYPE_FONT, is);
				font = font.deriveFont(Font.PLAIN, 24f);
		
				btnRefresh.setText( "\uf021" );
				btnRefresh.setFont( font );
			}
			else
			{
				btnRefresh.setText( "Refresh Lists" );
			}
		}
		catch (Exception ex)
		{
			System.err.println( "font exception");
			ex.printStackTrace();
		}
		
		
		btnUpdateShow = new JButton("Make updates");
				btnUpdateShow.setEnabled(false);
		
		btnRefreshDatabase = new JButton("Refresh database");
		
/* Lists */

		videos = new JList(titleList);
		videos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		videos.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollVideoPane = new JScrollPane( videos, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		
		genres = new JList( genreList );
		genres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		genres.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollGenrePane = new JScrollPane( genres, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		
		groups = new JList(groupList);
		groups.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		groups.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollGroupPane = new JScrollPane( groups, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		
		series_numbers = new JList(seriesList);
		series_numbers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		series_numbers.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollSeriesPane = new JScrollPane( series_numbers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
				
		season_numbers = new JList(seasonList);
	  	season_numbers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	  	season_numbers.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollSeasonPane = new JScrollPane( season_numbers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		
/* Check Boxes */	
		
		movieCB = new JCheckBox("Movies");
		tvCB = new JCheckBox("TV Shows");
		
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

		videos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
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

		genres.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				updateLists( genres, Finals.GENRE );
				
			}
		});

		groups.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				updateLists( groups, Finals.GROUP );

			}
		});

		series_numbers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				updateLists(series_numbers, Finals.SERIES_N);

			}
		});

		season_numbers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//read xml to match selection and reload all lists with new data
				
				updateLists(season_numbers, Finals.SEASON_N );

			}

		});

		movieCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		tvCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playAction(e);
			}
		});

		btnRefresh.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				updateAll();

			}

		});
		btnUpdateShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnRefreshDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VideoProvider controller = new VideoProvider( Repos.MYSQL );
				try
				{
					List<Video> data = controller.returnVideos();
					
					VideoProvider writer = new VideoProvider( Repos.XML );
					
					writer.writeVideos( data );
					
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
				
				updateAll();
				
			}
		});

	}
	
	
	private void setLists( List<Video> videos )
	{
		ArrayList<String> genres = new ArrayList<String>();
		ArrayList<String> groups = new ArrayList<String>();
		ArrayList<Integer> series = new ArrayList<Integer>();
		ArrayList<Integer> season = new ArrayList<Integer>();

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

			if ( !series.contains( video.getSeriesN() ) )
			{
				series.add( video.getSeriesN() );
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

		Collections.sort( series );
		for (Integer i : series ) {
			seriesList.addElement( i );
		}

		Collections.sort( season );
		for (Integer i : season ) {
			seasonList.addElement( i );
		}
		
	}

	private void updateLists(JList searchCat, String finalsValue) 
	{
		if ( searchCat.getSelectedIndex() > 0 )
		{
			VideoProvider controller = new VideoProvider( Repos.XML );

			try 
			{
				List<Video> videoList = controller.returnVideos( searchCat.getSelectedValue().toString(), finalsValue );
				
				titleList.clear();
				genreList.clear();
				groupList.clear();
				seriesList.clear();
				seasonList.clear();
				locationList.clear();
				
				setLists(videoList);
				
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
			
			titleList.clear();
			genreList.clear();
			groupList.clear();
			seriesList.clear();
			seasonList.clear();
			locationList.clear();
			
			setLists(videoList);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void playAction ( ActionEvent e )
	{
		File copyFrom = new File( /*"C:\\Users\\Alex.White\\Documents\\screen.avi"*/ "/Users/Alex/Desktop/13 Assassins.m4v" );
        File copyTo = new File( /*"C:\\Users\\Alex.White\\Documents\\copy\\screen.avi"*/ "/Users/Alex/Desktop/copy/13 Assassins.m4v" );

		btnPlay.setEnabled( false );
		progressBar.setStringPainted( true );
		task = new Task();
		task.source = copyFrom;
		task.dest = copyTo;
        task.addPropertyChangeListener( this );
        task.execute();
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
		protected Void doInBackground () throws Exception
		{
			this.check = false;
			InputStream is = null;
		    OutputStream os = null;

		    btnPlay.setText( "Copying file" );
			progressBar.setString( "Copying file" );

			int progress = 0;
			int length;
	        int fileSize = (int) source.length();
	        int bufferSize = 1024;
	        byte[] buffer = new byte[ bufferSize ];

			//Initialize progress property.
			setProgress(0);
            

		    try {
		    	
		        is = new FileInputStream(this.source);
		        os = new FileOutputStream(this.dest);
		        
		        while ((length = is.read(buffer)) > 0) {
		            os.write(buffer, 0, length);
		            progress += bufferSize;
		            double percent = ( (double) progress / fileSize ) * 100;
		            if ( (int) percent % 5 == 0 )
		            {
		            	setProgress(Math.min( ( int ) percent , 100));
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
            btnPlay.setEnabled(true);
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
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
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
