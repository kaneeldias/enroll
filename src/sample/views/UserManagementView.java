package sample.views;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class UserManagementView extends View{

    private VBox registerUserBox;
    private VBox viewProfileBox;

    public UserManagementView(Pane parent){
        super(parent);
    }

    protected void generateView(){
        HBox dashboardBox = new HBox(20);
        dashboardBox.setPadding(new Insets(10));
        dashboardBox.setId("dashboardBox");
        parent.getChildren().add(dashboardBox);

        registerUserBox = new VBox(20);
        registerUserBox.setPadding(new Insets(20));
        registerUserBox.getStyleClass().add("dashBox");
        Label registerUserLabel = new Label("Register User");
        registerUserLabel.getStyleClass().add("dashText");
        registerUserBox.getChildren().add(registerUserLabel);
        dashboardBox.getChildren().add(registerUserBox);

        viewProfileBox = new VBox(20);
        viewProfileBox.setPadding(new Insets(20));
        viewProfileBox.getStyleClass().add("dashBox");
        Label viewProfileLabel = new Label("View Profile");
        viewProfileLabel.getStyleClass().add("dashText");
        viewProfileBox.getChildren().add(viewProfileLabel);
        dashboardBox.getChildren().add(viewProfileBox);
        
    }

    protected void registerActions(){
        registerUserBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeView();
                RegisterView registerView = new RegisterView(parent);
            }
        });

        viewProfileBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeView();
                ProfileView profileView = new ProfileView(parent);
            }
        });

    }

}
