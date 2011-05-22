package ws.softlabs.lib.kino.dao.server.impl.mock;

import java.util.ArrayList;
import java.util.List;

import ws.softlabs.lib.kino.dao.server.intf.TheaterDAO;
import ws.softlabs.lib.kino.model.client.Theater;
import ws.softlabs.lib.util.client.CollectionUtils;


public class MockTheaterDAOImpl implements TheaterDAO {

	private static List<Theater> theaters = new ArrayList<Theater>();
	
	public Theater get(Long long1) {
		for(Theater t : theaters)
			if (t.getId() == long1)
				return t;
		return null;
	}
	public Theater get(String name, String url) {
		for(Theater t : theaters)
			if (t.getName().equals(name))
				return t;
		return null;
	}
	public List<Theater> getList() {
		return theaters;
	}
	public boolean add(Theater theater) {
		if (theater == null) return false;
		if (get(theater.getId()) == null) {
			theaters.add(theater);
			return true;
		}
		return false;
	}
	public boolean update(Theater theater) {
		Theater existing;
		if (theater == null) return false;
		if ((existing = get(theater.getId())) != null) {
			existing.setName(theater.getName());
			existing.setUrl(theater.getUrl());
			return true;
		}
		return false;
	}
	public Long getNextId() {
		if (theaters == null) return -1L;
		Long result = 0L;
		for (Theater t : theaters)
			result = Math.max(result, t.getId());
		return result+1;
	}
	public boolean remove(Theater theater) {
		if (theater == null) return false;		
		Object[] list = theaters.toArray();
		return ( theaters.remove(CollectionUtils.getIndexOf(list, (Object)theater)) != null );
	}
	@Override
	public List<String> getRawList() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
