package local.video.app;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import local.dto.Video;
import local.dto.VideoProvider;
import local.models.top.Categories;
import local.models.top.Repos;
import local.video.model.CreateTable;
import local.video.model.StartTable;
import local.video.model.UpdateModel;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class UpdateWindow {

	private JFrame frame;
	private JTextField txtTitle, txtGenre, txtGroup, txtEpisode, txtSeason;
	private JButton btnClear, btnSaveUpdate;
	private JScrollPane scrollTitle, scrollGenre, scrollGroup, scrollEpisode, scrollSeason;
	private JTable tableTitles, tableGenres, tableGroups, tableEpisodes, tableSeasons;
	private List<Video> videoList;
	private ArrayList locationList, genresArray, groupsArray, episodesArray, seasonArray, idArray,  tmpArray;
	

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
	public UpdateWindow( ArrayList<Integer> tmpArray ) {
		VideoProvider controller = new VideoProvider( Repos.XML );
		try 
		{
			this.tmpArray = new ArrayList();
			this.tmpArray = tmpArray;
			locationList = new ArrayList();
			genresArray = new ArrayList();
			groupsArray = new ArrayList();
			episodesArray = new ArrayList();
			seasonArray = new ArrayList();
			idArray = new ArrayList();

			this.videoList = controller.returnVideos();
			updateAll();
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
		frame.getContentPane().setLayout( new MigLayout( "", "[300,grow][10][200,grow][10][140,grow][10][100,grow][10][100,grow]", "[460][10][30][10][30][10]" ) );
		

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
		CreateTable tiModel = new CreateTable( videoList, Categories.TITLE );
		tableTitles = new JTable( tiModel.getTable() );
		tableTitles.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		tableTitles.getColumnModel().getColumn( 0 ).setMinWidth( 20 );
		tableTitles.getColumnModel().getColumn( 0 ).setMaxWidth( 30 );
		tableTitles.setAutoCreateRowSorter( true );

		scrollTitle.setViewportView(tableTitles);

		CreateTable geModel = new CreateTable( videoList, Categories.GENRE );
		tableGenres = new JTable( geModel.getTable() );
		tableGenres.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		tableGenres.setAutoCreateRowSorter( true );
		scrollGenre.setViewportView(tableGenres);

		
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

		frame.getContentPane().add( scrollTitle, "cell 0 0,grow" );
		frame.getContentPane().add( scrollGenre, "cell 2 0,grow" );
		frame.getContentPane().add( scrollGroup, "cell 4 0,grow" );
		frame.getContentPane().add( scrollEpisode, "cell 6 0,grow" );
		frame.getContentPane().add( scrollSeason, "cell 8 0,grow" );

		frame.getContentPane().add( txtTitle, "cell 0 2,growx" );
		frame.getContentPane().add( txtGenre, "cell 2 2,growx" );
		frame.getContentPane().add( txtGroup, "cell 4 2,growx" );
		frame.getContentPane().add( txtEpisode, "cell 6 2,growx" );
		frame.getContentPane().add( txtSeason, "cell 8 2,growx" );
		
		frame.getContentPane().add( btnClear, "cell 0 4,aligny bottom" );
		frame.getContentPane().add( btnSaveUpdate, "cell 8 4,alignx right,aligny bottom" );
	}

	private JTable returnPreparedTable()
	{
		JTable table = new JTable( );
	
		return table;
	}
	

	private void setLists ( List<Video> videos )
	{
		ArrayList<Integer> tmpArray = new ArrayList<Integer>();
		
		for ( int id : idArray )
		{
			tmpArray.add( id );
		}
		
		locationArray.clear();
		genresArray.clear();
		groupsArray.clear();
		episodesArray.clear();
		seasonArray.clear();
		idArray.clear();
		
				for( Video video : videos )
				{
					if ( tmpArray.contains( video.getID() ) )
					{
						idArray.add( video.getID() );
						titleArray.add( video.getTitle() );
						locationArray.add( video.getLocation() );
						
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

}
