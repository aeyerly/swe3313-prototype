package prototype;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("J's Corner Restaurant Prototype");

        //Login screen root
        GridPane root = new GridPane();

        //Main application root
        GridPane root2 = new GridPane();

        //Username label
        Label userLabel = new Label();
        userLabel.setText("Username");
        root.add(userLabel,0, 0, 1, 1);

        //Password label
        Label passLabel = new Label();
        passLabel.setText("Password");
        root.add(passLabel,1, 0, 1, 1);

        //Label for incorrect password
        Label errorLabel = new Label();
        errorLabel.setText("");
        root.add(errorLabel, 1, 2, 1, 1);

        //Username entry field
        TextField userEntry = new TextField();
        root.add(userEntry, 0, 1, 1, 1);

        //Password entry field
        PasswordField passEntry = new PasswordField();
        root.add(passEntry, 1, 1, 1, 1);

        //Sign in button
        Button signIn = new Button();
        signIn.setText("Sign In");
        signIn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Test Complete");
                boolean validAttempt = signIn(userEntry.getText(), passEntry.getText());

                if(validAttempt) {
                    System.out.println("Success");
                    primaryStage.setScene(new Scene(root2, 1280, 960));
                    primaryStage.show();
                }

                else {
                    errorLabel.setText("The username or password is incorrect");
                }
            }
        });
        root.add(signIn, 0, 3, 1, 1);

        root2.setHgap(10);
        root2.setVgap(10);

        /*Rectangle rect1 = new Rectangle(0, 0, 80,80);
        rect1.setFill(Color.WHITE);
        rect1.setStroke(Color.BLACK);
        root2.add(rect1, 0, 0, 1, 1);*/

        Line divider = new Line(0, 0, 0, 960);
        root2.add(divider, 6, 0, 1, 6);

        Rectangle rectArray[][] = new Rectangle[5][6];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                rectArray[i][j] = new Rectangle(0, 0, 150, 150);
                rectArray[i][j].setFill(Color.WHITE);
                rectArray[i][j].setStroke(Color.BLACK);
                root2.add(rectArray[i][j], i, j, 1, 1);
            }
        }

       /* Rectangle rect2 = new Rectangle(0, 0, 80,80);
        rect2.setFill(Color.WHITE);
        rect2.setStroke(Color.BLACK);
        root2.add(rect2, 1, 0, 1, 1);*/

        primaryStage.setScene(new Scene(root, 320, 240));
        primaryStage.show();

    }

    //Method to authenticate username and password
    public boolean signIn(String username, String password) {
        try {
            //replace this string with filepath on your computer
            String filepath = "../../../src/sample/loginInfo.txt";
            File employeeInfo = new File(filepath);
            Scanner scan = new Scanner(employeeInfo);

            //Keeps track of if a login matches a stored value
            boolean validLogin = false;
            while (scan.hasNextLine()) {

                //Splits each line into username and password to compare with user input
                String data = scan.nextLine();
                String[] login = data.split(",");

                //If username and password are the same, login is set to valid
                if (username.equals(login[0])) {
                    if (password.equals(login[1])) {
                        validLogin = true;
                        break;
                    }
                }
            }
            if (validLogin) {
                return true;
            }
            //Lets user know when the user or password is incorrect
            else {
                return false;
            }

        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
            e.printStackTrace();
        }

        return false;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
