package prototype;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * MenuItem --- Stores menu item information such as category, name, and price.
 * @author Andrew Loveless
 * @version 1.0
 */
public class MenuItem
{
    private int id;
    private double cost;
    private String name, category;

    /**
     * Create a MenuItem object and lookup the item info to populate it.
     * @param id The ID of the menu item
     */
    public MenuItem(int id) {
        lookupInfo(id);
    }

    /**
     * Pull information about the menu item from a file
     * @param id The ID of the menu item
     * @exception FileNotFoundException If menu file is not found
     */
    public void lookupInfo(int id) {
        try {
                File menuDB = new File("../../../src/prototype/menu.csv");
                Scanner scan = new Scanner(menuDB);

                int line = 1;
                while (scan.hasNextLine()) {
                    String data = scan.nextLine();
                    if (line == id) {
                        String[] item = data.split(",");
                        category = item[1];
                        name = item[2];
                        cost = Double.parseDouble(item[3]);
                        break;
                    } else line++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
    }

    /**
     * Get the cost of the menu item
     * @return cost The cost of the menu item
     */
    public double getCost() {
        return cost;
    }

    /**
     * Get the name of the menu item
     * @return name The name of the menu item
     */
    public String getName() {
        return name;
    }
}