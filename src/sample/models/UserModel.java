package sample.models;

import sample.common.Database;
import sample.excpetions.UserNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    protected int user_id;
    protected String type;
    protected String name;
    protected String nic;
    protected String email;
    protected String contact;

    public UserModel(String type, String name, String nic, String email, String contact){
        this.type = type;
        this.name = name;
        this.nic = nic;
        this.email = email;
        this.contact = contact;
    }

    public UserModel(){

    }

    public UserModel(int user_id) throws SQLException, UserNotFoundException{
        Database db = new Database();
        String query = "SELECT * FROM user WHERE user_id = ?;";
        PreparedStatement statement = db.getPreparedStatement(query);
        statement.setInt(1, user_id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            this.setUser_id(rs.getInt("user_id"));
            this.setName(rs.getString("name"));
            this.setNic(rs.getString("nic"));
            this.setEmail(rs.getString("email"));
            this.setContact(rs.getString("contact"));
            this.setType(rs.getString("type"));
        }
        else{
            throw new UserNotFoundException();
        }
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public UserModel saveToDB(){
        Database db = new Database();
        String sql = String.format("INSERT INTO user (type, name, nic, email, contact) VALUES('%s', '%s', '%s', '%s', '%s')", this.type, this.name, this.nic, this.email, this.contact);
        this.user_id =  db.executeWithKey(sql);
        return this;
    }

    public static UserModel search(String nic) throws SQLException, UserNotFoundException{
        Database db = new Database();
        String query = "SELECT user_id FROM user WHERE nic = ?;";
        PreparedStatement statement = db.getPreparedStatement(query);
        statement.setString(1, nic);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            UserModel user = new UserModel(rs.getInt("user_id"));
            return user;
        }
        else{
            throw new UserNotFoundException();
        }
    }


}
