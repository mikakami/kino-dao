package ws.softlabs.lib.kino.dao;

import org.junit.Test;

public class TestTools {

	private static int abs(int val) {
		return ( val >= 0 ) ? val : -val; 
	}
	
	public static int random(int n) {
		return abs(((new java.util.Random()).nextInt()) % (n - 1));		
	}
	
	@Test
	public void stub() {
		
	}
	
}
