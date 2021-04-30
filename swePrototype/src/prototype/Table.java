package prototype;

/**
 * Table --- Stores table information such as number, status, and orders at each seat.
 * @author Andrew Loveless
 * @version 1.0
 */
public class Table
{
    private int number, status;
    private Order[] orders = new Order[4];

    /**
     * Create the table object.
     * @param number
     * @param status
     */
    public Table(int number, int status) {
        this.number = number;
        this.status = status;

        // Initialize the orders for each seat as empty
        clearOrders();
    }

    /**
     * Returns if the table is open (0), occupied (1), or dirty (2).
     * @return status The status of the table
     */
    public int getStatus() {
        return status;
    }

    /**
     * Returns the table's number.
     * @return number The table's number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns the order at the specified seat index.
     * @param index The seat to get the order from
     * @return orders[index] The order from the checked seat
     */
    public Order getOrder(int index) {
        return orders[index];
    }

    /**
     * Set the status of the table to either open (0), occupied (1), or dirty (2).
     * @param newStatus The new status of the table
     */
    public void setStatus(int newStatus) {
        status = newStatus;
    }

    /**
     * Clear all of the orders at the table.
     */
    public void clearOrders() {
        for (int i = 0; i < orders.length; i++) {
            orders[i] = new Order();
        }
    }

    /**
     * Add a menu item to the end of a seat's current order.
     * @param seat The seat which is being added to
     * @param item The menu item to add to the order
     */
    public void addToOrder(int seat, int item) {
        Order order = getOrder(seat);
        order.addItem(item);
        orders[seat] = order;
    }
}