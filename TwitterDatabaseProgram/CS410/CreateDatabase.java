import java.io.*;
import java.sql.*;
import java.util.*;

import com.jcraft.jsch.*;

public class CreateDatabase {

	public static void main(String[] args) throws SQLException {
		if (args.length < 3){
			System.out.println("java CreateDatabase <Broncouser> <broncopassword> <DBname>");
		}
		else{
			Connection con = null;
			Session session = null;
			try
			{
				String strSshUser = args[0];                  // SSH loging username
				String strSshPassword = args[1];                   // SSH login password
				String databaseName = args[2];
				String strSshHost = "onyx.boisestate.edu";          // hostname or ip or SSH server
				int nSshPort = 22;                                    // remote SSH host port number
				String strRemoteHost = "localhost";  // hostname or ip of your database server
				int nLocalPort = 3368;  // local port number use to bind SSH tunnel
				
				String strDbUser = "msandbox";                    // database loging username
				String strDbPassword = "Reason2@ppeal";                    // database login password
				int nRemotePort = 10171; // remote port number of your database 
				
				/*
				 * STEP 0
				 * CREATE a SSH session to ONYX
				 * 
				 * */
				session = CreateDatabase.doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);
				
				
				/*
				 * STEP 1 and 2
				 * LOAD the Database DRIVER and obtain a CONNECTION
				 * 
				 * */
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:"+nLocalPort, strDbUser, strDbPassword);

				/*
				 * STEP 3
				 * EXECUTE STATEMENTS
				 * 
				 * */

				Statement stmt = null;
				stmt = con.createStatement();
				
		
				
			 	//ResultSet resultSet = stmt.executeQuery("select * from COMPANY.EMPLOYEE");
			 	try{
			 		stmt.executeUpdate("CREATE DATABASE " + databaseName);
			 		stmt.executeUpdate("USE " + databaseName);
			 		createTables(stmt);
			 		System.out.println("Database successfully created");
			 	} catch (SQLException e) {
			 		if(e.getMessage().equals("Can't create database '410G4'; database exists")) {
			 			stmt.executeUpdate("DROP DATABASE " + databaseName);
				 		stmt.executeUpdate("CREATE DATABASE " + databaseName);
				 		stmt.executeUpdate("USE " + databaseName);
				 		createTables(stmt);
				 		System.out.println("Database successfully created");
			 		}
			 		else
			 		{
			 			e.printStackTrace();
			 		}
			 		
			 	}

				
			} 
			catch( Exception e )
			{
				e.printStackTrace();
			}
			finally{
				
				/*
				 * STEP 5
				 * CLOSE CONNECTION AND SSH SESSION
				 * 
				 * */
				
				con.close();
				session.disconnect();
			}

		}
	}
	
	private static Session doSshTunnel( String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
	{
		/*This is one of the available choices to connect to mysql
		 * If you think you know another way, you can go ahead*/
		
		final JSch jsch = new JSch();
		java.util.Properties configuration = new java.util.Properties();
		configuration.put("StrictHostKeyChecking", "no");

		Session session = jsch.getSession( strSshUser, strSshHost, 22 );
		session.setPassword( strSshPassword );

		session.setConfig(configuration);
		session.connect();
		session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
		return session;
	}
	
	private static void createTables(Statement stmt) throws SQLException{
		
		stmt.executeUpdate("create table User (id int not null,"
				+ "username char(50) not null,"
				+ "location char(30) not null,"
				+ "primary key(id));");
		
		stmt.executeUpdate("create table Tweets (userid int not null,"
				+ "tweetid int not null,"
				+ "timestamp char(30) not null,"
				+ "tweet TEXT,"
				+ "primary key (tweetid),"
				+ "FOREIGN KEY (userid) REFERENCES User(id));");
		
		stmt.executeUpdate("create table ReTweet(user1 int not null, "
				+ "user2 int not null,"
				+ "primary key (user1, user2),"
				+ "FOREIGN KEY (user1) REFERENCES User(id),"
				+ "FOREIGN KEY (user2) REFERENCES User(id));");
		
		stmt.executeUpdate("create table Mention(user1 int not null, "
				+ "user2 int not null,"
				+ "primary key (user1, user2),"
				+ "FOREIGN KEY (user1) REFERENCES User(id),"
				+ "FOREIGN KEY (user2) REFERENCES User(id));");
		
		stmt.executeUpdate("create table Reply(user1 int not null,"
				+ "user2 int not null,"
				+ "primary key (user1, user2),"
				+ "FOREIGN KEY (user1) REFERENCES User(id),"
				+ "FOREIGN KEY (user2) REFERENCES User(id));"); 
		
		stmt.executeUpdate("create table Followers(follower int not null,"
				+ "followee int not null,"
				+ "primary key (follower, followee),"
				+ "FOREIGN KEY (follower) REFERENCES User(id),"
				+ "FOREIGN KEY (followee) REFERENCES User(id));");
		
		stmt.executeUpdate("LOAD DATA INFILE '/home/DavisClarke/CS410/tables/user.csv' INTO TABLE User FIELDS TERMINATED BY '|'");
		stmt.executeUpdate("LOAD DATA INFILE '/home/DavisClarke/CS410/tables/Tweets.csv' INTO TABLE Tweets FIELDS TERMINATED BY '|'");
		stmt.executeUpdate("LOAD DATA INFILE '/home/DavisClarke/CS410/tables/ReTweet.csv' INTO TABLE ReTweet FIELDS TERMINATED BY '|'");
		stmt.executeUpdate("LOAD DATA INFILE '/home/DavisClarke/CS410/tables/Mention.csv' INTO TABLE Mention FIELDS TERMINATED BY '|'");
		stmt.executeUpdate("LOAD DATA INFILE '/home/DavisClarke/CS410/tables/Reply.csv' INTO TABLE Reply FIELDS TERMINATED BY '|'");
		stmt.executeUpdate("LOAD DATA INFILE '/home/DavisClarke/CS410/tables/followers.csv' INTO TABLE Followers FIELDS TERMINATED BY '|'");

	}

}
