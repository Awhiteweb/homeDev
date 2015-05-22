package local.testarea;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;


public class ListDemo extends JFrame{

    private DefaultListModel<Integer> listModel1 = new DefaultListModel<>();
    private DefaultListModel<String> listModel2 = new DefaultListModel<>();
    private JList<Integer> list1 = new JList<>(listModel1);
    private JList<String> list2 = new JList<>(listModel2);
    int size = 101;

    public ListDemo() {

        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(list1));

        list2.addListSelectionListener(new ListParityFilter());
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModel2.addElement("All");
        listModel2.addElement("Even");
        listModel2.addElement("Odd");
        list2.setSelectedIndex(0);
        add(new JScrollPane(list2), BorderLayout.EAST);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class ListParityFilter implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {

            if (e.getValueIsAdjusting())
                return;
            System.out.println("Selected List2 item: " + list2.getSelectedValue());
            listModel1.clear();
            switch (list2.getSelectedIndex()) {
                case 0:
                    for (int i = 0; i < size; i++)
                        listModel1.addElement(i);
                    break;
                case 1:
                    for (int i = 0; i < size; i += 2)
                        listModel1.addElement(i);
                    break;
                case 2:
                    for (int i = 1; i < size; i += 2)
                        listModel1.addElement(i);
                    break;
            }
        }
    }

    public static void main(String[] args) {

        new ListDemo();
    }
}

//@SuppressWarnings( { "rawtypes", "unchecked" } )
//public class ListDemo {
//
//	private JFrame frame;
//	private JList list, list_1;
//	private JButton btnNewButton;
//	private DefaultListModel<String> list2;
//	private DefaultListModel<Integer> list1;
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main( String[] args )
//	{
//		EventQueue.invokeLater( new Runnable() {
//
//			public void run()
//			{
//				try
//				{
//					ListDemo window = new ListDemo();
//					window.frame.setVisible( true );
//				}
//				catch ( Exception e )
//				{
//					e.printStackTrace();
//				}
//			}
//		} );
//	}
//
//	/**
//	 * Create the application.
//	 */
//	public ListDemo()
//	{
//		list1 = new DefaultListModel<Integer>();
//		list2 = new DefaultListModel<String>();
//		initialize();
//		updateAll();
//	}
//
//	private void updateAll()
//	{
//		list1.clear();
//		list2.clear();
//		for ( int i = 0; i < 101; i++ )
//		{
//			list1.addElement( i );
//		}
//		list2.addElement( "Even" );
//		list2.addElement( "Odd" );
//		
//	}
//	
//	private void updateLists( int selected )
//	{
//		list1.clear();
//		list2.clear();
//		switch( selected )
//		{
//			case 0:
//				for ( int i = 0; i < 101; i++)
//				{
//					if ( i % 2 == 0 ) 
//					{
//						list1.addElement( i );
//					}
//				}
//				list2.addElement( "Even" );
//				break;
//				
//			case 1:
//				for ( int i = 0; i < 101; i++)
//				{
//					if ( i % 2 != 0 ) 
//					{
//						list1.addElement( i );
//					}
//				}
//				list2.addElement( "Odd" );
//				break;
//				
//			default:
//				int z = selected - 10;
//				list1.addElement( z );
//				list2.addElement( "Even" );
//				list2.addElement( "Odd" );
//				break;
//		}
//	}
//
//	/**
//	 * Initialize the contents of the frame.
//	 */
//	private void initialize()
//	{
//		frame = new JFrame();
//		frame.setBounds( 100, 100, 450, 481 );
//		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
//		frame.getContentPane().setLayout(new MigLayout("", "[grow][grow]", "[grow][]"));
//		
//		JScrollPane scrollPane = new JScrollPane();
//		frame.getContentPane().add(scrollPane, "cell 0 0,grow");
//		
//		list = new JList( list1 );
//		list.addListSelectionListener(new ListSelectionListener() {
//			public void valueChanged(ListSelectionEvent e) {
//				System.out.println( "List 1 trigger" );
//				System.out.println( "selected item: " + list_1.getSelectedIndex() );
//				if ( list.getSelectedIndex() >= 0 )
//				{
//					int z = 10 + list.getSelectedIndex();
//					updateLists( z );
//				}
//			}
//		});
//		scrollPane.setViewportView(list);
//		
//		JScrollPane scrollPane_1 = new JScrollPane();
//		frame.getContentPane().add(scrollPane_1, "cell 1 0,grow");
//		
//		list_1 = new JList( list2 );
//		list_1.addListSelectionListener(new ListSelectionListener() {
//			public void valueChanged(ListSelectionEvent e) {
//				System.out.println( "List 2 trigger" );
//				System.out.println( "selected item: " + list_1.getSelectedIndex() );
//				if ( list_1.getSelectedIndex() >= 0 )
//				{
//					updateLists( list_1.getSelectedIndex() );
//				}
//			}
//		});
//		scrollPane_1.setViewportView(list_1);
//		
//		btnNewButton = new JButton("New button");
//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.out.println( "refresh" );
//				updateAll();
//			}
//		});
//		frame.getContentPane().add(btnNewButton, "cell 1 1");
//	}
//
//}
