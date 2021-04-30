package prototype;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Main --- Main file which controls the UI.
 * @author Alex Eyerly, Andrew Loveless
 * @version 1.0
 */
public class Main extends Application {

    // Initialize UI elements
    GridPane loginPane = new GridPane();
    GridPane mainPane = new GridPane();
    VBox queuePane = new VBox();
    HBox labelPane = new HBox();
    HBox logoutPane = new HBox();

    ChoiceBox<String> statusChoiceBox = new ChoiceBox<String>();

    Label usernameLabel = new Label();
    Label tableLabel = new Label();
    Label seat1Label = new Label();
    Label seat2Label = new Label();
    Label seat3Label = new Label();
    Label seat4Label = new Label();

    Button seat1Button = new Button();
    Button seat2Button = new Button();
    Button seat3Button = new Button();
    Button seat4Button = new Button();

    Button tableButtons[][] = new Button[5][6];
    String[] statuses = {"Open", "Occupied", "Dirty"};

    Table[] tables = new Table[30];
    private String username; // Username of the user logged in

    @Override
    public void start(Stage primaryStage) throws Exception {

        for (int i = 0; i < 30; i++)
            tables[i] = new Table(i, 0);

        primaryStage.setTitle("J's Corner Restaurant Prototype");
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(e -> Platform.exit());

        mainPane.setPadding(new Insets(10, 10, 10, 10));
        loginPane.setPadding(new Insets(10, 10, 10, 10));

        Label userLabel = new Label();
        userLabel.setText("Username");
        loginPane.add(userLabel,0, 0, 1, 1);

        Label passLabel = new Label();
        passLabel.setText("Password");
        loginPane.add(passLabel,1, 0, 1, 1);

        Label errorLabel = new Label();
        errorLabel.setText("");
        loginPane.add(errorLabel, 1, 3, 1, 1);

        TextField userEntry = new TextField();
        loginPane.add(userEntry, 0, 1, 1, 1);

        PasswordField passEntry = new PasswordField();
        loginPane.add(passEntry, 1, 1, 1, 1);

        //Sign in button
        Button signIn = new Button();
        signIn.setText("Sign In");
        signIn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                username = userEntry.getText();

                // Attempt login
                if(signIn(username, passEntry.getText())) {
                    assignTables();
                    primaryStage.setScene(new Scene(mainPane, 1200, 980));
                    primaryStage.show();
                    primaryStage.centerOnScreen();
                } else {
                    errorLabel.setText("Incorrect login");
                }
            }
        });
        loginPane.add(signIn, 0, 3, 1, 1);

        GridPane addOrderPane = new GridPane();
        addOrderPane.setPrefWidth(0);
        addOrderPane.setPrefHeight(0);
        addOrderPane.setLayoutX(0);
        addOrderPane.setLayoutY(0);

        statusChoiceBox.getItems().addAll("Open", "Occupied", "Dirty");
        seat1Button.setText("Add");
        seat2Button.setText("Add");
        seat3Button.setText("Add");
        seat4Button.setText("Add");

        // Update table status when changed in the choice box
        statusChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue observable, Number value, Number newValue)
            {
                if (!statusChoiceBox.isVisible()) return;
                int tableNumber = Integer.parseInt(tableLabel.getText().split(" ")[1]);

                tables[tableNumber].setStatus(newValue.intValue());

                int x = (tableNumber - tableNumber % 6) / 6;
                int y = tableNumber % 6;
                String color = "";
                switch (newValue.intValue()) {
                    case 0:
                        tables[tableNumber].clearOrders();
                        updateTableStatus(tableNumber);
                        seat1Button.setDisable(true);
                        seat2Button.setDisable(true);
                        seat3Button.setDisable(true);
                        seat4Button.setDisable(true);
                        color = "#00ff00";
                        break;
                    case 1:
                        seat1Button.setDisable(false);
                        seat2Button.setDisable(false);
                        seat3Button.setDisable(false);
                        seat4Button.setDisable(false);
                        color = "#ffff00";
                        break;
                    case 2:
                        tables[tableNumber].clearOrders();
                        updateTableStatus(tableNumber);
                        seat1Button.setDisable(true);
                        seat2Button.setDisable(true);
                        seat3Button.setDisable(true);
                        seat4Button.setDisable(true);
                        color = "#ff0000";
                        break;
                }
                tableButtons[x][y].setStyle("-fx-background-color: " + color + "; -fx-border-color: #000000");
            }
        });

        // Order button control for seats
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

        // Start program with table and seat panel hidden and disabled
        tableLabel.setVisible(false);
        statusChoiceBox.setVisible(false);
        seat1Label.setVisible(false);
        seat2Label.setVisible(false);
        seat3Label.setVisible(false);
        seat4Label.setVisible(false);
        seat1Button.setVisible(false);
        seat2Button.setVisible(false);
        seat3Button.setVisible(false);
        seat4Button.setVisible(false);
        seat1Button.setDisable(true);
        seat2Button.setDisable(true);
        seat3Button.setDisable(true);
        seat4Button.setDisable(true);

        labelPane.getChildren().addAll(tableLabel, statusChoiceBox);
        addOrderPane.add(labelPane, 0, 0);
        addOrderPane.add(seat1Label, 0, 1);
        addOrderPane.add(seat2Label, 0, 2);
        addOrderPane.add(seat3Label, 0, 3);
        addOrderPane.add(seat4Label, 0, 4);
        addOrderPane.add(seat1Button, 2, 1);
        addOrderPane.add(seat2Button, 2, 2);
        addOrderPane.add(seat3Button, 2, 3);
        addOrderPane.add(seat4Button, 2, 4);
        mainPane.add(addOrderPane, 0, 6, 6, 1);

        // Set spacing of UI elements
        loginPane.setVgap(5);
        loginPane.setHgap(5);
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        addOrderPane.setHgap(10);
        addOrderPane.setVgap(10);
        labelPane.setSpacing(5);
        logoutPane.setSpacing(5);
        queuePane.setSpacing(2);

        // Alignment fixes
        logoutPane.setAlignment(Pos.CENTER_LEFT);
        labelPane.setAlignment(Pos.CENTER_LEFT);

        Separator separator = new Separator(Orientation.VERTICAL);
        mainPane.add(separator, 6, 0, 1, 8);

        mainPane.add(queuePane, 7, 0, 1, 6);
        Button logoutButton = new Button();
        logoutButton.setText("Logout");
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                    primaryStage.close();
                    Platform.exit();
            }
        });

        logoutPane.getChildren().addAll(usernameLabel, logoutButton);

        Label queueLabel = new Label();
        queueLabel.setText("Order Queue:");
        queuePane.getChildren().addAll(logoutPane, queueLabel);

        // Start program at login screen
        primaryStage.setScene(new Scene(loginPane, 360, 100));
        primaryStage.show();
    }

    /**
     * Attempt to sign in with given username and password.
     * @param username The username to check
     * @param password The password to check
     * @exception FileNotFoundException If users file is not found
     * @return Boolean if login was successful
     */
    public boolean signIn(String username, String password) {
        try {
                File userDB = new File("../../../src/prototype/users.csv");
                Scanner scan = new Scanner(userDB);

                //Keeps track of if a login matches a stored value
                while (scan.hasNextLine()) {
                    //Splits each line into username and password to compare with user input
                    String data = scan.nextLine();
                    String[] login = data.split(",");

                    //If username and password are the same, login is set to valid
                    if (username.equals(login[0]) && password.equals(login[1])) {
                        usernameLabel.setText("Current user: " + username);
                        return true;
                    }
                }
                return false;
            } catch (FileNotFoundException e) {
                System.out.println("File was not found");
                e.printStackTrace();
            }
        return false;
    }

    /**
     * Add to the order of the specified seat of the currently selected table,
     * then put that order in the order queue.
     * @param seat          The seat number (1-4) at the table
     * @param primaryStage  The primary stage
     */
    public void addOrderButton(int seat,  Stage primaryStage)
    {
        int tableNumber = Integer.parseInt(tableLabel.getText().split(" ")[1]);
        tables[tableNumber].addToOrder(seat, takeOrder(primaryStage)); //Take the order and add to table

        // Create a button and add it to the order queue
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

    /**
     * Show a pop-up menu to select an item to add to the order.
     * @param primaryStage  The primary stage
     * @return The ID of the menu item selected
     */
    public int takeOrder(Stage primaryStage) {

        int[] id = new int[1]; // Initialized as an array to avoid final variable errors
        Stage orderPopup = new Stage();

        orderPopup.initModality(Modality.APPLICATION_MODAL); // Identify the stage as a pop-up
        orderPopup.setTitle("Select item");

        GridPane menuPane = new GridPane();
        VBox itemList = new VBox();
        menuPane.setVgap(2);
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

        // Generate a list of the categories as radio buttons
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

                    // Find selected item in menu file
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

    /**
     * Update the UI with the correct table number and orders for the seats.
     * @param number The number of the table being used to update the UI
     */
    public void updateTableStatus(int number)
    {
        // Get all the orders for the table
        Order[] orders = new Order[4];
        for (int i = 0; i < 4; i++)
            orders[i] = tables[number].getOrder(i);

        tableLabel.setText("Table " + number);
        statusChoiceBox.setValue(statuses[tables[number].getStatus()]);
        seat1Label.setText("Seat 1 Order: " + (orders[0].size() > 0 ? orders[0] : ""));
        seat2Label.setText("Seat 2 Order: " + (orders[1].size() > 0 ? orders[1] : ""));
        seat3Label.setText("Seat 3 Order: " + (orders[2].size() > 0 ? orders[2] : ""));
        seat4Label.setText("Seat 4 Order: " + (orders[3].size() > 0 ? orders[3] : ""));
    }

    /**
     * Update the menu pop-up to display the items for the currently selected category.
     * @param itemsGroup    The group of radio buttons used in the pop-up
     * @param selected      The name of the selected category
     * @param itemList      The VBox in which the items group is contained in
     */
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

    /**
     * Creates the UI elements for the tables and enables the correct tables for the logged in user.
     * @exception FileNotFoundException If the tables file is not found
     */
    public void assignTables() {
        // Create all tables as white and disabled
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
                // Enable and color all tables assigned to the current user
                if (assignment[0].equals(username)) {
                    int x = Integer.parseInt(assignment[1]);
                    int y = Integer.parseInt(assignment[2]);
                    tableButtons[x][y]
                            .setStyle("-fx-background-color: #00ff00; -fx-border-color: #000000");
                    tableButtons[x][y]
                            .setDisable(false);
                    int number = x * 6 + y;
                    // When a table is clicked, show the table information panel if not already showing
                    tableButtons[x][y].setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionEvent) {
                            updateTableStatus(number);
                            tableLabel.setVisible(true);
                            statusChoiceBox.setVisible(true);
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
