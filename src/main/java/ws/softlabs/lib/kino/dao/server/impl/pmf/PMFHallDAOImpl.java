package ws.softlabs.lib.kino.dao.server.impl.pmf;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.log4j.Logger;

import ws.softlabs.lib.kino.dao.server.intf.HallDAO;
import ws.softlabs.lib.kino.dao.server.model.pmf.PHall;
import ws.softlabs.lib.kino.dao.server.model.pmf.PTheater;
import ws.softlabs.lib.kino.dao.server.service.pmf.PMF;
import ws.softlabs.lib.kino.model.client.Hall;
import ws.softlabs.lib.kino.model.client.Theater;

public class PMFHallDAOImpl implements HallDAO {

	private static final Logger log = 
		Logger.getLogger("kino.pmfdao.impl." + PMFHallDAOImpl.class.getSimpleName());
	
	public Hall get(Long id) {/*
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query query = pm.newQuery(PHall.class);
			query.setFilter("id == idParam");
			query.declareParameters("Integer idParam");
			List<PHall> ptheaters = 
				(List<PHall>)query.execute(id);
			if(ptheaters != null && !ptheaters.isEmpty())
				return ptheaters.get(0).asHall();
		} finally {
			pm.close();
		} /**/
		log.error("****** NOT IMPLEMENTED YET ********");
		return null;
	}
	@SuppressWarnings("unchecked")
	public Hall get(Theater theater, String name) {
		log.debug("ENTER (theater = " + theater + ", name = " + name + ")");
		PersistenceManager pm = PMF.getPersistenceManager();
		PTheater ptheater = PMFDAOUtils.getPTheater(theater);
		if (ptheater != null) {
			log.debug("Got PTheater from datastore");
			try {
				Query query = pm.newQuery(PHall.class);
				query.setFilter("theaterKey == keyParam && name == nameParam");
				query.declareParameters("com.google.appengine.api.datastore.Key keyParam, " +
						                "String nameParam");
				List<PHall> phalls = 
					(List<PHall>)query.execute(ptheater.getKey(), name);
				log.debug("query executed");
				if(phalls != null && !phalls.isEmpty()) {
					Hall result = phalls.get(0).asHall(); 
					log.debug("EXIT returning hall: " + result);
					return result;
				} else {
					log.debug("got no results from datastore");
					log.debug("EXIT (NULL)");
					return null;					
				}
			} catch (Exception e) {
				log.error("EXIT (EXCEPTION): " + e);
				return null;
			} finally {
				pm.close();
			}
		} else {
			log.debug("EXIT (NULL) [ptheater = NULL]");
			return null;
		}
	}
	public List<Hall> getList() {
		log.error("****** NOT IMPLEMENTED YET ********");
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<Hall> getList(Theater theater) {
		log.debug("ENTER (theater = " + theater + ")");
		PersistenceManager pm = PMF.getPersistenceManager();
		List<PHall> phalls = null;
		List<Hall>  result = null;
		PTheater ptheater = PMFDAOUtils.getPTheater(theater);
		if (ptheater != null) {
			log.debug("Got PTheater from datastore");
			try {
				Query query = pm.newQuery(PHall.class);
				query.setFilter("theaterKey == keyParam");
				query.declareParameters("com.google.appengine.api.datastore.Key keyParam");
				query.setOrdering("name");
				phalls = 
					(List<PHall>)query.execute(ptheater.getKey());
				log.debug("executed query");
				if(phalls != null && !phalls.isEmpty()) {
					log.debug("got halls from datastore");
					result = new ArrayList<Hall>();
					for (PHall pt : phalls) {
						Hall hall = pt.asHall();
						log.debug("adding hall (" + hall + ")");
						result.add(hall);
					}
					log.debug("EXIT returning list (size = " + result.size() + ")");
					return result;
				} else {
					log.debug("got no results from datastore");
					log.debug("EXIT (NULL)");
					return null;					
				}
			} catch (Exception e) {
				log.error("EXIT (EXCEPTION): " + e);
				e.printStackTrace();
				return null;
			} finally {
				pm.close();
			}
		} else {
			log.debug("EXIT (NULL) [ptheater = NULL]");
			return null;
		}
	}
	public boolean add(Hall hall) {
		log.debug("ENTER (hall = " + hall +")");
		if (hall == null) {
			log.debug("EXIT (false) [hall = NULL]");
			return false;
		}
		
		PersistenceManager pm = PMF.getPersistenceManager();

		try {
			PHall phall = new PHall(hall);
			log.debug("created new PHall: '" + phall + "'");
			pm.makePersistent(phall);
			hall.setId(phall.getKey().getId());
			log.debug("EXIT (true)");			
			return true;
		} catch (Exception e) {
			log.error("EXIT (EXCEPTION): " + e);
			//ex.printStackTrace(System.err);
			//return false;
			return true;
		} catch (Throwable t) {
			log.error("EXIT (THROWABLE): " + t);
			return true;
		} finally {
			pm.close();
		}
	}
	public boolean update(Hall hall) {
		log.error("****** NOT IMPLEMENTED YET ********");
		return false;
	}
	public boolean remove(Hall hall) {
		log.error("****** NOT IMPLEMENTED YET ********");
		return false;
	}
	public Long    getNextId() {
		// STUB - not needed for PMF
		log.error("****** SHOULD NOT BE CALLED IN PMF DAO IMPLEMENTATION ********");
		return 0L;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRawList() {
		PersistenceManager pm = PMF.getPersistenceManager();
		List<PHall>  phalls = null;
		List<String> result = null;
		try {
			Query query = pm.newQuery(PHall.class);
			query.setOrdering("theaterKey, name");
			phalls = (List<PHall>)query.execute();
			if(phalls != null && !phalls.isEmpty()) {
				result = new ArrayList<String>();
				for (PHall pt : phalls) {
//					String s = pt.getTheaterKey() + " "; 
//					s += pt.getKey().toString() + " - '";
//					s += pt.getName() + "' - '" + pt.getHtml() + "'";
//					result.add(s);
					result.add(pt.toString());
				}
				return result;
			} else {
				return null;					
			}
		} catch (Exception e) {
			return null;
		} finally {
			pm.close();
		}
	}

}
