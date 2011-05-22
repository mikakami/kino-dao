package ws.softlabs.lib.kino.dao.server.impl.mock;

import java.util.ArrayList;
import java.util.List;

import ws.softlabs.lib.kino.dao.server.intf.MovieDAO;
import ws.softlabs.lib.kino.model.client.Movie;
import ws.softlabs.lib.util.client.CollectionUtils;

public class MockMovieDAOImpl implements MovieDAO {

	private static List<Movie> movies = new ArrayList<Movie>();
	
	public Movie get(Long id) {
		for(Movie m : movies)
			if (m.getId() == id)
				return m;
		return null;
	}
	public Movie get(String name, String url) {
		for(Movie m : movies)
			if (m.getName().equals(name))
				return m;
		return null;
	}
	public List<Movie> getList() {
		return movies;
	}
	public boolean add(Movie movie) {
		if (movie == null) return false;
		if (get(movie.getId()) == null) {
			movies.add(movie);
			return true;
		}
		return false;
	}
	public boolean update(Movie movie) {
		Movie existing;
		if (movie == null) return false;
		if ((existing = get(movie.getId())) != null) {
			existing.setName(movie.getName());
			existing.setUrl(movie.getUrl());
			return true;
		}
		return false;
	}
	public Long getNextId() {
		if (movies == null) return -1L;
		Long result = 0L;
		for (Movie m : movies)
			result = Math.max(result, m.getId());
		return result+1;
	}
	public boolean remove(Movie movie) {
		if (movie == null) return false;		
		return ( movies.remove(CollectionUtils.getIndexOf(movies.toArray(), movie)) != null );
	}
	@Override
	public List<String> getRawList() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
