package mathesh;

// This class consists details about the Bookings Made

public class Bookings {

    private int bookingid;
    private int cust_id;
    private char restaurant;
    private char destination;
    private int starttime;
    private int endtime;
    private DeliveryPerson deliveryPerson;
    private int delivery_amount;

    public Bookings(int bookingid, int cust_id, char restaurant, char destination, int starttime) {
        this.bookingid = bookingid;
        this.cust_id = cust_id;
        this.restaurant = restaurant;
        this.destination = destination;
        this.starttime = starttime;
        this.endtime = starttime + 30;  // No need to set end time as it is mentioned end time will be 30 min from start.
    }

    public int getBookingid() {
        return bookingid;
    }

    public char getRestaurant() {
        return restaurant;
    }

    public char getDestination() {
        return destination;
    }

    public int getStarttime() {
        return starttime;
    }

    public int getCust_id() {
        return cust_id;
    }

    public int getEndtime() {
        return endtime;
    }

    public DeliveryPerson getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(DeliveryPerson deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public int getDelivery_amount() {
        return delivery_amount;
    }

    public void setDelivery_amount(int delivery_amount) {
        this.delivery_amount = delivery_amount;
    }
}
