import com.sun.xml.internal.org.jvnet.fastinfoset.sax.FastInfosetReader;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.*;


public class WKTZip {
   private Scanner read;
   private File file;
   private PrintWriter writer;
   private int columns;

   //Constructor creates file
   WKTZip(String scanned, String fileName, int col){
        read = openNewScanner(scanned);
        columns = col;
        file = new File(fileName);
        //file.getParentFile().mkdirs();
       try {
           writer = new PrintWriter(file);
       }
       catch(IOException ie){
           ie.printStackTrace();
       }
   }

    Scanner openNewScanner (String file){
        try {
            System.out.println("Success");
            Scanner scan = new Scanner (new File(file));
            return scan;
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
        }
        return null;
    }

    void setDelimiter(Scanner inp, String deLim){
        inp.useDelimiter(deLim);
    }


    //Creates .txt file in WKT format (THE MEAT OF THE PROGRAM
    public void createWKT() {
        writer.println("id|wkt");

        this.setDelimiter(read, ",|\\n"); //sets delimiter to both ',' and '/n' (new line)

        for (int i = 0; i < columns; i++) { //loops through header row
            read.next();

        }

        while (read.hasNext()) { //Loops through files until eof
            String firstLong = "a";//Initializing values (not actually using "a")
            String firstLat = "a";
            String secLong = "a";
            String secLat = "a";

            for (int i = 0; i < columns; i++) { //Loops through
                if (i == 7) { //If at 7th item in row put first coordinate in firstLong variable
                    firstLong = read.next();
                }
                else if (i == 8) {//If at 8th item.....
                    firstLat = read.next();
                }
                else if (i == 11) {//If at 11th item.....
                    secLong = read.next();
                }
                else if (i == 12) {//If at 12th item....
                    secLat = read.next();
                }
                else{//else continue reading items of row
                    read.next();
                }
            }
            if(Double.parseDouble(firstLong) != 0 && Double.parseDouble(firstLat) != 0) {//If not bad values
                writer.println("LINESTRING(" + firstLong + " " + firstLat + "," + secLong + " " + secLat + ")");//Adds Line to txt file Should Look like "LINESTRING(10.32 44.23, 77.33 48.4729)" for example
            }

        }
    }


    //Main Function
    public static void main(String[] args) {
       WKTZip zipper;
       zipper = new WKTZip("/Users/williamnewman/Desktop/From100G.csv", "/Users/williamnewman/Desktop/taxi_lines.txt", 22);
       zipper.createWKT();

    }


}
