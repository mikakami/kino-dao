package ws.softlabs.lib.kino.dao.server.impl.pmf;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.log4j.Logger;

import ws.softlabs.lib.kino.dao.server.intf.MovieDAO;
import ws.softlabs.lib.kino.dao.server.model.pmf.PMovie;
import ws.softlabs.lib.kino.dao.server.service.pmf.PMF;
import ws.softlabs.lib.kino.model.client.Movie;

public class PMFMovieDAOImpl implements MovieDAO {

	private static final Logger log = 
		Logger.getLogger("kino.pmfdao.impl." + PMFMovieDAOImpl.class.getSimpleName());

	public Movie get(Long id) {
		log.error("****** NOT IMPLEMENTED YET ********");
		return null;
	}
	@SuppressWarnings("unchecked")
	public Movie get(String name, String url) {
		log.debug("ENTER");
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query query = pm.newQuery(PMovie.class);
			query.setFilter("name == nameParam && url == urlParam");
			query.declareParameters("String nameParam, String urlParam");
			List<PMovie> pmovies = 
				(List<PMovie>)query.execute(name, url);
			log.debug("query executed");
			if(pmovies != null && !pmovies.isEmpty()) {
				Movie result = pmovies.get(0).asMovie(); 
				log.debug("EXIT returning movie: " + result);
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
	public List<Movie> getList() {
		log.debug("ENTER");
		PersistenceManager pm = PMF.getPersistenceManager();
		List<PMovie>  pmovies = null;
		List<Movie>   result  = null;
		try {
			Query query = pm.newQuery(PMovie.class);
			query.setOrdering("name");
			pmovies = 
				(List<PMovie>)query.execute();
			if(pmovies != null && !pmovies.isEmpty()) {
				result = new ArrayList<Movie>();
				for (PMovie m : pmovies) {
					log.debug("got pmovie: " + m.getName() + " ("+ m.getKey() +")");
					result.add(m.asMovie());
					log.debug("added movie: " + m.asMovie());
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
	public boolean add(Movie movie) {
		log.debug("ENTER (movie = " + movie + ")");
		if (movie == null) {
			log.debug("EXIT (false) [movie = NULL]");
			return false;
		}
		PMovie pmovie = new PMovie(movie);
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			pm.makePersistent(pmovie);
			movie.setId(pmovie.getKey().getId());
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
	public boolean update(Movie movie) {
		log.error("****** NOT IMPLEMENTED YET ********");
		return false;
	}
	public boolean remove(Movie movie) {
		log.error("****** NOT IMPLEMENTED YET ********");
		return false;
	}
	public Long getNextId() {
		// STUB - not needed for PMF
		log.error("****** SHOULD NOT BE CALLED IN PMF DAO IMPLEMENTATION ********");
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRawList() {
		PersistenceManager pm = PMF.getPersistenceManager();
		List<PMovie>  pmovies = null;
		List<String>  result  = null;
		try {
			Query query = pm.newQuery(PMovie.class);
			query.setOrdering("name");
			pmovies = (List<PMovie>)query.execute();
			if(pmovies != null && !pmovies.isEmpty()) {
				result = new ArrayList<String>();
				for (PMovie m : pmovies)
//					result.add(m.asMovie().toString());
					result.add(m.toString());
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
