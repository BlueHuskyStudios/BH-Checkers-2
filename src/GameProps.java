public class GameProps
{
  public static String[][] gameProp = new String[][] {
      {"title", Main.trans("Blue Husky's Checkers", "Blue Husky's Steine", "Las Damas del Husky Azul")},
      {"version","2.1.10"}};


  public static String getProp(String key)
  {
    for (int i=0; i < gameProp.length; i++)
    {
      if (gameProp[i][0] == null ? key == null : gameProp[i][0].equals(key))
        return gameProp[i][1];
    }
    return "Property not found: " + key;
  }

  public static void main(String[] args)
  {
    System.out.println(GameProps.getProp("title") + ", version " + GameProps.getProp("version"));
  }
}