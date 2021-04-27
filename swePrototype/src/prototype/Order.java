package prototype;

import java.util.ArrayList;

public class Order
{
    private ArrayList<MenuItem> items;
    private int total;
    private static int id;

    public Order() {
        ++id;
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public int getTotal() {
        total = 0;
        for (MenuItem item : items)
        {
            total += item.getCost();
        }
        return total;
    }

    public int getId() {
        return id;
    }
}