package local.video.model;

import javax.swing.table.DefaultTableModel;


public class MakeTable extends DefaultTableModel
{
	public MakeTable( String heading )
	{
		super( new String[] { heading }, 0 );
	}
	
	public Class<?> getColumnClass ( int columnIndex )
	{
		return ( getValueAt(0, columnIndex).getClass() );
	}	
}