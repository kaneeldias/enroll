package sample.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.common.Result;
import sample.models.CourseModel;
import sample.models.StudentModel;
import sample.models.UGStudentModel;

import java.util.ArrayList;

public class UGDetailsView extends View{

    private UGStudentModel undergraduate;

    private TextField yearText;
    private TextField subject1TitleText;
    private TextField subject2TitleText;
    private TextField subject3TitleText;
    private TextField islandRankText;
    private TextField zScoreText;
    private ComboBox<String> subject1ResultCombo;
    private ComboBox<String> subject2ResultCombo;
    private ComboBox<String> subject3ResultCombo;
    private Button enrollButton;

    public UGDetailsView(Pane parent, UGStudentModel undergraduate){

        super(parent);

        this.undergraduate = undergraduate;
    }

    protected void generateView(){
        VBox formBox = new VBox(20);
        formBox.setPadding(new Insets(10));
        formBox.setMaxWidth(350);
        formBox.getStyleClass().add("formBox");
        parent.getChildren().add(formBox);

        Label formTitleLabel = new Label("Enroll in Course > A/L Results");
        formTitleLabel.getStyleClass().add("formTitleLabel");
        formBox.getChildren().add(formTitleLabel);

        Label yearLabel = new Label("Year of Examination");
        yearLabel.getStyleClass().add("formLabel");
        yearText = new TextField();
        VBox yearBox = new VBox(5, yearLabel, yearText);
        formBox.getChildren().add(yearBox);
        
        Label subject1Label = new Label("Subject 1");
        subject1Label.getStyleClass().add("formLabel");
        subject1TitleText = new TextField();
        subject1ResultCombo = new ComboBox<String>();
        subject1ResultCombo.setPromptText("Select");
        subject1ResultCombo.setItems(FXCollections.observableArrayList(
                "A",
                "B",
                "C",
                "D",
                "E"
        ));
        HBox subject1SubBox = new HBox(5, subject1TitleText, subject1ResultCombo);
        VBox subject1Box = new VBox(5, subject1Label, subject1SubBox);
        formBox.getChildren().add(subject1Box);

        Label subject2Label = new Label("Subject 2");
        subject2Label.getStyleClass().add("formLabel");
        subject2TitleText = new TextField();
        subject2ResultCombo = new ComboBox<String>();
        subject2ResultCombo.setPromptText("Select");
        subject2ResultCombo.setItems(FXCollections.observableArrayList(
                "A",
                "B",
                "C",
                "D",
                "E"
        ));
        HBox subject2SubBox = new HBox(5, subject2TitleText, subject2ResultCombo);
        VBox subject2Box = new VBox(5, subject2Label, subject2SubBox);
        formBox.getChildren().add(subject2Box);

        Label subject3Label = new Label("Subject 3");
        subject3Label.getStyleClass().add("formLabel");
        subject3TitleText = new TextField();
        subject3ResultCombo = new ComboBox<String>();
        subject3ResultCombo.setPromptText("Select");
        subject3ResultCombo.setItems(FXCollections.observableArrayList(
                "A",
                "B",
                "C",
                "D",
                "E"
        ));
        HBox subject3SubBox = new HBox(5, subject3TitleText, subject3ResultCombo);
        VBox subject3Box = new VBox(5, subject3Label, subject3SubBox);
        formBox.getChildren().add(subject3Box);

        Label islandRankLabel = new Label("Island Rank");
        islandRankLabel.getStyleClass().add("formLabel");
        islandRankText = new TextField();
        VBox islandRankBox = new VBox(5, islandRankLabel, islandRankText);
        formBox.getChildren().add(islandRankBox);

        Label zScoreLabel = new Label("Z-Score");
        zScoreLabel.getStyleClass().add("formLabel");
        zScoreText = new TextField();
        VBox zScoreBox = new VBox(5, zScoreLabel, zScoreText);
        formBox.getChildren().add(zScoreBox);

        enrollButton = new Button("Enroll");
        formBox.getChildren().add(enrollButton);
    }

    protected void registerActions(){

        enrollButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Result[] results = {
                    new Result(subject1TitleText.getText(), subject1ResultCombo.getValue()),
                    new Result(subject2TitleText.getText(), subject2ResultCombo.getValue()),
                    new Result(subject3TitleText.getText(), subject3ResultCombo.getValue())
                };
                //UGStudentModel undergraduate = new UGStudentModel(student, Integer.parseInt(yearText.getText()), Integer.parseInt(islandRankText.getText()), Float.parseFloat(zScoreText.getText()), results);
                undergraduate.setResults(results);
                undergraduate.setYear(Integer.parseInt(yearText.getText()));
                undergraduate.setRank(Integer.parseInt(islandRankText.getText()));
                undergraduate.setZ_score(Float.parseFloat(zScoreText.getText()));
                undergraduate.saveDetails();
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
