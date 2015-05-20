package local.video.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class EditWindow extends JFrame
{
	private JTextField titleInput, genreInput, episodeInput, seasonInput, groupInput;
	private JComboBox episodeList, seasonList, genreList, groupList;
	private JLabel location;
	private JButton btnSave, btnCancel;


	/**
	 * Launch the application.
	 */
	public static void main ( String[] args )
	{
		EventQueue.invokeLater( new Runnable()
		{
			public void run ()
			{
				try
				{
					EditWindow frame = new EditWindow();
					frame.setVisible( true );
				}
				catch ( Exception e )
				{
					e.printStackTrace();
				}
			}
		} );
	}


	/**
	 * Create the frame.
	 */
	public EditWindow ()
	{
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setBounds( 100, 100, 600, 400 );
		getContentPane().setLayout(new MigLayout("", "[250,grow][10][125,grow][5px][125,grow]", "[30][30][30][30][30][30][30]"));
		
		titleInput = new JTextField();
		getContentPane().add(titleInput, "cell 0 0 5 1,growx");
		titleInput.setColumns(10);
		
		genreList = new JComboBox();
		getContentPane().add(genreList, "cell 0 1,growx");
		
		groupList = new JComboBox();
		getContentPane().add(groupList, "cell 0 3,growx");
		
		episodeList = new JComboBox();
		getContentPane().add(episodeList, "cell 2 1,growx");
		
		seasonList = new JComboBox();
		getContentPane().add(seasonList, "cell 4 1,growx");

		genreInput = new JTextField();
		getContentPane().add(genreInput, "cell 0 2,growx");
		genreInput.setColumns(10);
		
		episodeInput = new JTextField();
		getContentPane().add(episodeInput, "cell 2 2,growx");
		episodeInput.setColumns(10);
		
		seasonInput = new JTextField();
		getContentPane().add(seasonInput, "cell 4 2,growx");
		seasonInput.setColumns(10);
				
		groupInput = new JTextField();
		getContentPane().add(groupInput, "cell 0 4,growx");
		groupInput.setColumns(10);
		
		location = new JLabel("New label");
		getContentPane().add(location, "cell 0 5 5 1");
		
		btnCancel = new JButton("Cancel");
		getContentPane().add(btnCancel, "flowx,cell 0 6");
		
		btnSave = new JButton("Save");
		getContentPane().add(btnSave, "cell 0 6");
		getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{titleInput, genreList, genreInput, groupList, groupInput, episodeList, episodeInput, seasonList, seasonInput, btnCancel, btnSave, location}));
	}

}
