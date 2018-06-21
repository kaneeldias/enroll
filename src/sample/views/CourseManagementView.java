package sample.views;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sample.models.StudentModel;
import sample.models.UserModel;

public class CourseManagementView extends View{

    private VBox createSubjectBox;
    private VBox manageCourseBox;

    public CourseManagementView(Pane parent){
        super(parent);
    }

    protected void generateView(){
        HBox dashboardBox = new HBox(20);
        dashboardBox.setPadding(new Insets(10));
        dashboardBox.setId("dashboardBox");
        parent.getChildren().add(dashboardBox);

        manageCourseBox = new VBox(20);
        manageCourseBox.setPadding(new Insets(20));
        manageCourseBox.getStyleClass().add("dashBox");
        Label manageCourseLabel = new Label("Manage Course");
        manageCourseLabel.getStyleClass().add("dashText");
        manageCourseBox.getChildren().add(manageCourseLabel);
        dashboardBox.getChildren().add(manageCourseBox);

        createSubjectBox = new VBox(20);
        createSubjectBox.setPadding(new Insets(20));
        createSubjectBox.getStyleClass().add("dashBox");
        Label createSubjectLabel = new Label("Create Subject");
        createSubjectLabel.getStyleClass().add("dashText");
        createSubjectBox.getChildren().add(createSubjectLabel);
        dashboardBox.getChildren().add(createSubjectBox);
    }

    protected void registerActions(){
        createSubjectBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeView();
                CreateSubjectView createSubjectView = new CreateSubjectView(parent);
            }
        });

        manageCourseBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeView();
                ManageCourseView manageCourseView = new ManageCourseView(parent);
            }
        });
    }

}
