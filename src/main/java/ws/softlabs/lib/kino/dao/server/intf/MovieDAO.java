package ws.softlabs.lib.kino.dao.server.intf;

import java.util.List;
import ws.softlabs.lib.kino.model.client.*;

public interface MovieDAO {
	Movie get(Long id);
	Movie get(String name, String url);
	List<Movie> getList();
	boolean add(Movie movie);
	boolean update(Movie movie);
	boolean remove(Movie movie);	
	Long getNextId();
}
