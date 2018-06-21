package sample.views;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sample.models.StudentModel;
import sample.models.SubjectModel;
import sample.models.UserModel;

import java.sql.SQLException;

public class CreateSubjectView extends View{

    private TextField subjectCodeText;
    private TextField subjectTitleText;
    private Button createSubjectButton;


    public CreateSubjectView(Pane parent){
        super(parent);
    }

    protected void generateView(){
        VBox formBox = new VBox(20);
        formBox.setPadding(new Insets(10));
        formBox.setMaxWidth(300);
        formBox.getStyleClass().add("formBox");
        parent.getChildren().add(formBox);

        Label formTitleLabel = new Label("Create Subject");
        formTitleLabel.getStyleClass().add("formTitleLabel");
        formBox.getChildren().add(formTitleLabel);

        Label subjectCodeLabel = new Label("Subject Code");
        subjectCodeLabel.getStyleClass().add("formLabel");
        subjectCodeText = new TextField();
        VBox subjectCodeBox = new VBox(5, subjectCodeLabel, subjectCodeText);
        formBox.getChildren().add(subjectCodeBox);

        Label subjectTitleLabel = new Label("Subject Title");
        subjectTitleLabel.getStyleClass().add("formLabel");
        subjectTitleText = new TextField();
        VBox subjectTitleBox = new VBox(5, subjectTitleLabel, subjectTitleText);
        formBox.getChildren().add(subjectTitleBox);

        createSubjectButton = new Button("Create");
        formBox.getChildren().add(createSubjectButton);
    }

    protected void registerActions(){
        CreateSubjectView controller = this;
        createSubjectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
               SubjectModel subject = new SubjectModel();
                subject.setCode(subjectCodeText.getText());
                subject.setTitle(subjectTitleText.getText());
                try{
                    subject.saveToDB();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Subject has been created.");
                    alert.showAndWait();
                    changeView();
                    DashboardView dashboardView = new DashboardView(parent);
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("A database error occurred.");
                    alert.showAndWait();
                }
            }
        });
    }

}
