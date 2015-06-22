package local.dto;

import java.util.List;

import local.database.mysql.MysqlUOW;
import local.database.xml.XmlUOW;
import local.models.top.IUnitOfWork;
import local.models.top.Repos;

/**
 * 
 * instantiation requires 'Repos' enum to call either a XML or MYSQL data provider
 * 
 * @author Alex.White
 *
 */
public class VideoProvider {
	
	private IUnitOfWork uow = null;
	
	public VideoProvider( Repos repo )
	{
		switch ( repo )
		{
		case MYSQL:
			this.uow = new MysqlUOW();
			break;
		case XML:
			this.uow = new XmlUOW();
			break;
		}
	}
	
	/**
	 * retrieves all the available videos and outputs the console log
	 * 
	 * @throws Exception
	 */
	public void getVideos() throws Exception 
	{
		List<Video> videos = this.uow.VideoRepo().getVideos();
				
		for( Video video : videos)
		{
			System.out.println( video.getType() );
			System.out.println( video.getTitle() );
		}
	}
	
	/**
	 * returns all the available videos as objects in a Java util List
	 * 
	 * @return List<Video>
	 * @throws Exception
	 */
	public List<Video> returnVideos() throws Exception 
	{
		List<Video> videos = this.uow.VideoRepo().getVideos();
				
		return videos;
	}
	
	/**
	 * returns all the available videos that match the specified search criteria as objects in a Java util List
	 * 
	 * @param String searchTitle, the title to search for
	 * @param String searchCat, the category to search in
	 * @return List<Video>
	 * @throws Exception
	 */
	public List<Video> returnVideos( String searchTitle, String searchCat ) throws Exception
	{
		List<Video> videos = this.uow.VideoRepo().searchVideos( searchTitle, searchCat );
		
		return videos;	
	}

	/**
	 * NOT for MYSQL
	 * 
	 * writes videos to the XML file
	 * 
	 * ! does not currently do anything !
	 * 
	 * @param List<Videos>
	 */
	public void writeVideos( List<Video> data )
	{
		this.uow.VideoRepo().writeVideos( data );
	}
	
	/**
	 * 
	 * 
	 * @param String statement
	 */
	public void sendStatement( String statement )
	{
		this.uow.VideoRepo().sendPreparedStatement( statement );
	}


	/**
	 * 
	 * 
	 * @param Video
	 * @return
	 */
	public boolean updateVideo( Video video )
	{
		return this.uow.VideoRepo().updateVideo( video );
	}
	
}
