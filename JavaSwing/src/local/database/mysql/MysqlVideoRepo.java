package local.database.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import local.dto.Video;
import local.models.top.Finals;
import local.models.top.IVideoRepo;
import local.models.top.Types;

public class MysqlVideoRepo implements IVideoRepo
{
	private MysqlConnect conn;
	
	private String selectFrom = "SELECT `m`.`id` AS `" + Finals.ID + "`, "
								+ "`m`.`title` AS `" + Finals.TITLE + "`, "
								+ "`m`.`path` AS `" + Finals.LOCATION + "`, "
								+ "`ge`.`genre` AS `" + Finals.GENRE + "`, "
								+ "`gr`.`group` AS `" + Finals.GROUP + "`, "
								+ "`m`.`series_num` AS `" + Finals.EPISODE_N + "`, "
								+ "`m`.`tv_season` AS `" + Finals.SEASON_N + "`, "
								+ "`t`.`genre` AS `" + Finals.TYPE + "` "
								+ "FROM `main` AS `m` "
								+ "LEFT JOIN `video_genres` AS `vge` "
								+ "ON `m`.`id` = `vge`.`id` "
								+ "LEFT JOIN `genres` AS `ge` "
								+ "ON `vge`.`genre` = `ge`.`id` "
								+ "LEFT JOIN `video_groups` AS `vgr` " 
								+ "ON `m`.`id` = `vgr`.`id` "
								+ "LEFT JOIN `groups` AS `gr` "
								+ "ON `vgr`.`group` = `gr`.`id` " 
								+ "JOIN `type` AS `t` "
								+ "ON `m`.`type` = `t`.`id` ";
	
	
	
	public MysqlVideoRepo( MysqlConnect conn )
	{
		this.conn = conn;
	}
	
	private List<Video> MapResultSet( ResultSet resultSet )
	{
		List<Video> list = new ArrayList<Video>();
		try
		{
			System.out.println( "start video creation" );
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
				
				if ( resultSet.getString( Finals.EPISODE_N ) != null )
				{
					video.setEpisodeN( Integer.parseInt( resultSet.getString( Finals.EPISODE_N ) ) );
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
	public Video getVideoByID( int id )
	{
		List<Video> videos = null;
		
		try
		{
			String query = selectFrom + " WHERE `m`.`id` = " + id;
			ResultSet result = this.conn.query( query );
			videos = MapResultSet( result );
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return videos.get( 0 );
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

	@Override
	public void sendPreparedStatement ( String statement )
	{
		try
		{
			this.conn.update( statement );
		}
		catch ( SQLException e )
		{
			System.err.println( "error updating database" );
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean updateVideo( Video video )
	{
		// TODO needs to get the video from the xml otherwise with concatenated objects, duplicates will keep getting created.
		Video old = getVideoByID( video.getID() );
		ArrayList<String> updates = new ArrayList<String>();
		try 
		{
			if ( !old.getTitle().equals( video.getTitle() ) )
			{
				this.conn.update( "UPDATE `main` SET `title` = `" + video.getTitle() + "` WHERE `id` = " + video.getID() );
			}
			
			if ( video.getGenre() != null && video.getGenre().length() > 1 )
			{
				if ( old.getGenre() == null || !old.getGenre().equals( video.getGenre() ) )
				{
					compare( video, old, Types.GENRE );
				}
			}
			
			if ( video.getGroup() != null && video.getGroup().length() > 1 )
			{
				if ( old.getGroup() == null || !old.getGroup().equals( video.getGroup() ) )
				{
					compare( video, old, Types.GROUP );
				}
			}
			
			if ( old.getEpisodeN() != video.getEpisodeN() )
			{
				this.conn.update( "UPDATE `main` SET `series_num` = `" + video.getEpisodeN() + "` WHERE `id` = " + video.getID() );
			}
			if ( old.getSeasonN() != video.getSeasonN() )
			{
				this.conn.update( "UPDATE `main` SET `tv_season` = `" + video.getSeasonN() + "` WHERE `id` = " + video.getID() );
			}
	
			
			for ( String update : updates )
			{
				System.out.println( update );
			}
			
		}
		catch ( SQLException e )
		{
			System.err.println( "error in MYSQL UpdateVideo" );
			e.printStackTrace();
		}
		return true;
	}

	private void compare( Video video, Video old, Types type ) throws SQLException
	{
		String update = "";
		String table = "", col = "", vItem = "", oItem = "";
		String[] oItems;
		
		switch ( type )
		{
			case GENRE:
				vItem = video.getGenre();
				oItem = old.getGenre();
				update = "genres";
				table = "video_genres";
				col = "genre";
				break;
				
			case GROUP:
				vItem = video.getGroup();
				oItem = old.getGroup();
				update = "groups";
				table = "video_groups";
				col = "group";
				break;
				
			default:
				break;
		}
		
		String[] vItems = prepString( vItem );
		if ( oItem != null )
		{
			oItems = oItem.split( "," );
		}
		else
		{
			oItems = new String[1];
		}
		
		List<ResultList> results = getExisting( table, col, update );
		
		for ( int i = 0; i < vItems.length; i++ )
		{
			boolean exists = false;
			for ( ResultList rl : results )
			{
//				System.out.println( rl.getG() + " : " + vItems[i] );
				if ( rl.getG().equals( vItems[i] ) )
				{
					exists = true;
					boolean t = false;
					for ( String o : oItems )
					{
						if ( o.equals( rl.getG() ) )
							t = true;
					}
					if ( !t )
					{
						System.out.println( "INSERT IGNORE INTO `" + table + "` (`id`, `" + col + "`) VALUES (" + video.getID() + ", " + rl.getGID() + ")" );
						this.conn.update( "INSERT IGNORE INTO `" + table + "` (`id`, `" + col + "`) VALUES (" + video.getID() + ", " + rl.getGID() + ")" );
					}
					break;
				}
			}
			if ( !exists )
			{
				System.out.println( "INSERT IGNORE INTO `" + update + "` (`" + col + "`) VALUES (" + vItems[i] + ")" );
				this.conn.update( "INSERT IGNORE INTO `" + update + "` (`" + col + "`) VALUES (" + vItems[i] + ")" );
				int id = getNewID( update, col, vItems[i] );
				this.conn.update( "INSERT IGNORE INTO `" + table + "` (`id`, `" + col + "`) VALUES (" + video.getID() + ", " + id + ")" );
			}
		}
	}

	private List<ResultList> getExisting(String table , String col, String cols )
	{
		List<ResultList> items = new ArrayList<ResultList>();
		try
		{
			ResultSet result = this.conn.query( "SELECT " + table + ".id, " + table + "." + col + " AS gID, " + cols + "." + col + " FROM " + table + " JOIN " + cols + " ON " + table + "." + col + " = " + cols + ".id" );
			while ( result.next() )
			{
				items.add( new ResultList( result.getInt( "id" ), result.getInt( "gID" ), result.getString( col ) ) );
			}
		}
		catch ( SQLException e )
		{
			System.err.println( "failed to get " + col + "s from database" );
		}		
		return items;
	}

	private String[] prepString( String input )
	{
		String[] output = input.split( "," );
		for ( int i = 0; i < output.length; i++ )
		{
			output[i] = output[i].replace( "&", "&amp;" );
			output[i] = output[i].replace( "'", "&apos" );
		}
		return output;
	}
	
	private int getNewID( String table, String col, String item )
	{
		int id = 0;
		try
		{
			ResultSet result = this.conn.query( "SELECT `id` FROM `" + table + "` WHERE `" + col + "` LIKE '" + item + "'" );
			while ( result.next() )
			{
				id = result.getInt( "id" );
			}
		}
		catch ( SQLException e )
		{
			System.err.println( "Error using getNewID query" );
			id--;
		}
		return id;
	}

	private class ResultList
	{
		private int videoID;
		private int gID;
		private String g;
		
		public ResultList( int vID, int gID, String g )
		{
			this.videoID = vID ;
			this.gID = gID;
			this.g = g;
		}
		
		public int getVideoID()
		{
			return videoID;
		}
		
		public int getGID()
		{
			return gID;
		}
		
		public String getG()
		{
			return g;
		}
	}

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
	
	/**
	 * Not for MYSQL use
	 */
	@Override
	public List<Video> searchVideos( String searchTitle, String searchCat )
	{
		return null;
	}

	/**
	 * Not for MYSQL use
	 */
	@Override
	public void writeVideos( List<Video> videos )
	{		
	}
}
