import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;


public class Execute {
    static Connection connection = null;
    static Session session = null;
    static Statement statement = null;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        
    	if (args.length < 7){
			System.out.println("java Execute <Broncouser> <broncopassword> <DBname> <query/update> <TaskNumber> <TaskQueryFile> <outputFile> <parametersforQuery>");
		}
    	
    	String strSshUser = args[0];
        String strSshPassword = args[1];
        String strSshHost = "onyx.boisestate.edu";
        int nSshPort = 22;
        String strRemoteHost = "localhost";
        int nLocalPort = 3395;
        String strDbUser = "msandbox";
        String strDbPassword = "Reason2@ppeal";
        int nRemotePort = 10171;
        String databaseName = args[2];
        String queryOrUpdate = args[3].toLowerCase();
        String taskString = args[4];
        String taskQueryFile = args[5];
        String outputFile = args[6];
        ArrayList<String> paramtersForQuery = new ArrayList<String>();
        
        if(args.length > 7)
        {
        	for(int i = 7; i < args.length; i++)
        	{
        		paramtersForQuery.add(args[i]);
        	}
        }
        
        try {
            session = Execute.doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:" + nLocalPort, strDbUser, strDbPassword);

        statement = connection.createStatement();
        String sql = "USE " + databaseName;
        statement.executeUpdate(sql);

        if (queryOrUpdate.equals("query")) {
            int taskNumber = Integer.parseInt(taskString);
            switch (taskNumber) {
                case 1:
                    getUsersWithNoFollowers(taskQueryFile, outputFile);
                    break;
                case 2:
                    getUsersWithMaxRetweets(taskQueryFile, outputFile);
                    break;
                case 4:
                	getUsersWithMoreThanThousandFollowers(taskQueryFile, outputFile);
                    break;
                default:
                    System.out.println("Task number does not exist, please choose a task number between 1 and 7");
                    break;
            }
        }

        if (queryOrUpdate.equals("update")) {
        	int taskNumber = Integer.parseInt(taskString);
            switch (taskNumber) {
                case 3:
                	System.out.println("Parameters for get Tweet: <#yourhashtag>");
                    getTweetWithHashtag(taskQueryFile, outputFile, paramtersForQuery);
                    break; 
                case 5:
                	System.out.println("Parameters for new User: <UserId> <Username> <Location>");
                    addNewUser(taskQueryFile, outputFile, paramtersForQuery);
                    break;
                case 6:
                    System.out.println("Parameters for new Tweet: <UserId> <TweerId> <\"Timestamp\"> <\"Tweet\">");
                    addNewTweet(taskQueryFile, outputFile, paramtersForQuery);
                    break;
                case 7:
                    System.out.println("Parameters for new Followers: <FollowerId> <FolloweeId>");
                    addFollowersForUser(taskQueryFile, outputFile, paramtersForQuery);
                    break;
                default:
                	System.out.println("Task number does not exist, please choose a task number between 1 and 7");
                    break;
            }

        }
        connection.close();
        session.disconnect();
    }

    private static void addFollowersForUser(String taskQueryFile, String outputFile, ArrayList<String> paramtersForQuery){
    	try {
            BufferedReader reader = new BufferedReader(new FileReader(taskQueryFile));
            try {
            	
                String query = reader.readLine();
                
                try {
                	
                    statement = connection.createStatement();
                    if (paramtersForQuery.size() < 2) { 
                        System.err.println("Please provide all information for a new follower");
                        System.exit(1);
                    }
                    
                    ResultSet resultSet1 = statement.executeQuery("Select follower FROM Followers WHERE follower = " + paramtersForQuery.get(0) + " AND followee = "+ paramtersForQuery.get(1));
                    
                    boolean followerExists = resultSet1.first();
                    boolean followYourSelf = paramtersForQuery.get(0).equals(paramtersForQuery.get(1));
                    
                    if(followerExists)
                    {
                    	 System.out.println("Follower is already following followee");
                    	 System.exit(1);
                    }
                    else if(followYourSelf)
                    {
                    	System.out.println("Cannot follow yourself");
                   	 	System.exit(1);
                    }
                    else
                    {
                    	String update = query + paramtersForQuery.get(0) +", '"+ paramtersForQuery.get(1) + "')";
                        statement.executeUpdate(update);
                        System.out.println("Successfully added new follower");
                    }
                    
                    reader.close();
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private static void addNewTweet(String taskQueryFile, String outputFile, ArrayList<String> paramtersForQuery) {
        try {
        	
        	String tweetString = "";
        	
        	for(int i = 4; i < paramtersForQuery.size(); i ++)
        	{
        		tweetString = tweetString + " " + paramtersForQuery.get(i);
        	}
        	
            BufferedReader reader = new BufferedReader(new FileReader(taskQueryFile));
            Scanner scan = new Scanner(System.in);
            
            try {
            	
                String query = reader.readLine();
                
                try {
                	
                    statement = connection.createStatement();
                   
                    if (paramtersForQuery.size() < 4) {
                    	
                        System.err.println("Please provide all information for a new tweet");
                        System.exit(1);
                    }
                    
                    ResultSet resultSet = statement.executeQuery("Select username FROM User WHERE id = " + paramtersForQuery.get(0));
                    boolean foundUser = resultSet.first();
                    
                    ResultSet tweetIdFound = statement.executeQuery("Select tweetid FROM Tweets WHERE tweetid = " + paramtersForQuery.get(1));
                    boolean booltweet = tweetIdFound.first();
                    
                    ResultSet maxTweetId = statement.executeQuery("SELECT MAX(tweetid) FROM Tweets");
                    maxTweetId.first();
                    int tweetId = maxTweetId.getInt(1) + 1;
                    
                    if(booltweet)
                    {
                    	System.out.println("A tweet with that Id already exists generating new id");
                    }
                    
                    if(foundUser)
                    {
                    	 PreparedStatement stmt = connection.prepareStatement(query + " (?,?,?,?)");
                    	 
                         stmt.setInt(1,Integer.parseInt(paramtersForQuery.get(0)));
                         stmt.setInt(2, tweetId);
                         stmt.setString(3, paramtersForQuery.get(2) + " " + paramtersForQuery.get(3));
                         stmt.setString(4, tweetString);
                         stmt.executeUpdate();
                    }
                    else
                    {
                    	System.out.println("User doesnt exist attempting to create new user...");
                    	
                    	System.out.println("Please enter a username");
                    	String userName = scan.nextLine();
                    	
                    	System.out.println("Please enter a location");
                    	String location = scan.nextLine();
                    	
                    	Statement stmt = connection.createStatement();
                    	
                    	ArrayList<String> addUser = new ArrayList<String>();
                    	addUser.add(userName);
                    	addUser.add(location);
                    	
                    	ResultSet result = stmt.executeQuery("SELECT MAX(id) FROM User;");
                    	result.next();
                    	int userId = result.getInt(1) + 1;
                    	
                    	statement = connection.createStatement();
                    	
                        if (addUser.size() < 2) { 
                            System.err.println("Please provide all information for a new user");
                            System.exit(1);
                        }
                        String update = "INSERT INTO User VALUES (" + userId +", '"+ userName + "', '" + location + "')";
                        statement.executeUpdate(update);
                        
                        System.out.println("Successfully added new User");
                        
                        PreparedStatement preparestmt = connection.prepareStatement(query + " (?,?,?,?)");
                        
                        preparestmt.setInt(1, userId);
                        preparestmt.setInt(2, tweetId);
                        preparestmt.setString(3, paramtersForQuery.get(2) + " " + paramtersForQuery.get(3));
                        preparestmt.setString(4, tweetString);
                        preparestmt.executeUpdate();
                        System.out.println("Successfully added new Tweet");
                    }
              
                    scan.close();
                    reader.close();
                   
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void addNewUser(String taskQueryFile, String outputFile, ArrayList<String> paramtersForQuery) {
        try {
        	
            BufferedReader reader = new BufferedReader(new FileReader(taskQueryFile));
            
            try {
            	
                String query = reader.readLine();
                
                try {
                	
                    statement = connection.createStatement();
                    if (paramtersForQuery.size() < 3) { 
                    	
                        System.err.println("Please provide all information for a new user");
                        System.exit(1);
                    }
                    
                    ResultSet resultSet = statement.executeQuery("Select username FROM User WHERE id = " + paramtersForQuery.get(0));
                    
                    if(resultSet.first())
                    {
                    	 System.out.println("User with that Id already exists");
                    	 System.exit(1);
                    }
                    else
                    {
                    	ResultSet highestId = statement.executeQuery("SELECT MAX(id) FROM User;");
                    	highestId.next();
                    	int userId = highestId.getInt(1) + 1;
                    	String update = query + userId +", '"+ paramtersForQuery.get(1) + "', '" + paramtersForQuery.get(2) + "')";
                        statement.executeUpdate(update);
                        System.out.println("Successfully added new User");
                    }
                    
                    reader.close();
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static void getTweetWithHashtag(String taskQueryFile, String outputFile, ArrayList<String> paramtersForQuery) {
        try {

            BufferedReader reader = new BufferedReader(new FileReader(taskQueryFile));
            
            try {
            	
                String query = reader.readLine();
                
                try {
                	
                    statement = connection.createStatement();
                    
                    query += paramtersForQuery.get(0) + "%'";
                    ResultSet resultSet = statement.executeQuery(query);
                    ResultSetMetaData rsmd = resultSet.getMetaData();

                    int columnsNumber = rsmd.getColumnCount();
                    File fileOut = new File(outputFile);
                    FileWriter writer = new FileWriter(fileOut);
                    
                    while (resultSet.next()) {
                        for (int i = 1; i <= columnsNumber; i++) {
                            String columnValue = resultSet.getString(i);
                            writer.write(columnValue + " ");
                        }
                        
                        writer.write("\n");
                    }
                    
                    System.out.println("Query successful output written to file");
                    
                    reader.close();
                    writer.flush();
                    writer.close();
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void getUsersWithMoreThanThousandFollowers(String taskQueryFile, String outputFile) {
        try {

            BufferedReader reader = new BufferedReader(new FileReader(taskQueryFile));

            try {
            	
                String query = reader.readLine();
                
                try {
                	
                    statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    ResultSetMetaData rsmd = resultSet.getMetaData();

                    int columnsNumber = rsmd.getColumnCount();
                    File fileOut = new File(outputFile);
                    FileWriter writer = new FileWriter(fileOut);
                    
                    while (resultSet.next()) {
                        for (int i = 1; i <= columnsNumber; i++) {
                            String columnValue = resultSet.getString(i);
                            writer.write(columnValue + " ");
                        }
                        
                        writer.write("\n");
                        
                    }
                    
                    System.out.println("Query successful output written to file");
                    reader.close();
                    writer.flush();
                    writer.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void getUsersWithMaxRetweets(String taskQueryFile, String outputFile) {
        try {

            BufferedReader reader = new BufferedReader(new FileReader(taskQueryFile));

            try {
            	
                String query = reader.readLine();
                
                try {
                    statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    ResultSetMetaData rsmd = resultSet.getMetaData();

                    int columnsNumber = rsmd.getColumnCount();
                    File fileOut = new File(outputFile);
                    FileWriter writer = new FileWriter(fileOut);
                    
                    while (resultSet.next()) {
                        for (int i = 1; i <= columnsNumber; i++) {
                            String columnValue = resultSet.getString(i);
                            writer.write(columnValue + " ");
                        }
                        
                        writer.write("\n");
                    }
                    
                    System.out.println("Query successful output written to file");
                    
                    reader.close();
                    writer.flush();
                    writer.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void getUsersWithNoFollowers(String taskQueryFile, String outputFile) {
        try {
        	
            BufferedReader reader = new BufferedReader(new FileReader(taskQueryFile));
            
            try {
            	
                String query = reader.readLine();
                
                try {
                	
                    statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    ResultSetMetaData rsmd = resultSet.getMetaData();

                    int columnsNumber = rsmd.getColumnCount();
                    File FileOut = new File(outputFile);
                    FileWriter writer = new FileWriter(FileOut);
                    
                    while (resultSet.next()) {
                        for (int i = 1; i <= columnsNumber; i++) {
                            String columnValue = resultSet.getString(i);
                            writer.write(columnValue + " ");
                        }
                        
                        writer.write("\n");
                    }
                    
                    System.out.println("Query successful output written to file");
                    reader.close();
                    writer.flush();
                    writer.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Session doSshTunnel(String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort) throws JSchException {
        final JSch jsch = new JSch();
        Properties configuration = new Properties();
        configuration.put("StrictHostKeyChecking", "no");

        Session session = jsch.getSession(strSshUser, strSshHost, nSshPort);
        session.setPassword(strSshPassword);

        session.setConfig(configuration);
        session.connect();
        session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
        return session;
    }
}
