package checkBankAccount;


import java.time.LocalDate;
import java.util.Scanner;
import java.util.function.Consumer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Adel
 */
public class CheckBankAccount {

    /**
     * read input from terminal
     *
     */
    public static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Handle operations
     */
    public static enum Operation {
        WRITE_CHECK("1- Write Check"),
        DEPOSIT_AMOUNT("2- Deposit amount"),
        UPDATE_INFORMATION("3- Update Information"),
        BALANCE_INQUIRY("4- Balance Inquiry"),
        QUIT("5- Quit"),
        NONE("0- none");

        String value;

        Operation(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getIndex() {
            return Integer.parseInt(value.split(Constants.SEPARATOR)[Constants.FIRST]);
        }

        public static Operation getOperation(int index) {
            for (Operation operation : Operation.values()) {
                if (index == operation.getIndex()) {
                    return operation;
                }
            }
            return Operation.NONE;
        }
    }

    /**
     * Declare constants
     */
    public static class Constants {

        //BUSINESS
        public static final String OPERATIONS = "Operations";
        public static final String SELECT_TRANSACTION = "Select Transaction: ";
        public static final String OPENING_CHECKING_ACCOUNT = "Opening a checking account:";
        public static final String NAME_OF_DEPOSITOR = "Name of depositor: ";
        public static final String UPDATE_NAME_OF_DEPOSITOR = "Update Name of depositor: ";
        public static final String NAME = "Name: ";
        public static final String AMOUNT = "Amount: %.2f";
        public static final String DATE_OF_TRANSACTION = "Date of transaction: ";
        public static final String STARTING_BALANCE = "Starting Balance: %.2f";
        public static final String CURRENT_BALANCE = "Current balance: %.2f";
        public static final String THANK_YOU_FOR_TRANSACTING_WITH_US = "Thank you for transacting with us.";
        //LOGIC        
        public static final String SEPARATOR = "-";
        public static final String SPACE = " ";
        public static final String EMPTY = "";
        public static final int FIRST = 0;
        public static final double STARTING_BALANCE_VALUE = 0.00;
        public static final int MINUS_ONE = -1;

        //MESSAGES
        public static final String ENTER_INITIAL_BALANCE_AMOUNT = "Enter initial balance amount: ";
        public static final String ENTER_AMOUNT_TO_SUBSTRACT = "Enter amount to substract: ";
        public static final String ENTER_AMOUNT_TO_DEPOSIT = "Enter amount to deposit: ";
        public static final String YOU_SHOULD_ENTER_A_FLOATING_POINT_NUMBER = "You should enter a floating point number";
        public static final String YOU_SHOULD_ENTER_AN_INTEGER_NUMBER = "You should enter an integer number";

    }

    /**
     * Business client
     */
    public static class Client {

        private String name;
        private double startingBalance;
        private double currentBalance;

        public Client() {
            // do nothing
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getStartingBalance() {
            return startingBalance;
        }

        public void setStartingBalance(double startingBalance) {
            this.startingBalance = startingBalance;
        }

        public double getCurrentBalance() {
            return currentBalance;
        }

        public void setCurrentBalance(double currentBalance) {
            this.currentBalance = currentBalance;
        }
    }

    /**
     * Creates an initial client with balance account based on input data
     *
     * @return a client
     */
    public Client getClient() {
        CheckBankAccount.Client client = new Client();
        System.out.println(Constants.OPENING_CHECKING_ACCOUNT);

        System.out.print(Constants.NAME_OF_DEPOSITOR);
        client.setName(CheckBankAccount.SCANNER.nextLine());

        printTransactionDate();

        System.out.print(Constants.ENTER_INITIAL_BALANCE_AMOUNT);
        double initBalance = getAmount();
        client.setStartingBalance(initBalance);
        client.setCurrentBalance(initBalance);
        System.out.printf(Constants.STARTING_BALANCE, initBalance);
        System.out.println(Constants.EMPTY);
        System.out.printf(Constants.CURRENT_BALANCE, initBalance);
        System.out.println(Constants.EMPTY);
        return client;
    }

    /**
     * Get shows allowed operations and retrieves the selected one
     *
     * @return the operation selected
     */
    public Operation getOperation() {
        System.out.println(Constants.OPERATIONS);
        System.out.println(Operation.WRITE_CHECK.getValue());
        System.out.println(Operation.DEPOSIT_AMOUNT.getValue());
        System.out.println(Operation.UPDATE_INFORMATION.getValue());
        System.out.println(Operation.BALANCE_INQUIRY.getValue());
        System.out.println(Operation.QUIT.getValue());
        System.out.print(Constants.SELECT_TRANSACTION);
        return Operation.getOperation(getOperationIndex());
    }

    private static void printTransactionDate() {
        System.out.println(Constants.DATE_OF_TRANSACTION + Constants.SPACE + LocalDate.now());
    }

    private static double getAmount() {
        while (true) {
            try {
                return Double.parseDouble(SCANNER.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println(Constants.YOU_SHOULD_ENTER_A_FLOATING_POINT_NUMBER);
            }
        }
    }

    private static int getOperationIndex() {
        try {
            return Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println(Constants.EMPTY);
            System.out.println(Constants.YOU_SHOULD_ENTER_AN_INTEGER_NUMBER);
        }
        return Operation.NONE.getIndex();
    }

    /**
     * Write check
     *
     * @param client the client
     */
    public void writeCheck(Client client) {
        printCommonHelper(client, c -> {
            System.out.print(Constants.ENTER_AMOUNT_TO_SUBSTRACT);
            amountCommonsHelper(c, getAmount() * Constants.MINUS_ONE);
        });
    }

    /**
     * Deposit
     *
     * @param client the client
     */
    public void deposit(Client client) {
        printCommonHelper(client, c -> {
            System.out.print(Constants.ENTER_AMOUNT_TO_DEPOSIT);
            amountCommonsHelper(c, getAmount());
        });
    }

    private static void amountCommonsHelper(Client client, double amount) {
        client.setCurrentBalance(client.getCurrentBalance() + amount);
        System.out.printf(Constants.AMOUNT, amount);
        System.out.println(Constants.EMPTY);
        System.out.printf(Constants.CURRENT_BALANCE, client.getCurrentBalance());
    }

    /**
     * Update
     *
     * @param client the client
     */
    public void update(Client client) {
        printCommonHelper(client, c -> {
            System.out.println(Constants.UPDATE_NAME_OF_DEPOSITOR + c.getName());
            System.out.print(Constants.NAME_OF_DEPOSITOR);
            client.setName(CheckBankAccount.SCANNER.nextLine());
            System.out.printf(Constants.CURRENT_BALANCE, c.getCurrentBalance());
        });
    }

    /**
     * Display current balance
     *
     * @param client the client
     */
    public void displayCurrentBalance(Client client) {
        printCommonHelper(client, c -> {
            System.out.println(Constants.NAME + c.getName());
            System.out.printf(Constants.CURRENT_BALANCE, c.getCurrentBalance());
        });
    }

    public void printCommonHelper(Client client, Consumer<Client> consumer) {
        System.out.println(Constants.EMPTY);
        printTransactionDate();
        consumer.accept(client);
        System.out.println(Constants.EMPTY);
    }

    public static void main(String[] args) {
        CheckBankAccount checkBankAccount = new CheckBankAccount();
        Client client = checkBankAccount.getClient();
        boolean isWorking = true;
        while (isWorking) {
            System.out.println(CheckBankAccount.Constants.EMPTY);
            Operation operation = checkBankAccount.getOperation();
            switch (operation) {
                case WRITE_CHECK:
                    checkBankAccount.writeCheck(client);
                    break;
                case DEPOSIT_AMOUNT:
                    checkBankAccount.deposit(client);
                    break;
                case UPDATE_INFORMATION:
                    checkBankAccount.update(client);
                    break;
                case BALANCE_INQUIRY:
                    checkBankAccount.displayCurrentBalance(client);
                    break;
                case QUIT:
                    isWorking = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println(CheckBankAccount.Constants.EMPTY);
        System.out.println(CheckBankAccount.Constants.EMPTY);
        System.out.println(CheckBankAccount.Constants.EMPTY);
        System.out.println(Constants.THANK_YOU_FOR_TRANSACTING_WITH_US);
    }

}
