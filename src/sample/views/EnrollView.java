package sample.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.models.*;

import java.util.ArrayList;

public class EnrollView extends View{

    private ComboBox<String> facultyCombo;
    private ComboBox<CourseModel> courseCombo;
    private ComboBox<String> intakeCombo;
    private Button nextButton;
    private Button enrollButton;

    private CourseModel[][] courses = new CourseModel[3][];
    private StudentModel student;

    public EnrollView(Pane parent, StudentModel student){

        super(parent);

        this.student = student;
        getCourses();

    }

    protected void generateView(){
        VBox formBox = new VBox(20);
        formBox.setPadding(new Insets(10));
        formBox.setMaxWidth(350);
        formBox.getStyleClass().add("formBox");
        parent.getChildren().add(formBox);

        Label formTitleLabel = new Label("Enroll in Course > Select Course");
        formTitleLabel.getStyleClass().add("formTitleLabel");
        formBox.getChildren().add(formTitleLabel);


        Label facultyLabel = new Label("Faculty");
        facultyLabel.getStyleClass().add("formLabel");
        facultyCombo = new ComboBox<>();
        facultyCombo.setPromptText("Select");
        facultyCombo.setItems(FXCollections.observableArrayList(
                "Faculty of Business",
                "Faculty of Engineering",
                "Faculty of Computing"
        ));
        VBox facultyBox = new VBox(5, facultyLabel, facultyCombo);
        formBox.getChildren().add(facultyBox);

        Label courseLabel = new Label("Course");
        courseLabel.getStyleClass().add("formLabel");
        courseCombo = new ComboBox<>();
        courseCombo.setPromptText("Select");
        VBox courseBox = new VBox(5, courseLabel, courseCombo);
        formBox.getChildren().add(courseBox);

        Label intakeLabel = new Label("intake");
        intakeLabel.getStyleClass().add("formLabel");
        intakeCombo = new ComboBox<>();
        intakeCombo.setPromptText("Select");
        intakeCombo.setItems(FXCollections.observableArrayList(
                "February",
                "July"
        ));
        VBox intakeBox = new VBox(5, intakeLabel, intakeCombo);
        formBox.getChildren().add(intakeBox);


        courseCombo.setCellFactory(new Callback<ListView<CourseModel>,ListCell<CourseModel>>(){

            @Override
            public ListCell<CourseModel> call(ListView<CourseModel> p) {

                final ListCell<CourseModel> cell = new ListCell<CourseModel>(){

                    @Override
                    protected void updateItem(CourseModel t, boolean bln) {
                        super.updateItem(t, bln);

                        if(t != null){
                            setText(t.getTitle());
                        }else{
                            setText(null);
                        }
                    }

                };

                return cell;
            }
        });

        courseCombo.setConverter(new StringConverter<CourseModel>() {
            @Override
            public String toString(CourseModel course) {
                if (course == null){
                    return null;
                } else {
                    return course.getTitle();
                }
            }

            @Override
            public CourseModel fromString(String course_id) {
                return null;
            }
        });

        nextButton = new Button("Next");
        formBox.getChildren().add(nextButton);
    }


    protected void registerActions(){

        EnrollView controller = this;

        facultyCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals("Faculty of Business")) courseCombo.setItems(FXCollections.observableArrayList(courses[0]));
                if(newValue.equals("Faculty of Engineering")) courseCombo.setItems(FXCollections.observableArrayList(courses[1]));
                if(newValue.equals("Faculty of Computing")) courseCombo.setItems(FXCollections.observableArrayList(courses[2]));
            }
        });

        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                student.enroll(courseCombo.getValue());
                changeView();
                if(courseCombo.getValue().getType().equals("bsc")){
                    UGDetailsView ugDetailsView = new UGDetailsView(parent, new UGStudentModel(student));
                };
                if(courseCombo.getValue().getType().equals("msc")){
                    PGDetailsView pgDetailsView = new PGDetailsView(parent, new PGStudentModel(student));
                };
            }
        });
    }

    public void getCourses(){
        ArrayList<CourseModel> businessCourses = new ArrayList<>();
        ArrayList<CourseModel> engineeringCourses = new ArrayList<>();
        ArrayList<CourseModel> computingCourses = new ArrayList<>();

        for(CourseModel course: CourseModel.getAllCourses()){
            if(course.getFaculty_id() == 1) businessCourses.add(course);
            if(course.getFaculty_id() == 2) engineeringCourses.add(course);
            if(course.getFaculty_id() == 3) computingCourses.add(course);
        }

        courses[0] = businessCourses.stream().toArray(CourseModel[]::new);
        courses[1] = engineeringCourses.stream().toArray(CourseModel[]::new);
        courses[2] = computingCourses.stream().toArray(CourseModel[]::new);
    };

}
