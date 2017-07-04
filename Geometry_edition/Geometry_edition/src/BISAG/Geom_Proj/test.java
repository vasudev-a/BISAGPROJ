package BISAG.Geom_Proj;

import java.net.MalformedURLException;
import java.net.URL;

import it.geosolutions.geoserver.rest.*;


public class test {

	public static void main(String[] args) throws IllegalArgumentException, MalformedURLException {
		String RESTURL  = "http://localhost:8080/geoserver";
        String RESTUSER = "admin";
        String RESTPW   = "geoserver";
        
        GeoServerRESTReader reader = new GeoServerRESTReader(RESTURL, RESTUSER, RESTPW);
        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
        
        boolean created = publisher.createWorkspace("myWorkspace_one_more");
        
        
	}
}