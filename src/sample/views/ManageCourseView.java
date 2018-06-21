package sample.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.models.CourseModel;
import sample.models.StudentModel;
import sample.models.SubjectModel;
import sample.models.UserModel;

import java.sql.SQLException;
import java.util.Optional;

public class ManageCourseView extends View{

    private ObservableList<CourseModel> courses;

    private ListView<CourseModel> courseListView;
    private ListView<Integer> semesterListView;
    private ListView<SubjectModel> subjectListView;
    private Button addSubjectButton;
    private Button removeSubjectButton;

    public ManageCourseView(Pane parent){
        super(parent);
    }

    protected void generateView(){
        VBox formBox = new VBox(20);
        formBox.setPadding(new Insets(10));
        formBox.setMaxWidth(500);
        formBox.getStyleClass().add("formBox");
        parent.getChildren().add(formBox);

        Label formTitleLabel = new Label("Manage Courses");
        formTitleLabel.getStyleClass().add("formTitleLabel");
        formBox.getChildren().add(formTitleLabel);

        HBox selectorBox = new HBox(5);
        selectorBox.setPadding(new Insets(10));
        formBox.getChildren().add(selectorBox);

        Label courseListLabel = new Label("Courses");
        courseListLabel.getStyleClass().add("formLabel");

        courseListView = new ListView<>();
        courseListView.setCellFactory(new Callback<ListView<CourseModel>,ListCell<CourseModel>>(){

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
        courses = FXCollections.observableArrayList();
        courses.addAll(CourseModel.getAllCourses());
        courseListView.setItems(courses);

        VBox courseListBox = new VBox(5, courseListLabel, courseListView);
        selectorBox.getChildren().add(courseListBox);

        Label semesterListLabel = new Label("Semesters");
        semesterListLabel.getStyleClass().add("formLabel");

        semesterListView = new ListView<>();
        semesterListView.setCellFactory(new Callback<ListView<Integer>,ListCell<Integer>>(){

            @Override
            public ListCell<Integer> call(ListView<Integer> p) {

                final ListCell<Integer> cell = new ListCell<Integer>(){

                    @Override
                    protected void updateItem(Integer t, boolean bln) {
                        super.updateItem(t, bln);

                        if(t != null){
                            setText("Semester " + t.toString());
                        }else{
                            setText(null);
                        }
                    }

                };

                return cell;
            }
        });

        VBox semesterListBox = new VBox(5, semesterListLabel, semesterListView);
        selectorBox.getChildren().add(semesterListBox);


        Label subjectListLabel = new Label("Subjects");
        subjectListLabel.getStyleClass().add("formLabel");

        subjectListView = new ListView<>();

        subjectListView.setCellFactory(new Callback<ListView<SubjectModel>,ListCell<SubjectModel>>(){

            @Override
            public ListCell<SubjectModel> call(ListView<SubjectModel> p) {

                final ListCell<SubjectModel> cell = new ListCell<SubjectModel>(){

                    @Override
                    protected void updateItem(SubjectModel t, boolean bln) {
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

        addSubjectButton = new Button("Add");
        addSubjectButton.setDisable(true);
        addSubjectButton.getStyleClass().add("formButton");

        removeSubjectButton = new Button("Remove");
        removeSubjectButton.setDisable(true);
        removeSubjectButton.getStyleClass().remove("formButton");

        HBox subjectButtonBox = new HBox(5, addSubjectButton, removeSubjectButton);

        VBox subjectListBox = new VBox(5, subjectListLabel, subjectListView, subjectButtonBox);

        selectorBox.getChildren().add(subjectListBox);
    }

    protected void registerActions(){

        courseListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CourseModel>() {

            @Override
            public void changed(ObservableValue<? extends CourseModel> observable, CourseModel oldValue, CourseModel newValue) {
                if(newValue != null){
                    //getSubjects();
                    getSemesters();
                    //addSubjectButton.setDisable(false);
                }
                else{
                    //addSubjectButton.setDisable(true);
                }
            }
        });

        semesterListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {

            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if(newValue != null){
                    getSubjects();
                    addSubjectButton.setDisable(false);
                }
                else{
                    addSubjectButton.setDisable(true);
                }
            }
        });

        subjectListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SubjectModel>() {

            @Override
            public void changed(ObservableValue<? extends SubjectModel> observable, SubjectModel oldValue, SubjectModel newValue) {
                if(newValue != null){
                    removeSubjectButton.setDisable(false);
                }
                else{
                    removeSubjectButton.setDisable(true);
                }
            }
        });

        addSubjectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                CourseModel course = courseListView.getSelectionModel().getSelectedItem();
                int semester = semesterListView.getSelectionModel().getSelectedItem();
                Stage window =  View.getWindow("Add Subject", 400, 300);
                VBox content = (VBox) window.getScene().getRoot();
                AddSubjectView addSubjectView = new AddSubjectView(content, course, semester);
                window.showAndWait();
                getSubjects();
                /*try{
                    ObservableList<SubjectModel> subjectList = SubjectModel.getAllSubjects();
                    ChoiceDialog<SubjectModel> dialog = new ChoiceDialog<>(subjectList.get(0), subjectList);
                    dialog.setTitle("Select subject");
                    dialog.setHeaderText("Select the subject you want to add from the dropdown.");
                    dialog.setContentText(null);

                    Optional<SubjectModel> subject = dialog.showAndWait();
                    if (subject.isPresent()){
                        CourseModel course = courseListView.getSelectionModel().getSelectedItem();
                        int semester = semesterListView.getSelectionModel().getSelectedItem();
                        course.addSubject(subject.get(), semester);
                        getSubjects();
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("A database error occurred");
                    alert.showAndWait();
                }*/

            }
        });

        removeSubjectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try{
                    CourseModel course = courseListView.getSelectionModel().getSelectedItem();
                    SubjectModel subject = subjectListView.getSelectionModel().getSelectedItem();
                    int semester = semesterListView.getSelectionModel().getSelectedItem();
                    course.removeSubject(subject, semester);
                    getSubjects();
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("A database error occurred");
                    alert.showAndWait();
                }

            }
        });
    }

    private void getSemesters(){
        CourseModel course = courseListView.getSelectionModel().getSelectedItem();
        int semesters = course.getSemesters();
        ObservableList<Integer> semesterList = FXCollections.observableArrayList();
        for(int i = 1; i <= semesters; i++){
            semesterList.add(i);
        }
        semesterListView.setItems(semesterList);
    }

    private void getSubjects(){
        try{
            CourseModel course = courseListView.getSelectionModel().getSelectedItem();
            int semester = semesterListView.getSelectionModel().getSelectedItem();
            subjectListView.setItems(course.getSubjectsForSemester(semester));
        }
        catch(SQLException ex){
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("A database error occurred");
            alert.showAndWait();
        }
    }

}
