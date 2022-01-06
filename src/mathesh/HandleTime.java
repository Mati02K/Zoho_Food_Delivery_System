package mathesh;
/*
    This is the Utility Class to perform some time operations in order for whole logic to flow correctly.
 */

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class HandleTime {

    public static final int DELIVERY_TIME = 30; // This is the Delivery Time mentioned taken by the delivery guy.

    //    Simple Function to get out the time from the String
    public static String convertTime(String time){
        String starttime = "";
        try {
            char[] ch = time.toCharArray();
            String meridiem = ""; // This consist of AM or PM
            String tmp = "";
            int hours = 0;
            int minutes = 0;
            char character;
            int position = 0;
    //        Getting Hours
            while (position < 3) {
                character = ch[position];
                if (character == '.') {
                    break;
                } else {
                    tmp = tmp + character;
                }
                position++;
            }
            hours = Integer.parseInt(tmp);
            tmp = "";
            position += 1; // TO get rid of . in the string
    //        Getting Minutes
            while (position < 5) {
                character = ch[position];
                if (character == 'A' || character == 'P' || character == 'a' || character == 'p') {
                    break;
                } else {
                    tmp = tmp + character;
                }
                position++;
            }
            tmp = tmp.trim();
            minutes = Integer.parseInt(tmp);
            tmp = "";
    //        Getting Meridian
            while (position < ch.length) {
                character = ch[position];
                tmp = tmp + character;
                position++;
            }
            tmp = tmp.trim();
            meridiem = tmp;
            if (Objects.equals(meridiem, "PM") || Objects.equals(meridiem, "pm")) {
                hours = hours + 12;
            }
//            Format the minutes and hours if they are single characters
            String h = (String.valueOf(hours).length() == 2)? String.valueOf(hours) : "0" + String.valueOf(hours);
            String m = (String.valueOf(minutes).length() == 2)? String.valueOf(minutes) : "0" + String.valueOf(minutes);
            starttime = h + ":" + m;
        }
        catch (Exception e){
            System.out.println("Please Enter in Specified Format");
        }
        return starttime;
    }

//    This function is used to calculate
    public static String returnEndTime(String startTime){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime lt = LocalTime.parse(startTime);
        String endTime = df.format(lt.plusMinutes(DELIVERY_TIME));
        return endTime;
    }

//    This time difference function is used to calculate all bookings within 15 min timeframe.
    public static long getTimeDifference(String time1, String time2){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        long differenceInMinutes = 0;
        try {
            Date date1 = simpleDateFormat.parse(time1);
            Date date2 = simpleDateFormat.parse(time2);
            long differenceInMilliSeconds = Math.abs(date2.getTime() - date1.getTime());
            long differenceInHours = (differenceInMilliSeconds / (60 * 60 * 1000)) % 24;
            differenceInMinutes = (differenceInMilliSeconds / (60 * 1000)) % 60;
            if(differenceInHours > 0){
                differenceInMinutes = differenceInMinutes + (differenceInHours * 60);
            }
        }
        catch (Exception e){
            System.out.println("Unable to calculate time Difference");
        }
        return differenceInMinutes;
    }

    //    This function is used to correct time while displaying
    public static String correctTime(String time){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime lt = LocalTime.parse(time);
        String endTime = df.format(lt.plusMinutes(15));
        return endTime;
    }
}
