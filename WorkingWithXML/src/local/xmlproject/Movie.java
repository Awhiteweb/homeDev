package local.xmlproject;

public class Movie
{

	private String name;
	private String genre;
	private String group;
	private String location;
	private String kind;
	private String number;
	
	public static final String
		NAME="Name",
		GENRE="Genre",
		GROUP="Series",
		LOCATION="Location",
		KIND="Kind",
		NUMBER="Episode Order";
	
	public Movie()
	{
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getGenre()
	{
		return genre;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}
	
	public String getGroup()
	{
		return group;
	}

	public void setGroup(String group)
	{
		this.group = group;
	}
	
	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}
	
	public String getKind()
	{
		return kind;
	}

	public void setKind(String kind)
	{
		this.kind = kind;
	}
	
	public String getNumber()
	{
		return number;
	}
	public void setNumber( String number )
	{
		this.number = number;		
	}
	
	@Override
	public String toString()
	{
		return this.name + ", " + this.genre + ", " + this.group + ", " + this.location + ", " + this.kind + ", " + this.number;
	}

}
