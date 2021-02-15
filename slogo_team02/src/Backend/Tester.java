package Backend;


public class Tester {//Make into Junits

    public static void main(String[] args) {
        CommandManager manager = new CommandManager("languages.English");

        String line1 = "ifelse 0 [ if 1 [ sum 1 2 ] ] [ if 1 [ sum 3 4 ] ] ";
        //String line2 = "bk";
        String out1 = manager.execute(line1);
        //String out2 = manager.execute(line2);
    }
}
