package mathesh;

import java.util.*;

// Main Class
public class BookFoodDelivery {

    public static final int ORDER_AMOUNT = 50;  // Total Order Amount
    public static final int SAME_ORDER_AMOUNT = 5; // Same Order Amount if the deliver destination is same.
    public static final int ALLOWANCE = 10; // Allowance Amount

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
    Creating 5 Basic Delivery Persons as per question
    But the same code will work for n number of delivery persons.
    In many persons case new Object can be created at the particular instance.
*/
    public static DeliveryPerson DE1 = new DeliveryPerson();
    public static DeliveryPerson DE2 = new DeliveryPerson();
    public static DeliveryPerson DE3 = new DeliveryPerson();
    public static DeliveryPerson DE4 = new DeliveryPerson();
    public static DeliveryPerson DE5 = new DeliveryPerson();

/*
    This Queue is also one of the important parts of the module.
    Here whenever a booking is done a queue (character) is added along with time (integer)
    This is used to lookup to the second constraint if the destination is same and the time limit is within 15 minutes.
*/
    public static Queue<HashMap<Character,Integer>> restaurantlist = new LinkedList<>();

    public static void setDeliverypersonamt(DeliveryPerson deliveryPerson, int current_amt){
        int amt = deliveryPerson.getTotal_delivery_charge();
        ArrayList<DeliveryPerson> amtPersons = new ArrayList<>();
        if(current_amt != amt){
            amtPersons = deliveryPersonAmt.get(amt);
            amtPersons.remove(deliveryPerson);
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
                deliveryPersonAmt.put(current_amt,amtPersons);
            }
        }

    }
    public static DeliveryPerson getLowestDeliveryPerson(){
//        Return the lowest Delivery Person by querying the hashmap along with treeset
        int lowestamt = allDeliveryAmts.first();
        ArrayList<DeliveryPerson> lowestDeliveryPerson = deliveryPersonAmt.get(lowestamt);
//        Return the first index of the lowest person
        return lowestDeliveryPerson.get(0);
    }

    public void handleBooking(int bookno, int cust_id, char res, char des, int time){
        Bookings bookingdetails = new Bookings(bookno,cust_id,res,des,time);
        assignDeliveryExecutive(bookingdetails);
    }

    public void assignDeliveryExecutive(Bookings bookingdetails){
        DeliveryPerson currentDeliver = getLowestDeliveryPerson();
//        setDeliverypersonamt(currentDeliver,);

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
        String time = "09:00 AM";

//        For getting the element based on the index in treeset
        TreeSet<Integer> check = new TreeSet<>();
        check.add(5);
        check.add(7);
        check.add(1);
        check.add(5);
        check.add(41);
        check.add(21);
        for(int element : check){
            System.out.println(element);
        }

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
*/
