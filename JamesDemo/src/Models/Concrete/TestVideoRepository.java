package Models.Concrete;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.Video;
import Models.Abstract.IVideoRepository;

public class TestVideoRepository implements IVideoRepository{
	
	public TestVideoRepository()
	{
	}
	
	@Override
	public ArrayList<Video> GetVideos() {
		ArrayList<Video> videos = new ArrayList<Video>();
		
		Video video = new Video();
		video.Title = "Bob";
		videos.add(video);
		
		Video video2 = new Video();
		video2.Title = "Your ma";
		videos.add(video2);
		
		return videos;
	}

}
