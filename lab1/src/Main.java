import sun.misc.Signal;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        while (true){
            Signal.handle(new Signal("INT"), (o1)-> {
                System.out.println("\nUser cancellation. x was not written");
                System.exit(0);
            });
            System.out.println("Testing: ");
            System.out.println("0. f finishes before g with non-zero value");
            System.out.println("1. g finishes before f with non-zero value");
            System.out.println("2. f finishes zero, g hangs");
            System.out.println("3. g finishes zero, f hangs");
            System.out.println("4. f finishes non-zero value, g hangs. Check cancellation.");
            System.out.println("5. g finishes non-zero value, f hangs. Check cancellation.");
            int choice = scanner.nextInt();
            if (choice > 5 || choice < 0){
                System.exit(0);
            }
            Server server =  new Server(choice);
            Signal.handle(new Signal("INT"), (o2)-> {
                System.out.println("\nUser cancellation.");
                try {
                    server.showResult();
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            });

            server.start();
            server.showMultiplication();
            server.close();
        }
    }
}
