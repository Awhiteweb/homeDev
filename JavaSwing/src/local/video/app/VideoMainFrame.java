package local.video.app;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import local.dto.Video;
import local.dto.VideoProvider;
import local.database.xml.XMLVideoRepo;

import javax.swing.JScrollPane;

public class VideoMainFrame {

	private JFrame frame;
	private DefaultListModel<String> titleList = new DefaultListModel<String>();
	private ArrayList<String> locationList = new ArrayList<String>();
	private DefaultListModel<String> genreList = new DefaultListModel<String>();
	private DefaultListModel<String> groupList = new DefaultListModel<String>();
	private DefaultListModel<Integer> seriesList = new DefaultListModel<Integer>();
	private DefaultListModel<Integer> seasonList = new DefaultListModel<Integer>();

	
	
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
		VideoProvider controller = new VideoProvider();
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
	private void initialize( List<Video> videoList ) {
		
		setLists( videoList );		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 800);
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
		JLabel lblTitle = new JLabel("Select a show");
		lblTitle.setVerticalAlignment(SwingConstants.TOP);
		frame.getContentPane().add(lblTitle, "cell 0 0,alignx left,aligny bottom");
		
		JLabel lblGenre = new JLabel("Genre");
		frame.getContentPane().add(lblGenre, "cell 2 2,aligny bottom");
		
		JLabel lblGroup = new JLabel("Group");
		frame.getContentPane().add(lblGroup, "cell 4 2,aligny bottom");
		
		JLabel lblSeriesNumber = new JLabel("Series Number");
		frame.getContentPane().add(lblSeriesNumber, "cell 6 2,aligny bottom");
		
		JLabel lblSeasonNumber = new JLabel("Season Number");
		frame.getContentPane().add(lblSeasonNumber, "cell 6 4,aligny bottom");

/* Buttons */
		JButton btnPlay = new JButton("PLAY");
		btnPlay.setMinimumSize(new Dimension(300,25));
		btnPlay.setEnabled(false);
		btnPlay.setToolTipText("This will copy to the desktop and then play the movie");
		frame.getContentPane().add(btnPlay, "cell 0 6,alignx left,aligny center");
		
		JButton btnUpdateShow = new JButton("Make updates");
		btnUpdateShow.setEnabled(false);
		frame.getContentPane().add(btnUpdateShow, "cell 4 6,alignx right,aligny bottom");
		
		JButton btnRefreshDatabase = new JButton("Refresh database");
		frame.getContentPane().add(btnRefreshDatabase, "cell 6 6,alignx left,aligny bottom");
		
/* Lists */

		JList videos = new JList(titleList);
		videos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		videos.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollVideoPane = new JScrollPane( videos, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		frame.getContentPane().add(scrollVideoPane, "cell 0 2 1 4,grow");
		
		JList genres = new JList( genreList );
		genres.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		genres.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollGenrePane = new JScrollPane( genres, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		frame.getContentPane().add(scrollGenrePane, "cell 2 3 1 3,grow");
		
		JList groups = new JList(groupList);
		groups.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		groups.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollGroupPane = new JScrollPane( groups, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		frame.getContentPane().add(scrollGroupPane, "cell 4 3 1 3,grow");
		
		JList series_numbers = new JList(seriesList);
		series_numbers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		series_numbers.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollSeriesPane = new JScrollPane( series_numbers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		frame.getContentPane().add(scrollSeriesPane, "cell 6 3,grow");
				
		JList season_numbers = new JList(seasonList);
		season_numbers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		season_numbers.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollSeasonPane = new JScrollPane( season_numbers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		frame.getContentPane().add(scrollSeasonPane, "cell 6 5,grow");
		
/* Check Boxes */		
		JCheckBox movieCB = new JCheckBox("Movies");
		frame.getContentPane().add(movieCB, "cell 4 0,alignx right,aligny bottom");
		
		JCheckBox tvCB = new JCheckBox("TV Shows");
		frame.getContentPane().add(tvCB, "cell 6 0,alignx left,aligny bottom");
		
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
			}
		});

		groups.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			}
		});

		series_numbers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			}
		});

		season_numbers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
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
				if (Desktop.isDesktopSupported()) {
				    
					try 
					{
						System.out.println( locationList.get( videos.getSelectedIndex() ) );
				    	String location = "files/icon.png"; // replace with selected item from titleList 
				        File myFile = new File( location );
				        Desktop.getDesktop().open(myFile);
				        videos.clearSelection();
				        btnPlay.setEnabled( false );
				    }
					catch (IOException ex)
					{
				      System.err.println( "unable to open file" );
				    }
				}
			}
		});

		btnUpdateShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btnRefreshDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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

	
}
