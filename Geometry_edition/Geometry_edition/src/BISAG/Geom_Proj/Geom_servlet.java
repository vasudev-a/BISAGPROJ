package BISAG.Geom_Proj;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		String id= request.getParameter("id");
		System.out.println(id);
		System.out.println(usename);
		Geom_servlet gs = new Geom_servlet();
		int err=0;
		try {
			err = gs.update_db(usename, name, notes, id);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Error Code: "+ err);
	}
	
	public int update_db(String route, String name, String notes, String id) throws ClassNotFoundException, SQLException{
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
		Geom_servlet gs = new Geom_servlet();
		String[] pro_route = gs.process_string(route);
		for(int i =0;i<pro_route.length;i++){
			String temp_route = pro_route[i];
			query = "insert into features_test values('"+ name +"', '"+ notes +"', '"+ id +"', ST_GeomFromText('"+ temp_route +"', 4326));";
			Statement stmt = conn.createStatement();
			System.out.println(query);
			try{
				stmt.executeQuery(query);
			}catch(Exception e){}
		}
		return 0;	
	}
	
	public String[] process_string(String route){
		if(route.indexOf("GEOMETRYCOLLECTION")==-1){}
		else{
			int length = route.length();
			route = route.substring(19,length-1);
			length = route.length();
		}
		String[] shape = {"POINT","LINESTRING","POLYGON"};
		System.out.println(route);
		int[] count = {0 , 0 , 0};
		for(int i =0;i<shape.length;i++){
			Pattern p = Pattern.compile(shape[i]);
			Matcher m = p.matcher(route);
			while (m.find()){
				count[i]++;
			}	
		}
		String[] temp = route.split("\\),");
		for(int i =0; i< temp.length-1;i++){
			temp[i]=temp[i]+")";
		}
		return temp;
	}
}
