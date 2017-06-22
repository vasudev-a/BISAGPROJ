package BISAG.Geom_edit;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Geom_servlet
 */
@WebServlet("/Geom_servlet")
public class Geom_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String usename = request.getParameter("top");
		String name = request.getParameter("name");
		String notes = request.getParameter("notes");
		System.out.println(name+" and "+notes);
		Geom_servlet gs = new Geom_servlet();
		int err=0;
		try {
			err = gs.update_db(usename, name, notes);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("In geom_servlet " + usename);
		System.out.println("Error Code: "+ err);
	}

	public int update_db(String route, String name, String notes) throws ClassNotFoundException, SQLException{
		Connection conn;
		Class.forName("org.postgresql.Driver");
		String user = "postgres";
		String password = "postgresql";
		String url = "jdbc:postgresql://localhost:5432/postgres";
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println(conn);
		} catch (SQLException e) {
			System.out.println(e);
			return 1;
		}
		String query="";
		if(route.contains("POINT"))
		{
			query = "insert into features values('"+ name +"', '"+ notes +"', ST_GeomFromText('"+ route +"', 4326));";
			
		}
		else if(route.contains("POLYGON"))
		{
			query = "insert into polygon values('"+ name +"', '"+ notes +"', ST_GeomFromText('"+ route +"', 4326));";
		}
		else
		{
			query = "insert into linestring values('"+ name +"', '"+ notes +"', ST_GeomFromText('"+ route +"', 4326));";
		}
		
		System.out.println(query);
		Statement stmt = conn.createStatement();
		try{
			stmt.executeQuery(query);
		}catch(Exception e){}
		
		return 0;
		
	}
}
