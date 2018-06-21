package sample.models;

import sample.common.Database;
import sample.common.Result;

public class UGStudentModel extends StudentModel {

    int year;
    int rank;
    float z_score;
    Result results[] = new Result[3];


    public void setYear(int year) {
        this.year = year;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setZ_score(float z_score) {
        this.z_score = z_score;
    }

    public void setResults(Result[] results) {

        this.results = results;
    }

    public UGStudentModel(StudentModel student) {
        super(student);
    }

    public void saveDetails(){
        Database db = new Database();
        String sql = String.format("INSERT INTO al_details (student_id, subject1, subject1_result, subject2, subject2_result,  subject3, subject3_result, year, rank, z_score) VALUES('%d', '%s', '%s', '%s', '%s', '%s', '%s', '%d', '%d', '%.4f')", this.user_id, this.results[0].title, this.results[0].result, this.results[1].title, this.results[1].result, this.results[2].title, this.results[2].result, this.year, this.rank, this.z_score);
        db.execute(sql);
    }
}
