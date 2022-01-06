package mathesh;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class HandleTime {

    public static void convertTime(String time){
//        Simple Function to get out the time from the String
        char[] ch = time.toCharArray();
        StringBuilder meridiem = new StringBuilder(); // This consist of AM or PM
        String tmp = "";
        int hours = 0;
        int minutes = 0;
        char character;
        int position = 0;
//        Getting Hours
        while(position < 3){
            character = ch[position];
            if(character == '.'){
                break;
            }
            else{
                tmp = tmp + character;
            }
            position++;
        }
        hours = Integer.parseInt(tmp);
        tmp = "";
        position += 1; // TO get rid of . in the string
//        Getting Minutes
        while(position < 5){
            character = ch[position];
            if(character == 'A' || character == 'P' || character == 'a' || character == 'p'){
                break;
            }
            else{
                tmp = tmp + character;
            }
            position++;
        }
        tmp = tmp.trim();
        minutes = Integer.parseInt(tmp);
//        Getting Meridian
        while(position < ch.length){
            character = ch[position];
            meridiem.append(character);
            position++;
        }
        System.out.println("Hours " + hours);
        System.out.println("Minutes " + minutes);
        System.out.println("Meridiem " + meridiem);
    }

    public static String returnEndTime(String startTime){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime lt = LocalTime.parse(startTime);
//        Check AM or PM while returning
        String endTime = df.format(lt.plusMinutes(30));
        return endTime;
    }

    public static long getTimeDifference(String time1, String time2){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        long differenceInMinutes = 0;
        try {
            Date date1 = simpleDateFormat.parse(time1);
            Date date2 = simpleDateFormat.parse(time2);
            long differenceInMilliSeconds = Math.abs(date2.getTime() - date1.getTime());
            differenceInMinutes = (differenceInMilliSeconds / (60 * 1000)) % 60;
        }
        catch (Exception e){
            System.out.println("Unable to calculate time Difference");
        }
        return differenceInMinutes;
    }

    public static void main(String[] args) {
        convertTime("9.59 AM");
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
//        LocalTime lt = LocalTime.parse("09:59");
//        System.out.println(df.format(lt.minusMinutes(30)));
//        System.out.println(df.format(lt.plusMinutes(10)));
//        System.out.println(returnEndTime("09:59"));
        getTimeDifference("12:15","12:15");
    }
}
