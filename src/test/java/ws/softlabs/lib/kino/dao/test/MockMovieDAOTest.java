package ws.softlabs.lib.kino.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ws.softlabs.lib.kino.dao.TestTools;
import ws.softlabs.lib.kino.dao.server.impl.mock.MockMovieDAOImpl;
import ws.softlabs.lib.kino.model.client.Movie;


public class MockMovieDAOTest {

	private static String testName  = "test_name";
	private static String testUrl   = "test_url";
	private static int MAX_MOVIES = 10;
	
	private static MockMovieDAOImpl testDao = 
		new MockMovieDAOImpl();
	
	@Before
	public void prepare() {
		/* clear dao */
		testDao.getList().removeAll(testDao.getList());

		for(int i = 0; i < MAX_MOVIES; i++) {
			Movie m = new Movie(new Long(i)+1, "Movie #" + i, "MovieURL #" + i);
			testDao.add(m);
		}
	}

	@Test
	public void tetsGet() {
		int idx = TestTools.random(testDao.getList().size());
		Movie object = testDao.getList().get(idx);
		assertTrue(object.equals(testDao.get(object.getId())));
		assertTrue(object.equals(testDao.get(object.getName(), object.getUrl())));
		assertFalse(object.equals(testDao.get(0L)));
		assertFalse(object.equals(testDao.get(testName, testUrl)));
	}
	@Test
	public void testUpdate() {
		int idx = TestTools.random(testDao.getList().size());
		Movie object = testDao.getList().get(idx);		
		object.setName(testName);
		object.setUrl(testUrl);		
		assertTrue(testDao.update(object));
		assertTrue(object.equals(testDao.get(testName, testUrl)));
	}
	@Test
	public void testRemove() {
		int idx = TestTools.random(testDao.getList().size());
		Movie object = testDao.getList().get(idx);		
		assertTrue (testDao.remove(object));
		assertFalse(object.equals(testDao.get(testName, testUrl)));	
	}
	@Test
	public void testGetNextIndex() {
		assertEquals((long)MAX_MOVIES+1, (long)testDao.getNextId());
	}
}
