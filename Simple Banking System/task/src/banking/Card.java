package banking;

/**
 * Represents a card
 */
public class Card {

    private String number;
    private String pin;
    private int balance;
    CardDAO cardDAO;

    Card(CardDAO cardDAO, String card_number, String pin, int balance) {
        this.number = card_number;
        this.pin = pin;
        this.balance = balance;
        this.cardDAO = cardDAO;
    }

    Card(CardDAO cardDAO, String card_number, String pin) {
        this.number = card_number;
        this.pin = pin;
        balance = 0;
        this.cardDAO = cardDAO;
    }

    public String getNumber() {
        return number;
    }

    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public boolean add_income(int income) {
        cardDAO.addIncome(income, number);
        Integer balance = cardDAO.getBalance(number);
        if (balance == null)
            return false;
        else{
            this.balance = balance;
            return  true;
        }
    }

    public Card findCardToTransfer(String number) {
        return cardDAO.findCardByNumber(number);
    }

    public boolean transferMoney(Card other, int amount) {
        other.add_income(amount);
        add_income(-amount);
        return true;
    }

    public void delete() {
        cardDAO.deleteCard(number);
    }

}
