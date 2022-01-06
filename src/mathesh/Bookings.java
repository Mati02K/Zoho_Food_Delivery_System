package mathesh;

// This class consists details about the Bookings Made

public class Bookings {

    private final int bookingid;
    private final int cust_id;
    private final char restaurant;
    private final char destination;
    private final String starttime;
    private final String endtime;
    private DeliveryPerson deliveryPerson;
    private int delivery_amount; // Delivery charge for each booking

    public Bookings(int bookingid, int cust_id, char restaurant, char destination, String starttime) {
        this.bookingid = bookingid;
        this.cust_id = cust_id;
        this.restaurant = restaurant;
        this.destination = destination;
        this.starttime = starttime;
        this.endtime = HandleTime.returnEndTime(starttime);  // No need to set end time as it is mentioned end time will be 30 min from start.
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

    public String getStarttime() {
        return starttime;
    }

    public int getCust_id() {
        return cust_id;
    }

    public String getEndtime() {
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
