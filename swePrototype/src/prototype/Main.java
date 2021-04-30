package prototype;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    Label tableLabel = new Label();
    Label seat1Label = new Label();
    Label seat2Label = new Label();
    Label seat3Label = new Label();
    Label seat4Label = new Label();
    Button seat1Button = new Button();
    Button seat2Button = new Button();
    Button seat3Button = new Button();
    Button seat4Button = new Button();

    GridPane loginPane = new GridPane();

    GridPane mainPane = new GridPane();

    VBox queuePane = new VBox();


    Button tableButtons[][] = new Button[5][6];

    Table[] tables = new Table[30];
    private String username;

    @Override
    public void start(Stage primaryStage) throws Exception {

        for (int i = 0; i < 30; i++)
            tables[i] = new Table(i, 0);

        primaryStage.setTitle("J's Corner Restaurant Prototype");

        loginPane.setPadding(new Insets(10, 10, 10, 10));

        //Username label
        Label userLabel = new Label();
        userLabel.setText("Username");
        loginPane.add(userLabel,0, 0, 1, 1);

        //Password label
        Label passLabel = new Label();
        passLabel.setText("Password");
        loginPane.add(passLabel,1, 0, 1, 1);

        //Label for incorrect password
        Label errorLabel = new Label();
        errorLabel.setText("");
        loginPane.add(errorLabel, 1, 3, 1, 1);

        //Username entry field
        TextField userEntry = new TextField();
        loginPane.add(userEntry, 0, 1, 1, 1);

        //Password entry field
        PasswordField passEntry = new PasswordField();
        loginPane.add(passEntry, 1, 1, 1, 1);

        //Sign in button
        Button signIn = new Button();
        signIn.setText("Sign In");
        signIn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                username = userEntry.getText();

                boolean validAttempt = signIn(username, passEntry.getText());

                if(validAttempt) {
                    assignTables();
                    primaryStage.setScene(new Scene(mainPane, 1280, 980));
                    primaryStage.show();
                }

                else {
                    errorLabel.setText("Incorrect login");
                }
            }
        });
        loginPane.add(signIn, 0, 3, 1, 1);

        GridPane addOrderPane = new GridPane();
        addOrderPane.setPrefWidth(400);
        addOrderPane.setPrefHeight(400);
        addOrderPane.setLayoutX(640);
        addOrderPane.setLayoutY(320);

        tableLabel.setText("Table");
        seat1Label.setText("Seat 1 Order");
        seat2Label.setText("Seat 2 Order");
        seat3Label.setText("Seat 3 Order");
        seat4Label.setText("Seat 4 Order");
        seat1Button.setText("Add");
        seat2Button.setText("Add");
        seat3Button.setText("Add");
        seat4Button.setText("Add");

        seat1Button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                addOrderButton(0, primaryStage);
            }
        });
        seat2Button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                addOrderButton(1, primaryStage);
            }
        });
        seat3Button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                addOrderButton(2, primaryStage);

            }
        });
        seat4Button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                addOrderButton(3, primaryStage);
            }
        });

        tableLabel.setVisible(false);
        seat1Label.setVisible(false);
        seat2Label.setVisible(false);
        seat3Label.setVisible(false);
        seat4Label.setVisible(false);
        seat1Button.setVisible(false);
        seat2Button.setVisible(false);
        seat3Button.setVisible(false);
        seat4Button.setVisible(false);

        addOrderPane.add(tableLabel, 0, 0);
        addOrderPane.add(seat1Label, 0, 1);
        addOrderPane.add(seat2Label, 0, 2);
        addOrderPane.add(seat3Label, 0, 3);
        addOrderPane.add(seat4Label, 0, 4);
        addOrderPane.add(seat1Button, 2, 1);
        addOrderPane.add(seat2Button, 2, 2);
        addOrderPane.add(seat3Button, 2, 3);
        addOrderPane.add(seat4Button, 2, 4);

        mainPane.add(addOrderPane, 0, 6, 6, 1);

        loginPane.setVgap(5);
        loginPane.setHgap(5);
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        addOrderPane.setHgap(10);
        addOrderPane.setVgap(10);

        Line divider = new Line(0, 0, 0, 980);
        mainPane.add(divider, 6, 0, 1, 6);

        queuePane.setSpacing(2);
        mainPane.add(queuePane, 7, 0, 1, 6);

        Label queueLabel = new Label();
        queueLabel.setText("Order Queue:");
        queuePane.getChildren().add(queueLabel);

        primaryStage.setScene(new Scene(loginPane, 360, 100));
        primaryStage.show();
    }

    //Method to authenticate username and password
    public boolean signIn(String username, String password) {
        try {
            File userDB = new File("../../../src/prototype/users.csv");
            Scanner scan = new Scanner(userDB);

            //Keeps track of if a login matches a stored value
            boolean validLogin = false;
            while (scan.hasNextLine()) {

                //Splits each line into username and password to compare with user input
                String data = scan.nextLine();
                String[] login = data.split(",");

                //If username and password are the same, login is set to valid
                if (username.equals(login[0]) && password.equals(login[1])) {
                    validLogin = true;
                    break;
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

    public void addOrderButton(int seat,  Stage primaryStage)
    {
        int tableNumber = Integer.parseInt(tableLabel.getText().split(" ")[1]);
        tables[tableNumber].addToOrder(seat, takeOrder(primaryStage));
        Order order = tables[tableNumber].getOrder(seat);
        Button orderButton = new Button(String.format("%s-%s: %s",
                 tableNumber, seat + 1, order.get(order.size() - 1)));
        orderButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                queuePane.getChildren().remove(orderButton);
            }
        });
        queuePane.getChildren().add(orderButton);
        updateTableStatus(tableNumber);
    }

    public int takeOrder(Stage primaryStage) {

        int[] id = new int[1];
        Stage orderPopup = new Stage();

        orderPopup.initModality(Modality.APPLICATION_MODAL);
        orderPopup.setTitle("Select item");

        GridPane menuPane = new GridPane();
        menuPane.setVgap(2);
        VBox itemList = new VBox();
        itemList.setSpacing(2);

        final ToggleGroup categoryGroup = new ToggleGroup();
        ToggleGroup itemsGroup = new ToggleGroup();

        final String[] selectedValue = new String[1];
        ArrayList<String> categories = new ArrayList<String>();

        try {
                File menuDB = new File("../../../src/prototype/menu.csv");
                Scanner scan = new Scanner(menuDB);

                while (scan.hasNextLine()) {
                    String data = scan.nextLine();
                    String[] item = data.split(",");
                    if (!(categories.contains(item[1]))) categories.add(item[1]);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        Label categoryLabel = new Label();
        categoryLabel.setText("Categories:");
        menuPane.add(categoryLabel, 0, 0, 1, 1);

        for (int i = 0; i < categories.size(); i ++) {
            RadioButton categoryButton = new RadioButton();
            categoryButton.setText(categories.get(i));
            categoryButton.setToggleGroup(categoryGroup);
            categoryButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent actionEvent) {
                    RadioButton selected = (RadioButton) categoryGroup.getSelectedToggle();
                    selectedValue[0] = selected.getText();
                    itemList.getChildren().clear();
                    updateCategoryList(itemsGroup, selectedValue[0], itemList);
                }
            });
            menuPane.add(categoryButton, 0, i + 1, 1, 1);
        }

        menuPane.add(itemList, 1, 0, 1, categories.size() + 2);

        Button confirm = new Button();
        confirm.setText("Confirm");
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                try {
                    File menuDB = new File("../../../src/prototype/menu.csv");
                    Scanner scan = new Scanner(menuDB);

                    RadioButton selected = (RadioButton) itemsGroup.getSelectedToggle();
                    if (selected == null) return;
                    selectedValue[0] = selected.getText();

                    while (scan.hasNextLine()) {
                        String data = scan.nextLine();
                        String[] item = data.split(",");

                        if (item[2].equals(selectedValue[0])) {
                            id[0] = Integer.parseInt(item[0]);
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                orderPopup.close();
            }
        });
        menuPane.add(confirm, 1, categories.size() + 2, 1, 1);

        //Take the order
        orderPopup.setScene(new Scene(menuPane, 510, 400));
        orderPopup.showAndWait();
        return id[0];
    }

    public void updateTableStatus(int number)
    {
        Order[] orders = new Order[4];
        for (int i = 0; i < 4; i++)
            orders[i] = tables[number].getOrder(i);

        tableLabel.setText("Table " + number);
        seat1Label.setText("Seat 1 Order: " + (orders[0].size() > 0 ? orders[0] : ""));
        seat2Label.setText("Seat 2 Order: " + (orders[1].size() > 0 ? orders[1] : ""));
        seat3Label.setText("Seat 3 Order: " + (orders[2].size() > 0 ? orders[2] : ""));
        seat4Label.setText("Seat 4 Order: " + (orders[3].size() > 0 ? orders[3] : ""));
    }

    public void updateCategoryList(ToggleGroup itemsGroup, String selected, VBox itemList) {

        try {
            File menu = new File("../../../src/prototype/menu.csv");
            Scanner scan = new Scanner(menu);
            while (scan.hasNextLine()) {
                String[] data = scan.nextLine().split(",");
                if (data[1].equals(selected)) {
                    RadioButton temp = new RadioButton(data[2]);
                    temp.setToggleGroup(itemsGroup);
                    itemList.getChildren().add(temp);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void assignTables() {

        int count = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {

                tableButtons[i][j] = new Button(String.valueOf(count));
                tableButtons[i][j].setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000");
                tableButtons[i][j].setDisable(true);
                tableButtons[i][j].setPrefHeight(150);
                tableButtons[i][j].setPrefWidth(150);

                mainPane.add(tableButtons[i][j], j, i, 1, 1);
                count++;
            }
        }

        try {
            String filepath = "../../../src/prototype/tables.csv";
            File tableDB = new File(filepath);
            Scanner scan = new Scanner(tableDB);
            while (scan.hasNextLine()) {
                String data = scan.nextLine();
                String[] assignment = data.split(",");
                if (assignment[0].equals(username)) {
                    int x = Integer.parseInt(assignment[1]);
                    int y = Integer.parseInt(assignment[2]);
                    tableButtons[x][y]
                            .setStyle("-fx-background-color: #00ff00; -fx-border-color: #000000");
                    tableButtons[x][y]
                            .setDisable(false);
                    int number = x * 6 + y;
                    tableButtons[x][y].setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionEvent) {
                            updateTableStatus(number);
                            tableLabel.setVisible(true);
                            seat1Label.setVisible(true);
                            seat2Label.setVisible(true);
                            seat3Label.setVisible(true);
                            seat4Label.setVisible(true);
                            seat1Button.setVisible(true);
                            seat2Button.setVisible(true);
                            seat3Button.setVisible(true);
                            seat4Button.setVisible(true);
                        }
                    });
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
