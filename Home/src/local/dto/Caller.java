package local.dto;

import java.util.List;

public class Caller
{	
	public static void main( String[] args ) throws Exception
	{
		
		VideoProvider controller = new VideoProvider();
//		controller.getVideos();
		
//		List<Video> videos = controller.returnVideos(); // gets all videos
		
		String searchTitle = "comedy";
		String searchCat = "genre";
		
		List<Video> videos = controller.returnVideos( searchTitle, searchCat ); // searches for videos
		
		for (Video video : videos) {
			
			System.out.println( video.getTitle() );
			System.out.println( video.getLocation() );
			System.out.println( video.getGenre() );
			System.out.println( video.getGroup() );
			System.out.println("");
			
		}
		
	}
	
	
}
