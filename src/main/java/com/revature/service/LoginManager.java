package com.revature.service;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.javatuples.Pair;

import at.favre.lib.crypto.bcrypt.BCrypt.Hasher;
import com.revature.doa.DatabaseManager;

import at.favre.lib.crypto.bcrypt.BCrypt;
public class LoginManager {
	private String adminKey = System.getenv("OD_Admin");
	DatabaseManager db;
	public LoginManager(){
		this.db = new DatabaseManager();
	}
	
	public boolean createUser(String username, String password) {
		
		try(Connection d =db.getLocalConnection("OutdoorApp", "postgres", "myLocal");) {
			d.setAutoCommit(false);
			String sqlQuery = "Insert into username"
					+ "(special)"
					+ "Values"
					+"(?)"; 
			PreparedStatement stmt = d.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
			stmt.setBoolean(1, false);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return false;
	}
	public boolean createUser(String username, String password, String adminKey) {
		if(adminKey == this.adminKey)
		{
			try(Connection d =db.getLocalConnection("OutdoorApp", "postgres", "myLocal")) {
				d.setAutoCommit(false);
				String sqlQuery = "Insert into users"
						+ "(username,salt, password, special)"
						+ "Values"
						+"(?, ?, ?, ?)"; 
				PreparedStatement stmt = d.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
				byte[] salt = new byte[16];
				try {
					salt = getSalt();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchProviderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				byte[] pass = hashPass(password, salt);
				stmt.setString(1, username);
				stmt.setBytes(2,salt);
				stmt.setBytes(3, pass);
				stmt.setBoolean(4, false);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return true;
		}
		else
			return false;
	}
	private byte[] hashPass(String password , byte[] salt) {
		// TODO Auto-generated method stub
		Hasher bcrypter = BCrypt.withDefaults();
		byte[] generatedPw = bcrypter.hash(8, salt, password.getBytes());
		return generatedPw;
	}

	public boolean logUser(String username, String password) {
		byte[] hashedPassword;
	    byte[] salt;
	    String sql = "select salt, password from users where username = ?";
	    
	    try(Connection d =db.getLocalConnection("OutdoorApp", "postgres", "myLocal")) {
	    	PreparedStatement pstmt = d.prepareStatement(sql);
	    	pstmt.setString(1, username);
	        ResultSet resultSet = pstmt.executeQuery();

	        resultSet.next();
	        salt = resultSet.getBytes("salt");
	        hashedPassword = resultSet.getBytes("password");

	        if (hashedPassword.equals(hashPass(password, salt))) {
	            return true;
	        } else {
	            return false;
	        }
	    } catch(NoSuchAlgorithmException | SQLException | UnsupportedEncodingException ex) {
	        return false;
	    }
	}
	public byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}
}
