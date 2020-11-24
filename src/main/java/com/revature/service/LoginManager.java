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

import com.revature.beans.People;
import com.revature.beans.UserTemplate;
import com.revature.doa.DatabaseManager;

import at.favre.lib.crypto.bcrypt.BCrypt;
public class LoginManager {
	private String adminKey = System.getenv("OD_Admin");
	DatabaseManager db;
	public LoginManager(){
		this.db = new DatabaseManager();
	}
	
	public People createUser(UserTemplate user) {
		int autoId = -1;
		String HashedPassword ="";
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
			byte[] pass = hashPass(user.getPassword(), salt);
			stmt.setString(1, user.getUsername());
			stmt.setString(2,salt.toString());
			stmt.setString(3, pass.toString());
			stmt.setBoolean(4, false);
			HashedPassword = pass.toString();
			if (stmt.executeUpdate() != 1) {
				throw new SQLException("Inserting new user failed, no rows were affected");
			}
			autoId = 0;
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				autoId = generatedKeys.getInt(1);
			} else {
				throw new SQLException("Creating user failed, no ID generated");
			}
			
			d.commit();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return new People(user.getUsername(), HashedPassword, autoId, false);
	}
	public People createUser(UserTemplate user, String adminKey) {
		if(adminKey == this.adminKey)
		{
			int autoId = -1;
			String HashedPassword ="";
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
				byte[] pass = hashPass(user.getPassword(), salt);
				stmt.setString(1, user.getUsername());
				stmt.setString(2,salt.toString());
				stmt.setString(3, pass.toString());
				stmt.setBoolean(4, false);
				System.out.println(pass.toString());
				HashedPassword = pass.toString();
				if (stmt.executeUpdate() != 1) {
					throw new SQLException("Inserting new user failed, no rows were affected");
				}
				autoId = 0;
				ResultSet generatedKeys = stmt.getGeneratedKeys();
				System.out.println("I got here");
				if (generatedKeys.next()) {
					System.out.println("I got further");
					autoId = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating user failed, no ID generated");
				}
				
				d.commit();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return new People(user.getUsername(), HashedPassword, autoId, false);
		}
		else
			return null;
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
	        salt = resultSet.getString("salt").getBytes();
	        hashedPassword = resultSet.getString("password").getBytes();

	        if (hashedPassword.equals(hashPass(password, salt))) {
	            return true;
	        } else {
	            return false;
	        }
	    } catch(SQLException ex) {
	        return false;
	    }catch(Exception e) {
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
