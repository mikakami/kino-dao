package ws.softlabs.lib.kino.dao.server.impl.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ws.softlabs.lib.kino.dao.server.intf.ShowDAO;
import ws.softlabs.lib.kino.model.client.Hall;
import ws.softlabs.lib.kino.model.client.Show;
import ws.softlabs.lib.kino.model.client.Theater;
import ws.softlabs.lib.util.client.CollectionUtils;
import ws.softlabs.lib.util.client.DateUtils;


public class MockShowDAOImpl implements ShowDAO {

	private static List<Show> shows = new ArrayList<Show>();
	
	public Show get(Long id) {
		for(Show s : shows)
			if (s.getId() == id)
				return s;
		return null;
	}
	public Show get(Hall hall, Date dateTime) {
		for(Show s : shows)
			if (s.getHall().equals(hall) &&
				s.getDate().equals(dateTime))
				return s;
		return null;
	}
	public List<Show> getList() {
		// UNIMPLEMENTED IN MOCK-DAO
		return shows;
	}
	public List<Show> getList(Hall hall, Date day) {
		List<Show> result = new ArrayList<Show>();
		for(Show s : shows)
			if ((s.getHall().equals(hall)) &&
				(DateUtils.equalDates(s.getDate(), day)))
				result.add(s);
		return result;
	}
	public boolean add(Show show) {
		if (show == null) return false;
		if (get(show.getId()) == null) {
			shows.add(show);
			return true;
		}
		return false;
	}
	public boolean update(Show show) {
		Show existing;
		if (show == null) return false;
		if ((existing = get(show.getId())) != null) {
			existing.setHall(show.getHall());
			existing.setMovie(show.getMovie());
			existing.setDate(show.getDate());
			existing.setPrice(show.getPrice());
			return true;
		}
		return false;
	}
	public Long getNextId() {
		if (shows == null) return -1L;
		Long result = 0L;
		for (Show s : shows)
			result = Math.max(result, s.getId());
		return result+1;
	}
	public boolean remove(Show show) {
		if (show == null) return false;		
		return ( shows.remove(CollectionUtils.getIndexOf(shows.toArray(), show)) != null );
	}
	public List<Show> getListSince(Date date) {
		// UNIMPLEMENTED IN MOCK-DAO
		return null;
	}
	public List<Show> getListSince(Hall hall, Date date) {
		// UNIMPLEMENTED IN MOCK-DAO
		return null;
	}
	public List<String> getDaysList(Theater theater, Date date) {
		// UNIMPLEMENTED IN MOCK-DAO
		return null;
	}
	@Override
	public List<String> getRawList() {
		// UNIMPLEMENTED IN MOCK-DAO
		return null;
	}
	@Override
	public List<String> getRawDayList() {
		// UNIMPLEMENTED IN MOCK-DAO
		return null;
	}
}
