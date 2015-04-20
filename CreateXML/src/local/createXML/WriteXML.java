package local.createXML;

import java.io.StringWriter;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import local.dto.Video;
import local.dto.VideoProvider;

public class WriteXML {

	public static void main(String[] args) throws Exception
	{
		VideoProvider video = new VideoProvider();
//		video.getVideos();

		List<Video> data = video.returnVideos();
		createXML creator = new createXML();
		creator.createDocument( data , "./files/file.xml" );
		
		
		
	}
}
