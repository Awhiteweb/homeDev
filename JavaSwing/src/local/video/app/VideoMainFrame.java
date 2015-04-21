package local.video.app;

import java.awt.Dimension;
import java.awt.EventQueue;

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
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import local.dto.Video;
import local.dto.VideoProvider;
import local.database.xml.XMLVideoRepo;
import javax.swing.JScrollPane;

public class VideoMainFrame {

	private JFrame frame;
	private List<Video> videoList;
	private DefaultListModel<String> titleList = new DefaultListModel<String>();
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
			controller.getVideos();
		
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
		
		
		for ( Video video : videoList )
		{
			titleList.addElement( video.getTitle() );
			genreList.addElement( video.getGenre() );
			groupList.addElement( video.getGroup() );
			seriesList.addElement( video.getSeriesN() );
			seasonList.addElement( video.getSeasonN() );
		}
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[400px:n,grow][20px][250px:n,grow][20px][150px:n,grow][20px][100px:n:250px,grow]", "[30px][5px][30px][grow][30px][grow][30px]"));
	
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
		
/* Lists */

		JList videos = new JList(titleList);
		videos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		videos.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollVideoPane = new JScrollPane( videos, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		frame.getContentPane().add(scrollVideoPane, "cell 0 2 1 4,grow");

		
		JList genres = new JList(genreList);
		genres.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			}
		});
		genres.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		genres.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollGenrePane = new JScrollPane( genres, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		frame.getContentPane().add(scrollGenrePane, "cell 2 3 1 3,grow");
		
		JList groups = new JList(groupList);
		groups.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			}
		});
		groups.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		groups.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollGroupPane = new JScrollPane( groups, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		frame.getContentPane().add(scrollGroupPane, "cell 4 3 1 3,grow");
		
		JList series_numbers = new JList(seriesList);
		series_numbers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			}
		});
		series_numbers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		series_numbers.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollSeriesPane = new JScrollPane( series_numbers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		frame.getContentPane().add(scrollSeriesPane, "cell 6 3,grow");
				
		JList season_numbers = new JList(seasonList);
		season_numbers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			}
		});
		season_numbers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		season_numbers.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollSeasonPane = new JScrollPane( season_numbers, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		frame.getContentPane().add(scrollSeasonPane, "cell 6 5,grow");
		
/* Check Boxes */		
		JCheckBox movieCB = new JCheckBox("Movies");
		movieCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		frame.getContentPane().add(movieCB, "cell 4 0,alignx right,aligny bottom");
		
		JCheckBox tvCB = new JCheckBox("TV Shows");
		tvCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		frame.getContentPane().add(tvCB, "cell 6 0,alignx left,aligny bottom");
		
/* Buttons */
		JButton btnPlay = new JButton("PLAY");
		btnPlay.setMinimumSize(new Dimension(300,25));
		btnPlay.setEnabled(false);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPlay.setToolTipText("This will copy to the desktop and then play the movie");
		frame.getContentPane().add(btnPlay, "cell 0 6,alignx left,aligny center");
		
		JButton btnUpdateShow = new JButton("Update show");
		btnUpdateShow.setEnabled(false);
		btnUpdateShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		frame.getContentPane().add(btnUpdateShow, "cell 4 6,alignx right,aligny bottom");
		
		JButton btnRefreshDatabase = new JButton("Refresh database");
		btnRefreshDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		frame.getContentPane().add(btnRefreshDatabase, "cell 6 6,alignx left,aligny bottom");
	}
	
	private void returnLists( List<Video> videos )
	{
			for( Video video : videos )
			{
				titleList.addElement( video.getTitle() );
				genreList.addElement( video.getGenre() );
				groupList.addElement( video.getGroup() );
				seriesList.addElement( video.getSeriesN() );
				seasonList.addElement( video.getSeasonN() );				
			}
		
	}

	
}
