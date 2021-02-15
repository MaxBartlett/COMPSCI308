package menu;

import engine.backend.Commands.Command;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineMenu extends Menu {

    public CommandLineMenu(List<Command> options){
        super(options);
    }

    @Override
    public List<Command> getChoices() {
        for(int i = 0; i < myOptions.size(); i ++){
            System.out.println(i + ". " + myOptions.get(i).getName());
        }
        System.out.print("Your choice: ");

        Scanner in = new Scanner(System.in);
//        String choice = console.readLine();
        int choiceIndex = in.nextInt();
        var choiceList = new ArrayList();
        choiceList.add(myOptions.get(choiceIndex));
        return choiceList;
    }
}
