package ws.softlabs.lib.kino.dao.server.model.pmf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
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

	private static final Logger log = 
		Logger.getLogger("kino.pmfdao.model." + PShow.class.getSimpleName());

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key				key;
	
	@Persistent	
	private	long			timestamp;
	
	@Persistent	
	private	Key				hallKey;
	
	@Persistent	
	private	Key				movieKey;
	
	@Persistent	
	private List<Integer> 	price;
	
	public PShow(){
		init(null, null, null, null);
	}
	public PShow(Hall hall, Movie movie, Date date, List<Integer> price) {
		init(hall, movie, date, price);
	}
	public PShow(Show show) {
System.err.println("PShow constructor (show = " + show + ")");
		if (show != null) {			
			init(show.getHall(), show.getMovie(), show.getDate(), show.getPrice());
		} else {
			init(null, null, null, null);
		}
System.err.println("PShow constructor DONE (result = " + this.toString() + ")");
	}
	public void init(Hall hall, Movie movie, Date dateTime, List<Integer> price) {
		this.setHallKey(PMFDAOUtils.getPHall(hall).getKey());
		this.setMovieKey(PMFDAOUtils.getPMovie(movie).getKey());
		this.timestamp  =  dateTime.getTime();
		this.price 		=  price;
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
		this.price = price;
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
		return dt + " " + tm + " " + hallKey + " " + movieKey;
	}
	public Show asShow() {
		long     id = this.getKey().getId();
		Hall   hall = PMFDAOUtils.getPHall(this.hallKey).asHall();
		Movie movie = PMFDAOUtils.getPMovie(this.hallKey).asMovie();
		Date   date = new Date(timestamp);
		return new Show(id, hall, movie, date, price);
	}

/*	public Hall getHall() {
		PHall phall = PMFDAOUtils.getPHall(hallKey);
		if (phall == null) 
			return null;
		return phall.asHall();
	}
	public void setHall(Hall hall) {
		//this.hall = hall;		
	}
	public Movie getMovie() {
		return null;
	}
	public void setMovie(Movie movie) {
		//this.movie = movie;
	}
/**/

}
