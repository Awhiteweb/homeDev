package database;

public interface IVideo
{
	
	String getTitle();
	void setTitle( String title );
	
	String getLocation();
	void setLocation( String location );
	
	String getGenre();
	void setGenre( String genre );
	
	String getGroup();
	void setGroup( String group );
	
	int getSeriesN();
	void setSeriesN( int seriesN );
	
}
