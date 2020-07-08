import javax.swing.*;

public class YesNoBox
{
  public static boolean bool(String prompt)
  {
    int yn = JOptionPane.showConfirmDialog(null, prompt);
    //System.out.println("yn: " + yn);

    if (yn == 0)
      return true;

    return false;
  }

  public static boolean bool(String prompt, String title, int type)
  {
    int yn = JOptionPane.showConfirmDialog(null, prompt, title, 0, type);
    //System.out.println("yn: " + yn);

    if (yn == 0)
      return true;

    return false;
  }

  public static int integer(String prompt, String title, int type)
  {
    return JOptionPane.showConfirmDialog(null, prompt, title, 0, type);
  }

  public static int integer(String prompt)
  {
    return JOptionPane.showConfirmDialog(null, prompt);
  }
}