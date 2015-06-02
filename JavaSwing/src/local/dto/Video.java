package local.dto;

/**
 * Video class, to set/get video details
 * @author Alex.White
 *
 */
public class Video
{
	
	private String  title,
					location,
					genre,
					group,
					type;
	private int 	id,
					episodeN,
					seasonN;
	
	public Video()
	{
	}
	
	public int getID() 
	{
		return this.id;
	}
	

	public void setID(int id) 
	{
		this.id = id;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public void setType( String type )
	{
		this.type = type;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	

	public void setTitle( String title )
	{
		this.title = title;
	}
	

	public String getLocation()
	{
		return this.location;
	}
	

	public void setLocation( String location )
	{
		this.location = location;
	}

	public String getGenre()
	{
	
		return this.genre;
	}
	

	public void setGenre( String genre )
	{
		this.genre = genre;
	}
	

	public String getGroup()
	{
		return this.group;
	}
	

	public void setGroup( String group )
	{
		this.group = group;
	}
	

	public int getEpisodeN()
	{
		return this.episodeN;
	}
	

	public void setEpisodeN( int episodeN )
	{
		this.episodeN = episodeN;
	}
	
	public int getSeasonN()
	{
		return this.seasonN;
	}
	
	public void setSeasonN( int seasonN )
	{
		this.seasonN = seasonN;
	}
	
	
}
