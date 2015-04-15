package local.database.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import local.dto.Video;
import local.models.top.IVideoRepo;

public class MysqlVideoRepo implements IVideoRepo
{
	private MysqlConnect conn;
	private ResultSet resultSet = null;
	
	public MysqlVideoRepo(MysqlConnect conn)
	{
		this.conn = conn;
	}
	
	@Override
	public List<Video> getVideos()
	{
		try
		{
			this.resultSet = this.conn.query();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		List<Video> list = new ArrayList<Video>();
		
		try
		{
			
			while ( this.resultSet.next() )
			{
				Video video = new Video();
				video.setID( Integer.parseInt( resultSet.getString( "ID" ) ) );
				video.setTitle( resultSet.getString( "Title" ) );
				video.setLocation( resultSet.getString( "Path" ) );
				video.setGenre( resultSet.getString( "Genre" ) );
				video.setGroup( resultSet.getString( "Group" ) );
				video.setSeriesN( Integer.parseInt( resultSet.getString( "Series Number" ) ) );
				video.setSeasonN( Integer.parseInt( resultSet.getString( "Season Number" ) ) );
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

}
