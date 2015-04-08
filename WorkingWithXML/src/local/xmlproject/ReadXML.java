package local.xmlproject;

import java.util.Iterator;
import java.util.List;

import javax.xml.transform.sax.SAXResult;


 
public class ReadXML
{

	
	public static void main(String[] args) throws Exception
	{
		String filename = "files/iTunes Music Library.xml";
		
		SAXMovieHandler saxHandler = new SAXMovieHandler();
		/*List<Movie> data =*/ saxHandler.readDataFromXML( filename );
		
		System.out.println("finished");
		
	}
	
}
