package sample.views;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sample.models.PGStudentModel;
import sample.models.UGStudentModel;

public class DashboardView extends View{


    private VBox userManagementBox;
    private VBox courseManagementBox;


    public DashboardView(Pane parent){
        super(parent);
    }

    protected void generateView(){
        HBox dashboardBox = new HBox(20);
        dashboardBox.setPadding(new Insets(10));
        dashboardBox.setId("dashboardBox");
        parent.getChildren().add(dashboardBox);

        userManagementBox = new VBox(20);
        userManagementBox.setPadding(new Insets(20));
        userManagementBox.getStyleClass().add("dashBox");
        Label userManagementLabel = new Label("User Management");
        userManagementLabel.getStyleClass().add("dashText");
        userManagementBox.getChildren().add(userManagementLabel);
        dashboardBox.getChildren().add(userManagementBox);

        courseManagementBox = new VBox(20);
        courseManagementBox.setPadding(new Insets(20));
        courseManagementBox.getStyleClass().add("dashBox");
        Label courseManagementLabel = new Label("Courses & Subjects");
        courseManagementLabel.getStyleClass().add("dashText");
        courseManagementBox.getChildren().add(courseManagementLabel);
        dashboardBox.getChildren().add(courseManagementBox);


    }

    protected void registerActions(){

        userManagementBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeView();
                UserManagementView userManagementView = new UserManagementView(parent);
            }
        });

        courseManagementBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeView();
                CourseManagementView courseManagementView = new CourseManagementView(parent);
            }
        });
    }

}
