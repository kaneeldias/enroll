package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.models.StudentModel;
import sample.views.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        VBox frame = new VBox(20);
        frame.setBackground(new Background(new BackgroundFill(Color.web("#EEEEEE"), CornerRadii.EMPTY, Insets.EMPTY )));

        HBox headerBox = new HBox(5);
        headerBox.setId("headerBox");
        headerBox.setPadding(new Insets(10));
        ImageView logoImageView = new ImageView();
        logoImageView.setFitHeight(50);
        logoImageView.setPreserveRatio(true);
        logoImageView.setId("headerLogo");
        Image logoImage = new Image(Main.class.getResourceAsStream("/assets/logo.png"));
        logoImageView.setImage(logoImage);
        headerBox.getChildren().add(logoImageView);
        //Label headerLabel = new Label("NSBM Enrollment System");
        //headerLabel.setId("headerLabel");
        //headerBox.getChildren().add(headerLabel);
        frame.getChildren().add(headerBox);

        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        frame.getChildren().add(content);
        frame.setId("app");

        //RegisterView registerView = new RegisterView(content);
        headerBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("ddd");
                content.getChildren().clear();
                DashboardView dashboardView = new DashboardView(content);
            }
        });
        DashboardView dashboardView = new DashboardView(content);

        //EnrollView enrollView = new EnrollView(content, new StudentModel(1));

        primaryStage.setTitle("Register User");
        Scene scene = new Scene(frame, 800, 600);
        scene.getStylesheets().add("styles/headerStyles.css");
        scene.getStylesheets().add("styles/dashboardStyles.css");
        scene.getStylesheets().add("styles/formStyles.css");
        scene.getStylesheets().add("styles/detailStyles.css");
        //scene.setFill(Color.TRANSPARENT);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
