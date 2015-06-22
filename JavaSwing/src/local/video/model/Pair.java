package local.video.model;

import local.models.top.Types;


public class Pair {
	private Types type;
	private String name;
	
	public Pair( Types type, String name )
	{
		this.type = type;
		this.name = name;
	}
	
	public Types getType()
	{
		return type;
	}
	
	public String getName()
	{
		return name;
	}
}
