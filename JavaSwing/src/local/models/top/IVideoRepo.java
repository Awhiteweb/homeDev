package local.models.top;

import java.util.List;

import local.dto.Video;

public interface IVideoRepo
{

	/**
	 * returns specified number of videos as objects in a Java util List
	 * 
	 * @param int amount of videos required
	 * @return List<Video>
	 * @throws Exception
	 */
	List<Video> getVideos( int amount );

	/**
	 * returns a single video
	 * 
	 * @param int id of video required
	 * @return Video object
	 */
	Video getVideoByID( int id );
	
	/**
	 * searches the XML file for specified videos
	 * 
	 * @param String searchTitle string of title to search for
	 * @param String searchCat string of category to search in
	 * @return List<Video>
	 */
	List<Video> searchVideos(String searchTitle, String searchCat);
	
	/**
	 * writes Videos to XMl from a List provided by from the database
	 * 
	 * @param videos
	 */
	void writeVideos( List<Video> videos );
	
	/**
	 * sends a prepared statement to the database
	 * 
	 * @param String statement
	 */
	void sendPreparedStatement ( String statement );

	/**
	 * gets all available videos
	 * 
	 * @return List<Video>
	 */
	List<Video> getVideos();

	/**
	 * Updates the provided video
	 * 
	 * @param Video
	 * @return boolean
	 */
	boolean updateVideo( Video video );
	
}
