package local.database.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import local.dto.Video;
import local.dto.VideoProvider;
import local.models.top.Finals;

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
	
	public List<Video> readXML( File f ) throws Exception
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		
		parser.parse( f, this );
		
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
		case "parent":
			break;
			
		case "video":
			video = new Video();
			video.setID( Integer.parseInt( attributes.getValue( Finals.ID ) ) );
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
		case "parent":
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
		case Finals.TYPE:
			if ( content.equalsIgnoreCase( this.searchTitle ) && Finals.TYPE.equalsIgnoreCase( this.searchCat ) )
			{
				this.check = true;
			}
			video.setType( content );
			break;
			
		case Finals.TITLE:
			content = content.replace( "&amp;", "&" );
			content = content.replace( "&apos;", "'" );
			video.setTitle( content );
			break;
		
		case Finals.LOCATION:
			content = content.replace( "&amp;", "&" );
			content = content.replace( "&apos;", "'" );
			video.setLocation( content );
			break;			
			
		case Finals.GENRE:
			if ( content.equalsIgnoreCase( this.searchTitle ) && Finals.GENRE.equalsIgnoreCase( this.searchCat ) )
			{
				this.check = true;
			}
			content = content.replace( "&amp;", "&" );
			content = content.replace( "&apos;", "'" );
			video.setGenre( content );
			break;
			
		case Finals.GROUP:
			if ( content.equalsIgnoreCase( this.searchTitle ) && Finals.GROUP.equalsIgnoreCase( this.searchCat ) )
			{
				this.check = true;
			}
			content = content.replace( "&amp;", "&" );
			content = content.replace( "&apos;", "'" );
			video.setGroup( content );
			break;
			
		case Finals.EPISODE_N:
			if ( content.equalsIgnoreCase( this.searchTitle ) && Finals.EPISODE_N.equalsIgnoreCase( this.searchCat ) )
			{
				this.check = true;
			}
			video.setEpisodeN( Integer.parseInt( content ) );
			break;
			
		case Finals.SEASON_N:
			if ( content.equalsIgnoreCase( this.searchTitle ) && Finals.SEASON_N.equalsIgnoreCase( this.searchCat ) )
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
