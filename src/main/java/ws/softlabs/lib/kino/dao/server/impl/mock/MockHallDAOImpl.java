package ws.softlabs.lib.kino.dao.server.impl.mock;

import java.util.ArrayList;
import java.util.List;

import ws.softlabs.lib.kino.dao.server.intf.*;
import ws.softlabs.lib.kino.model.client.*;
import ws.softlabs.lib.util.client.CollectionUtils;

public class MockHallDAOImpl implements HallDAO {

	private static List<Hall> halls = new ArrayList<Hall>();

	public Hall get(Long id) {
		for(Hall h : halls)
			if (h.getId() == id)
				return h;
		return null;
	}
	public Hall get(Theater theater, String name) {
		for(Hall h : halls)
			if (	(h.getName() != null) && 
					(h.getName().equals(name)) &&
					(h.getTheatre().equals(theater))
			   )
				return h;
			else if ((h.getName() == null) && 
					 (name == null) &&
					 (h.getTheatre().equals(theater))
					)
				return h;
		return null;
	}
	public List<Hall> getList() {
		return halls;
	}
	public List<Hall> getList(Theater theater) {
		List<Hall> theaterHalls = new ArrayList<Hall>();
		for (Hall h : halls) 
			if(h.getTheatre().equals(theater))
				theaterHalls.add(h);
		return theaterHalls;
	}
	public boolean add(Hall hall) {
		if (hall == null) return false;
		if (get(hall.getId()) == null) {
			halls.add(hall);
			return true;
		}
		return false;
	}
	public boolean update(Hall hall) {
		Hall existing;
		if (hall == null) return false;
		if ((existing = get(hall.getId())) != null) {
			existing.setName(hall.getName());
			existing.setHtml(hall.getHtml());
			existing.setTheatre(hall.getTheatre());
			return true;
		}
		return false;
	}
	public boolean remove(Hall hall) {
		if (hall == null) return false;
		int idx = CollectionUtils.getIndexOf(halls.toArray(), hall);
		return halls.remove(halls.get(idx)) ;
	}	
	public Long getNextId() {
		if (halls == null) return -1L;
		Long result = 0L;
		for (Hall h : halls)
			result = Math.max(result, h.getId());
		return result+1;
	}
	@Override
	public List<String> getRawList() {
		// TODO Auto-generated method stub
		return null;
	}

}
