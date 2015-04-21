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

public class XMLReader extends DefaultHandler
{

	private List<Video> data;
	private Video video;
	private String currentElement;
	private StringBuilder currentText;
	public String searchTitle;
	public String searchCat;
	private boolean check;
	
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
			this.check = false;
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
			if ( this.check || ( this.searchTitle == null ) )
			{
				data.add( video );
			}
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
			if ( content.equalsIgnoreCase( this.searchTitle ) && VideoProvider.GENRE.equalsIgnoreCase( this.searchCat ) )
			{
				this.check = true;
			}
			video.setGenre( content );
			break;
			
		case VideoProvider.GROUP:
			if ( content.equalsIgnoreCase( this.searchTitle ) && VideoProvider.GROUP.equalsIgnoreCase( this.searchCat ) )
			{
				this.check = true;
			}
			video.setGroup( content );
			break;
			
		case VideoProvider.SERIES_N:
			if ( content.equalsIgnoreCase( this.searchTitle ) && VideoProvider.SERIES_N.equalsIgnoreCase( this.searchCat ) )
			{
				this.check = true;
			}
			video.setSeriesN( Integer.parseInt( content ) );
			break;
			
		case VideoProvider.SEASON_N:
			if ( content.equalsIgnoreCase( this.searchTitle ) && VideoProvider.SEASON_N.equalsIgnoreCase( this.searchCat ) )
			{
				this.check = true;
			}
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
