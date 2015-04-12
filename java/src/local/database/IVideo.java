package local.database;

public interface IVideo
{
	
	int getID();
	void setID( int id );
	
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
