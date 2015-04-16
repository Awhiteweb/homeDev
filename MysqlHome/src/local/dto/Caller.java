package local.dto;

public class Caller
{	
	public static void main( String[] args ) throws Exception
	{
		
		VideoProvider controller = new VideoProvider();
		controller.getVideos();

	}
	
	
}
