package local.database.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javanet.staxutils.IndentingXMLStreamWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import local.dto.Video;
import local.dto.VideoProvider;

public class XMLWriter {

	public void createDocument (List<Video> data, String filename) throws XMLStreamException, IOException
	{
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		FileWriter fw = new FileWriter( new File( filename ) );
		XMLStreamWriter w = factory.createXMLStreamWriter( fw );
		
		IndentingXMLStreamWriter writer = new IndentingXMLStreamWriter( w );
		
		writer.writeStartDocument();
		writer.writeStartElement( "videos" );
		
		createElement(writer, data);
		
		writer.writeEndElement();
		writer.writeEndDocument();
		
		writer.flush();
		
		writer.close();
		
		System.out.println( "Job Done!" );

	}

	private void createElement(XMLStreamWriter writer, List<Video> data) throws XMLStreamException 
	{
		for (Video video : data) 
		{
			writer.writeStartElement( "video" );
			writer.writeAttribute( VideoProvider.ID , Integer.toString(video.getID()) );
			
			writer.writeStartElement( VideoProvider.TYPE );
			writer.writeCharacters( video.getType() );
			writer.writeEndElement();

			writer.writeStartElement( VideoProvider.TITLE );
			writer.writeCharacters( video.getTitle() );
			writer.writeEndElement();
			
			writer.writeStartElement( VideoProvider.LOCATION );
			writer.writeCharacters( video.getLocation() );
			writer.writeEndElement();
			
			writer.writeStartElement( VideoProvider.GENRE );
			writer.writeCharacters( video.getGenre() );
			writer.writeEndElement();
			
			writer.writeStartElement( VideoProvider.GROUP );
			writer.writeCharacters( video.getGroup() );
			writer.writeEndElement();
			
			writer.writeStartElement( VideoProvider.SERIES_N );
			writer.writeCharacters( Integer.toString( video.getSeriesN() ) );
			writer.writeEndElement();

			writer.writeStartElement( VideoProvider.SEASON_N );
			writer.writeCharacters( Integer.toString( video.getSeriesN() ) );
			writer.writeEndElement();

				
			writer.writeEndElement();
	
		}
	}
	
}
