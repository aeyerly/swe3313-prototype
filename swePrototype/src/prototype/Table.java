package prototype;

public class Table
{

    //Status [0 - Open] [1 - Occupied] [2 - Dirty]
    private int number, status;
    private Order[] orders = new Order[4];

    public Table(int number, int status) {
        this.number = number;
        this.status = status;

        for (int i = 0; i < orders.length; i++) {
            orders[i] = new Order();
        }
    }

    public int getStatus() {
        return status;
    }

    public int getNumber() {
        return number;
    }

    public Order getOrder(int index) {
        return orders[index];
    }

    public void setStatus(int newStatus) {

        status = newStatus;
    }

    public void addToOrder(int seat, int item) {
        Order order = getOrder(seat);
        order.addItem(item);
        setOrder(seat, order);
    }

    public void setNumber(int newNumber) {

        number = newNumber;
    }

    public void setOrder(int index, Order newOrder) {

        orders[index] = newOrder;
    }

}