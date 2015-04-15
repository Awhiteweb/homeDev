import java.util.List;

import DTO.Video;
import Models.Abstract.IUnitOfWork;
import Models.Concrete.MySQLUnitOfWork;
import Models.Concrete.TestUnitOfWork;
import Models.Concrete.TestVideoRepository;


public class Init {
	public static void main( String[] args )
	{
		IUnitOfWork unitOfWork = new TestUnitOfWork();
		
		List<Video> videos = null;
		
		try
		{
			videos = unitOfWork.VideoRepository().GetVideos();	
		}
		catch(Exception e){}
		
		for( Video video : videos)
		{
			System.out.println( video.Title );
		}
	}
}
