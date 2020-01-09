//A simple class which is supposed to model a basic functionality of a coffee maker.
// The point of the exercise was to create a single method to accept all of program's input.

import java.util.Scanner;

public class CoffeeMachine {
    private static int water = 400;
    private static int milk = 540;
    private static int coffeeBeans = 120;
    private static int cups = 9;
    private static int money = 550;
    private static Scanner input = new Scanner(System.in);
    private MachineState machineState = MachineState.CHOOSE_ACTION;

    public static void perform() {
        while (this.machineState != MachineState.EXIT) {
            System.out.print(machineState.text);
            takeInput(input.nextLine());
        }
    }

    private void takeInput(String input) {
        switch (this.machineState) {
            case CHOOSE_ACTION:
                switch (input) {
                    case "buy":
                        this.machineState = MachineState.CHOOSE_COFFEE;
                        break;
                    case "fill":
                        this.machineState = MachineState.FILL_WATER;
                        break;
                    case "take":
                        System.out.println("I gave you $" +money+"\n");
                        money = 0;
                        break;
                    case "remaining":
                        displayStatus();
                        break;
                    case "exit":
                        machineState = MachineState.EXIT;
                        break;
                }
                break;
            case CHOOSE_COFFEE:
                this.machineState = MachineState.CHOOSE_ACTION;
                switch (input) {
                    case "1":
                        checkResources(250, 0, 16, 4);
                        break;
                    case "2":
                        checkResources(350, 75, 20, 7);
                        break;
                    case "3":
                        checkResources(200, 100, 12, 6);
                        break;
                    case "back":
                        break;
                }
                break;
            case FILL_WATER:
                this.machineState = MachineState.FILL_MILK;
                water += Integer.parseInt(input);
                break;
            case FILL_MILK:
                this.machineState = MachineState.FILL_COFFEE_BEANS;
                milk += Integer.parseInt(input);
                break;
            case FILL_COFFEE_BEANS:
                this.machineState = MachineState.FILL_CUPS;
                coffeeBeans += Integer.parseInt(input);
                break;
            case FILL_CUPS:
                this.machineState = MachineState.CHOOSE_ACTION;
                cups += Integer.parseInt(input);
                break;
            case EXIT:
                break;
        }
    }

    private void displayStatus() {
        System.out.println("The coffee machine has:\n"+
                water + " of water\n"+
                milk +" of milk\n" +
                coffeeBeans + " of coffee beans\n" +
                cups + " of disposable cups\n" +
                money + " of money\n");
    }

    private void checkResources(int water_, int milk_, int coffeeBeans_, int cost) {
        if (cups==0){
            System.out.println("Sorry, not enough disposable cups!");
        }else if (water_>water) {
            System.out.println("Sorry, not enough water!");
        } else if (milk_>milk) {
            System.out.println("Sorry, not enough milk!");
        } else if (coffeeBeans_>coffeeBeans) {
            System.out.println("Sorry, not enough coffee beans!");
        } else {
            System.out.println("I have enough resources, making you a coffee!");
            cups -=1;
            water -= water_;
            milk -= milk_;
            coffeeBeans -= coffeeBeans_;
            money += cost;
        }
    }

    enum MachineState {
        FILL(""),
        FILL_WATER("Write how many ml of water do you want to add: "),
        FILL_MILK("Write how many ml of milk do you want to add: "),
        FILL_COFFEE_BEANS("Write how many grams of coffee beans do you want to add: "),
        FILL_CUPS("Write how many disposable cups of coffee do you want to add: "),
        CHOOSE_COFFEE("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: "),
        CHOOSE_ACTION("Write action (buy, fill, take, remaining, exit): "),
        EXIT("");

        String text;

        MachineState(String text) {
            this.text = text;
        }
    }
}

