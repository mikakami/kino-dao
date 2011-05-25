package ws.softlabs.lib.kino.dao.server.model.pmf;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ws.softlabs.lib.kino.dao.server.impl.pmf.PMFDAOUtils;
import ws.softlabs.lib.kino.model.client.Hall;
import ws.softlabs.lib.kino.model.client.Theater;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PHall implements StoreCallback {

	@NotPersistent
	private static final Logger log = 
		LoggerFactory.getLogger("kino.pmfdao.model." + PHall.class.getSimpleName());
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)		
	private Key		key;
	
	@Persistent		
	private String	name;
	
	@Persistent		
	private String	html;
	
	@Persistent		
	private Key		theaterKey;
	
	public PHall(){
		init(null, null, null);		
	}
	public PHall(Theater theater, String name, String hmtl) {
		init(theater, name, hmtl);
	}
	public PHall(Hall hall) {
		log.debug("ENTER (hall = " + hall + ")");
		if (hall == null) {
			init(null, null, null);
		}
		else {
			init(hall.getTheatre(), hall.getName(), hall.getHtml());
		}
		log.debug("EXIT");
	}	
	public void   init(Theater theater, String name, String html) {
		log.debug("ENTER (theater = " + theater + ", name = " + name + ", html = " + html + ")");
		this.name = (name == null) ? "" : name;
		this.html = (html == null) ? "" : html;
		if (theater != null) {
			log.debug("getting theaterKey");
			PTheater ptheater = PMFDAOUtils.getPTheater(theater);
			if (ptheater != null) {
				this.theaterKey = ptheater.getKey();
				log.debug("new theaterKey = " + theaterKey);
			} else {
				log.debug("got no ptheater from datastore");
			}
		}
		log.debug("EXIT");
	}
	public Key    getKey() {
		return key;
	}
	public void   setKey(Key key) {
		this.key = key;
	}
	public Key    getTheaterKey() {
		return this.theaterKey;
	}
	public void   setTheaterKey(Key key) {
		this.theaterKey = key;
	}
	public String getName() {
		return name;
	}
	public void   setName(String name) {
		this.name = name;
	}
	public String getHtml() {
		return html;
	}
	public void   setHtml(String html) {
		this.html = html;
	}
	public Hall   asHall() {
		long id = this.getKey().getId();
		Theater theater = PMFDAOUtils.getPTheater(this.theaterKey).asTheater();
		String hName = ("".equals(name)) ? null : name;
		String hHtml = ("".equals(html)) ? null : html;
		return new Hall(id, theater, hName, hHtml);
	}
	public Theater getTheater() {
		return PMFDAOUtils.getPTheater(theaterKey).asTheater();
	}
	public void setTheater(Theater theater) {
		PTheater ptheater = 
			PMFDAOUtils.getPTheater(theater);
		if (ptheater != null)
			this.theaterKey = ptheater.getKey();
	}
	public String toString() {
		return this.getName() + "(" + this.getKey() + ":" + theaterKey + ")";
	}
	public void jdoPreStore() {
		//if (name == null) name = "";
		//if (html == null) html = "";
	}
}
