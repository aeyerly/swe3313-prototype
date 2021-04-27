package prototype;

public class MenuItem
{
    private int id, cost;
    private String name, category;

    public MenuItem(int id)
    {
        lookupInfo(id);
    }

    public void lookupInfo(int id) {
        // Pull info from csv
    }

    public int getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}