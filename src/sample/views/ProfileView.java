package sample.views;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sample.excpetions.CourseNotFoundException;
import sample.excpetions.UserNotFoundException;
import sample.models.PGStudentModel;
import sample.models.StudentModel;
import sample.models.UGStudentModel;
import sample.models.UserModel;

import java.awt.*;
import java.sql.SQLException;

public class ProfileView extends View{

    private Button searchUserButton;
    private TextField searchUserText;

    private Label nameLabel;
    private Label typeLabel;
    private Label nicLabel;
    private Label emailLabel;
    private Label contactLabel;
    private VBox userInfoBox;

    private Label courseLabel;
    private Label semesterLabel;

    public ProfileView(Pane parent){
        super(parent);
    }

    protected void generateView(){
        VBox formBox = new VBox(20);
        formBox.setPadding(new Insets(10));
        formBox.setMaxWidth(250);
        formBox.getStyleClass().add("formBox");
        parent.getChildren().add(formBox);

        Label formTitleLabel = new Label("Search User");
        formTitleLabel.getStyleClass().add("formTitleLabel");
        formBox.getChildren().add(formTitleLabel);

        Label formSubtitleLabel = new Label("Enter NIC No.");
        formSubtitleLabel.getStyleClass().add("formSubtitleLabel");
        formBox.getChildren().add(formSubtitleLabel);

        searchUserText = new TextField();
        searchUserButton = new Button("Search");
        HBox searchUserBox = new HBox(5, searchUserText, searchUserButton);
        formBox.getChildren().add(searchUserBox);

        nameLabel = new Label();
        nameLabel.getStyleClass().add("nameLabel");

        typeLabel = new Label();
        typeLabel.getStyleClass().add("subtitleLabel");

        nicLabel = new Label();
        emailLabel = new Label();
        contactLabel = new Label();

        VBox detailsBox = new VBox(0, nameLabel, typeLabel, nicLabel, emailLabel, contactLabel);
        detailsBox.getStyleClass().add("infoBoxHeader");

        courseLabel = new Label();
        courseLabel.getStyleClass().add("subtitleLabel");
        semesterLabel = new Label();
        VBox studentInfoBox = new VBox(0, courseLabel, semesterLabel);

        VBox moreInfoBox = new VBox(0, studentInfoBox);
        moreInfoBox.getStyleClass().add("infoBoxContent");


        userInfoBox = new VBox(0, detailsBox, moreInfoBox);
        userInfoBox.getStyleClass().add("infoBox");
        userInfoBox.setVisible(false);

        parent.getChildren().add(userInfoBox);
    }

    protected void registerActions(){
        searchUserButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try{
                    String nic = searchUserText.getText();
                    UserModel user = UserModel.search(nic);
                    nameLabel.setText(user.getName());
                    typeLabel.setText(user.getType());
                    nicLabel.setText(user.getNic());
                    emailLabel.setText(user.getEmail());
                    contactLabel.setText(user.getContact());
                    if(user.getType().equals("Student")){
                        StudentModel student = new StudentModel(user.getUser_id());
                        courseLabel.setText(student.getCourse().getTitle());
                        semesterLabel.setText("Semester " + String.valueOf(student.getSemester()));
                    }
                    userInfoBox.setVisible(true);
                }
                catch(SQLException | CourseNotFoundException ex){
                    ex.printStackTrace();
                    userInfoBox.setVisible(false);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("A database error occurred");
                    alert.showAndWait();

                }
                catch(UserNotFoundException ex){
                    ex.printStackTrace();
                    userInfoBox.setVisible(false);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("User not found");
                    alert.showAndWait();
                }
            }
        });
    }

}
