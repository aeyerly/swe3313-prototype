package prototype;

import java.util.ArrayList;

public class Order
{
    private ArrayList<MenuItem> items = new ArrayList<MenuItem>();

    public Order() {
        this.items = items;
    }

    public Order(int id) {
        this.items = items;
        addItem(id);
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public int getTotal() {
        int total = 0;
        for (MenuItem item : items)
        {
            total += item.getCost();
        }
        return total;
    }

    public void addItem(int id) {
        items.add(new MenuItem(id));
    }

    public String get(int index)
    {
        return items.get(index).getName();
    }

    public int size()
    {
        return items.size();
    }

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