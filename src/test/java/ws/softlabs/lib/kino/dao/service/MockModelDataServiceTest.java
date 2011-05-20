package ws.softlabs.lib.kino.dao.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ws.softlabs.lib.kino.dao.TestTools;
import ws.softlabs.lib.kino.dao.server.service.mock.MockModelDataService;
import ws.softlabs.lib.kino.model.client.Hall;
import ws.softlabs.lib.kino.model.client.Movie;
import ws.softlabs.lib.kino.model.client.Show;
import ws.softlabs.lib.kino.model.client.Theater;

@SuppressWarnings("unused")
public class MockModelDataServiceTest {

	private static MockModelDataService dataService = new MockModelDataService();

	private static int MAX_SHOWS    = 7;
	private static int MAX_MOVIES   = 7;
	private static int MAX_HALLS    = 3;
	private static int MAX_THEATERS = 3;
	private static int MAX_TIME     = 1000000;	
	
	List<Theater> 	theaters = new ArrayList<Theater>();
	List<Hall>  	halls    = new ArrayList<Hall>();
	List<Movie> 	movies   = new ArrayList<Movie>();
	List<Show> 		shows    = new ArrayList<Show>();
	
	private static String testTheaterName = "test_theater_name";
	private static String testTheaterUrl  = "test_theater_url";
	private static String testMovieName   = "test_movie_name";
	private static String testMovieUrl    = "test_movie_url";
	
	private static long maxShowId;
	
	private void generateMovies() {
		for (int i = 0; i < MAX_MOVIES; i++) {			
			movies.add(
				new Movie(new Long(i)   + (1000+1),
						  "MovieName#" + (i+1),
						  "MovieUrl#"  + (i+1)));
		}
	}
	private void generateTheaters(){
		for (int i = 0; i < MAX_THEATERS; i++) {
			theaters.add(new Theater(new Long(i) + (2000+1),
									"TheaterName#" + (i+1),
									"TheaterUrl#" + (i+1)));
		}
	}
	private void generateHalls() {
		int cnt = 0;
		for(Theater t : theaters) {
			int numHalls = TestTools.random(MAX_HALLS+1) + 1;
			if (numHalls == 1) {
				cnt++;
				halls.add(new Hall (new Long(cnt+1),
									t,
									null,
									null));
			}
			else 
				for(int i = 0; i < numHalls; i++) {
					cnt++;
					halls.add(new Hall (new Long(cnt+1),
										t,
										t.getName() + "_hallName#" + (i+1),
										t.getName() + "_hallHtml#" + (i+1)));
				}
		}
	}
	private void generateShows() {
		int cnt = 0;
		for(Hall h : halls) {
			int numShows = TestTools.random(MAX_SHOWS+1)+1;
			for(int i = 0; i < numShows; i++) {
				cnt++;
				Movie m = movies.get(TestTools.random(MAX_MOVIES));
				Show show = new Show (	new Long(cnt+1+3000),
										h,
										m,
										new Date(TestTools.random(MAX_TIME)),
										new ArrayList<Integer>());				
				shows.add(show);
				maxShowId = Math.max(maxShowId, show.getId());;
			}
		}
	}

	private void printShows() {
		for(Show s : shows)
			System.out.println("\t *** " + s);
	}
	private void printParams() {
		System.out.println("-----------------------------------------------------");
		System.out.println("theaters: " + theaters.size());
		System.out.println("halls: "    + halls.size());
		System.out.println("shows: "    + shows.size());
		System.out.println("movies: "   + movies.size());
		// get halls for theater
		int cnt0 = 0;
		for(Theater t : theaters){
			cnt0++;
			int cnt1 = 0;
			for(Hall h : halls) {
				if (h.getTheatre().equals(t))
					cnt1++;
			}
			System.out.println("  theater " + cnt0 + " halls: " + cnt1);
			// get shows for hall
			for(Hall h : halls) {				
				if (h.getTheatre().equals(t)) {
					int cnt2 = 0;
					for (Show s : shows) {
						if (s.getHall().equals(h))
							cnt2++;
					}
					System.out.println("      " + " shows: " + cnt2);
				}
			}
		}
	}
	
	@Before
	public void prepare() {
		dataService.clearDatabase();
		generateMovies();
		generateTheaters();
		generateHalls();
		generateShows();		
		//printShows();
		//printParams();
	}	
	@Test
	public void testGetTheater() {
		int idx1 = TestTools.random(theaters.size());
		Theater t1 = theaters.get(idx1);
		Theater t2 = (Theater)dataService.getTheater("name2", "url2");
		Theater t3 = (Theater)dataService.getTheater(t1.getName(), t1.getUrl());
		assertFalse(t1.equals(t2));
		assertEquals((long)t2.getId(), 1L);
		assertTrue(t1.equals(t3));
		assertEquals((long)t3.getId(), 2L);
		Theater t4 = new Theater(0L, testTheaterName, testTheaterUrl);
		Theater t5 = (Theater) dataService.getTheater(testTheaterName, testTheaterUrl);
		assertTrue(t4.equals(t5));
		assertEquals((long)t5.getId(), 3L);
	}
	@Test
	public void testGetMovie() {
		//Theater t = theaters.get(TestTools.random(theaters.size()));		
		Movie m1   = movies.get(TestTools.random(movies.size()));
		Movie m2   = (Movie)dataService.getMovie("name2", "url2");
		Movie m3   = (Movie)dataService.getMovie(m1.getName(), m1.getUrl());
		assertFalse(m1.equals(m2));
		assertEquals((long)m2.getId(), 1L);
		assertTrue(m1.equals(m3));
		assertEquals((long)m3.getId(), 2L);
		Movie m4 = new Movie(0L, testMovieName, testMovieUrl);
		Movie m5 = (Movie) dataService.getMovie(testMovieName, testMovieUrl);
		assertTrue(m4.equals(m5));
		assertEquals((long)m5.getId(), 3L);
	}
	@Test
	public void testGetHall() {
		int idx1 = TestTools.random(halls.size());
		Hall h1 = halls.get(idx1);	
		
		String s1 = h1.getName();
		String s2 = h1.getHtml();
		Theater t = (Theater)h1.getTheatre();
		
		Hall h2 = (Hall)dataService.getHall(t, s1, s2);
		Hall h3 = (Hall)dataService.getHall(t, s1, s2);
		
		assertTrue(h1.equals(h2));
		assertTrue(h1.equals(h3));
		assertEquals((long)h2.getId(), 1L);
		assertEquals((long)h3.getId(), 1L);
		
		Hall h4 = (Hall)dataService.getHall(t, "test", "test");
		assertEquals((long)h4.getId(), 2L);
	}
	@Test
	public void testGetHallNull() {
		int idx1 = TestTools.random(halls.size());
		Hall h1 = halls.get(idx1);			
		Hall h2 = new Hall(0L, (Theater)h1.getTheatre(), null, null);
		Hall h3 = (Hall)dataService.getHall((Theater)h1.getTheatre(), null, null);
		assertTrue(h3.equals(h2));
		assertEquals((long)h3.getId(), 1L);		
	}
	@Test
	public void testGetShow() {
		int idx1 = TestTools.random(shows.size());
		Show s1  = shows.get(idx1);
		Show s2 = new Show(0L, (Hall)s1.getHall(), (Movie)s1.getMovie(), s1.getDate(), null/*s1.getPrice()/**/);
		Show s3 = (Show)dataService.getShow((Hall)s1.getHall(), (Movie)s1.getMovie(), s1.getDate(), s1.getPrice());
		assertTrue(s3.equals(s2));
		assertEquals((long)s3.getId(), 1L);
	}
}
