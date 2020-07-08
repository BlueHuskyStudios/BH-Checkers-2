public class GameBoard
{
  public static String print(int size, String[][] board)
  {
    String square = "";
    boolean bigCol = false;
    char col = 'A';
    char col2 = 'A';
    int row = 1;

    square += ("\n\n  ");
    for (int i=0; i < size; i++)
    {
      if (bigCol)
        square +=(col2);
      else
        square +=(" ");

      square +=(col);
      col++;

      if (col > 'Z' && !bigCol)
      {
        bigCol = true;
        col = 'A';
      }
      if (col > 'Z' && bigCol)
      {
        col = 'A';
        col2++;
      }
      square +=("  ");
    }
    square +=("\n");

    for (int i=0; i < size; i++)
    {
      if (i < 10)
        square += (" " + (i + 1));
      else
        square += (i + 1);

      for (int j=0; j < size; j++)
      {
        square += (" " + board[i][j] + " ");
        if (j < (size - 1))
          square += ("|");
      }
      square +=("\n");

      if (i < 10)
        square += ("  ");
      else
        square += (" ");

      if (i < (size - 1))
      {
        for (int j=0; j < size; j++)
        {
          square += ("---");
          if (j < size - 1)
            square += ("+");
        }
        square +=("\n");
      }
    }
    return square;
  }

  public static String print(int size, char[][] board)
  {
    String square = "";
    boolean bigCol = false;
    char col = 'A';
    char col2 = 'A';
    int row = 1;

    square += "\n\n  ";
    for (int i=0; i < size; i++)
    {
      if (bigCol)
        square +=(col2);
      else
        square +=(" ");

      square +=(col);
      col++;

      if (col > 'Z' && !bigCol)
      {
        bigCol = true;
        col = 'A';
      }
      if (col > 'Z' && bigCol)
      {
        col = 'A';
        col2++;
      }
      square +=("  ");
    }
    square +=("\n");

    for (int i=0; i < size; i++)
    {
      if (i < 10)
        square += (" " + (i + 1));
      else
        square += (i + 1);

      for (int j=0; j < size; j++)
      {
        square += (" " + board[i][j] + " ");
        if (j < (size - 1))
          square += ("|");
      }
      square +=("\n");

      if (i < 10)
        square += ("  ");
      else
        square += (" ");

      if (i < (size - 1))
      {
        for (int j=0; j < size; j++)
        {
          square += ("---");
          if (j < size - 1)
            square += ("+");
        }
        square +=("\n");
      }
    }
    return square;
  }

  public static String print(int size, int[][] board)
  {
    String square = "";
    boolean bigCol = false;
    char col = 'A';
    char col2 = 'A';
    int row = 1;

    square += "\n\n  ";
    for (int i=0; i < size; i++)
    {
      if (bigCol)
        square +=(col2);
      else
        square +=(" ");

      square +=(col);
      col++;

      if (col > 'Z' && !bigCol)
      {
        bigCol = true;
        col = 'A';
      }
      if (col > 'Z' && bigCol)
      {
        col = 'A';
        col2++;
      }
      square +=("  ");
    }
    square +=("\n");

    for (int i=0; i < size; i++)
    {
      if (i < 10)
        square += (" " + (i + 1));
      else
        square += (i + 1);

      for (int j=0; j < size; j++)
      {
        square += (" " + board[i][j] + " ");
        if (j < (size - 1))
          square += ("|");
      }
      square +=("\n");

      if (i < 10)
        square += ("  ");
      else
        square += (" ");

      if (i < (size - 1))
      {
        for (int j=0; j < size; j++)
        {
          square += ("---");
          if (j < size - 1)
            square += ("+");
        }
        square +=("\n");
      }
    }
    return square;
  }

  public static String miniPrint(String[][] board)
  {
    String square = "";

    square += ("\n\n ");

    for (int i=0; i < board.length; i++)
    {
      square += (char)(i + 65);
    }

    square += "\r\n";

    for (int i=0; i < board.length; i++)
    {
      square += i + 1;
      for (int j=0; j < board.length; j++)
      {
        square += board[i][j];
      }
      square += "\r\n";
    }

    return square;
  }

  public static String miniPrint(char[][] board)
  {
    String square = "";

    square += ("\n\n ");

    for (int i=0; i < board.length; i++)
    {
      square += (char)(i + 65);
    }

    square += "\r\n";

    for (int i=0; i < board.length; i++)
    {
      square += i + 1;
      for (int j=0; j < board.length; j++)
      {
        square += board[i][j];
      }
      square += "\r\n";
    }

    return square;
  }

  public static String miniPrint(int[][] board)
  {
    String square = "";

    square += ("\n\n ");

    for (int i=0; i < board.length; i++)
    {
      square += (char)(i + 65);
    }

    square += "\r\n";

    for (int i=0; i < board.length; i++)
    {
      square += i + 1;
      for (int j=0; j < board.length; j++)
      {
        square += board[i][j];
      }
      square += "\r\n";
    }

    return square;
  }
}