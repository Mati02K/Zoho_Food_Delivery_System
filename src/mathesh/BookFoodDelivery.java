package mathesh;

import java.util.*;

// Main Class
public class BookFoodDelivery {

    public static final int ORDER_AMOUNT = 50;  // Total Order Amount
    public static final int SAME_ORDER_AMOUNT = 5; // Same Order Amount if the deliver destination is same.

/*
    The Below Hashmap is one of the important piece of this workflow.
    It contains the Integer which is the Deliveryperson Amount as key
    and the values as arraylist where it contains the Deliverypersons whose amount is same as the key
    This is done to find the lowest amount effectively within a quick time frame O(1) instead of arrays O(n).
 */
    public static HashMap<Integer, ArrayList<DeliveryPerson>> deliveryPersonAmt = new HashMap<>();

/*
    The Below Treeset contains all the Delivery Amounts Calculated.
    This will be used along with above hashmap deliverypersonamt to find the lowest possible amount quickly.
*/
    public static TreeSet<Integer> allDeliveryAmts = new TreeSet<>();

/*
    Here whenever a booking is done a queue (character) is added along with time (integer)
    This is used to lookup to the last added record if the destination is same and the time limit is within 15 minutes.
*/
    public static ArrayList<Bookings> restaurantlist = new ArrayList<>();

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
//                If Current Amount is not present in Hashmap
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
        return size <= 5;
    }

//      Get the last added restaurant
    public ArrayList getMatchingDeliveryExecutive(char rest, String start){
        ArrayList result = new ArrayList();  // This result will consist of two values (True or False to denote wheather previous trip is used) and the matching delivery executive.
        DeliveryPerson deliveryPerson = new DeliveryPerson();
        int n = restaurantlist.size() - 1;
        if(n > 0){
        Bookings lastBook = restaurantlist.get(n);
        String laststarttime = lastBook.getStarttime();
        long timeDifference = HandleTime.getTimeDifference(laststarttime,start);
        if(timeDifference >= 0 && timeDifference <= 15){
//            Check the restaurant too
            ArrayList<Bookings> allprevbookings = new ArrayList<>();
            allprevbookings.add(lastBook);
            while(n >= 0){
                lastBook = restaurantlist.get(n);
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
        deliveryPerson = getCorrectDeliveryPerson();
        result.add(false);
        result.add(deliveryPerson);
        result.add(0);
        return result;
        }
        //  Reserations aren't open so add
        deliveryPerson = getCorrectDeliveryPerson();
        result.add(false);
        result.add(deliveryPerson);
        result.add(0);
        return result;
    }

    public void handleBooking(int bookno, int cust_id, char res, char des, String startTime){
        Bookings bookingdetails = new Bookings(bookno,cust_id,res,des,startTime);
        //restaurantlist.add(bookingdetails); // Adding the restarurant and start time to the
//        assignDeliveryExecutive(bookingdetails);
        //        Call Getting Match Delivery Executive here

        ArrayList details = getMatchingDeliveryExecutive(res,startTime);
        boolean isPreviousTrip = (boolean) details.get(0);
        DeliveryPerson deliveryexecutive = (DeliveryPerson) details.get(1);
        int prevDeliveryamt = (int) details.get(2);
        assignDeliveryExecutive(bookingdetails,deliveryexecutive, isPreviousTrip, prevDeliveryamt);
        restaurantlist.add(bookingdetails);
    }

    public void assignDeliveryExecutive(Bookings bookingdetails, DeliveryPerson deliveryPerson, boolean isPrevTrip, int prevDeliveryAmt){
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
        }
        else{
            deliveryPerson.setNo_of_trips(); // Will Increment the trip by one
            deliverycharge = prevDeliveryAmt + ORDER_AMOUNT;
            totaldeliverycharge = deliveryPerson.getTotal_delivery_charge();
            totaldeliverycharge = totaldeliverycharge + ORDER_AMOUNT;
            setDeliveryPersonAmt(deliveryPerson,totaldeliverycharge);  // Setting the current delivery amount to the Delivery Executive
            deliveryPerson.setTotal_delivery_charge(totaldeliverycharge);
            bookingdetails.setDelivery_amount(deliverycharge);
            bookingdetails.setDeliveryPerson(deliveryPerson);
        }
        deliveryPerson.updateCurrentBooking(bookingdetails.getStarttime()); // Updating the delivery person
        System.out.println("Asssigned Delivery Executive : " + deliveryPerson.getDelivery_executive_name());


    }

    public static void main(String[] args) {

        //    Setting all the Deliverypersons beginning amt to zero in the list
        ArrayList<DeliveryPerson> beginningamt = new ArrayList<>();
        beginningamt.add(DE1);
        beginningamt.add(DE2);
        beginningamt.add(DE3);
        beginningamt.add(DE4);
        beginningamt.add(DE5);
        deliveryPersonAmt.put(0,beginningamt);
        allDeliveryAmts.add(0);

        int cust_id = 1;
        char restaurant = 'A';
        char dest_point = 'D';
        String time = "09:00";

        BookFoodDelivery booking = new BookFoodDelivery();

        booking.handleBooking(1,cust_id,restaurant,dest_point,time);
        booking.handleBooking(2,2,'B','A',"10:00");
        booking.handleBooking(3,3,'B','A',"10:10");
        booking.handleBooking(4,4,'D','C',"10:35");

//        HashMap<Character,String> check = new HashMap<>();
//        check.put('k',"anjksna");
//        check.put('a',"jvcbhdbdh");
//        check.put('g',"bshgvsgd");
//        for (HashMap.Entry<Character,String> entry : check.entrySet())
//            System.out.println("Key = " + entry.getKey() +
//                    ", Value = " + entry.getValue());
//        TreeSet<Integer> ts = new TreeSet<Integer>();
//        ts.add(1);
//        ts.add(4);
//        ts.add(4);
//        ts.add(3);
//        ts.add(5);
//        ts.add(11);
//        System.out.println(ts.higher(11));

    }
}

/*
    Notes:
    1. You have to handle the previous booking and delete them from trips
        \___ For this you can make the booking as the map or set and whenever a booking is made a check will be done to the old query or the first query to check wheather it is outdated.
        \___ Make the same booking as false (which means done) to avoid any future confusion.
    2. Bring finding the delivery exceutive back to handle booking function itself.
    3. Use handle delivery executive only to assign values and check other basic testcases.
    4 .You itself give book id based on indexes.
    5. Each location can have multiple customers. Think about queue restaurant data structure once more.
    6. Combined Orders will be counted as single trip So dont forget this for calculating allowance.
    7. Format the start time.
*/
