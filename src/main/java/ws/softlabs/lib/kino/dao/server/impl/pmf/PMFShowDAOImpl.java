package ws.softlabs.lib.kino.dao.server.impl.pmf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import ws.softlabs.lib.kino.dao.server.intf.ShowDAO;
import ws.softlabs.lib.kino.dao.server.model.pmf.PHall;
import ws.softlabs.lib.kino.dao.server.model.pmf.PShow;
import ws.softlabs.lib.kino.dao.server.service.pmf.PMF;
import ws.softlabs.lib.kino.model.client.Hall;
import ws.softlabs.lib.kino.model.client.Show;
import ws.softlabs.lib.kino.model.client.Theater;

public class PMFShowDAOImpl implements ShowDAO {

	private static final Logger log = 
		Logger.getLogger("kino.pmfdao.impl." + PMFShowDAOImpl.class.getSimpleName());

	public Show get(Long id) {
		log.error("****** NOT IMPLEMENTED YET ********");
		return null;
	}
	@SuppressWarnings("unchecked")
	public Show get(Hall hall, Date dateTime) {
		log.debug("ENTER (hall = " + hall + ", dateTime = " + dateTime + ")");
		PersistenceManager pm = PMF.getPersistenceManager();
		PHall phall = PMFDAOUtils.getPHall(hall);
		if (phall != null) {
			log.debug("Got PHall from datastore");
			try {
				Query query = pm.newQuery(PShow.class);
				query.setFilter("hallKey == keyParam && timestamp == dateParam");
				query.declareParameters("com.google.appengine.api.datastore.Key keyParam, " +
						                "long dateParam");
				
				List<PShow> pshows = 
					(List<PShow>)query.execute(phall.getKey(), dateTime.getTime());
				log.debug("query executed");
				if(pshows != null && !pshows.isEmpty()) {
					Show result = pshows.get(0).asShow(); 
					log.debug("EXIT returning show: " + result);
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
			log.debug("EXIT (NULL) [phall = NULL]");
			return null;
		}
	}
	public List<Show> getList() {
		log.error("****** NOT IMPLEMENTED YET ********");
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<Show> getList(Hall hall, Date date) {
		log.debug("ENTER (hall = " + hall + ", date = " + date + ")");
		DateTime dt = new DateTime(date);
		long tsLow  = dt.toDateMidnight().getMillis();
		long tsHigh = dt.plusDays(1).toDateMidnight().getMillis();
		PersistenceManager pm = PMF.getPersistenceManager();
		List<PShow> pshows = null;
		List<Show>  result = null;
		PHall phall = PMFDAOUtils.getPHall(hall);
		if (phall != null) {
			log.debug("Got PHall from datastore");
			try {
				Query query = pm.newQuery(PShow.class);
				query.setFilter("hallKey   == keyParam  && " + 
								"timestamp > sinceParam && " +
								"timestamp <= tillParam ");
				query.declareParameters("com.google.appengine.api.datastore.Key keyParam, " +
										"long sinceParam, " +
										"long tillParam");
				query.setOrdering("timestamp");
				pshows = 
					(List<PShow>)query.execute(phall.getKey(), tsLow, tsHigh);
				log.debug("executed query");
				if(pshows != null && !pshows.isEmpty()) {
					log.debug("got shows from datastore");
					result = new ArrayList<Show>();
					for (PShow ps : pshows) {
						Show show = ps.asShow();
						log.debug("adding show (" + show + ")");
						result.add(show);
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
			log.debug("EXIT (NULL) [phall = NULL]");
			return null;
		}
	}
	public boolean add(Show show) {
		log.debug("ENTER (show = " + show +")");
		if (show == null) {
			log.debug("EXIT (false) [show = NULL]");
			return false;
		}		
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			log.debug("trying to create new PShow...");
			PShow pshow = new PShow(show);
			log.debug("created new PShow: '" + pshow + "'");
			pm.makePersistent(pshow);
			show.setId(pshow.getKey().getId());
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
	public boolean update(Show show) {
		log.error("****** NOT IMPLEMENTED YET ********");
		return false;
	}
	public boolean remove(Show show) {
		log.error("****** NOT IMPLEMENTED YET ********");
		return false;
	}
	public List<Show> getListSince(Hall hall, Date date) {
		log.error("****** NOT IMPLEMENTED YET ********");
		return null;
	}
	public List<String> getDaysList(Theater theater, Date date) {
		log.error("****** NOT IMPLEMENTED YET ********");
		return null;
	}
	public Long getNextId() {
		// STUB - not needed for PMF
		log.error("****** SHOULD NOT BE CALLED ********");
		return null;
	}

}
