package ws.softlabs.lib.kino.dao.server.intf;

import java.util.List;

import ws.softlabs.lib.kino.model.client.*;

public interface TheaterDAO {
	Theater get(Long id);
	Theater get(String name, String url);
	List<Theater> getList();
	boolean add(Theater theater);
	boolean update(Theater theater);
	boolean remove(Theater theater);	
	Long getNextId();
	
	List<String> getRawList();
}
