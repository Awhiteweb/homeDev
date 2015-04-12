package local.database;

public class Movie implements IVideo
{
	private String  title,
					location,
					genre,
					group;
	private int 	seriesN;
	
	public Movie()
	{
	}

	@Override
	public String getTitle()
	{
		return this.title;
	}

	@Override
	public void setTitle( String title )
	{
		this.title = title;
	}

	@Override
	public String getLocation()
	{
		return this.location;
	}

	@Override
	public void setLocation( String location )
	{
		this.location = location;
	}

	@Override
	public String getGenre()
	{

		return this.genre;
	}

	@Override
	public void setGenre( String genre )
	{
		this.genre = genre;
	}

	@Override
	public String getGroup()
	{
		return this.group;
	}

	@Override
	public void setGroup( String group )
	{
		this.group = group;
	}

	@Override
	public int getSeriesN()
	{
		return this.seriesN;
	}

	@Override
	public void setSeriesN( int seriesN )
	{
		this.seriesN = seriesN;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
		
	}
	
	
}
