package sample.models;

import sample.common.Database;
import sample.common.Result;

public class PGStudentModel extends StudentModel {

    String degree;
    String institute;
    int year;
    float gpa;
    String d_class;

    public PGStudentModel(StudentModel student) {
        super(student);
    }


    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public void setD_class(String d_class) {
        this.d_class = d_class;
    }

    public void saveDetails(){
        Database db = new Database();
        String sql = String.format("INSERT INTO qual_details (student_id, degree, institute, year, gpa, class) VALUES('%d', '%s', '%s', '%d', '%.2f', '%s')", this.user_id, this.degree, this.institute, this.year, this.gpa, this.d_class);
        db.execute(sql);
    }
}
