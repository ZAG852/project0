package com.revature.service;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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
			stmt.setBytes(2,salt);
			stmt.setBytes(3, pass);
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
		} catch (SQLException | NoSuchAlgorithmException e1) {
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
				stmt.setBytes(2,salt);
				stmt.setBytes(3, pass);
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
			} catch (SQLException | NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return new People(user.getUsername(), HashedPassword, autoId, false);
		}
		else
			return null;
	}
	private byte[] hashPass(String password , byte[] salt) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		Hasher bcrypter = BCrypt.withDefaults();
		byte[] generatedPw = bcrypter.hash(8, salt, password.getBytes());
		return generatedPw;
		// Option 1: Hash password using SHA + salt
		//MessageDigest md = MessageDigest.getInstance("SHA-512");
		//md.update(salt);
		//byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
		//return hashedPassword;
	}

	public boolean logUser(String username, String password) {
		String hashedPassword;
	    byte[] salt;
	    String sql = "select salt, password from users where username = ?";
	    
	    try(Connection d =db.getLocalConnection("OutdoorApp", "postgres", "myLocal")) {
	    	PreparedStatement pstmt = d.prepareStatement(sql);
	    	pstmt.setString(1, username);
	        ResultSet resultSet = pstmt.executeQuery();

	        resultSet.next();
	        salt = resultSet.getBytes("salt");
	        System.out.println("Salt: " + new String(salt));
	        hashedPassword = new String(resultSet.getBytes("password"));
	        System.out.println("hash: " + hashedPassword);
	        System.out.println("my hash: " + hashPass(password, salt));
	        if (hashedPassword.equals(new String(hashPass(password, salt)))) {
	        	
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
