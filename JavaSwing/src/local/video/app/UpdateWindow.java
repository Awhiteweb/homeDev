package local.video.app;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTextField;

import local.dto.Video;
import local.dto.VideoProvider;
import local.models.top.Categories;
import local.models.top.Repos;
import local.video.model.CreateTable;
import local.video.model.StartTable;
import local.video.model.UpdateModel;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class UpdateWindow {

	private JFrame frame;
	private JTextField txtTitle;
	private JTextField txtGenre;
	private JTextField txtGroup;
	private JTextField txtEpisode;
	private JTextField txtSeason;
	private JLabel lblTitles;
	private JLabel lblGenres;
	private JLabel lblGroups;
	private JLabel lblEpisodeNumber;
	private JLabel lblSeasonNumber;
	private JButton btnClear;
	private JButton btnSaveUpdate;
	private JScrollPane scrollTitle;
	private JScrollPane scrollGenre;
	private JScrollPane scrollGroup;
	private JScrollPane scrollEpisode;
	private JScrollPane scrollSeason;
	private JTable tableTitles;
	private List<Video> videoList;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateWindow window = new UpdateWindow();
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
	public UpdateWindow() {
		VideoProvider controller = new VideoProvider( Repos.XML );
		try 
		{
			this.videoList = controller.returnVideos();
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds( 100, 100, 900, 600 );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.getContentPane().setLayout( new MigLayout( "", "[300,grow][10][200,grow][10][140,grow][10][100,grow][10][100,grow]", "[30][460][10][30][10][30][10]" ) );
		
		lblTitles = new JLabel( "Titles" );
		lblGenres = new JLabel( "Genres" );
		lblGroups = new JLabel( "Groups" );
		lblEpisodeNumber = new JLabel( "Episode Number" );
		lblSeasonNumber = new JLabel( "Season Number" );

		btnClear = new JButton( "Clear" );
		btnSaveUpdate = new JButton( "Save Update" );

		JCheckBox cbTitles = new JCheckBox( "Titles" );
		JCheckBox cbGenres = new JCheckBox( "Genres" );
		JCheckBox cbGroups = new JCheckBox( "Groups" );
		JCheckBox cbEpisodes = new JCheckBox( "Episodes" );
		JCheckBox cbSeason = new JCheckBox( "Seasons" );
		
		scrollTitle = new JScrollPane( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		scrollGenre = new JScrollPane( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		scrollGroup = new JScrollPane( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		scrollEpisode = new JScrollPane( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		scrollSeason = new JScrollPane( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		
// create the titles table		
		CreateTable tModel = new CreateTable( videoList, Categories.TITLE );
		tableTitles = new JTable( tModel.getTable() );
		scrollTitle.setViewportView(tableTitles);

		CreateTable geModel = new CreateTable( videoList, Categories.GENRE );
		tableTitles = new JTable( geModel.getTable() );
		scrollGenre.setViewportView(tableTitles);

		
		txtTitle = new JTextField();
		txtTitle.setText( "Update Title" );
		txtTitle.setColumns(10);
		txtGenre = new JTextField();
		txtGenre.setText( "Add Genre" );
		txtGenre.setColumns(10);
		txtGroup = new JTextField();
		txtGroup.setText( "Add Group" );
		txtGroup.setColumns(10);
		txtEpisode = new JTextField();
		txtEpisode.setText( "Add Episode #" );
		txtEpisode.setColumns(10);		
		txtSeason = new JTextField();
		txtSeason.setText( "Add Season #" );
		txtSeason.setColumns(10);

		frame.getContentPane().add( lblTitles, "cell 0 0" );
		frame.getContentPane().add( lblGenres, "cell 2 0" );
		frame.getContentPane().add( lblGroups, "cell 4 0" );
		frame.getContentPane().add( lblEpisodeNumber, "cell 6 0" );
		frame.getContentPane().add( lblSeasonNumber, "cell 8 0" );

		frame.getContentPane().add( scrollTitle, "cell 0 1,grow" );
		frame.getContentPane().add( scrollGenre, "cell 2 1,grow" );
		frame.getContentPane().add( scrollGroup, "cell 4 1,grow" );
		frame.getContentPane().add( scrollEpisode, "cell 6 1,grow" );
		frame.getContentPane().add( scrollSeason, "cell 8 1,grow" );

		frame.getContentPane().add( txtTitle, "cell 0 3,growx" );
		frame.getContentPane().add( txtGenre, "cell 2 3,growx" );
		frame.getContentPane().add( txtGroup, "cell 4 3,growx" );
		frame.getContentPane().add( txtEpisode, "cell 6 3,growx" );
		frame.getContentPane().add( txtSeason, "cell 8 3,growx" );
		
		frame.getContentPane().add( btnClear, "cell 0 5,aligny bottom" );
		frame.getContentPane().add( btnSaveUpdate, "cell 8 5,alignx right,aligny bottom" );
	}

	private JTable returnPreparedTable()
	{
		JTable table = new JTable( );
	
		return table;
	}

}