package mathesh;

/*
    This consists all the properties needed for delivery executive and their strucuture and
    some module for maintaining workflow.
 */

import java.util.ArrayList;

public class DeliveryPerson {

    private static final int ALLLOWANCE = 10; // Allowance amount

    private String delivery_executive_name;
    private ArrayList<Bookings> allbookingdetails; // This Consists of all BookingDetails
    public  ArrayList<Bookings> currentbookingdetails; // At a given time a delivery executive can only hold 5 bookings
    public int allowance; // 10 * No of trips
    private int total_delivery_charge;  // Total Delivery Charge they incurred over all the trips
    private int no_of_trips;

//    Empty Constructor For Initialization
    public DeliveryPerson(){
        this.delivery_executive_name = "";
        this.allbookingdetails = new ArrayList<>();
        this.allowance = 0;
        this.total_delivery_charge = 0;
        this.no_of_trips = 0;
        this.currentbookingdetails = new ArrayList<>();
    }

    public DeliveryPerson(String dname){
        this.delivery_executive_name = dname;
        this.allbookingdetails = new ArrayList<>();
        this.allowance = 0;
        this.total_delivery_charge = 0;
        this.no_of_trips = 0;
        this.currentbookingdetails = new ArrayList<>();
    }

    public String getDelivery_executive_name() {
        return delivery_executive_name;
    }

    public void setBookingdetails(Bookings bookingdetails) {
        this.allbookingdetails.add(bookingdetails);
    }

    public void setNo_of_trips(){
//        Increment Trip By One to calculate allowance
        this.no_of_trips = this.no_of_trips + 1;
    }

    public int getNo_of_trips(){
        return this.no_of_trips;
    }

//    Add all the booking to the list to know the history of the bookings the delivery executive made.
    public void setCurrentbookingdetails(Bookings bookingdetails) {
        this.currentbookingdetails.add(bookingdetails);
    }

    public int getAllowance() {
        allowance = ALLLOWANCE * this.getNo_of_trips();
        return allowance;
    }

    public int getTotal_delivery_charge() {
        return total_delivery_charge;
    }

    public void setTotal_delivery_charge(int total_delivery_charge) {
        this.total_delivery_charge = total_delivery_charge;
    }

//    Update the current Booking details by deleting the outdated ones the delivered ones (Always Call this function when adding a booking)
    public void updateCurrentBooking(String starttime){
        long diff = 0;
        String endtime = "";
        for(Bookings bookings : this.currentbookingdetails){
            endtime = bookings.getEndtime();
            diff = HandleTime.getTimeDifference(endtime,starttime);
            if(diff > 30){
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

