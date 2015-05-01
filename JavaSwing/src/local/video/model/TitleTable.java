package local.video.model;

import javax.swing.table.DefaultTableModel;


public class TitleTable extends DefaultTableModel
{
	public TitleTable()
	{
		super( new String[] { "", "Title" }, 0 );
	}
	
	public Class<?> getColumnClass ( int columnIndex )
	{
		return ( getValueAt(0, columnIndex).getClass() );
	}	
}