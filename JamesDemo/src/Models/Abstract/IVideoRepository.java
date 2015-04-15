package Models.Abstract;

import java.util.List;
import DTO.Video;

public interface IVideoRepository {
	List<Video> GetVideos();
}
