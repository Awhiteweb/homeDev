package local.video.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import local.dto.Video;
import local.models.top.Categories;

public class CreateTable
{
	
	private MyTable tModel;
	private List<String> data = new ArrayList<String>();
	
	
	public CreateTable( List<Video> videos, Categories cat )
	{
		switch ( cat )
		{
			case ID:
				getIDs( videos );
				break;

			case TITLE:
				getTitles( videos );
				break;
			
			case LOCATION:
				getLocations( videos );
				break;

			case GENRE:
				getGenres( videos );
				break;

			case GROUP:
				getGroups( videos );
				break;
			
			case EPISODE:
				getEpisodes( videos );
				break;

			case SEASON:
				getSeasons( videos );
				break;

			case TYPE:
				getTypes( videos );
				break;

			default:
				break;
		}	
	}


	private void getTypes( List<Video> videos )
	{
		for ( Video video : videos )
		{
			this.data.add( video.getType() );
		}
	}


	private void getSeasons( List<Video> videos )
	{
		for ( Video video : videos )
		{
			this.data.add( Integer.toString( video.getSeasonN() ) );
		}
	}


	private void getEpisodes( List<Video> videos )
	{
		for ( Video video : videos )
		{
			this.data.add( Integer.toString( video.getSeriesN() ) );
		}
	}


	private void getGroups( List<Video> videos )
	{
		for ( Video video : videos )
		{
			this.data.add( video.getGroup() );
		}
	}


	private void getGenres( List<Video> videos )
	{
		for ( Video video : videos )
		{
			this.data.add( video.getGenre() );
		}
	}


	private void getLocations( List<Video> videos )
	{
		for ( Video video : videos )
		{
			this.data.add( video.getLocation() );
		}
	}


	private void getIDs( List<Video> videos )
	{
		for ( Video video : videos )
		{
			this.data.add( Integer.toString( video.getID() ) );
		}
	}

	private void getTitles( List<Video> videos )
	{
		for ( Video video : videos )
		{
			this.data.add( video.getTitle() );
		}
	}

	
	public TableModel getTable()
	{
		return makeTable( this.data );
	}


	private TableModel makeTable( List<String> data )
	{
		this.tModel = new MyTable();

		for ( String s : data )
		{
			Object[] obj = { new Boolean( false ) , s };
			this.tModel.addRow( obj );
		}

		return tModel;
	}	
    
}
