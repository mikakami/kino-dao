package ws.softlabs.lib.kino.dao.server.service.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ws.softlabs.lib.kino.dao.server.impl.mock.MockHallDAOImpl;
import ws.softlabs.lib.kino.dao.server.impl.mock.MockMovieDAOImpl;
import ws.softlabs.lib.kino.dao.server.impl.mock.MockShowDAOImpl;
import ws.softlabs.lib.kino.dao.server.impl.mock.MockTheaterDAOImpl;
import ws.softlabs.lib.kino.dao.server.intf.DataService;
import ws.softlabs.lib.kino.dao.server.intf.HallDAO;
import ws.softlabs.lib.kino.dao.server.intf.MovieDAO;
import ws.softlabs.lib.kino.dao.server.intf.ShowDAO;
import ws.softlabs.lib.kino.dao.server.intf.TheaterDAO;
import ws.softlabs.lib.kino.model.client.Hall;
import ws.softlabs.lib.kino.model.client.Movie;
import ws.softlabs.lib.kino.model.client.Show;
import ws.softlabs.lib.kino.model.client.Theater;
import ws.softlabs.lib.util.client.DateUtils;
import ws.softlabs.lib.util.client.DayComparator;

public class MockModelDataService implements DataService {
	
	private static final TheaterDAO daoTheater 	= new MockTheaterDAOImpl();
	private static final MovieDAO 	daoMovie 	= new MockMovieDAOImpl();
	private static final HallDAO 	daoHall 	= new MockHallDAOImpl();
	private static final ShowDAO 	daoShow 	= new MockShowDAOImpl();
	
	/* get or create */
	public Theater getTheater(String name, String url) {
		Theater theater = daoTheater.get(name, url);
		if (theater!= null)
			return theater;		
		theater = new Theater(daoTheater.getNextId());
		theater.init(name, url);
		if (daoTheater.add(theater))		
			return theater;
		else
			return null;
	}
	public Hall    getHall(Theater theatre, String name, String html) {
		Hall hall = daoHall.get((Theater)theatre, name);
		if (hall != null)
			return hall;		
		hall = new Hall(daoHall.getNextId());
		hall.init(theatre, name, html);
		if (daoHall.add(hall))		
			return hall;
		else
			return null;		
	}
	public Movie   getMovie(String name, String url) {
		Movie movie = daoMovie.get(name, url);
		if (movie != null)
			return movie;		
		movie = new Movie(daoMovie.getNextId());
		movie.init(name, url);
		if (daoMovie.add(movie))		
			return movie;
		else
			return null;		
	}
	public Show    getShow(Hall hall, Movie movie, Date dateTime,
							List<Integer> price) {
		Show show = daoShow.get((Hall)hall, dateTime);
		if (show != null)
			return show;		
		show = new Show(daoShow.getNextId());
		show.init(hall, movie, dateTime, price);
		if (daoShow.add(show))		
			return show;
		else
			return null;		
	}
	
	/* add */
	public boolean addTheater(Theater theater) {
		return daoTheater.add(theater);	
	}
	public boolean addHall(Hall hall) {
		return daoHall.add(hall);
	}
	public boolean addMovie(Movie movie) {
		return daoMovie.add(movie);
	}
	public boolean addShow(Show show) {
		return daoShow.add(show);
	}

	/* update */ 
	public boolean updateTheater(Theater theater) {
		return daoTheater.add(theater);	
	}
	public boolean updateHall(Hall hall) {
		return daoHall.add(hall);
	}
	public boolean updateMovie(Movie movie) {
		return daoMovie.add(movie);
	}
	public boolean updateShow(Show show) {
		return daoShow.add(show);
	}
	
	/* remove */
	public boolean removeTheater(Theater theater) {
		return daoTheater.add(theater);	
	}
	public boolean removeHall(Hall hall) {
		return daoHall.add(hall);
	}
	public boolean removeMovie(Movie movie) {
		return daoMovie.add(movie);
	}
	public boolean removeShow(Show show) {
		return daoShow.add(show);
	}

	/* get existing */
	public Theater getTheaterExisting(String name, String url) {
		return daoTheater.get(name, url);
	}
	public Hall    getHallExisting(Theater theatre, String name, String html) {
		return daoHall.get(theatre, name);
	}
	public Movie   getMovieExisting(String name, String url) {
		return daoMovie.get(name, url);
	}
	public Show    getShowExisting(Hall hall, Movie movie, Date dateTime,
							List<Integer> price) {
		return daoShow.get((Hall)hall, dateTime);
	}
	
	/* special queries */
	public List<Theater> getTheaterList(){
		List<Theater> newList = new ArrayList<Theater>();
		for(Theater t : daoTheater.getList())
			newList.add(t);
		return newList;
	}
	public List<Hall>    getTheaterHallList(Theater theater) {
		List<Hall> newList = new ArrayList<Hall>();
		for(Hall h : daoHall.getList())
			if (h.getTheatre().equals(theater))
				newList.add(h);
		return newList;
	}
	public List<Show>    getShowList(Hall hall, Date date){
		List<Show> newList = new ArrayList<Show>();
		for(Show s : daoShow.getList())
			if (s.getHall().equals(hall) &&
				DateUtils.equalDates(s.getDate(), date)) {
					newList.add(s);
			}
		return newList;
	}
	public List<String>  getShowDaysList(Theater theater) {
		Set<String> days = new TreeSet<String>();
		for(Show s : daoShow.getList()){
			if (s.getHall().getTheatre().equals(theater))
				days.add(DateUtils.dateToStringSpecial(s.getDate()));
		}
		List<String> daysList = new ArrayList<String>(days);
		Collections.sort(daysList, new DayComparator());
		return daysList;
	}

	public List<Show> getAllShows() {
		return null;
	}

	public void clearDatabase() {
		daoTheater.getList().removeAll(daoTheater.getList());
		daoMovie.getList().removeAll  (daoMovie.getList());
		daoHall.getList().removeAll   (daoHall.getList());
		daoShow.getList().removeAll   (daoShow.getList());
	}
	@Override
	public List<String> getRawTheaterList() {
		// NOT IMPLEMENTED FOR MOCK-DAO
		return null;
	}
	@Override
	public List<String> getRawHallList() {
		// NOT IMPLEMENTED FOR MOCK-DAO
		return null;
	}
	@Override
	public List<String> getRawDayList() {
		// NOT IMPLEMENTED FOR MOCK-DAO
		return null;
	}
	@Override
	public List<String> getRawMovieList() {
		// NOT IMPLEMENTED FOR MOCK-DAO
		return null;
	}
	@Override
	public List<String> getRawShowList() {
		// NOT IMPLEMENTED FOR MOCK-DAO
		return null;
	}
	@Override
	public void clearTheaters() {
		// NOT IMPLEMENTED FOR MOCK-DAO
	}
	@Override
	public void clearHalls() {
		// NOT IMPLEMENTED FOR MOCK-DAO
	}
	@Override
	public void clearShows() {
		// NOT IMPLEMENTED FOR MOCK-DAO
	}
	@Override
	public void clearMovies() {
		// NOT IMPLEMENTED FOR MOCK-DAO
	}
	@Override
	public void clearShows(Hall hall, Date date) {
		// NOT IMPLEMENTED FOR MOCK-DAO
	}
}
