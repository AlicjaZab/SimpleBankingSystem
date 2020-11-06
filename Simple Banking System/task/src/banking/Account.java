package banking;

import java.util.Scanner;

/**
 * Represents an account-
 * stores reference to clint's card and operations he can perform
 */
public class Account {
    Card card;
    Scanner scanner;

    public Account(Card card, Scanner scanner) {
        this.card = card;
        this.scanner = scanner;
    }

    /**
     * Allows logged customer to manage his account:
     * see balance, log out or exit
     * @return Returns true if just logged out, false if choosed to exit
     */
    public boolean manageAccount() {
        System.out.println("\nYou have successfully logged in!\n");
        while(true) {
            System.out.println("1. Balance" +
                    "\n2. Add Income" +
                    "\n3. Do transfer" +
                    "\n4. Close Account"+
                    "\n5. Log out" +
                    "\n0. Exit");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    readBalance();
                    continue;
                case "2":
                    addIncome();
                    continue;
                case "3":
                    doTransfer();
                    continue;
                case "4":
                    closeAccount();
                    System.out.println("\nThe account has been closed!\n");
                    return true;
                case "5":
                    System.out.println("\nYou have successfully logged out!\n");
                    return true;
                case "0":
                    return false;
                default:
                    System.out.println("\n" + "There is no such option!\n");
                    continue;
            }
        }
    }

    private void readBalance() {
        System.out.println("\n" + "Balance: " + card.getBalance() + "\n");
    }

    private void addIncome() {
        System.out.println("\nEnter Income: ");
        String income = scanner.nextLine();
        int inc = 0;
        try {
            inc = Integer.parseInt(income);
        }catch (NumberFormatException e) {
            System.out.println("Incorrect input.\n");
            return;
        }
        if(card.add_income(inc)){
            System.out.println("Income was added!\n");
        }else
            System.out.println("Failed to add income :(\n");

    }

    private void doTransfer() {
        System.out.println("\nEnter card number:");
        String number = scanner.nextLine();
        if(number.equals(card.getNumber())){
            System.out.println("You can't transfer money to the same account!\n");
            return;
        }
        if(number.charAt(15) != countChecksum(number)){
            System.out.println("Probably you made mistake in the card number. Please try again!\n");
            return;
        }
        Card other = card.findCardToTransfer(number);
        if (other == null) {
            System.out.println("Such a card does not exist.\n");
            return;
        }


        System.out.println("Enter how much money you want to transfer:");
        String amountS = scanner.nextLine();
        int amount = 0;
        try {
            amount = Integer.parseInt(amountS);
        }catch (NumberFormatException e) {
            System.out.println("Incorrect input.\n");
            return;
        }
        if(amount > card.getBalance()) {
            System.out.println("Not enough money!\n");
            return;
        }

        if(card.transferMoney(other, amount)) {
            System.out.println("Success!\n");
            return;
        }
        System.out.println("error");

    }

    private void closeAccount() {
        card.delete();
    }

    private char countChecksum(String number) {
        //generating checksum that passes Luhn algorythm
        int[] digits = new int[16];
        String[] digitsS = number.toString().split("");
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
        return (char) (checksum + '0');
    }

}
