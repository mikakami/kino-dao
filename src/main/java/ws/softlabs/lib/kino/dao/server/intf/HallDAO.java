package ws.softlabs.lib.kino.dao.server.intf;

import java.util.List;

import ws.softlabs.lib.kino.model.client.*;

public interface HallDAO {
	Hall get(Long id);
	Hall get(Theater theater, String name);
	List<Hall> getList();
	List<Hall> getList(Theater theater);
	boolean add(Hall hall);
	boolean update(Hall hall);
	boolean remove(Hall hall);	
	Long getNextId();
}
