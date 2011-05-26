package ws.softlabs.lib.kino.dao.server.model.pmf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.apache.log4j.Logger;

import ws.softlabs.lib.kino.dao.server.impl.pmf.PMFDAOUtils;
import ws.softlabs.lib.kino.model.client.Hall;
import ws.softlabs.lib.kino.model.client.Movie;
import ws.softlabs.lib.kino.model.client.Show;
import ws.softlabs.lib.util.client.DateUtils;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PShow {

	@NotPersistent
	private static final Logger log = 
		Logger.getLogger("kino.pmfdao.model." + PShow.class.getSimpleName());

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key				key = null;
	
	@Persistent	
	private	long			timestamp = 0L;
	
	@Persistent	
	private	Key				hallKey = null;
	
	@Persistent	
	private	Key				movieKey = null;
	
	@Persistent	
	private List<Integer> 	price = null;
	
	public PShow() {
		log.debug("ENTER default constructor");
		//init(null, null, null, null);
		log.debug("EXIT default constructor (result = " + this.toString() + ")");
	}
	public PShow(Hall hall, Movie movie, Date date, List<Integer> price) {
		log.debug("ENTER params constructor");
		init(hall, movie, date, price);
		log.debug("EXIT params constructor (result = " + this.toString() + ")");
	}
	public PShow(Show show) {
		log.debug("ENTER constructor (show = " + show + ")");
		if (show != null) {			
			init(show.getHall(), show.getMovie(), show.getDate(), show.getPrice());
		} else {
			init(null, null, null, null);
		}
		log.debug("EXIT show constructor (result = " + this.toString() + ")");
	}
	public void init(Hall hall, Movie movie, Date dateTime, List<Integer> price) {
		log.debug("ENTER");
		
		PHall  phall  = null;
		PMovie pmovie = null;
		
		if (hall != null)
			phall = PMFDAOUtils.getPHall(hall);
		if (movie != null)
			pmovie = PMFDAOUtils.getPMovie(movie);

		if (phall != null)
			this.setHallKey(phall.getKey());
		if (pmovie != null)
			this.setMovieKey(pmovie.getKey());
		
		if (dateTime != null) {
			this.timestamp  =  dateTime.getTime();
			this.timestamp = this.timestamp / 1000;
			this.timestamp = this.timestamp * 1000;
		}
		
		if (price != null) {
			this.price = new ArrayList<Integer>();
			for(Integer i : price) {
				if (i == null)
					this.price.add(-1);
				else 
					this.price.add(i);
			}
		}		
		
		log.debug("EXIT");
	}
	public void init (Hall hall, Movie movie, Date date) {
		init(hall, movie, date, new ArrayList<Integer>());
	}
	public Key  getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public Key  getMovieKey() {
		return movieKey;
	}
	public void setMovieKey(Key movieKey) {
		this.movieKey = movieKey;
	}
	public Key  getHallKey() {
		return hallKey;
	}	
	public void setHallKey(Key hallKey) {
		this.hallKey = hallKey;
	}
	public long getTimeStamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}	
	public List<Integer> getPrice() {
		return price;
	}
	public void setPrice(List<Integer> price) {
		if (price != null)
			this.price = new ArrayList<Integer>(price);
	}
	public Date getDate() {
		return new Date(this.timestamp);
	}
	public void setDate(Date date) {
		 timestamp = date.getTime();
	}	
	@SuppressWarnings("deprecation")
	public String toString() {
		Date date = new Date(timestamp);
		String dt = DateUtils.dateToStringSpecial(date);
		String tm = date.getHours() + ":" + date.getMinutes();	
		String p = "";
		if (price != null && !price.isEmpty())
			for(Integer i : price)
				p += i + " ";
		return dt + " " + tm + " (" + timestamp + ") " + hallKey + " " + movieKey + " " + p;
	}
	public Show asShow() {
		long     id = this.getKey().getId();
		Hall   hall = PMFDAOUtils.getPHall(this.hallKey).asHall();
		Movie movie = PMFDAOUtils.getPMovie(this.movieKey).asMovie();
		Date   date = new Date(timestamp);
		List<Integer> newPrice = new ArrayList<Integer>();
		
		if (price != null) {
			for(Integer i : price)
				if (i >= 0)
					newPrice.add(i);
				else 
					newPrice.add(null);
			return new Show(id, hall, movie, date, newPrice);	
		} else
			return new Show(id, hall, movie, date, null);
	}
}
