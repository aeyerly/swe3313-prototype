package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Controller {


    @FXML
    private Pane rootPane;

    @FXML
    private TextField usernameEntry;

    @FXML
    private TextField passwordEntry;

    @FXML
    private Label userLabel;

    @FXML
    private Label passLabel;

    @FXML
    private Button signInButton;

    @FXML
    private Label errorLabel;

    @FXML
    void signIn(ActionEvent event) {
        String username = usernameEntry.getText();
        String password = passwordEntry.getText();

        try {
            File employeeInfo = new File("C:\\Users\\Alex\\Desktop\\swePrototype\\src\\sample\\loginInfo.txt");
            Scanner scan = new Scanner(employeeInfo);
            boolean validLogin = false;
            while (scan.hasNextLine()) {
                String data = scan.nextLine();
                String[] login = data.split(",");
                if (username.equals(login[0])) {
                    if (password.equals(login[1])) {
                        validLogin = true;
                        break;
                    }
                }
            }
            if (validLogin) {
                System.out.println("Login Successful.");
            }

            else {
                errorLabel.setText("Username or password incorrect");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
            e.printStackTrace();
        }


    }

}
