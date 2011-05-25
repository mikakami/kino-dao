package ws.softlabs.lib.kino.dao.server.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ws.softlabs.lib.kino.dao.server.model.pmf.PHall;
import ws.softlabs.lib.kino.model.client.Hall;
import ws.softlabs.lib.kino.model.client.Show;
import ws.softlabs.lib.kino.model.client.Theater;

public class DAOResultUtils {

	private static final Logger log = 
		LoggerFactory.getLogger("kino.pmfdao.util." + DAOResultUtils.class.getSimpleName());
	
	public static void printTheaterList(String callFunctionName, List<Theater> list) {
		log.debug("ENTER [called from " + callFunctionName + "]");
		if (list == null) {
			log.debug("EXIT [list = NULL]");
			return;
		}
		for(Theater o : list)
			log.debug(o.toString());
		log.debug("EXIT");
	}
	public static void printHallList(String callFunctionName, List<Hall> list) {
		log.debug("ENTER [called from " + callFunctionName + "]");
		if (list == null) {
			log.debug("EXIT [list = NULL]");
			return;
		}
		for(Hall o : list)
			log.debug(o.toString());
		log.debug("EXIT");
	}
	public static void printShowList(String callFunctionName, List<Show> list) {
		log.debug("ENTER [called from " + callFunctionName + "]");
		if (list == null) {
			log.debug("EXIT [list = NULL]");
			return;
		}
		for(Show o : list)
			log.debug(o.toString());
		log.debug("EXIT");
	}
	public static void printShowDaysList(String callFunctionName, List<String> list) {
		log.debug("ENTER [called from " + callFunctionName + "]");
		if (list == null) {
			log.debug("EXIT [list = NULL]");
			return;
		}
		for(String o : list)
			log.debug(o);
		log.debug("EXIT");
	}
	public static void printPHallList(String callFunctionName, List<PHall> list) {
		log.debug("ENTER [called from " + callFunctionName + "]");
		if (list == null) {
			log.debug("EXIT [list = NULL]");
			return;
		}
		for(PHall o : list)
			log.debug(o.toString());
		log.debug("EXIT");
	}
	public static void printStringList(List<String> list) {
		if (list == null)
			return;
		for(String s : list)
			log.debug(s);
	}

}
