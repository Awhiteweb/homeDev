package local.video.constants;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

@SuppressWarnings ( "rawtypes" )
public class ListStore
{
	private DefaultListModel titleList;
	private DefaultListModel genreList;
	private DefaultListModel groupList;
	private DefaultListModel episodeList;
	private DefaultListModel seasonList;
	private ArrayList locationList;

	
	public void setTitles( DefaultListModel titleList )
	{
		this.titleList = titleList;
	}
	
	public DefaultListModel getTitles()
	{
		return this.titleList;
	}

	public void setGenres( DefaultListModel genreList )
	{
		this.genreList = genreList;
	}
	
	public DefaultListModel getGenres()
	{
		return this.genreList;
	}

	public void setGroups( DefaultListModel groupList )
	{
		this.groupList = groupList;
	}
	
	public DefaultListModel getGroups()
	{
		return this.groupList;
	}

	public void setEpisodes( DefaultListModel episodeList )
	{
		this.episodeList = episodeList;
	}
	
	public DefaultListModel getEpisodes()
	{
		return this.episodeList;
	}

	public void setSeasons( DefaultListModel seasonList )
	{
		this.seasonList = seasonList;
	}
	
	public DefaultListModel getSeasons()
	{
		return this.seasonList;
	}

	public void setLocations( ArrayList locationList )
	{
		this.locationList = locationList;
	}
	
	public ArrayList getLocations()
	{
		return this.locationList;
	}
	
}
