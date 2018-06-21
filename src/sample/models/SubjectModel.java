package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.common.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectModel {

    private int subject_id;
    private String code;
    private String title;

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void saveToDB() throws SQLException{
        Database db = new Database();
        String query = "INSERT INTO subject (code, title) VALUES (?, ?);";
        PreparedStatement statement = db.getPreparedStatement(query);
        statement.setString(1, this.code);
        statement.setString(2, this.title);
        statement.execute();
    }

    public static ObservableList<SubjectModel> getAllSubjects() throws SQLException{
        ObservableList<SubjectModel> subjectList = FXCollections.observableArrayList();
        Database db = new Database();
        String query = "SELECT * FROM subject;";
        PreparedStatement statement = db.getPreparedStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            SubjectModel subject = new SubjectModel();
            subject.setSubject_id(rs.getInt("subject_id"));
            subject.setCode(rs.getString("code"));
            subject.setTitle(rs.getString("title"));
            subjectList.add(subject);
        }
        return subjectList;
    }

    @Override
    public String toString(){
        return this.getCode() + " - " + this.getTitle();
    }

}
