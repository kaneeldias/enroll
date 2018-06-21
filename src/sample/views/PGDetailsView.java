package sample.views;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sample.common.Result;
import sample.models.PGStudentModel;
import sample.models.UGStudentModel;

public class PGDetailsView extends View{

    private PGStudentModel postgraduate;

    private TextField degreeText;
    private TextField instituteText;
    private TextField yearText;
    private TextField gpaText;
    private TextField classText;
    private Button enrollButton;

    public PGDetailsView(Pane parent, PGStudentModel postgraduate){

        super(parent);

        this.postgraduate = postgraduate;
    }

    protected void generateView(){
        VBox formBox = new VBox(20);
        formBox.setPadding(new Insets(10));
        formBox.setMaxWidth(350);
        formBox.getStyleClass().add("formBox");
        parent.getChildren().add(formBox);

        Label formTitleLabel = new Label("Enroll in Course > Degree Info");
        formTitleLabel.getStyleClass().add("formTitleLabel");
        formBox.getChildren().add(formTitleLabel);

        Label degreeLabel = new Label("Degree Title");
        degreeLabel.getStyleClass().add("formLabel");
        degreeText = new TextField();
        VBox degreeBox = new VBox(5, degreeLabel, degreeText);
        formBox.getChildren().add(degreeBox);

        Label instituteLabel = new Label("Institute");
        instituteLabel.getStyleClass().add("formLabel");
        instituteText = new TextField();
        VBox instituteBox = new VBox(5, instituteLabel, instituteText);
        formBox.getChildren().add(instituteBox);

        Label yearLabel = new Label("Year of Completion");
        yearLabel.getStyleClass().add("formLabel");
        yearText = new TextField();
        VBox yearBox = new VBox(5, yearLabel, yearText);
        formBox.getChildren().add(yearBox);

        Label gpaLabel = new Label("GPA");
        gpaLabel.getStyleClass().add("formLabel");
        gpaText = new TextField();
        VBox gpaBox = new VBox(5, gpaLabel, gpaText);
        formBox.getChildren().add(gpaBox);

        Label classLabel = new Label("Class");
        classLabel.getStyleClass().add("formLabel");
        classText = new TextField();
        VBox classBox = new VBox(5, classLabel, classText);
        formBox.getChildren().add(classBox);
        
        enrollButton = new Button("Enroll");
        formBox.getChildren().add(enrollButton);
    }

    protected void registerActions(){

        enrollButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                postgraduate.setDegree(degreeText.getText());
                postgraduate.setInstitute(instituteText.getText());
                postgraduate.setYear(Integer.parseInt(yearText.getText()));
                postgraduate.setGpa(Float.parseFloat(gpaText.getText()));
                postgraduate.setD_class(classText.getText());
                postgraduate.saveDetails();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Student has been enrolled successfully!");
                alert.showAndWait();
                changeView();
                DashboardView dashView = new DashboardView(parent);
            }
        });

    }
}
