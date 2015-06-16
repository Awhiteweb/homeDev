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
	public List<Video> searchVideos( String search, String category )
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
			int i = this.conn.update( statement );
			System.out.println( "rows updated " + i );
		}
		catch ( SQLException e )
		{
			System.err.println( "error updating database" );
//			e.printStackTrace();
		}
	}
	
	@Override
	public boolean updateVideo( Video video )
	{
		Video old = getVideoByID( video.getID() );
		ArrayList<String> updates = new ArrayList<String>();
		
		if ( !old.getTitle().equals( video.getTitle() ) )
		{
			updates.add( "UPDATE `main` SET `title` = `" + video.getTitle() + "` WHERE `id` = " + video.getID() );
		}
		if ( !old.getGenre().equals( video.getGenre() ) )
		{
			String[] genres = video.getGenre().split( ";" );
			List<ResultList> dbGenres = getGenres();
			for ( int i = 0; i < genres.length; i++ )
			{
				boolean exists = false;
				for ( ResultList rl : dbGenres )
				{
					if (rl.equals( genres[i] ) )
					{
						exists = true;
						break;
					}
				}
				if ( !exists )
				{
					updates.add( "UPDATE `genres` SET `genre` = " + genres[i] );
					// select from genres to get id, to update video_genres
				}
			}
		}
		if ( !old.getGroup().equals( video.getGroup() ) )
		{
			updates.add( "UPDATE `video_groups` SET `" );
		}
		if ( old.getEpisodeN() != video.getEpisodeN() )
		{
			updates.add( "UPDATE `main` SET `series_num` = `" + video.getEpisodeN() + "` WHERE `id` = " + video.getID() );
		}
		if ( old.getSeasonN() != video.getSeasonN() )
		{
			updates.add( "UPDATE `main` SET `tv_season` = `" + video.getSeasonN() + "` WHERE `id` = " + video.getID() );
		}

		
		
		
//		String updateTitle = "UPDATE `main` SET `" + Finals.TITLE + "` = `" + title + "` WHERE `" + Finals.ID + "` = " + id ;
//		String updateGenre = "";
		return true;
	}


	private List<ResultList> getGenres()
	{
		List<ResultList> genres = new ArrayList<ResultList>();
		try
		{
			ResultSet result = this.conn.query( "SELECT video_genres.id, video_genres.genre AS genreID, genres.genre FROM video_genres JOIN genres ON video_genres.genre = genres.id" );
			while ( result.next() )
			{
				genres.add( new ResultList( result.getString( "id" ), result.getString( "genreID" ), result.getString( "genre" ) ) );
			}
		}
		catch ( SQLException e )
		{
			System.err.println( "failed to get genres from database" );
		}
		
		return genres;
	}

	private List<String> getGroups()
	{
		List<String> groups = new ArrayList<String>();
		return groups;
	}

	private class ResultList
	{
		private int videoID;
		private int gID;
		private String g;
		
		public ResultList( String videoID, String gID, String g )
		{
			this.videoID = Integer.parseInt( g );
			this.gID = Integer.parseInt( gID );
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
}
