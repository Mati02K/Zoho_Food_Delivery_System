/*
    This is the Main Class File which reads the input from input.txt file in the specified format and
    outputs in the output.txt file in the specified format.

    JUST RUN THIS FILE TO PERFORM ALL OPERATIONS
 */

package mathesh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class MainActivity {

    private static void writeToFile(String result){
        try {
            FileWriter myWriter = new FileWriter("output.txt");
            myWriter.write(result);
            myWriter.close();
            System.out.println("Successfully completed the task. Go and Check the output.txt file");
        }
        catch (IOException e) {
            System.out.println("Ouch !! An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int custid;
        char res;
        char des;
        String starttime;
        StringBuilder output = new StringBuilder();
        String result = "";
        BookFoodDelivery bookFoodDelivery = new BookFoodDelivery();
        bookFoodDelivery.doFirstInvocation(); // Initialize first five values
//        Read From File
        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
            int bookno = 1;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(Objects.equals(data, "Input:")){
                    custid = Integer.parseInt(myReader.nextLine());
                    res = myReader.nextLine().charAt(0);
                    des = myReader.nextLine().charAt(0);
                    starttime = myReader.nextLine();
                    result = bookFoodDelivery.handleBooking(bookno,custid,res,des,starttime);
                    output.append(result);
                    output.append("\n");
                    bookno++;
                }
                else if(Objects.equals(data, "GET OUTPUT")) {
                    output.append(bookFoodDelivery.displayActivities());
                    writeToFile(String.valueOf(output));
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
