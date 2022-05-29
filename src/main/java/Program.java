import ru.model.Parser;

import java.util.Scanner;

public class Program {


    public static void main(String[] args) {
        if (args != null && args.length != 0) {
            Parser.parse(args[0], args[1]);
        }
        else System.out.println("Параметр не указан.");
    }
}




