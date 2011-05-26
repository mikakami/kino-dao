package ws.softlabs.lib.kino.dao.server.impl.pmf;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.log4j.Logger;

import ws.softlabs.lib.kino.dao.server.intf.TheaterDAO;
import ws.softlabs.lib.kino.dao.server.model.pmf.PTheater;
import ws.softlabs.lib.kino.dao.server.service.pmf.PMF;
import ws.softlabs.lib.kino.model.client.Theater;

public class PMFTheaterDAOImpl implements TheaterDAO {

	private static final Logger log = 
		Logger.getLogger("kino.pmfdao.impl." + PMFTheaterDAOImpl.class.getSimpleName());

	public Theater get(Long id) { /*
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query query = pm.newQuery(PTheater.class);
			query.setFilter("id == idParam");
			query.declareParameters("Integer idParam");
			List<PTheater> ptheaters = 
				(List<PTheater>)query.execute(id);
			if(ptheaters != null && !ptheaters.isEmpty())
				return ptheaters.get(0).asTheater();
		} finally {
			pm.close();
		}/**/
		log.error("****** NOT IMPLEMENTED YET ********");
		return null;
	}
	@SuppressWarnings("unchecked")
	public Theater get(String name, String url) {
		log.debug("ENTER");
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query query = pm.newQuery(PTheater.class);
			query.setFilter("name == nameParam && url == urlParam");
			query.declareParameters("String nameParam, String urlParam");
			List<PTheater> ptheaters = 
				(List<PTheater>)query.execute(name, url);
			log.debug("query executed");
			if(ptheaters != null && !ptheaters.isEmpty()) {
				Theater result = ptheaters.get(0).asTheater(); 
				log.debug("EXIT returning theater: " + result);
				return result;
			} else {
				log.debug("got no results from datastore");
				log.debug("EXIT (NULL)");
				return null;				
			}
		} catch (Exception ex) {
			log.error("EXIT (EXCEPTION): " + ex);
			return null;
		} finally {
			pm.close();
		}
	}
	@SuppressWarnings("unchecked")
	public List<Theater> getList() {
		log.debug("ENTER");
		PersistenceManager pm = PMF.getPersistenceManager();
		List<PTheater> ptheaters = null;
		List<Theater>  result    = null;
		try {
			Query query = pm.newQuery(PTheater.class);
			query.setOrdering("name");
			ptheaters = 
				(List<PTheater>)query.execute();
			if(ptheaters != null && !ptheaters.isEmpty()) {
				result = new ArrayList<Theater>();
				for (PTheater pt : ptheaters) {
					log.debug("got ptheater: " + pt.getName() + " ("+ pt.getKey() +")");
					result.add(pt.asTheater());
					log.debug("added theater: " + pt.asTheater());
				}
				log.debug("EXIT returning list (size = " + result.size() + ")");
				return result;
			} else {
				log.debug("got no results from datastore");
				log.debug("EXIT returning NULL");
				return null;
			}
		} catch (Exception ex) {
			log.error("EXIT (EXCEPTION): " + ex);
			return null;
		} finally {
			pm.close();
		}
	}
	public boolean add(Theater theater) {
		log.debug("ENTER (theater = " + theater + ")");
		if (theater == null) {
			log.debug("EXIT (false) [theater = NULL]");
			return false;
		}
		PTheater ptheater = new PTheater(theater);
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			pm.makePersistent(ptheater);
			theater.setId(ptheater.getKey().getId());
			log.debug("EXIT (true)");
			return true;
		} catch (Exception e) {
			log.error("EXIT (EXCEPTION): " + e);
			//return false;
			return true;
		} catch (Throwable t) {
			log.error("EXIT (THROWABLE): " + t);
			//return false;
			return true;
		} finally {
			pm.close();
		}
	}
	public boolean update(Theater theater) {
		log.error("****** NOT IMPLEMENTED YET ********");
		return false;
	}
	public boolean remove(Theater theater) {
		// TODO Auto-generated method stub
		log.error("****** NOT IMPLEMENTED YET ********");
		return false;
	}
	public Long    getNextId() {
		// STUB (as to interface TheaterDAO) - not needed for PMF implementation
		log.error("****** SHOULD NOT BE CALLED IN PMF DAO IMPLEMENTATION ********");
		return 0L;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRawList() {
		PersistenceManager pm = PMF.getPersistenceManager();
		List<PTheater> ptheaters = null;
		List<String>  result    = null;
		try {
			Query query = pm.newQuery(PTheater.class);
			query.setOrdering("name");
			ptheaters = (List<PTheater>)query.execute();
			if(ptheaters != null && !ptheaters.isEmpty()) {
				result = new ArrayList<String>();
				for (PTheater pt : ptheaters)
					result.add(pt.toString());
				return result;
			} else {
				return null;
			}
		} catch (Exception ex) {
			return null;
		} finally {
			pm.close();
		}
	}
	
}
