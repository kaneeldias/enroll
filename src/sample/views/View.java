package sample.views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Created by test12345 on 2018-05-29.
 */
public abstract class View {

    private View previous;

    protected Pane parent;

    public View(Pane parent){
        this.parent = parent;
        generateView();
        registerActions();
    }

    abstract protected void generateView();
    abstract protected void registerActions();

    protected void changeView(){
        parent.getChildren().clear();
    }

    public static Stage getWindow(String title, int width, int height){
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        Stage stage = new Stage();
        stage.setTitle(title);
        Scene scene = new Scene(content, width, height);
        scene.getStylesheets().add("styles/headerStyles.css");
        scene.getStylesheets().add("styles/dashboardStyles.css");
        scene.getStylesheets().add("styles/formStyles.css");
        stage.setScene(scene);
        //scene.setFill(Color.TRANSPARENT);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        return stage;
        //return content;
    }

}
