package ws.softlabs.lib.kino.dao.server.impl.pmf;


import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.log4j.Logger;

import ws.softlabs.lib.kino.dao.server.model.pmf.PHall;
import ws.softlabs.lib.kino.dao.server.model.pmf.PMovie;
import ws.softlabs.lib.kino.dao.server.model.pmf.PShow;
import ws.softlabs.lib.kino.dao.server.model.pmf.PTheater;
import ws.softlabs.lib.kino.dao.server.service.pmf.PMF;
import ws.softlabs.lib.kino.model.client.Hall;
import ws.softlabs.lib.kino.model.client.Movie;
import ws.softlabs.lib.kino.model.client.Theater;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class PMFDAOUtils {

	private static final Logger log = 
		Logger.getLogger("kino.pmfdao.impl." + PMFDAOUtils.class.getSimpleName());

	public static PTheater getPTheater(Theater theater) {
		log.debug("ENTER (theater = " + theater + ")");
		if (theater == null) {
			log.debug("EXIT (NULL) [theater = null]");
			return null;
		}
		PTheater result; 
		if (theater.getId() != null && theater.getId() > 0)
			result = getPTheater(theater.getId(), theater.getName(), theater.getUrl());
		else 
			result = getPTheater(theater.getName(), theater.getUrl());
		log.debug("EXIT (result = " + result + ")");
		return result;
	}
	@SuppressWarnings("unchecked")
	public static PTheater getPTheater(long id, String name, String url) {
		log.debug("ENTER (id = " + id + ", name = " + name + ", url = " + url + ")");
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Key key = KeyFactory.createKey(PTheater.class.getSimpleName(), id);
			log.info("theater id: " + id);
			log.info("generated key: " + key.toString());
			Query query = pm.newQuery(PTheater.class);
			query.setFilter("key == keyParam && name == nameParam && url == urlParam");
			query.declareParameters("com.google.appengine.api.datastore.Key keyParam, " +
			                        "String nameParam, " +
			                        "String urlParam");
			List<PTheater> ptheaters = 
				(List<PTheater>)query.execute(key, name, url);
			log.debug("executed query");
			if(ptheaters != null && !ptheaters.isEmpty()) {
				PTheater result = ptheaters.get(0);
				log.debug("EXIT (result = " + result + ")");
				return result;
			} else {
				log.debug("EXIT (NULL)");
				return null;
			}
		} catch (Exception ex) {
			log.warn("EXCEPTION occured: " + ex);
			log.debug("EXIT EXCEPTION (NULL)");
			return null;
		} finally {
			pm.close();
		}
	}
	@SuppressWarnings("unchecked")
	public static PTheater getPTheater(String name, String url) {
		log.debug("ENTER (name = " + name + ", url = " + url + ")");
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query query = pm.newQuery(PTheater.class);
			query.setFilter("name == nameParam && url == urlParam");
			query.declareParameters("String nameParam, String urlParam");
			List<PTheater> ptheaters = 
				(List<PTheater>)query.execute(name, url);
			log.debug("executed query");			
			if(ptheaters != null && !ptheaters.isEmpty()) {
				log.debug("query result size: " + ptheaters.size());
				PTheater result = ptheaters.get(0);
				log.debug("EXIT (result = " + result + ")");
				return result;
			} else {
				log.debug("EXIT (NULL)");
				return null;
			}
		} catch (Exception ex) {
			log.warn("EXIT (EXCEPTION): " + ex);
			return null;
		}
		finally {
			pm.close();
		}
	}
	public static PTheater getPTheater(Key key) {
		log.debug("ENTER (key = " + key + ")");
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			PTheater result = (PTheater)pm.getObjectById(PTheater.class, key);
			log.debug("EXIT (result = " + result + ")");
			return result;
		} catch (Exception ex) {
			log.warn("EXIT (EXCEPTION): " + ex);
			return null;
		} finally {
			pm.close();
		}
	}	
	public static PHall getPHall(Hall hall) {
		log.debug("ENTER (hall = " + hall + ")");
		PTheater theater = getPTheater(hall.getTheatre());
		if (theater == null) {
			log.debug("EXIT (NULL) [theater = NULL]");
			return null;
		}
		PHall result = getPHall(theater.getKey(), hall.getName(), hall.getHtml());
		log.debug("EXIT (result = " + result + ")");
		return result;
	}
	@SuppressWarnings("unchecked")
	public static PHall getPHall(Key theaterKey, String name, String html) {
		log.debug("ENTER (theaterKey = " + theaterKey + ", name = " + name + ", html = " + html + ")");
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query query = pm.newQuery(PHall.class);
			query.setFilter("name == nameParam && html == htmlParam && key == keyParam");
			query.declareParameters("String nameParam, " +
									"String htmlParam, " +
									"com.google.appengine.api.datastore.Key keyParam");
			List<PHall> phalls = 
				(List<PHall>)query.execute(name, html, theaterKey);
			log.debug("executed query");
			if(phalls != null && !phalls.isEmpty()) {
				PHall result = phalls.get(0);
				log.debug("executed query");
				log.debug("EXIT (result = " + result + ")");
				return result;
			} else {
				log.debug("EXIT (NULL)");
				return null;
			}
		} catch (Exception ex) {
			log.warn("EXIT (EXCEPTION): " + ex);
			return null;
		} finally {		
			pm.close();
		}	
	}
	public static PHall getPHall(Key key) {
		log.debug("ENTER (key = " + key + ")");
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			PHall result = (PHall)pm.getObjectById(PHall.class, key);
			log.debug("EXIT (result = " + result + ")");
			return result;
		} catch (Exception ex) {
			log.warn("EXIT (EXCEPTION): " + ex);
			return null;
		} finally {
			pm.close();
		}		
	}	
	public static PMovie getPMovie(Movie movie) {
		log.debug("ENTER (movie = " + movie + ")");
		if (movie == null) {
			log.debug("EXIT (NULL) [movie = NULL]");
			return null;
		}
		PMovie result = getPMovie(movie.getName(), movie.getUrl());
		log.debug("EXIT (result = " + result +")");
		return result;
	}
	@SuppressWarnings("unchecked")
	public static PMovie getPMovie(String name, String url) {
		log.debug("ENTER (name = " + name + ", url = " + url + ")");
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query query = pm.newQuery(PTheater.class);
			query.setFilter("name == nameParam && url == urlParam");
			query.declareParameters("String nameParam, String urlParam");
			List<PMovie> pmovies = 
				(List<PMovie>)query.execute(name, url);
			log.debug("executed query");
			log.debug("query result size: " + pmovies.size());
			if(pmovies != null && !pmovies.isEmpty()) {
				PMovie result = pmovies.get(0);
				log.debug("EXIT (result = " + result + ")");
				return result;
			} else {
				log.debug("EXIT (NULL)");
				return null;
			}
		} catch (Exception ex) {
			log.warn("EXIT (EXCEPTION): " + ex);
			return null;
		} finally {
			pm.close();
		}
	}
	public static PMovie getPMovie(Key key) {
		log.debug("ENTER (key = " + key + ")");
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			PMovie result = (PMovie)pm.getObjectById(PMovie.class, key);
			log.debug("EXIT (result = " + result + ")");
			return result;
		} catch (Exception ex) {
			log.warn("EXIT (EXCEPTION): " + ex);
			return null;
		} finally {
			pm.close();
		}				
	}
	public static void removeAll() {
		log.debug("ENTER");
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query query;
			query = pm.newQuery(PShow.class);
			query.deletePersistentAll();
			log.debug("cleared PShow");
			query = pm.newQuery(PMovie.class);
			query.deletePersistentAll();
			log.debug("cleared PMovie");
			query = pm.newQuery(PHall.class);
			query.deletePersistentAll();
			log.debug("cleared PHall");
			query = pm.newQuery(PTheater.class);
			query.deletePersistentAll();
			log.debug("cleared PTheater");
		} catch (Exception ex) {
			log.warn("EXCEPTION occured: " + ex);
			log.debug("EXIT EXCEPTION");
		} finally {
			pm.close();
		}
		log.debug("EXIT OK");
	}

}
