package prototype;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MenuItem
{
    private int id;
    private double cost;
    private String name, category;

    public MenuItem(int id)
    {
        lookupInfo(id);
    }

    public void lookupInfo(int id) {
        try {
                String filepath = "../../../src/prototype/menu.csv";
                File menuDB = new File(filepath);
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

    public int getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}