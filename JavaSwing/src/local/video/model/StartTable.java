package local.video.model;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class StartTable extends DefaultTableModel
{
	
	Object[][] rowData = { { Boolean.FALSE , "Select All" } };
	Object[] colNames = { "Check" , "Titles" };

	
	public int getRowCount ()
	{
		return rowData.length;
	}

	
	public int getColumnCount ()
	{
		return colNames.length;
	}
	
	public Class<?> getColumnClass ( int columnIndex )
	{
		return ( getValueAt(0, columnIndex).getClass() );
	}


	public Object getValueAt ( int rowIndex, int columnIndex )
	{
		return rowData[rowIndex][columnIndex];
	}
	

}
