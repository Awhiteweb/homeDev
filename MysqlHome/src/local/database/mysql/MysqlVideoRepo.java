package local.database.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import local.dto.Video;
import local.models.top.Finals;
import local.models.top.IVideoRepo;

public class MysqlVideoRepo implements IVideoRepo
{
	private MysqlConnect conn;
	
	private String selectFrom = "SELECT `m`.`id` AS `" + Finals.ID + "`, "
								+ "`m`.`title` AS `" + Finals.TITLE + "`, "
								+ "`m`.`path` AS `" + Finals.LOCATION + "`, "
								+ "`ge`.`genre` AS `" + Finals.GENRE + "`, "
								+ "`gr`.`group` AS `" + Finals.GROUP + "`, "
								+ "`m`.`series_num` AS `" + Finals.SERIES_N + "`, "
								+ "`m`.`tv_season` AS `" + Finals.SEASON_N + "`, "
								+ "`t`.`genre` AS `" + Finals.TYPE + "` "
								+ "FROM `main` AS `m` "
								+ "LEFT JOIN `genres` AS `ge` "
								+ "ON `m`.`genre` = `ge`.`id` "
								+ "LEFT JOIN `groups` AS `gr` "
								+ "ON `m`.`group` = `gr`.`id` "
								+ "LEFT JOIN `type` AS `t` "
								+ "ON `m`.`type` = `t`.`id` ";
	
	
	public MysqlVideoRepo(MysqlConnect conn)
	{
		this.conn = conn;
	}
	
	private List<Video> MapResultSet( ResultSet resultSet )
	{
		List<Video> list = new ArrayList<Video>();
		
		String t;
		try
		{
			
			while ( resultSet.next() )
			{
//				System.out.println( "Group " + resultSet.getString( Finals.GROUP ) );
				
				Video video = new Video();
				
				video.setID( Integer.parseInt( resultSet.getString( Finals.ID ) ) );
				video.setType( resultSet.getString( Finals.TYPE ) );
				video.setTitle( resultSet.getString( Finals.TITLE ) );
				video.setLocation( resultSet.getString( Finals.LOCATION ) );
				
				if ( resultSet.getString( Finals.GENRE ) != null )
				{
					video.setGenre( resultSet.getString( Finals.GENRE ) );
				}
				
				if ( resultSet.getString( Finals.GROUP ) != null )
				{
					video.setGroup( resultSet.getString( Finals.GROUP ) );
				}
				
				if ( resultSet.getString( Finals.SERIES_N ) != null )
				{
					video.setSeriesN( Integer.parseInt( resultSet.getString( Finals.SERIES_N ) ) );
				}
				
				if ( resultSet.getString( Finals.SEASON_N ) != null )
				{
					video.setSeasonN( Integer.parseInt( resultSet.getString( Finals.SEASON_N ) ) );
				}
				
				list.add( video );
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<Video> getVideos( int amount )
	{
		List<Video> videos = null;
		
		try
		{
			ResultSet result = this.conn.query( selectFrom + " LIMIT " + amount);
			videos = MapResultSet( result );
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return videos;
	}
	
	@Override
	public Video getVideoByID( int id )
	{
		List<Video> videos = null;
		
		try
		{
			ResultSet result = this.conn.query( selectFrom + " WHERE `id`=" + id);
			videos = MapResultSet( result );
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return videos.get( 0 );

	}
	

	@Override
	public List<Video> searchVideos( String search )
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeVideos( List<Video> videos )
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateVideos( List<Video> videos )
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Video> getVideos()
	{
		List<Video> videos = null;
		
		try
		{
			ResultSet result = this.conn.query( selectFrom + " ORDER BY " + Finals.TYPE + ", " + Finals.TITLE);
			videos = MapResultSet( result );
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return videos;

	}

}
