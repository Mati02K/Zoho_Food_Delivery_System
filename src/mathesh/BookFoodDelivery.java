package mathesh;

import java.util.*;

public class BookFoodDelivery {

    public static final int ORDER_AMOUNT = 50;  // Total Order Amount
    public static final int SAME_ORDER_AMOUNT = 5; // Order Amount if the delivery destination is same.

/*
    The Below Hashmap is one of the important piece of this workflow.
    It contains the Integer which is the Deliveryperson Amount as key
    and the values as arraylist where it contains the Deliverypersons whose amount is same as the key
    This is done to find the lowest amount effectively within a quick time frame O(1) instead of arrays O(n).
 */
    public static HashMap<Integer, ArrayList<DeliveryPerson>> deliveryPersonAmt = new HashMap<>();

/*
    The Below Treeset contains all the Delivery Amounts Calculated.
    This will be used along with above hashmap deliverypersonamt to find the lowest possible amount quickly
    as treeset will store values in ascending order.
*/
    public static TreeSet<Integer> allDeliveryAmts = new TreeSet<>();

/*
    Below is a ArrayList Which acts like a Queue but with bit more operations.
    This contains all the bookings made and will be used to retrieve if the last booking is done within 15 mins of the same restaurant.
*/
    public static ArrayList<Bookings> restaurantQueue = new ArrayList<>();

/*
    Creating 5 Basic Delivery Persons as per question
    But the same code will work for n number of delivery persons.
    In many persons case new Object can be created at the particular instance.
*/
    public static DeliveryPerson DE1 = new DeliveryPerson("DE1");
    public static DeliveryPerson DE2 = new DeliveryPerson("DE2");
    public static DeliveryPerson DE3 = new DeliveryPerson("DE3");
    public static DeliveryPerson DE4 = new DeliveryPerson("DE4");
    public static DeliveryPerson DE5 = new DeliveryPerson("DE5");

//    Work Flow Starts
//    Remove the delivery persons from the hashmap to stay updated
    public static void removeDeliveryPersonAmt(DeliveryPerson deliveryPerson){
        int prev_amt = deliveryPerson.getTotal_delivery_charge();
        ArrayList<DeliveryPerson> amtPersons = deliveryPersonAmt.get(prev_amt);
        amtPersons.remove(deliveryPerson);
        deliveryPersonAmt.put(prev_amt,amtPersons);
        if(amtPersons.size() == 0){
//            Then there is no Delivery Person with the same amount so just remove it from Hashmap and Treeset to just clean out outdated values.
            deliveryPersonAmt.remove(prev_amt);
            allDeliveryAmts.remove(prev_amt);
        }
    }

//    Set the Delivery Person current amt in the HashMap
    public static void setDeliveryPersonAmt(DeliveryPerson deliveryPerson, int current_amt){
        int amt = deliveryPerson.getTotal_delivery_charge();
        ArrayList<DeliveryPerson> amtPersons = new ArrayList<>();
        if(current_amt != amt){
            removeDeliveryPersonAmt(deliveryPerson);
            amtPersons = deliveryPersonAmt.get(current_amt);
            if(amtPersons != null) {
//                If Current Amount is Present in the Hashmap
                amtPersons.add(deliveryPerson);
                deliveryPersonAmt.put(current_amt,amtPersons);
            }
            else{
//                If Current Amount not present in Hashmap
                amtPersons = new ArrayList<>();
                amtPersons.add(deliveryPerson);
                allDeliveryAmts.add(current_amt);
                deliveryPersonAmt.put(current_amt,amtPersons);
            }
        }
    }
//    This Function helps in finding the best suited delivery executive with least delivery charge and also checks if he is free.
    public static DeliveryPerson getCorrectDeliveryPerson(){
        DeliveryPerson deliveryPerson = new DeliveryPerson();
        int lowestamt = allDeliveryAmts.first();
        int i = 0;
        while(i < allDeliveryAmts.size()){
            ArrayList<DeliveryPerson> lowestDeliveryPerson = deliveryPersonAmt.get(lowestamt);
            for(DeliveryPerson d : lowestDeliveryPerson){
                if(isDeliveryManFree(d)){
                    deliveryPerson = d;
                    return deliveryPerson;
                }
            }
            if(allDeliveryAmts.higher(lowestamt) != null){
                lowestamt = allDeliveryAmts.higher(lowestamt);
            }
            else{
                break;
            }
            i++;
        }
//        Else Go for the second next closest delivery person
        return deliveryPerson;
    }

    public static boolean isDeliveryManFree(DeliveryPerson deliveryPerson){
        int size = deliveryPerson.getCurrentDeliverySize();
        return size < 5;
    }

/*
    This is the Function which decides the Delivery Executives Based on the Condition given in PDF
    It returns the Delivery Executive, Whether it is a same trip and in that case the previous amount.
*/
    public ArrayList getMatchingDeliveryExecutive(char rest, String start){
        // This result will consist of three values (True or False to denote wheather previous trip is used) and the matching delivery executive, and the previous amount if it is the same trip.
        ArrayList result = new ArrayList();
        DeliveryPerson deliveryPerson = new DeliveryPerson();
        int n = restaurantQueue.size() - 1;
        if(n > 0){
        Bookings lastBook = restaurantQueue.get(n);
        String laststarttime = lastBook.getStarttime();
        long timeDifference = HandleTime.getTimeDifference(laststarttime,start);
        if(timeDifference >= 0 && timeDifference <= 15){
//            Check the restaurant too
            ArrayList<Bookings> allprevbookings = new ArrayList<>();
            allprevbookings.add(lastBook);
            while(n >= 0){
                lastBook = restaurantQueue.get(n);
                laststarttime = lastBook.getStarttime();
                timeDifference = HandleTime.getTimeDifference(laststarttime,start);
//                Getting all those restaurants in that time interval
                if(timeDifference >= 0 && timeDifference <= 15){
                    allprevbookings.add(lastBook);
                }
                else{
                    break;
                }
                n--;
            }
//            Since we got all those Booking details within that time range check wheather anything matches with the character
            for(Bookings prevbooks : allprevbookings){
                if(prevbooks.getRestaurant() == rest){
//                    Then he is the delivery man but check if he is free and return ---> Charge is 5
                    deliveryPerson = prevbooks.getDeliveryPerson();
                    if(isDeliveryManFree(deliveryPerson)){
                        result.add(true);
                        result.add(deliveryPerson);
                        result.add(prevbooks.getDelivery_amount());
                        return result;
                    }
                }
            }
        }
//        If previous Delivery is greater than 15 min Timeframe
        deliveryPerson = getCorrectDeliveryPerson();
        result.add(false);
        result.add(deliveryPerson);
        result.add(0); // No Previous Trip so Zero.
        return result;
        }
        //  Reserations aren't open so add
        deliveryPerson = getCorrectDeliveryPerson();
        result.add(false);
        result.add(deliveryPerson);
        result.add(0); // No Previous Trip so Zero.
        return result;
    }

//    TO Handle All the Booking
    public String handleBooking(int bookno, int cust_id, char res, char des, String startTime){
        startTime = HandleTime.convertTime(startTime);
        Bookings bookingdetails = new Bookings(bookno,cust_id,res,des,startTime);
        ArrayList details = getMatchingDeliveryExecutive(res,startTime);
        boolean isPreviousTrip = (boolean) details.get(0);
        DeliveryPerson deliveryexecutive = (DeliveryPerson) details.get(1);
        int prevDeliveryamt = (int) details.get(2);
        String result = assignDeliveryExecutive(bookingdetails,deliveryexecutive, isPreviousTrip, prevDeliveryamt);
        restaurantQueue.add(bookingdetails);
        return result;
    }

//    Assign all the data in the Delivery executive and Booking module.
    public String assignDeliveryExecutive(Bookings bookingdetails, DeliveryPerson deliveryPerson, boolean isPrevTrip, int prevDeliveryAmt){
        int deliverycharge = 0;
        int totaldeliverycharge = 0;
        if(isPrevTrip){
//            If it is the same TRIP
            deliverycharge = prevDeliveryAmt + SAME_ORDER_AMOUNT;
            totaldeliverycharge = deliveryPerson.getTotal_delivery_charge();
            totaldeliverycharge = totaldeliverycharge + SAME_ORDER_AMOUNT;
            setDeliveryPersonAmt(deliveryPerson,totaldeliverycharge);  // Setting the current delivery amount to the Delivery Executive
            deliveryPerson.setTotal_delivery_charge(totaldeliverycharge);
            bookingdetails.setDelivery_amount(deliverycharge);
            bookingdetails.setDeliveryPerson(deliveryPerson);
            deliveryPerson.setCurrentbookingdetails(bookingdetails); // Adding to current Booking Queue
        }
        else{
//            If it is a different TRIP
            deliveryPerson.setNo_of_trips(); // Will Increment the trip by one
            deliverycharge = prevDeliveryAmt + ORDER_AMOUNT;
            totaldeliverycharge = deliveryPerson.getTotal_delivery_charge();
            totaldeliverycharge = totaldeliverycharge + ORDER_AMOUNT;
            setDeliveryPersonAmt(deliveryPerson,totaldeliverycharge);  // Setting the current delivery amount to the Delivery Executive
            deliveryPerson.setTotal_delivery_charge(totaldeliverycharge);
            bookingdetails.setDelivery_amount(deliverycharge);
            bookingdetails.setDeliveryPerson(deliveryPerson);
            deliveryPerson.setCurrentbookingdetails(bookingdetails); // Adding to current Booking Queue
        }
        deliveryPerson.updateCurrentBooking(bookingdetails.getStarttime()); // Updating the delivery person
        deliveryPerson.setBookingdetails(bookingdetails); // Set the current Booking to the whole list of booking details of the delivery executive
        return "Assigned Delivery Executive : " + deliveryPerson.getDelivery_executive_name();

    }

//    Display all Delivery Executive Activities
    public StringBuilder displayActivities(){

        HashSet<DeliveryPerson> deliveryPeople = new HashSet<>(); // This set consists of all the delivery executives who got the booking which will be used for printing total earned
        StringBuilder invoice = new StringBuilder();

//        Printing Delivery History
        invoice.append("-------------------------------------------------------------------------------------------------------------------------");
        invoice.append("\n");
        invoice.append("DELIVERY HISTORY");
        invoice.append("\n");
        invoice.append("TRIP " + "    " + "EXECUTIVE " +  "    " +
                "RESTAURANT " +  "    " + "DESTINATION POINT " +  "    " +
                "ORDERS " +  "    " + "PICK_UP_TIME " +  "    " +
                "DELIVERY TIME " +  "    " + "DELIVERY CHARGE ");
        invoice.append("\n");
        int trip = 1;
        String exec_name = "";
        char res;
        char des;
        int no_of_orders;
        String start_time;
        String end_time;
        int delivery_charge;
        DeliveryPerson deliveryPerson;
        Bookings book;
        int b = 0;
        int count = 1;
        String output = "";
        while(b < restaurantQueue.size()){
            book = restaurantQueue.get(b);
            res = book.getRestaurant();
            des = book.getDestination();
            deliveryPerson = book.getDeliveryPerson();
//            Check if next restaturant is also same restaurant:
            if(b + 1 < restaurantQueue.size() && deliveryPerson == restaurantQueue.get(b + 1).getDeliveryPerson()){
                count = b + 1;
//                Go until where the current booking is from same deliveryperson
                while(restaurantQueue.get(count).getDeliveryPerson() != deliveryPerson){
                    count++;
                }
                b = count;
                book = restaurantQueue.get(b);
            }
            delivery_charge = book.getDelivery_amount();
            start_time = HandleTime.correctTime(book.getStarttime());
            end_time = HandleTime.correctTime(book.getEndtime());
            no_of_orders = count;
            exec_name = deliveryPerson.getDelivery_executive_name();
            output = trip + "          " +
                     exec_name + "             " +
                     res +  "                  " +
                     des +  "                " +
                     no_of_orders +  "           " +
                     start_time + "            " +
                     end_time +  "              " +
                     delivery_charge;
            invoice.append(output);
            invoice.append("\n");
            trip++;
            b++;
            count = 1; // Resetting the count to calculate next trip
            deliveryPeople.add(deliveryPerson);
        }
        invoice.append("-------------------------------------------------------------------------------------------------------------------------");
        invoice.append("\n");
//        Printing total Amount Earned
        invoice.append("TOTAL EARNED");
        invoice.append("\n");
        invoice.append("EXECUTIVE " +  "    " +
                "ALLOWANCE " +  "    " + "DELIVERY_CHARGES " +  "    " +
                "TOTAL ");
        invoice.append("\n");
        String result = "";
        int total;
        for(DeliveryPerson d : deliveryPeople){
            total = d.getAllowance() + d.getTotal_delivery_charge();
            result = "  " + d.getDelivery_executive_name() + "            " +
                    d.getAllowance() + "              " +
                    d.getTotal_delivery_charge() + "               " +
                    total;
            invoice.append(result);
            invoice.append("\n");
        }

        invoice.append("-------------------------------------------------------------------------------------------------------------------------");
        return invoice;
    }

//    This Will be Invoked Only One time per object and it is used only for adding Zero as the start amount for all the delivery executives.
    public void doFirstInvocation(){
//    Setting all the Deliverypersons beginning amt to zero in the list
        ArrayList<DeliveryPerson> beginningamt = new ArrayList<>();
        beginningamt.add(DE1);
        beginningamt.add(DE2);
        beginningamt.add(DE3);
        beginningamt.add(DE4);
        beginningamt.add(DE5);
        deliveryPersonAmt.put(0,beginningamt);
        allDeliveryAmts.add(0);

    }
}

