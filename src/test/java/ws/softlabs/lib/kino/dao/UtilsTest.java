package ws.softlabs.lib.kino.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ws.softlabs.lib.kino.model.client.Movie;
import ws.softlabs.lib.util.client.CollectionUtils;


public class UtilsTest {
	
	List<Movie> movies = new ArrayList<Movie>();
	Movie movie1, movie2, movie3, movie4, movie5, movie6;
	
	@Before
	public void setData() {
		movie1 = new Movie(1L);
		movie1.init("Name1", "URL1");

		movie2 = new Movie(2L);
		movie2.init("Name2", "URL2");
		
		movie3 = new Movie(3L);
		movie3.init("Name3", "URL3");		

		movie4 = new Movie(4L);
		movie4.init("Name4", "URL4");
		
		movie5 = new Movie(5L);
		movie5.init("Name5", "URL5");
		
		movie6 = new Movie(6L);
		movie6.init("Name6", "URL6");

		movies.add(movie1);
		movies.add(movie2);
		movies.add(movie3);
		movies.add(movie4);
		movies.add(movie5);		
	}
	@Test
	public void testGetIndexOf() {
		int idx1 = CollectionUtils.getIndexOf(movies.toArray(), movie1);
		int idx2 = CollectionUtils.getIndexOf(movies.toArray(), movie2);
		int idx3 = CollectionUtils.getIndexOf(movies.toArray(), movie3);
		int idx4 = CollectionUtils.getIndexOf(movies.toArray(), movie4);
		int idx5 = CollectionUtils.getIndexOf(movies.toArray(), movie5);
		int idx6 = CollectionUtils.getIndexOf(movies.toArray(), movie6);
		int idx7 = CollectionUtils.getIndexOf(movies.toArray(), null);
		assertEquals(idx1, 0);
		assertEquals(idx2, 1);
		assertEquals(idx3, 2);
		assertEquals(idx4, 3);
		assertEquals(idx5, 4);
		assertEquals(idx6, -1);		
		assertEquals(idx7, -1);		
	}

}
