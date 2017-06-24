package BISAG.Geom_Proj;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {

	public static void main(String[] args) {

		Connection conn = null;
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
					String st = rs.getString("jsonb_build_object");
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
	}
}