package local.video.model;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class stores the current items details and the lists store all the possible options
 * for the update
 *
 */
public class ItemToUpdate {

	public ItemToUpdate()
	{
	}

	private String  title,
					location,
					genre,
					group,
					type;
	private int 	id,
					episode,
					season;
	private List<String> genreList = new ArrayList<String>();
	private List<String> groupList = new ArrayList<String>();
	private List<Integer> episodeList = new ArrayList<Integer>();
	private List<Integer> seasonList = new ArrayList<Integer>();
	
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
	
	public int getEpisode()
	{
		return this.episode;
	}
	
	public void setEpisode( int episode )
	{
		this.episode = episode;
	}
	
	public int getSeason()
	{
		return this.season;
	}
	
	public void setSeason( int season )
	{
		this.season = season;
	}

	public List<String> getGenreList()
	{
		return genreList;
	}

	public void setGenreList( List<String> genreList )
	{
		this.genreList  = genreList;
	}

	public List<String> getGroupList()
	{
		return groupList;
	}

	public void setGroupList( List<String> groupList )
	{
		this.groupList = groupList;
	}

	public List<Integer> getEpisodeList()
	{
		return episodeList;
	}

	public void setEpisodeList( List<Integer> episodeList )
	{
		this.episodeList = episodeList;
	}

	public List<Integer> getSeasonList()
	{
		return seasonList;
	}
	
	public void setSeasonList( List<Integer> seasonList )
	{
		this.seasonList = seasonList;
	}

}
