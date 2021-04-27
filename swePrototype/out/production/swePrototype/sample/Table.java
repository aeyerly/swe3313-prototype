public class Table
{

    //Status [0 - Open] [1 - Occupied] [2 - Dirty]
    private int number, status;
    private Order[4] orders;

    public Table(int number, int status) {
        this.number = number;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public int getNumber() {
        return number;
    }

    public getOrder(int index) {
        return orders[index];
    }

    public setStatus(int newStatus) {
         status = newStatus;
    }

    public setNumber(int newNumber) {
        number = newNumber;
    }

    public setOrder(int index, Order newOrder) {
        orders[index] = newOrder;
    }

}