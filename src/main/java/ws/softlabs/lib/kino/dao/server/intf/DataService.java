package ws.softlabs.lib.kino.dao.server.intf;

import java.util.Date;
import java.util.List;

import ws.softlabs.lib.kino.model.client.Hall;
import ws.softlabs.lib.kino.model.client.Movie;
import ws.softlabs.lib.kino.model.client.Show;
import ws.softlabs.lib.kino.model.client.Theater;

public interface DataService {
	
	/* get or create */
	public Hall 			getHall(Theater theatre, String name, String html);
	public Movie 			getMovie(String name, String url);	
	public Show 			getShow(Hall hall, Movie movie, Date dateTime, List<Integer> price);
	public Theater 			getTheater(String name, String url);	

	/* special queries */
	public List<Theater> 	getTheaterList();
	public List<Hall> 		getTheaterHallList(Theater theater);
	public List<Show> 		getShowList(Hall hall, Date date);
	public List<String> 	getShowDaysList(Theater t);

	/*  tools  */
	public void				clearDatabase();
	public void				clearTheaters();
	public void				clearHalls();
	public void				clearShows();
	public void				clearMovies();
	public List<String>		getRawTheaterList();
	public List<String>		getRawHallList();
	public List<String>		getRawDayList();
	public List<String>		getRawMovieList();
	public List<String>		getRawShowList();

}
