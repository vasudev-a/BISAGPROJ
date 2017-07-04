package BISAG.Geom_Proj;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class edit_feature
 */
@WebServlet("/edit_feature")
public class edit_feature extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usename = request.getParameter("top");
		String id= request.getParameter("id");
		System.out.println(id);
		System.out.println(usename);
		edit_feature ef = new edit_feature();
		try {
			ef.update_db(usename, id);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update_db(String route, String id) throws ClassNotFoundException, SQLException{
		Connection conn = null;
		ResultSet rs = null;
		String name = null;
		String notes = null;
		Class.forName("org.postgresql.Driver");
		String user = "postgres";
		String password = "postgresql";
		String url = "jdbc:postgresql://localhost:5432/postgres";
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println(conn);
		} catch (SQLException e) {
			System.out.println(e);
		}
		String query="select * from features_test where id="+id;
		Statement stmt = conn.createStatement();
		System.out.println(query);
		try{
			rs = stmt.executeQuery(query);
		}catch(Exception e){}
		while(rs.next()){
			name = rs.getString("name");
			notes = rs.getString("notes");
		}
		
		query = "delete from features_test where id="+id+";";
		stmt = conn.createStatement();
		System.out.println(query);
		try{
			rs = stmt.executeQuery(query);
		}catch(Exception e){}
		
		query = "insert into features_test values('"+ name +"', '"+ notes +"', '"+ id +"', ST_GeomFromText('"+ route +"', 4326));";
		stmt = conn.createStatement();
		System.out.println(query);
		try{
			rs = stmt.executeQuery(query);
		}catch(Exception e){}
		while(rs.next()){
			name = rs.getString("name");
			notes = rs.getString("notes");
		}
	}
}
