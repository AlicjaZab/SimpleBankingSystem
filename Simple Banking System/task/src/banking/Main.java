package banking;

import java.io.StringWriter;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static Scanner scanner;
    static CardDAO cardDAO;

    /**
     * Connects with database provided int the argument,
     * executes the application loop
     * @param args name (and path) of a file with database
     */
    public static void main(String[] args) {

        scanner = new Scanner(System.in);
        cardDAO = new CardDAO();
        cardDAO.init(args[1]);

        while(true) {
            System.out.println("1. Create an account" +
                    "\n2. Log into account" +
                    "\n0. Exit");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    Card newAccount = createNewCard();
                    System.out.println("\nYour card has been created");
                    System.out.println("Your card number:\n" + newAccount.getNumber());
                    System.out.println("Your card PIN:\n" + newAccount.getPin() + "\n");
                    continue;
                case "2":
                    System.out.println("\nEnter your card number:");
                    String cardNumber = scanner.nextLine();
                    System.out.println("Enter your PIN:");
                    String pin = scanner.nextLine();

                    Card existingAccount = cardDAO.findCardByNumberAndPin(cardNumber, pin);

                    if (existingAccount == null) {
                        System.out.println("\nWrong card number or PIN!\n");
                        continue;
                    } else {
                        if(new Account(existingAccount, scanner).manageAccount()){

                            continue;
                        } else {
                            return;
                        }
                    }
                case "0":
                    System.out.println("\n" + "Bye!");
                    return;
                default:
                    System.out.println("\n" + "There is no such option!\n");
                    continue;
            }
        }

    }


    /**
     * Generates new unique card and saves it to database
     * @return created card
     */
    static Card createNewCard() {
        StringWriter cardNumber = new StringWriter();
        Random random = new Random();

        cardNumber.append("400000");

        String number = null;

        boolean unique = false;

        while(!unique) {

            number = String.format("%09d", random.nextInt(1000000000));

            unique = cardDAO.checkIfUnique(number);
        }

        cardNumber.append(number);

        //generating checksum that passes Luhn algorythm
        int[] digits = new int[15];
        String[] digitsS = cardNumber.toString().split("");
        for (int i = 0; i < 15; i++) {
            digits[i] = Integer.parseInt(digitsS[i]);
        }
        for (int i = 0; i < 15; i += 2) {
            digits[i] *= 2;
        }
        for (int i = 0; i < 15; i++) {
            if (digits[i] > 9) digits[i] -= 9;
        }
        int sum = 0;
        for (int i = 0; i < 15; i++) {
            sum += digits[i];
        }
        int checksum = 0;
        for (int i = 0; i < 10; i++) {
            if ((sum + i) % 10 == 0){
                checksum = i;
                break;
            }
        }

        cardNumber.append(Integer.toString(checksum));

        String pin = String.format("%04d",random.nextInt(10000));

        cardDAO.addCard(cardNumber.toString(), pin);
        return cardDAO.findCardByNumberAndPin(cardNumber.toString(), pin);
    }
}
