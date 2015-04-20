package local.database.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import local.dto.Video;
import local.dto.VideoProvider;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler
{

	private List<Video> data;
	private Video video;
	private String currentElement;
	private StringBuilder currentText;
	
	public List<Video> readXML(String filename) throws Exception
	{
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		
		parser.parse( new File( filename ), this );
		
		return data;	
	}
	
	@Override
	public void startDocument() throws SAXException
	{
		data = new ArrayList<>();
	}
	
	@Override
	public void endDocument() throws SAXException
	{
	}
	
	@Override
	public void startElement( String uri, String localName, String qName,
			Attributes attributes ) throws SAXException
	{
		currentElement = qName;
		
		switch ( currentElement )
		{
		case "videos":
			break;
			
		case "video":
			video = new Video();
			break;
			
		default:
			currentText = new StringBuilder();
			break;
		}
		
	}
	
	@Override
	public void endElement( String uri, String localName, String qName )
			throws SAXException
	{
		
		switch ( qName )
		{
		case "videos":
			return;
			
		case "video":
			data.add( video );
			return;
			
		default:
			break;	
		}
		
		
		String content = currentText.toString();
		
		switch ( currentElement )
		{
		case VideoProvider.TYPE:
			video.setType( content );
			break;
			
		case VideoProvider.TITLE:
			video.setTitle( content );
			break;
		
		case VideoProvider.LOCATION:
			video.setLocation( content );
			break;			
			
		case VideoProvider.GENRE:
			video.setGenre( content );
			break;
			
		case VideoProvider.GROUP:
			video.setGroup( content );
			break;
			
		case VideoProvider.SERIES_N:
			video.setSeriesN( Integer.parseInt( content ) );
			break;
			
		case VideoProvider.SEASON_N:
			video.setSeasonN( Integer.parseInt( content ) );
			break;
			
		default:
			break;
		}
		
		currentElement = "";
		
	}
	
	@Override
	public void characters( char[] ch, int start, int length )
			throws SAXException
	{
		if( currentText != null )
		{
			currentText.append( ch, start, length );
		}
	}
	
}
