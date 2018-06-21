package sample.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.models.CourseModel;
import sample.models.SubjectModel;

import java.sql.SQLException;

/**
 * Created by test12345 on 2018-06-18.
 */
public class AddSubjectView extends View {

    private static int width = 400;
    private static int height = 200;

    private CourseModel course;
    private int semester;

    private ComboBox<SubjectModel> subjectCombo;
    private Button addButton;
    private Label courseTitleLabel;
    private Label semesterTitleLabel;
    private CheckBox compulsoryCheckBox;

    public AddSubjectView(Pane parent, CourseModel course, int semester){
        super(parent);
        this.course = course;
        this.semester = semester;
        courseTitleLabel.setText(course.getTitle());
        semesterTitleLabel.setText("Semester " + String.valueOf(semester));
    }

    public void generateView(){
        try{
            ObservableList<SubjectModel> subjects = SubjectModel.getAllSubjects();
            VBox formBox = new VBox(20);
            formBox.setPadding(new Insets(10));
            formBox.setMaxWidth(300);
            formBox.getStyleClass().add("formBox");
            parent.getChildren().add(formBox);

            Label formTitleLabel = new Label("Add Subject");
            formTitleLabel.getStyleClass().add("formTitleLabel");
            formBox.getChildren().add(formTitleLabel);

            courseTitleLabel  = new Label();
            courseTitleLabel.getStyleClass().add("formSubtitleLabel");
            semesterTitleLabel = new Label();
            semesterTitleLabel.getStyleClass().add("formSubtitleLabel");
            VBox subTitleBox = new VBox(5, courseTitleLabel,semesterTitleLabel);

            formBox.getChildren().add(subTitleBox);


            Label subjectLabel = new Label("Subject");
            subjectLabel.getStyleClass().add("formLabel");
            subjectCombo = new ComboBox<>();
            subjectCombo.setPromptText("Select");
            subjectCombo.setItems(subjects);
            VBox subjectBox = new VBox(5, subjectLabel, subjectCombo);
            formBox.getChildren().add(subjectBox);

            compulsoryCheckBox = new CheckBox("Compulsory");

            VBox compulsoryBox = new VBox(5, compulsoryCheckBox);
            formBox.getChildren().add(compulsoryBox);

            addButton = new Button("Add Subject");
            VBox buttonBox = new VBox(5, addButton);
            formBox.getChildren().add(buttonBox);
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

    public void registerActions(){
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try{
                    course.addSubject(subjectCombo.getSelectionModel().getSelectedItem(), semester, compulsoryCheckBox.isSelected());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Subject has been successfully added.");
                    alert.showAndWait();
                }
                catch (SQLException ex){
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

}
