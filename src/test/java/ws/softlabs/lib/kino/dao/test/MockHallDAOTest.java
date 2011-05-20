package ws.softlabs.lib.kino.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ws.softlabs.lib.kino.dao.TestTools;
import ws.softlabs.lib.kino.dao.server.impl.mock.MockHallDAOImpl;
import ws.softlabs.lib.kino.model.client.Hall;
import ws.softlabs.lib.kino.model.client.Theater;

public class MockHallDAOTest {
	
	private static String testName  = "test_name";
	private static String testHtml  = "test_html";
	private static int MAX_HALLS = 10;
	
	Theater theater1, 	theater2;
	int 	cnt1=0,		cnt2=0;
	
	private static MockHallDAOImpl testDao = 
		new MockHallDAOImpl();
	
	@Before
	public void prepare() {
		/* clear dao */
		testDao.getList().removeAll(testDao.getList());
		
		theater1 = new Theater(new Long(1) + 1000, "Theater #1", "TheaterUrl #1");
		theater2 = new Theater(new Long(2) + 1000, "Theater #2", "TheaterUrl #2");
		
		for(int i = 0; i < MAX_HALLS; i++) {
			Theater t = ( i < MAX_HALLS / 2) ? theater1 : theater2;
			if (i < MAX_HALLS / 2) 	cnt1++;
			else 					cnt2++;
			Hall h = new Hall(  new Long(i)+1,
								t,
								"Movie #" + i, 
								"MovieHTML #" + i);
			testDao.add(h);
		}
	}

	@Test
	public void tetsGetList() {
		assertEquals(testDao.getList().size(), MAX_HALLS);
		assertEquals(testDao.getList(theater1).size(), cnt1);
	}
	
	@Test
	public void tetsGet() {
		int idx = TestTools.random(testDao.getList().size());
		Hall object = testDao.getList().get(idx);
		assertTrue(object.equals(testDao.get(object.getId())));
		assertTrue(object.equals(testDao.get((Theater)object.getTheatre(), object.getName())));
		assertFalse(object.equals(testDao.get(0L)));
		assertFalse(object.equals(testDao.get((Theater)object.getTheatre(), testName)));
	}
	@Test
	public void testUpdate() {
		int idx = TestTools.random(testDao.getList().size());
		Hall object = testDao.getList().get(idx);		
		object.setName(testName);
		object.setHtml(testHtml);		
		assertTrue(testDao.update(object));
		assertTrue(object.equals(testDao.get((Theater)object.getTheatre(), testName)));
	}
	@Test
	public void testRemove() {
		int idx = TestTools.random(testDao.getList().size());
		Hall object = testDao.getList().get(idx);		
		assertTrue (testDao.remove(object));
		assertFalse(object.equals(testDao.get((Theater)object.getTheatre(), testName)));	
	}
	@Test
	public void testGetNextIndex() {
		assertEquals((long)MAX_HALLS+1, (long)testDao.getNextId());
	}	
}
