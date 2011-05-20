package ws.softlabs.lib.kino.dao.server.service.pmf;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.apache.log4j.Logger;

public class PMF {

	private static final Logger log = 
		Logger.getLogger("kino.pmfdao.service." + PMF.class.getSimpleName());

	private static final
	PersistenceManagerFactory pmfInstance = 
		JDOHelper.getPersistenceManagerFactory("transactions-optional");

	private PMF(){
	};
	public static PersistenceManagerFactory get() {
		log.debug("CALL TO PMF.get()");
		return pmfInstance;
	}
	public static PersistenceManager getPersistenceManager() {
		log.debug("CALL TO PMF.getPersistenceManager()");
		return get().getPersistenceManager();
	}
}
