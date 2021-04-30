package prototype;

import java.util.ArrayList;

/**
 * Order --- Stores a list of individual menu items.
 * @author Andrew Loveless
 * @version 1.0
 */
public class Order
{
    private ArrayList<MenuItem> items = new ArrayList<MenuItem>();

    /**
     * Create an empty order.
     */
    public Order() {
        this.items = items;
    }

    /**
     * Create an order and add a menu item to it.
     * @param id The menu item to add to the order
     */
    public Order(int id) {
        this.items = items;
        addItem(id);
    }

    /**
     * Return the list of menu items on the order.
     * @return items The arraylist of menu items
     */
    public ArrayList<MenuItem> getItems() {
        return items;
    }

    /**
     * Calculate the total cost of the items on the order.
     * @return total The total cost of the order
     */
    public int getTotal() {
        int total = 0;
        for (MenuItem item : items)
        {
            total += item.getCost();
        }
        return total;
    }

    /**
     * Add a menu item to the order.
     * @param id The menu item to add to the order
     */
    public void addItem(int id) {
        items.add(new MenuItem(id));
    }

    /**
     * Get the name of the menu item at the specified index.
     * @param index The index of the menu item to check
     * @return The name of the menu item at the index
     */
    public String get(int index)
    {
        return items.get(index).getName();
    }

    /**
     * Return the number of items on the order.
     * @return The number of items on the order
     */
    public int size()
    {
        return items.size();
    }

    /**
     * Print the order as a list of its menu items and total cost.
     * @return The total cost of the order followed by each item on the order
     */
    @Override
    public String toString() {
        String output = "($" + getTotal() + ") ";
        for (int i = 0; i < items.size(); i++){
            output += items.get(i).getName();
            if (i < items.size() - 1) output += ", ";
        }
        return output;
    }
}