import sun.misc.Signal;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Signal.handle(new Signal("INT"), (o1)-> {
            System.out.println("\nAborted by user, x has not been provided");
            System.exit(-2);
        });
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Testing: ");
            System.out.println("1. f finishes before g with non-zero value");
            System.out.println("2. g finishes before f with non-zero value");
            System.out.println("3. f finishes zero, g hangs");
            System.out.println("4. g finishes zero, f hangs");
            System.out.println("5. f finishes non-zero value, f hangs");
            System.out.println("6. g finishes non-zero value, f hangs");
            int choice = scanner.nextInt();
            if (choice > 6 || choice < 1){
                System.exit(0);
            }
            Server server =  new Server(choice);
            server.start();
        }
    }
}
