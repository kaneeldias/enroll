package sample.models;

import sample.common.Database;
import sample.excpetions.CourseNotFoundException;
import sample.excpetions.UserNotFoundException;
import sample.views.CourseManagementView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentModel extends UserModel {

    CourseModel course;
    int semester;

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public CourseModel getCourse() {
        return course;
    }

    public void setCourse(CourseModel course) {
        this.course = course;
    }

    public StudentModel(int user_id) throws SQLException, UserNotFoundException, CourseNotFoundException{
        super(user_id);
        Database db = new Database();
        String query = "SELECT course_id, current_semester FROM enrollment WHERE student_id = ?";
        PreparedStatement statement = db.getPreparedStatement(query);
        statement.setInt(1, user_id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            this.setSemester(rs.getInt("current_semester"));
            CourseModel course = CourseModel.getCourse(rs.getInt("course_id"));
            this.setCourse(course);
        }
        else{
            throw new UserNotFoundException();
        }
    }

    public StudentModel(UserModel user){
        this.user_id = user.user_id;
        this.type = user.type;
        this.name = user.name;
        this.contact = user.contact;
        this.email = user.email;
    }

    public void enroll(CourseModel course){
        Database db = new Database();
        String sql = String.format("INSERT INTO enrollment (student_id, course_id) VALUES('%d', '%d')", this.user_id, course.getCourse_id());
        db.execute(sql);
    }

    public static StudentModel getStudent(int user_id) throws SQLException, UserNotFoundException, CourseNotFoundException{
        Database db = new Database();
        StudentModel student = new StudentModel(user_id);
        String query = "SELECT course_id, semester FROM enrollment WHERE student_id = ?";
        PreparedStatement statement = db.getPreparedStatement(query);
        statement.setInt(1, user_id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            student.setSemester(rs.getInt("semester"));
            CourseModel course = CourseModel.getCourse(rs.getInt("course_id"));
            student.setCourse(course);
            return student;
        }
        throw new UserNotFoundException();
    }


}
