package local.loadxml;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.Node;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class View extends JFrame implements ActionListener {

	private String dataSheet = "http://localhost:/HomeJSP/fileList.jsp";
	public ArrayList<String> titles = new ArrayList<String>();
	public ArrayList<String> genres = new ArrayList<String>();
	public ArrayList<String> groups = new ArrayList<String>();
	public ArrayList<String> paths = new ArrayList<String>();
	public JList list;
	public JComboBox combo;
	public JTextArea textArea = new JTextArea();
	public JLabel titleLabel = new JLabel();
	public JLabel pathlabel = new JLabel();
	
	public View() 
	{
		super( "Home Media App" );
		setLayout( new FlowLayout() );
		loadData(dataSheet);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		 
	}
	
	public void loadData(String xmlURL) 
	{
		
		try
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse( newURL( xmlURL ).openStream() );
			doc.getDocumentElement().normalize();
			
			NodeList nodes = doc.getElementsByTagName("dict");
			
			for( int i = 0; i < nodes.getLength(); i++ )
			{
				Node n = nodes.item(i);
				if( n.getNodeType() == Node.ELEMENT_NODE ) 
				{
					Element e = (Element) n;
					
					titles.add(getTagValue("tourTitle", e));
					
				}
			}
		}
		catch ()
		{
			
		}
		
		
	}
	
}
