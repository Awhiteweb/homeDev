package local.video.model;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class CellRenderer extends JTable implements TableCellRenderer 
{
	
	public CellRenderer()
	{
		super();
	}

	@Override
	public Component getTableCellRendererComponent( 
								JTable table, 
								Object value,
								boolean isSelected, 
								boolean hasFocus, 
								int row, 
								int column 
								)
	{
		
		return null;
	}
	

}
