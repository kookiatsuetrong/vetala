package server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Storage {

	public static User 
	getUserByEmail(String email) {
		String source = Setup.connectionString;
		var sql = "select * from users where email = ?";
		User u = null;
		try {
			var cn = DriverManager.getConnection(source);
			var ps = cn.prepareStatement(sql);
			ps.setString(1, email);
			var rs = ps.executeQuery();
			if (rs.next()) {
				u = new User();
				u.number    = rs.getInt("number");
				u.email     = rs.getString("email");
				u.firstName = rs.getString("first_name");
				u.lastName  = rs.getString("last_name");
				u.password  = rs.getString("password");
				u.type      = rs.getString("type");
			}
			rs.close(); ps.close(); cn.close();
		} catch (Exception e) { }
		return u;
	}
	
	public static User
	checkPassword(String email, String password) {
		String source = Setup.connectionString;
		var sql =   " select * from users where email = ? " +
					" and password = sha2(?, 512)         ";
		User u = null;
		try {
			var cn = DriverManager.getConnection(source);
			var ps = cn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			var rs = ps.executeQuery();
			if (rs.next()) {
				u = new User();
				u.number    = rs.getInt("number");
				u.email     = rs.getString("email");
				u.firstName = rs.getString("first_name");
				u.lastName  = rs.getString("last_name");
				u.password  = rs.getString("password");
				u.type      = rs.getString("type");
			}
			rs.close(); ps.close(); cn.close();
		} catch (Exception e) { }
		return u;
	}
	
	public static boolean
	createAccount(String email, String password, 
			String firstName, String lastName) {
		String source = Setup.connectionString;
		boolean result = false;
		var sql =   " insert into users                      " +
					" (email,password,first_name,last_name)  " +
					" values(?, sha2(?, 512), ?, ?)          ";
		try {
			var cn = DriverManager.getConnection(source);
			var ps = cn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ps.setString(3, firstName);
			ps.setString(4, lastName);
			result = ps.execute();
			ps.close(); cn.close();
		} catch (Exception e) { }
		return result;
	}
	
	public static boolean
	resetPassword(String email, String password) {
		String source = Setup.connectionString;
		boolean result = false;	
		var sql =   " update users set password = sha2(?, 512)  " +
					" where email = ?                           ";
		try {
			var cn = DriverManager.getConnection(source);
			var ps = cn.prepareStatement(sql);
			ps.setString(1, password);
			ps.setString(2, email);
			result = ps.execute();    // or ps.executeUpdate()
			ps.close(); cn.close();
		} catch (Exception e) { }
		return result;
	}
	
	public static int
	changePassword(String email, String current, String password) {
		String source = Setup.connectionString;
		int result = 0;
		var sql =   " update users set password = sha2(?, 512)    " +
					" where email = ? and password = sha2(?, 512) ";
		try {
			var cn = DriverManager.getConnection(source);
			var ps = cn.prepareStatement(sql);
			ps.setString(1, password);
			ps.setString(2, email);
			ps.setString(3, current);
			result = ps.executeUpdate();
			ps.close(); cn.close();
		} catch (Exception e) { }
		return result;
	}
	
	public static int
	saveContactMessage(String topic, String detail, String email) {
		String source = Setup.connectionString;
		var sql   = " insert into messages(topic,detail,email,time) " +
					" values(?, ?, ?, utc_timestamp())              ";
		ArrayList<Integer> list = new ArrayList<>();
		
		try {
			var cn = DriverManager.getConnection(source);
			var ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, topic);
			ps.setString(2, detail);
			ps.setString(3, email);
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				list.add( (int)rs.getLong(1) );
			}
			rs.close(); ps.close(); cn.close();
		} catch (Exception e) { }
		
		if (list.size() == 0) return 0;
		Integer first = list.get(0);
		if (first == null) return 0;
		return first;
	}
	
	/*
		WARNING: This method can be attacked by SQL Injection
		Try to log in by this password:
		select * from user where
		email = 'x' and password = sha2('y', 512)
										'
										'
					.-------------------'
					'
					v
					', 512) or true; --  
	*/
	public static User findUser(String email, String password) {
		String source = Setup.connectionString;
		var query = " select * from users where       " +
					" email = '" + email + "' and     " +
					" password = sha2('" + password + "', 512)";
		User user = null;
		try {
			var cn = DriverManager.getConnection(source);
			var sm = cn.createStatement();
			var rs = sm.executeQuery(query);
			if (rs.next()) {
				user = new User();
				user.email     = rs.getString("email");
				user.firstName = rs.getString("first_name");
				user.lastName  = rs.getString("last_name");
			}
			rs.close(); sm.close(); cn.close();
		} catch (Exception e) { }

		return user;
	}
	
}
