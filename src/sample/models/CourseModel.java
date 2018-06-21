package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.common.Database;
import sample.excpetions.CourseNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseModel {

    private int course_id;
    private String type;
    private String title;
    private int semesters;
    private int faculty_id;

    public int getCourse_id() {
        return course_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSemesters() {
        return semesters;
    }

    public void setSemesters(int semesters) {
        this.semesters = semesters;
    }

    public int getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(int faculty_id) {
        this.faculty_id = faculty_id;
    }

    public CourseModel(int course_id, String type, String title, int semsesters, int faculty_id){
        this.course_id = course_id;
        this.type = type;
        this.title = title;
        this.semesters = semsesters;
        this.faculty_id = faculty_id;
    }

    public ObservableList<SubjectModel> getSubjects() throws SQLException{
        ObservableList<SubjectModel> subjectList = FXCollections.observableArrayList();
        Database db = new Database();
        String query = "SELECT subject.subject_id, subject.code, subject.title FROM course_subject INNER JOIN subject ON course_subject.subject_id = subject.subject_id WHERE course_id = ?";
        PreparedStatement statement = db.getPreparedStatement(query);
        statement.setInt(1, this.course_id);
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

    public ObservableList<SubjectModel> getSubjectsForSemester(int semester) throws SQLException{
        ObservableList<SubjectModel> subjectList = FXCollections.observableArrayList();
        Database db = new Database();
        String query = "SELECT subject.subject_id, subject.code, subject.title FROM course_subject INNER JOIN subject ON course_subject.subject_id = subject.subject_id WHERE course_id = ? AND semester = ?";
        PreparedStatement statement = db.getPreparedStatement(query);
        statement.setInt(1, this.course_id);
        statement.setInt(2, semester);
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

    public void addSubject(SubjectModel subject, int semester, boolean compulsory) throws SQLException{
        Database db = new Database();
        String query = "INSERT into course_subject (course_id, subject_id, semester, compulsory) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = db.getPreparedStatement(query);
        statement.setInt(1, this.course_id);
        statement.setInt(2, subject.getSubject_id());
        statement.setInt(3, semester);
        statement.setBoolean(4, compulsory);
        statement.execute();
    }

    public void removeSubject(SubjectModel subject, int semester) throws SQLException{
        Database db = new Database();
        String query = "DELETE FROM course_subject WHERE course_id = ? AND subject_id = ? AND semester = ?";
        PreparedStatement statement = db.getPreparedStatement(query);
        statement.setInt(1, this.course_id);
        statement.setInt(2, subject.getSubject_id());
        statement.setInt(3, semester);
        statement.execute();
    }

    public static CourseModel[] getAllCourses(){
        ArrayList<CourseModel> courses = new ArrayList<>();

        try{
            Database db = new Database();
            String sql = String.format("SELECT * FROM course;");
            ResultSet rs = db.query(sql);
            while (rs.next()){
                int course_id = rs.getInt("course_id");
                String type = rs.getString("course_type");
                String title = rs.getString("title");
                int semesters = rs.getInt("semesters");
                int faculty_id = rs.getInt("faculty_id");
                CourseModel course = new CourseModel(course_id, type, title, semesters, faculty_id);
                courses.add(course);
            }
        }
        catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }

        return courses.stream().toArray(CourseModel[]::new);
    }

    public static CourseModel getCourse(int course_id) throws SQLException, CourseNotFoundException{
        Database db = new Database();
        String query = "SELECT * FROM course WHERE course_id = ?";
        PreparedStatement statement = db.getPreparedStatement(query);
        statement.setInt(1, course_id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            int id = rs.getInt("course_id");
            String type = rs.getString("course_type");
            String title = rs.getString("title");
            int semesters = rs.getInt("semesters");
            int faculty_id = rs.getInt("faculty_id");
            CourseModel course = new CourseModel(course_id, type, title, semesters, faculty_id);
            return course;
        }
        throw new CourseNotFoundException();
    }

    public String toString(){
        return this.getTitle();
    }

}
