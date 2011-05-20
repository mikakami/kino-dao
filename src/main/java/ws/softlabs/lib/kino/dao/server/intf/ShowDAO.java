package ws.softlabs.lib.kino.dao.server.intf;

import java.util.Date;
import java.util.List;

import ws.softlabs.lib.kino.model.client.*;

public interface ShowDAO {
	Show get(Long id);
	Show get(Hall hall, Date dateTime);
	List<Show> getList();
	List<Show> getListSince(Hall hall, Date date);
	List<Show> getList(Hall hall, Date date);
	List<String> getDaysList(Theater theater, Date date);
	boolean add(Show show);
	boolean update(Show show);	
	boolean remove(Show show);
	Long getNextId();
}
