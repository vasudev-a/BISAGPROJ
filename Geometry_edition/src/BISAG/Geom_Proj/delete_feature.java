package BISAG.Geom_Proj;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Servlet implementation class return_json
 */
@WebServlet("/delete_feature")
public class delete_feature extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String urls = request.getParameter("top");
		urls = urls.replace("[]","&");
		urls = "http://"+urls;
		System.out.println(urls);
		delete_feature rj = new delete_feature();
		URL url = new URL(urls);
		int id = rj.getid(url);
		out.println(id);
	}
	
	public int getid(URL url){
	    InputStream is = null;
	    BufferedReader br;
	    String line;
	    String id_s = null;
	    int id=-1;
	    try {
	        is = url.openStream();  // throws an IOException
	        br = new BufferedReader(new InputStreamReader(is));

	        while ((line = br.readLine()) != null) {
	        	 Pattern p = Pattern.compile(">\\d+<");
	        	 Matcher m = p.matcher(line);
	        	 if(m.find())
	        	    if((m.group().subSequence(1, m.group().length()-1)).length()!=3)
	        	    		{
	        	    			id_s = (String)m.group().subSequence(1, m.group().length()-1);
	        	    		}
	        	}
	        System.out.println(id_s);
	    } catch (MalformedURLException mue) {
	         mue.printStackTrace();
	    } catch (IOException ioe) {
	         ioe.printStackTrace();
	    } finally {
	        try {
	            if (is != null) is.close();
	        } catch (IOException ioe) {
	            // nothing to see here
	        }
	    }
	    try{
	    	id = Integer.parseInt(id_s);
	    }catch(Exception e){}
	    
		return id;
	}
	
}
