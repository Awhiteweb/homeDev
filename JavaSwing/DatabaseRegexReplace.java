package com.testarea.local;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import local.dto.Video;
import local.dto.VideoProvider;
import local.models.top.Repos;

public class DatabaseRegexReplace
{
	private static Pattern pattern = Pattern.compile( "^file:.*" ); //^file:[/\\w%]+?Season%20(\\d+).*$

	public static void main( String[] args )
	{
		VideoProvider controller = new VideoProvider( Repos.MYSQL );
		try
		{
			List<Video> data = controller.returnVideos();
			for ( Video video : data )
			{
				Matcher m = pattern.matcher( video.getLocation() );
				String s = video.getLocation().substring( 16 );
//				s = s.replace( "'", "''" );
				if ( m.find() )
				{
//					s = m.group( 1 );
					String statement = String.format( "UPDATE `main` SET `path` = '%s' WHERE `id` = %d", s, video.getID() );
					controller.sendStatement( statement );
				}
			}
			
		}
		catch ( Exception ex )
		{
			ex.printStackTrace();
		}
	}
}
