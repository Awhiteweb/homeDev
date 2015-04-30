package local.video.model;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import local.dto.Video;

public class UpdateModel extends JLabel implements ListCellRenderer {

	public UpdateModel() 
	{
		setOpaque( true );
	}
	
	
	@Override
	public Component getListCellRendererComponent( JList list, 
												   Object value,
												   int index, 
												   boolean isSelected, 
												   boolean cellHasFocus )
	{
		setText( value.toString() );
		
		Color background;
		Color forground;
		
		JList.DropLocation dropLocation = list.getDropLocation();
		if ( dropLocation != null 
				&& !dropLocation.isInsert() 
				&& dropLocation.getIndex() == index )
		{
			background = Color.BLUE;
			forground = Color.WHITE;
		}
		else if ( isSelected )
		{
			background = Color.RED;
			forground = Color.BLACK;
		}
		else
		{
			background = Color.WHITE;
			forground = Color.BLACK;
		}
		
		setBackground( background );
		setForeground( forground );
		
		return this;
	}

	
}
