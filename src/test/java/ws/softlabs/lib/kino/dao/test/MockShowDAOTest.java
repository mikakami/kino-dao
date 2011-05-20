package ws.softlabs.lib.kino.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ws.softlabs.lib.kino.dao.TestTools;
import ws.softlabs.lib.kino.dao.server.impl.mock.MockShowDAOImpl;
import ws.softlabs.lib.kino.model.client.Hall;
import ws.softlabs.lib.kino.model.client.Movie;
import ws.softlabs.lib.kino.model.client.Show;
import ws.softlabs.lib.kino.model.client.Theater;


@SuppressWarnings("unused")
public class MockShowDAOTest {
	
	private static String testName  = "test_name";
	private static String testHtml  = "test_html";
	
	private static int MAX_SHOWS    = 7;
	private static int MAX_MOVIES   = 7;
	private static int MAX_HALLS    = 3;
	private static int MAX_THEATERS = 3;
	private static int MAX_TIME     = 1000000;
	
	List<Theater> 	theaters = new ArrayList<Theater>();
	List<Hall>  	halls    = new ArrayList<Hall>();
	List<Movie> 	movies   = new ArrayList<Movie>();
	List<Show> 		shows    = new ArrayList<Show>();

	private static long maxShowId;
	
	private static MockShowDAOImpl testDao  = new MockShowDAOImpl();
	
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
		/* clear dao */
		testDao.getList().removeAll(testDao.getList());
		
		maxShowId = 0;
		
		generateMovies();
		generateTheaters();
		generateHalls();
		generateShows();
		
		//printShows();
		//printParams();
		
		for (Show show : shows)
			testDao.add(show);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGet() {
		int idx = TestTools.random(testDao.getList().size());
		Show  object   = testDao.getList().get(idx);
		Hall  hall     = (Hall)object.getHall();
		Date date1 	   = (Date)object.getDate().clone();
		Date date2 	   = (Date)date1.clone();
		date2.setDate(date1.getDate() + 1);
		assertTrue(object.equals(testDao.get(object.getId())));
		assertTrue(object.equals(testDao.get(hall,date1)));
		assertFalse(object.equals(testDao.get(0L)));
		assertFalse(object.equals(testDao.get(hall, date2)));
	}
	@Test
	public void testUpdate() {
		int idx1 = TestTools.random(testDao.getList().size());
		int idx2 = idx1;
		while ( idx1 == idx2 )
			idx2 = TestTools.random(testDao.getList().size());
		Show object1 = testDao.getList().get(idx1);		
		Show object2 = testDao.getList().get(idx2);		
		
		object1.setId(new Long((Long)object2.getId()));
		object1.setHall(new Hall((Hall)object2.getHall()));
		object1.setMovie(new Movie((Movie)object2.getMovie()));		
		object1.setDate((Date)object2.getDate().clone());		
		object1.setPrice(object2.getPrice());		
		
		assertTrue(testDao.update(object1));
		assertTrue(object2.equals(testDao.get(object1.getId())));
	}
	@Test
	public void testRemove() {
		int idx1 = TestTools.random(testDao.getList().size());
		int idx2 = idx1;
		while ( idx1 == idx2 )
			idx2 = TestTools.random(testDao.getList().size());
		Show object1 = testDao.getList().get(idx1);		
		Show object2 = testDao.getList().get(idx2);		
		assertTrue (testDao.remove(object1));
		assertFalse(object1.equals(testDao.get(object1.getId())));	
		assertTrue(object2.equals(testDao.get(object2.getId())));	
	}
	@Test
	public void testGetNextIndex() {
		assertEquals((long)maxShowId+1, (long)testDao.getNextId());
	}		

	/*
	@Test
	public void tetsGetList() {
		assertEquals(testDao.getList().size(), shows.size());		
		int cnt = 0;
		for(Hall hall : halls) {
			System.out.println(hall);
			for(Show show : shows) 
				if (hall.equals(show.getHall()))
					cnt++;
			assertEquals(testDao.getList(hall, new DateTime(System.currentTimeMillis())).size(), 
						 cnt);
		}
	}
/**/
}
