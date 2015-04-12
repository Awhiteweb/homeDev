package local.createXML;

import java.io.StringWriter;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import local.database.Movie;
import local.database.VideoProvider;

public class createXML {

	public void createDocument (List<Movie> data, String filename) throws XMLStreamException
	{
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		StringWriter sw = new StringWriter();
		XMLStreamWriter writer = factory.createXMLStreamWriter( sw );
		
		writer.writeStartDocument();
		
		createElement(writer, data);
		
		writer.writeEndDocument();
		
		writer.flush();
		
		writer.close();
		
		System.out.println( sw.toString() );

	}

	private void createElement(XMLStreamWriter writer, List<Movie> data) throws XMLStreamException 
	{
		for (Movie movie : data) 
		{
			writer.writeStartElement( "movie" );
			writer.writeAttribute( VideoProvider.ID , Integer.toString(movie.getID()) );
			
				writer.writeStartElement( VideoProvider.TITLE );
				writer.writeCharacters( movie.getTitle() );
				writer.writeEndElement();
				
				writer.writeStartElement( VideoProvider.LOCATION );
				writer.writeCharacters( movie.getLocation() );
				writer.writeEndElement();
				
				writer.writeStartElement( VideoProvider.GENRE );
				writer.writeCharacters( movie.getGenre() );
				writer.writeEndElement();
				
				writer.writeStartElement( VideoProvider.GROUP );
				writer.writeCharacters( movie.getGroup() );
				writer.writeEndElement();
				
				writer.writeStartElement( VideoProvider.SERIESN );
				writer.writeCharacters( Integer.toString(movie.getSeriesN()) );
				writer.writeEndElement();
			
			writer.writeEndElement();
	
		}
	}

}
