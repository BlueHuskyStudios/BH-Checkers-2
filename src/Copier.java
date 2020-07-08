import java.awt.datatransfer.*;
import java.awt.*;

public class Copier
{
  public static void copy(String input)
  {
    Transferable output = new StringSelection(input);

    Toolkit tk = Toolkit.getDefaultToolkit();
    ClipboardOwner owner = null;
    Clipboard copy = tk.getSystemClipboard();
    copy.setContents(output, owner);
  }

  public static void copy(int input)
  {
    Transferable output = new StringSelection(input + "");

    Toolkit tk = Toolkit.getDefaultToolkit();
    ClipboardOwner owner = null;
    Clipboard copy = tk.getSystemClipboard();
    copy.setContents(output, owner);
  }

  public static void copy(double input)
  {
    Transferable output = new StringSelection(input + "");

    Toolkit tk = Toolkit.getDefaultToolkit();
    ClipboardOwner owner = null;
    Clipboard copy = tk.getSystemClipboard();
    copy.setContents(output, owner);
  }

  public static void copy(long input)
  {
    Transferable output = new StringSelection(input + "");

    Toolkit tk = Toolkit.getDefaultToolkit();
    ClipboardOwner owner = null;
    Clipboard copy = tk.getSystemClipboard();
    copy.setContents(output, owner);
  }

  public static void copy(float input)
  {
    Transferable output = new StringSelection(input + "");

    Toolkit tk = Toolkit.getDefaultToolkit();
    ClipboardOwner owner = null;
    Clipboard copy = tk.getSystemClipboard();
    copy.setContents(output, owner);
  }

  public static void copy(char input)
  {
    Transferable output = new StringSelection(input + "");

    Toolkit tk = Toolkit.getDefaultToolkit();
    ClipboardOwner owner = null;
    Clipboard copy = tk.getSystemClipboard();
    copy.setContents(output, owner);
  }

  public static void copy(byte input)
  {
    Transferable output = new StringSelection(input + "");

    Toolkit tk = Toolkit.getDefaultToolkit();
    ClipboardOwner owner = null;
    Clipboard copy = tk.getSystemClipboard();
    copy.setContents(output, owner);
  }
}