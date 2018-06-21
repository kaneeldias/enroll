package sample.views;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sample.models.StudentModel;
import sample.models.UserModel;

public class RegisterView extends View{

    private ComboBox<String> userCombo;
    private TextField nameText;
    private ComboBox<String> genderCombo;
    private TextField nicText;
    private TextField emailText;
    private TextField contactText;
    private Button registerButton;


    public RegisterView(Pane parent){
        super(parent);
    }

    protected void generateView(){
        VBox formBox = new VBox(20);
        formBox.setPadding(new Insets(10));
        formBox.setMaxWidth(300);
        formBox.getStyleClass().add("formBox");
        parent.getChildren().add(formBox);

        Label formTitleLabel = new Label("Register User");
        formTitleLabel.getStyleClass().add("formTitleLabel");
        formBox.getChildren().add(formTitleLabel);


        Label userLabel = new Label("User Type");
        userLabel.getStyleClass().add("formLabel");
        userCombo = new ComboBox<>();
        userCombo.setPromptText("Select");
        userCombo.setItems(FXCollections.observableArrayList(
                "Student",
                "Lecturer",
                "Instructor"
        ));
        VBox userBox = new VBox(5, userLabel, userCombo);
        formBox.getChildren().add(userBox);

        Label nameLabel = new Label("Name");
        nameLabel.getStyleClass().add("formLabel");
        nameText = new TextField();
        VBox nameBox = new VBox(5, nameLabel, nameText);
        formBox.getChildren().add(nameBox);
        
        Label genderLabel = new Label("Gender");
        genderLabel.getStyleClass().add("formLabel");
        genderCombo = new ComboBox<>();
        genderCombo.setPromptText("Select");
        genderCombo.setItems(FXCollections.observableArrayList(
                "Male",
                "Female"
        ));
        VBox genderBox = new VBox(5, genderLabel, genderCombo);
        formBox.getChildren().add(genderBox);

        Label nicLabel = new Label("NIC No.");
        nicLabel.getStyleClass().add("formLabel");
        nicText = new TextField();
        VBox nicBox = new VBox(5, nicLabel, nicText);
        formBox.getChildren().add(nicBox);

        Label emailLabel = new Label("Email Address");
        emailLabel.getStyleClass().add("formLabel");
        emailText = new TextField();
        VBox emailBox = new VBox(5, emailLabel, emailText);
        formBox.getChildren().add(emailBox);

        Label contactLabel = new Label("Contact No.");
        contactLabel.getStyleClass().add("formLabel");
        contactText = new TextField();
        VBox contactBox = new VBox(5, contactLabel, contactText);
        formBox.getChildren().add(contactBox);

        registerButton = new Button("Register");
        formBox.getChildren().add(registerButton);
    }

    protected void registerActions(){
        RegisterView controller = this;
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                UserModel user = controller.register(userCombo.getValue(), nameText.getText(), nicText.getText(), emailText.getText(), contactText.getText());
                if(user != null){
                    if(userCombo.getValue().equals("Student")){
                        StudentModel student = new StudentModel(user);
                        changeView();
                        EnrollView enrollView = new EnrollView(parent, student);
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("User has been successfully registered.");
                        alert.show();
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("An error occurred.");
                    alert.show();
                }
            }
        });
    }

    private UserModel register(String type, String name, String nic, String email, String contact){
        UserModel user = new UserModel(type, name, nic, email, contact);
        return(user.saveToDB());
    }

}
