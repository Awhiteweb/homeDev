package database;

import java.util.List;

public interface IMovieRepository
{
	List<String> getMovies();
	
	List<String> setMovies();
	
	List<String> writeMovies();
	
	List<String> readMovies();
	
}
