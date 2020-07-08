import java.io.*;
import java.util.*;
import javax.swing.*;

public class SystemInfo
{
  public static void main(String[] args) throws IOException
  {
    String properties;
//    Scanner scan = new Scanner(properties);
//
//    for (int i=0; scan.hasNext(); i++)
//    {
//      System.out.println(scan.next());
//    }
//    FileWriter writeToFile = new FileWriter(fileName);
//    FileWriter addToFile = new FileWriter(fileName, true);
//    writeToFile.write(fileContents);
//    writeToFile.close();

//    Properties prop = new Properties(System.getProperties());
//    prop.list(System.out);
//    String s = "";
    System.out.println("You're running this file from " + System.getProperty("user.dir") + " using the program at " + System.getProperty("java.class.path"));
    System.out.println("OS name: " + System.getProperty("os.name") + " (version " + System.getProperty("os.version") + ")");
    System.out.println("OS Architecture: " + System.getProperty("os.arch"));
    System.out.println("Java Virtural Machine version: " + System.getProperty("java.vm.version"));
    System.out.println("Java runtime version: " + System.getProperty("java.runtime.version"));
    System.out.println("Country: " + System.getProperty("user.country"));
    System.out.println("Time Zone: " + System.getProperty("user.timezone"));
    System.out.println("Language: " + System.getProperty("user.language"));
    System.out.println("Username: " + System.getProperty("user.name"));
  }
}