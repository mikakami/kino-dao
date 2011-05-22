package ws.softlabs.lib.kino.dao.server.model.pmf;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.apache.log4j.Logger;

import ws.softlabs.lib.kino.model.client.Movie;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PMovie {

	@NotPersistent
	private static final Logger log = 
		Logger.getLogger("kino.pmfdao.model." + PMovie.class.getSimpleName());

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key		key;
	
	@Persistent		
	private String	name;
	
	@Persistent	
	private String	url;
	
	public PMovie(){
		init(null, null);
	}
	public PMovie(String name, String url) {
		init(name, url);
	}
	public PMovie(Movie movie) {
		this.name = movie.getName();
		this.url  = movie.getUrl();
	}
	public void init(String name, String url) {
		this.name = name;
		this.url  = url;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Movie asMovie() {
		return new Movie(this.key.getId(), this.getName(), this.getUrl());
	}
	public String toString() {
		//return this.getName() + "(" + this.getKey().toString() + ")";
		return this.getKey() + ": " + this.getName() + " (" + this.getUrl() + ")";
	}
}
