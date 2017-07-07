package BISAG.Geom_Proj;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import it.geosolutions.geoserver.rest.*;
import it.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import it.geosolutions.geoserver.rest.encoder.coverage.GSImageMosaicEncoder;


public class test {

	public static void main(String[] args) throws IllegalArgumentException, MalformedURLException, FileNotFoundException {
       
        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher("http://localhost:8080/geoserver", "admin", "geoserver");
        //boolean pub = publisher.publishGeoTIFF("test", "myTIFF", new File("G:\\PS 1\\SATIMAGES\\FCC.tif")); //single tiff image
        
        
        //***************************************
        //multiple images as mosaic
        // layer encoder
       final GSLayerEncoder layerEnc = new GSLayerEncoder();
        String style="raster";
        layerEnc.setDefaultStyle(style);

       // coverage encoder
       final GSImageMosaicEncoder coverageEnc=new GSImageMosaicEncoder();
       coverageEnc.setName("DEMImageName");
       coverageEnc.setTitle("DEMAnotherTitle_new");
       coverageEnc.setMaxAllowedTiles(500); 

       // ... many other options are supported

       // create a new ImageMosaic layer...
       final boolean published = publisher.publishExternalMosaic("test", "myDEM", new File("G:\\PS 1\\SATIMAGES\\DEM"), coverageEnc, layerEnc);
         	
       //*****************************
	}
}