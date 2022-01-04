package mathesh;//This class consists of Delivery Person Detail Structure

import java.util.HashMap;

public class DeliveryPerson {
    private Bookings bookingdetails;
    private int allowance;
    private int total_delivery_charge;
    private int no_of_orders;

//    Empty Constructor For Initialization
    public DeliveryPerson(){
        this.bookingdetails = null;
        this.allowance = 0;
        this.total_delivery_charge = 0;
        this.no_of_orders = 0;
    }

    public DeliveryPerson(Bookings bookingdetails, int allowance, int total_delivery_charge, int no_of_orders) {
        this.bookingdetails = bookingdetails;
        this.allowance = allowance;
        this.total_delivery_charge = total_delivery_charge;
        this.no_of_orders = no_of_orders;
    }

    public Bookings getBookingdetails() {
        return bookingdetails;
    }

    public void setBookingdetails(Bookings bookingdetails) {
        this.bookingdetails = bookingdetails;
    }

    public int getAllowance() {
        return 10 + getTotal_delivery_charge();
    }

    public void setAllowance(int allowance) {
        this.allowance = allowance;
    }

    public int getTotal_delivery_charge() {
        return total_delivery_charge;
    }

    public void setTotal_delivery_charge(int total_delivery_charge) {
        this.total_delivery_charge = total_delivery_charge;
    }

    public int getNo_of_orders() {
        return no_of_orders;
    }

    public void setNo_of_orders(int no_of_orders) {
        this.no_of_orders = no_of_orders;
    }
}

