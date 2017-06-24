package BISAG.Geom_Proj;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


/**
 * Servlet implementation class return_json
 */
@WebServlet("/return_json")
public class return_json extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*String result=null;
		try {
			String webPage = "http://localhost:8080/geoserver/testSQL/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=testSQL:features&maxFeatures=50&outputFormat=application%2Fjson";
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
			//System.out.println(result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(result);*/
		
		return_json rj = new return_json();
		String json = rj.getJSON();
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
	}
	
	public String getJSON(){
		Connection conn = null;
		String st=null;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String user = "postgres";
		String password = "postgresql";
		String url = "jdbc:postgresql://localhost:5432/postgres";
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println(conn);
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		String query = "SELECT jsonb_build_object("+
    "'type',     'FeatureCollection',"+
    "'features', jsonb_agg(feature)) "+
    "FROM ( "+
    "SELECT jsonb_build_object( "+
    "'type',       'Feature', "+
    "'geometry',   ST_AsGeoJSON(geom_feat)::jsonb, "+
    "'properties', to_jsonb(row) - 'geom_feat' "+
    ") AS feature "+
    "FROM (SELECT * FROM features) row) features; ";
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs = null;
		System.out.println(query);
		try{
			rs = stmt.executeQuery(query);
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			while(rs.next())
			{
				try {
					st = rs.getString("jsonb_build_object");
					System.out.println(st);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return st;
	}

}
