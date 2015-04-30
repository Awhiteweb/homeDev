package local.video.model;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;


public class MyTable extends DefaultTableModel
{
	public MyTable()
	{
		super( new String[] { "blah", "blah" }, 0 );
	}
	
	 @Override
	    public Class<?> getColumnClass(int columnIndex) {
	      Class clazz = String.class;
	      switch (columnIndex) {
	        case 0:
	          clazz = Boolean.class;
	          break;
	        default:
	          clazz = String.class;
	          break;
	      }
	      return clazz;
	    }
	 
	@Override
	    public boolean isCellEditable(int row, int column) {
	      return column == 0;
	    }
	
	@Override
	public int getRowCount()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt( int rowIndex, int columnIndex )
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}