package local.video.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import local.dto.Video;
import local.dto.VideoProvider;
import local.models.top.Repos;
import local.video.model.ItemToUpdate;
import net.miginfocom.swing.MigLayout;


public class EditorWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField titleInput, genreInput, episodeInput, seasonInput, groupInput;
	private JComboBox<Object> genreList, groupList;
	private JLabel lblTitle, lblGenres, lblEpisodes, lblSeasons, lblGroups, lblLocation;
	private JButton btnSave, btnCancel, btnPrevious, btnNext;
	private ItemToUpdate item = new ItemToUpdate();
	private String title, genre, group, location, episode, season;
	private String[] genres, groups, episodes, seasons;
	private JTextArea textArea;
	private JButton genreAddBtn;
	private JButton groupAddBtn;


//	/**
//	 * Launch the application.
//	 */
//	public static void main( String[] args )
//	{
//		try
//		{
//			EditorWindow dialog = new EditorWindow();
//			dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
//			dialog.setModalityType( Dialog.ModalityType.APPLICATION_MODAL );
//			dialog.setVisible( true );
//		}
//		catch ( Exception e )
//		{
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public EditorWindow( ItemToUpdate item )
	{
		System.out.println( item.getID() );
		this.item = item;
		loadsvalues();
		setSize(600, 400);
		getContentPane().setLayout( new BorderLayout() );
		contentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		getContentPane().add( contentPanel, BorderLayout.CENTER );
		contentPanel.setLayout(new MigLayout("", "[250,grow][][20][50][50]", "[20][20][20][20][20][20][20][20][20][40]"));
		{

			lblTitle = new JLabel("Title");
			lblTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPanel.add(lblTitle, "cell 0 0");

			titleInput = new JTextField();
			contentPanel.add(titleInput, "cell 0 1 5 1,growx");
			titleInput.setColumns(10);
			titleInput.setText( title );
			
			lblGenres = new JLabel("Genres");
			lblGenres.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPanel.add(lblGenres, "cell 0 2");
			
			lblEpisodes = new JLabel("Episodes");
			lblEpisodes.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPanel.add(lblEpisodes, "cell 4 2,alignx right");
			
			genreList = new JComboBox<Object>( genres );
			contentPanel.add(genreList, "cell 0 3,growx");
			
			genreAddBtn = new JButton("+");
			contentPanel.add(genreAddBtn, "cell 1 3");
			genreAddBtn.addActionListener( new ActionAddHandler( "genre" ) );
			
			episodeInput = new JTextField();
			contentPanel.add(episodeInput, "cell 4 3");
			episodeInput.setColumns(10);
			episodeInput.setText( episode );

			genreInput = new JTextField();
			contentPanel.add(genreInput, "cell 0 4,growx");
			genreInput.setColumns(10);
			genreInput.setText( genre );
			
			lblGroups = new JLabel("Groups");
			lblGroups.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPanel.add(lblGroups, "cell 0 5");
			
			lblSeasons = new JLabel("Seasons");
			lblSeasons.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPanel.add(lblSeasons, "cell 4 5,alignx right");

			groupList = new JComboBox<Object>( groups );
			contentPanel.add(groupList, "cell 0 6,growx");
			
			groupAddBtn = new JButton("+");
			contentPanel.add(groupAddBtn, "cell 1 6");
			groupAddBtn.addActionListener( new ActionAddHandler( "group" ) );
			
			seasonInput = new JTextField();
			contentPanel.add(seasonInput, "cell 4 6");
			seasonInput.setColumns(10);
			seasonInput.setText( season );
		
			groupInput = new JTextField();
			contentPanel.add(groupInput, "cell 0 7,growx");
			groupInput.setColumns(10);
			groupInput.setText( group );
		
			lblLocation = new JLabel("Location");
			lblLocation.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPanel.add(lblLocation, "cell 0 8");
			
			textArea = new JTextArea( location );
			textArea.setFont(new Font("Tahoma", Font.PLAIN, 11));
			contentPanel.add(textArea, "cell 0 9 5 1,grow");
			
			textArea.setColumns(10);
			textArea.setLineWrap( true );
			textArea.setEnabled( false );
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout( new FlowLayout( FlowLayout.CENTER ) );
			getContentPane().add( buttonPane, BorderLayout.SOUTH );
			{
				btnPrevious = new JButton("Save & Previous");
				btnPrevious.addActionListener(new ActionEventHandler() );
				btnPrevious.setMinimumSize( new Dimension( 150,25 ) );
				buttonPane.add(btnPrevious);

				btnNext = new JButton("Save & Next");
				btnNext.addActionListener(new ActionEventHandler() );
				btnNext.setMinimumSize( new Dimension( 150,25 ) );
				buttonPane.add(btnNext);

				btnSave = new JButton( "Save & Close" );
				btnSave.addActionListener(new ActionEventHandler() );
				btnSave.setActionCommand( "Save & Close" );
				btnSave.setMinimumSize( new Dimension( 150,25 ) );
				buttonPane.add( btnSave );
				getRootPane().setDefaultButton( btnSave );

				btnCancel = new JButton( "Cancel" );
				btnCancel.addActionListener(new ActionEventHandler() );
				btnCancel.setMinimumSize( new Dimension( 150,25 ) );
				btnCancel.setActionCommand( "Cancel" );
				buttonPane.add( btnCancel );			
			}
		}		
	}

	private void loadsvalues()
	{
		title = item.getTitle();
		genre = item.getGenre();
		group = item.getGroup();
		episode = Integer.toString( item.getEpisode() );
		season = Integer.toString( item.getSeason() );
		location = item.getLocation().replace( "%20", " " );
		
		genres = new String[ item.getGenreList().size() ];
		for ( int i = 0; i < item.getGenreList().size(); i++ )
		{
			genres[i] = item.getGenreList().get( i );
		}
		groups = new String[ item.getGroupList().size() ];
		for ( int i = 0; i < item.getGroupList().size(); i++ )
		{
			groups[i] = item.getGroupList().get( i );
		}
		episodes = new String[ item.getEpisodeList().size() ];
		for ( int i = 0; i < item.getEpisodeList().size(); i++ )
		{
			episodes[i] = Integer.toString( item.getEpisodeList().get( i ) );
		}
		seasons = new String[ item.getSeasonList().size() ];
		for ( int i = 0; i < item.getSeasonList().size(); i++ )
		{
			seasons[i] = Integer.toString( item.getSeasonList().get( i ) );
		}
	}
	
	private class ActionAddHandler implements ActionListener
	{
		private String button;
		
		ActionAddHandler( String button )
		{
			this.button = button;
		}
		
		@Override
		public void actionPerformed( ActionEvent e )
		{
			String s = "";
			String t = "";
			if ( button.equals( "genre" ) )
			{
				t = genreList.getSelectedItem().toString();
				if ( genreInput.getText() != null )
				{
					s = genreInput.getText();
					if ( s.contains( t ) )
					{}
					else
					{
						s = s + "," + t;
						genreInput.setText( s );
					}
					
				}
			}
			else if ( button.equals( "group" ) )
			{
				t = groupList.getSelectedItem().toString();
				if ( groupInput.getText() != null )
				{
					s = groupInput.getText();
					if ( s.equals( "" ) )
					{
						s = t;
					}
					else if ( !s.contains( t ) )
					{
						s = s + "," + t;
					}
					else
					{
						return;
					}
					groupInput.setText( s );
				}

			}
			else
			{
				System.out.println( "some other button" );
			}
		}
	}
	
	private class ActionEventHandler implements ActionListener
	{
		public void actionPerformed( ActionEvent e )
		{
			String command = e.getActionCommand();
//			System.out.println( command );
			
			if ( command.equals( "Cancel" ) )
			{
//				System.out.println( "cancel update" );
				dispose();
			}
			else if ( command.equals( "Save & Close" ) )
			{
//				System.out.println( "save and close update" );
				System.out.println( titleInput.getText() );
				System.out.println( genreInput.getText() );
				System.out.println( groupInput.getText() );
				System.out.println( episodeInput.getText() );
				System.out.println( seasonInput.getText() );
				updateVideo();
				dispose();
			}
			else if ( command.equals( "Save & Next" ) )
			{
//				System.out.println( "save & next update" );
			}
			else if ( command.equals( "Save & Previous" ) )
			{
//				System.out.println( "save & previous update" );
			}
			else
			{
//				System.out.println( "some other bottom bar button" );
			}
		}
	}

	private void updateVideo()
	{
		VideoProvider controller = new VideoProvider( Repos.MYSQL );
		Video video = new Video();
		video.setID( item.getID() );
		video.setType( item.getType() );
		video.setTitle( titleInput.getText() );
		video.setGenre( genreInput.getText() );
		video.setGroup( groupInput.getText() );
		video.setEpisodeN( Integer.parseInt( episodeInput.getText() ) );
		video.setSeasonN( Integer.parseInt( seasonInput.getText() ) );
		if ( !controller.updateVideo( video ) )
			System.err.println( "failed to update video " + item.getID());
	}
	
}
