package mathesh;//This class consists of Delivery Person Detail Structure


import java.util.ArrayList;

public class DeliveryPerson {

    private String delivery_executive_name;
    private ArrayList<Bookings> allbookingdetails; // This Consists of all BookingDetails
    public  ArrayList<Bookings> currentbookingdetails; // At a given time a delivery executive can only hold 5 bookings
    public int allowance; // 10 * No of trips
    private int total_delivery_charge;  // Total Delivery Charge they incurred over all the trips
    private int no_of_orders;
    private int no_of_trips;

//    Empty Constructor For Initialization
    public DeliveryPerson(){
        this.delivery_executive_name = "";
        this.allbookingdetails = new ArrayList<>();
        this.allowance = 0;
        this.total_delivery_charge = 0;
        this.no_of_orders = 0;
        this.no_of_trips = 0;
        this.currentbookingdetails = new ArrayList<>();
    }

    public DeliveryPerson(String dname){
        this.delivery_executive_name = dname;
        this.allbookingdetails = new ArrayList<>();
        this.allowance = 0;
        this.total_delivery_charge = 0;
        this.no_of_orders = 0;
        this.no_of_trips = 0;
        this.currentbookingdetails = new ArrayList<>();
    }

    public String getDelivery_executive_name() {
        return delivery_executive_name;
    }

    public void setDelivery_executive_name(String delivery_executive_name) {
        this.delivery_executive_name = delivery_executive_name;
    }

    public ArrayList<Bookings> getBookingdetails() {
        return allbookingdetails;
    }

    public void setBookingdetails(Bookings bookingdetails) {
        this.allbookingdetails.add(bookingdetails);
    }

    public void setNo_of_trips(){
        this.no_of_trips = this.no_of_trips + 1;
    }

    public int getNo_of_trips(){
        return this.no_of_trips;
    }

    public ArrayList<Bookings> getCurrentbookingdetails() {
        return currentbookingdetails;
    }

    public void setCurrentbookingdetails(Bookings bookingdetails) {
        this.currentbookingdetails.add(bookingdetails);
    }

    public int getAllowance() {
        allowance = 10 * this.getNo_of_trips();
        return allowance;
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

//    Update the current Booking details by deleting the outdated ones (Always Call this function when adding a booking)
    public void updateCurrentBooking(String starttime){
        long diff = 0;
        String endtime = "";
        for(Bookings bookings : this.currentbookingdetails){
            endtime = bookings.getEndtime();
            diff = HandleTime.getTimeDifference(endtime,starttime);
            if(diff > 15){
                this.currentbookingdetails.remove(bookings);
            }
        }
    }

//    Get the Current Delivery Size
    public int getCurrentDeliverySize(){
        int size = this.currentbookingdetails.size();
        return size;
    }
}

