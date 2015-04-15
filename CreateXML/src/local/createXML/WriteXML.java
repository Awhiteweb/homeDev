package local.createXML;

import java.io.StringWriter;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import local.database.Movie;
import local.database.VideoProvider;

public class WriteXML {

	public static void main(String[] args) throws Exception
	{
		
		List<Movie> data = VideoProvider.getVideos();
		
		createXML creator = new createXML();
		creator.createDocument( data , "file.xml" );
		
	}
}
