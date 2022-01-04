package mathesh;

public class HandleTime {

    public static String addTime(int hours1, int hours2, int minutes1, int minutes2, int seconds1, int seconds2){
        int totalHours = hours1 + hours2;
        int totalMinutes = minutes1 + minutes2;
        int totalSeconds = seconds1 + seconds2;
        if (totalSeconds >= 60) {
            totalMinutes ++;
            totalSeconds = totalSeconds % 60;
        }
        if (totalMinutes >= 60) {
            totalHours ++;
            totalMinutes = totalMinutes % 60;
        }
        return String.valueOf(totalHours) + " : " + String.valueOf(totalMinutes) + " : " + String.valueOf(totalSeconds);
    }
}
