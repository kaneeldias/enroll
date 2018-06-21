package sample.common;

import javax.xml.transform.Result;
import java.sql.*;


public class Database {

    private final String host = "localhost:3306";
    private final String database = "enroll";
    private final String username = "root";
    private final String password = "";
    private Connection connection;

    public Database(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database + "?user="+ username +"&password=" + password);
        }
        catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }

    }

    public PreparedStatement getPreparedStatement(String query) throws SQLException{
        return connection.prepareStatement(query);
    }

    public boolean execute(String sql){
        try{
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }
        catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return false;
        }
        return true;
    }

    public int executeWithKey(String sql){
        try{
            Statement statement = connection.createStatement();
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }
        }
        catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }

        return 0;
    }

    public ResultSet query(String sql) throws SQLException {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
    }

}
