package local.dto;

import java.io.FileWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import local.database.mysql.MysqlConnect;
import local.models.top.Repos;

public class Caller
{	
	public static void main( String[] args ) throws Exception
	{

		FileWriter fw = new FileWriter( "C:\\Users\\Alex.White\\Desktop\\g-s.txt" );
		
		MysqlConnect conn = new MysqlConnect();
		String query = "SELECT * FROM `genres`";
		ResultSet results = conn.query( query );
		while ( results.next() )
		{
			int i = results.getInt( 1 );
			String c = results.getString( 2 );
			c = c.replace( "'", "''" );
			String d = String.format( "UPDATE `genres` SET `genre` = '%s' WHERE `id` = %d;\n", c, i );
			fw.write( d );
		}
		query = "SELECT * FROM `groups`";
		results = conn.query( query );
		while ( results.next() )
		{
			int i = results.getInt( 1 );
			String c = results.getString( 2 );
			c = c.replace( "'", "''" );
			String d = String.format( "UPDATE `groups` SET `group` = '%s' WHERE `id` = %d;\n", c, i );
			fw.write( d );
		}

		fw.close();
		conn.close();

//		VideoProvider controller = new VideoProvider( Repos.MYSQL );
//		List<Video> videos = controller.returnVideos();

//		for ( Video video : videos )
//		{
//			if ( video.getGroup() != null )
//			{
//				if ( video.getGenre().equals( "tv drama" ) )
//				{
//					String statement = String.format( "UPDATE `main` SET `genre` = %d WHERE `id` = %d", 25, video.getID() );
//					controller.sendStatement( statement );
//				}
//			}
//		}
		
//		FileWriter fw = new FileWriter( "C:\\Users\\Alex.White\\Desktop\\videos.txt" );
//		
//		for ( Video video : videos )
//		{
//			String s = video.getLocation().replace( "'", "''" );
//			String statement = String.format( "UPDATE `main` SET `path` = '%s' WHERE `id` = %d;\n", s, video.getID() );
//			fw.write( statement );
//		}
//		
//		fw.close();
//		System.out.println( "finished" );
		
		
//		List<Video> videos = controller.returnVideos(); // gets all videos

		
//		String searchTitle = "comedy";
//		String searchCat = "genre";
//		
//		List<Video> videos = controller.returnVideos( searchTitle, searchCat ); // searches for videos
//		
//		for (Video video : videos) {
//			
//			System.out.println( video.getTitle() );
//			System.out.println( video.getLocation() );
//			System.out.println( video.getGenre() );
//			System.out.println( video.getGroup() );
//			System.out.println("");
//			
//		}
		
	}
	
	
}
