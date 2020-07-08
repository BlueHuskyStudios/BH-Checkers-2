import java.io.*;
import javax.naming.directory.InitialDirContext;
import java.util.*;

public class Setup
{
  public static void main(String[] args) throws IOException
  {
    //String thisFile = "" + File.toString(this);
    File file =  new File("run.bat");
    String uri = file.toURI() + "";

    Properties prop = new Properties(System.getProperties());
//    String dir = uri.substring(8, (uri.length() - file.toString().length()));
    String dir = System.getProperty("user.dir");

    String fileContents = "@echo off\r\necho Starting Program...\r\n";
    fileContents += "CD \"" + dir + "\\bin\"\r\njava Checkers\r\n";

    for (int i=0; i < fileContents.length() - 3; i++)
    {
//      System.out.print(fileContents.substring(i, i + 3) + ".equals(\"%20\") ? ");
      if (fileContents.substring(i, i + 3).equals("%20"))
        System.err.println("problem creating run.bat. Fixing...");
//      else
//        System.out.println(false);

//      System.out.print(fileContents.substring(i, i + 1) + ".equals(\"/\") ? ");
      if (fileContents.substring(i, i + 1).equals("/"))
        System.err.println("problem creating run.bat. Fixing...");
//      else
//        System.out.println(false);

      if (fileContents.substring(i, i + 3).equals("%20"))
        fileContents = fileContents.substring(0, i) + " " + fileContents.substring(i + 3, fileContents.length());
      if (fileContents.substring(i, i + 1).equals("/"))
        fileContents = fileContents.substring(0, i) + (System.getProperty(file.separator)) + fileContents.substring(i + 1, fileContents.length());
    }

//    System.err.print(fileContents);
    FileWriter writeToFile = new FileWriter("run.bat");
    writeToFile.write(fileContents);
    writeToFile.close();
  }
}