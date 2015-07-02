package local.database.xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javanet.staxutils.IndentingXMLStreamWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import local.dto.Video;
import local.dto.VideoProvider;
import local.models.top.Finals;

public class XMLWriter {

	public void createDocument (List<Video> data, String filename) throws XMLStreamException, IOException
	{
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
//		Writer fw = new OutputStreamWriter( new FileOutputStream( filename ), "UTF-8" );
		XMLStreamWriter w = factory.createXMLStreamWriter( new FileOutputStream( filename ) , "UTF-8" );
		
		IndentingXMLStreamWriter writer = new IndentingXMLStreamWriter( w );
		
		writer.writeStartDocument("UTF-8", "1.0");
		writer.writeStartElement( "videos" );
		
		createElement(writer, data);
		
		writer.writeEndElement();
		writer.writeEndDocument();
		"
		writer.flush();
		
		writer.close();
		
		System.out.println( "Job Done!" );

	}

	private void createElement(XMLStreamWriter writer, List<Video> data) throws XMLStreamException 
	{
		for (Video video : data) 
		{
			writer.writeStartElement( "video" );
			writer.writeAttribute( Finals.ID , Integer.toString(video.getID()) );
			
			writer.writeStartElement( Finals.TYPE );
			writer.writeCharacters( video.getType() );
			writer.writeEndElement();

			writer.writeStartElement( Finals.TITLE );
			writer.writeCharacters( video.getTitle() );
			writer.writeEndElement();
			
			writer.writeStartElement( Finals.LOCATION );
			writer.writeCharacters( video.getLocation() );
			writer.writeEndElement();
			
			writer.writeStartElement( Finals.GENRE );
			writer.writeCharacters( video.getGenre() );
			writer.writeEndElement();
			
			writer.writeStartElement( Finals.GROUP );
			writer.writeCharacters( video.getGroup() );
			writer.writeEndElement();
			
			writer.writeStartElement( Finals.EPISODE_N );
			writer.writeCharacters( Integer.toString( video.getEpisodeN() ) );
			writer.writeEndElement();

			writer.writeStartElement( Finals.SEASON_N );
			writer.writeCharacters( Integer.toString( video.getSeasonN() ) );
			writer.writeEndElement();

			writer.writeEndElement();
		}
	}
	
}
