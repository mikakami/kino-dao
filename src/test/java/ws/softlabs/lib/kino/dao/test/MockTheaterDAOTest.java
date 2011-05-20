package ws.softlabs.lib.kino.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ws.softlabs.lib.kino.dao.TestTools;
import ws.softlabs.lib.kino.dao.server.impl.mock.MockTheaterDAOImpl;
import ws.softlabs.lib.kino.model.client.Theater;

public class MockTheaterDAOTest {

	private static String testName  = "test_name";
	private static String testUrl   = "test_url";
	private static int MAX_THEATERS = 10;
	
	private static MockTheaterDAOImpl testDao = 
		new MockTheaterDAOImpl();
	
//	private void printTheaters(String string) {
//		if (( string != null ) && (!string.isEmpty()))
//			System.out.println("------ " + string + " ------");
//		for ( Theater t : objectDao.getList())
//			System.out.println(t);
//		System.out.println("---------------");
//	}
	
	@Before
	public void prepare() {
		/* clear dao */
		testDao.getList().removeAll(testDao.getList());

		for(int i = 0; i < MAX_THEATERS; i++) {
			Theater t = new Theater(new Long(i)+1, "Theater #" + i, "TheaterUrl #" + i);
			testDao.add(t);
		}
	}
	@Test
	public void tetsGet() {
		int idx = TestTools.random(testDao.getList().size());
		Theater object = testDao.getList().get(idx);
		assertTrue(object.equals(testDao.get(object.getId())));
		assertTrue(object.equals(testDao.get(object.getName(), object.getUrl())));
		assertFalse(object.equals(testDao.get(0L)));
		assertFalse(object.equals(testDao.get(testName, testUrl)));
	}
	@Test
	public void testUpdate() {
		int idx = TestTools.random(testDao.getList().size());
		Theater object = testDao.getList().get(idx);		
		object.setName(testName);
		object.setUrl(testUrl);		
		assertTrue(testDao.update(object));
		assertTrue(object.equals(testDao.get(testName, testUrl)));
	}
	@Test
	public void testRemove() {
		//printTheaters("");
		int idx = TestTools.random(testDao.getList().size());
		Theater object = testDao.getList().get(idx);		
		assertTrue (testDao.remove(object));
		assertFalse(object.equals(testDao.get(testName, testUrl)));	
		//printTheaters("");
	}
	@Test
	public void testGetNextIndex() {
		assertEquals((long)MAX_THEATERS+1, (long)testDao.getNextId());
	}
}
