import java.io.*;
import java.util.*;

//A simple command line program for creating term-definition pairs (flashcards) and allows for (de)serialization.
// It keeps track of every line of input/output by adding those to an arraylist named log.  
 
public class Main {
    static ArrayList<String> log = new ArrayList<>();
    static String msg;
    static ArrayList<FlashCard> deck = new ArrayList<>();
    static String definition;
    static String term;
    static FlashCard randomCard;
    static int howManyTimes;
    static boolean cardExists;
 
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length>0) {
            if (args[0].equals("-import")) {
                try {
                    FileInputStream file = new FileInputStream(args[1]);
                    ObjectInputStream instr = new ObjectInputStream(file);
                    deck = (ArrayList<FlashCard>) instr.readObject();
                    instr.close();
                    file.close();
                    msg = deck.size() + " cards have been loaded.";
                    System.out.println(msg);
                    log.add(msg);
                } catch (FileNotFoundException e) {
                    msg = "File not found.";
                    System.out.println(msg);
                    log.add(msg);
                }
            } else if (args[0].equals("-export")) {
                FileOutputStream file = new FileOutputStream(args[1]);
                ObjectOutputStream outstr = new ObjectOutputStream(file);
                outstr.writeObject(deck);
                outstr.close();
                file.close();
                msg = deck.size() + " cards have been saved.";
                System.out.println(msg);
                log.add(msg);
            }
        }
 
 
        Scanner input = new Scanner(System.in);
        String answer = "";
 
        while (!answer.equals("exit")) {
            msg = "Input the action (add, remove, import, export, ask, exit," +
                    " log, hardest card, reset stats):";
            System.out.println(msg);
            log.add(msg);
 
            answer = input.nextLine();
            log.add(answer);
 
            switch (answer.toLowerCase()) {
                case "add":
                    cardExists = false;
                    msg = "The card:";
                    System.out.println(msg);
                    log.add(msg);
                    term = input.nextLine();
                    log.add(term);
                    for (FlashCard flashCard : deck) {
                        if (flashCard.getTerm().equalsIgnoreCase(term.trim())) {
                            msg = "The card \"" + term + "\" already exists";
                            System.out.println(msg);
                            log.add(msg);
                            cardExists = true;
                        }
                    }
                    if (cardExists) {
                        break;
                    }
                    msg = "The definition of the card:";
                    System.out.println(msg);
                    log.add(msg);
                    definition = input.nextLine();
                    log.add(definition);
                    for (FlashCard flashCard : deck) {
                        if (flashCard.getDefinition().equalsIgnoreCase(definition.trim())) {
                            msg = "The definition \"" + definition + "\" already exists";
                            System.out.println(msg);
                            log.add(msg);
                            cardExists = true;
                        }
                    }
                    if (cardExists) {
                        break;
                    }
                    deck.add(new FlashCard(term, definition));
                    msg = "The pair (\"" +term+"\":\""+definition+"\") has been added.";
                    System.out.println(msg);
                    log.add(msg);
                    break;
                case "remove":
                    cardExists = false;
                   msg = "The card:";
                    System.out.println(msg);
                    log.add(msg);
                    term = input.nextLine();
                    log.add(term);
                    for (Iterator<FlashCard> it=deck.iterator(); it.hasNext();) {
                        FlashCard flashCard = it.next();
                        if (flashCard.getTerm().equalsIgnoreCase(term.trim())) {
                            cardExists = true;
                            it.remove();
                            msg = "The card has been removed.";
                            System.out.println(msg);
                            log.add(msg);
                        }
                    }
                    if (!cardExists) {
                        msg = "Can't remove \""+term+"\": there is no such card.";
                        System.out.println(msg);
                        log.add(msg);
                    }
                    break;
                case "import":
                    msg = "File name:";
                    System.out.println(msg);
                    log.add(msg);
                    msg = input.nextLine();
                    log.add(msg);
                    try {
                        FileInputStream file = new FileInputStream(msg);
                        ObjectInputStream instr = new ObjectInputStream(file);
                        ArrayList<FlashCard> importedDeck = (ArrayList<FlashCard>) instr.readObject();
                        instr.close();
                        file.close();
                        for (FlashCard importedFlashCard : importedDeck) {
                            deck.removeIf(flashCard ->
                                    importedFlashCard.getTerm().equalsIgnoreCase(flashCard.getTerm()));
                            deck.add(importedFlashCard);
                        }
                        msg = importedDeck.size() + " cards have been loaded.";
                        System.out.println(msg);
                        log.add(msg);
                    } catch (FileNotFoundException e) {
                        msg = "File not found.";
                        System.out.println(msg);
                        log.add(msg);
                    }
                    break;
                case "export":
                    msg = "File name:";
                    System.out.println(msg);
                    log.add(msg);
                    String fileName = input.nextLine();
                    log.add(fileName);
                    FileOutputStream file = new FileOutputStream(fileName);
                    ObjectOutputStream outstr = new ObjectOutputStream(file);
                    outstr.writeObject(deck);
                    outstr.close();
                    file.close();
                    msg = deck.size() + " cards have been saved.";
                    System.out.println(msg);
                    log.add(msg);
                    break;
                case "ask":
                    msg = "How many times to ask?";
                    System.out.println(msg);
                    log.add(msg);
                    howManyTimes = input.nextInt();
                    log.add(Integer.toString(howManyTimes));
                    input.nextLine();
                    for (int i=0; i<howManyTimes; i++) {
                        randomCard = deck.get(new Random().nextInt(deck.size()));
                        msg = "Print the definition of \""+randomCard.getTerm()+"\":";
                        System.out.println(msg);
                        log.add(msg);
                        answer = input.nextLine();
                        log.add(answer);
                        if (answer.strip().equalsIgnoreCase(randomCard.getDefinition())) {
                            msg = "Correct answer.";
                            System.out.println(msg);
                            log.add(msg);
                        } else if (containsCardValue(deck, answer)){
                            randomCard.increaseErrorCount();
                            msg = "Wrong answer. The correct one is \""+randomCard.getDefinition()+"\", " +
                                    "you've just written the definition of \""+getTermByDefinition(deck, answer)+"\".";
                            System.out.println(msg);
                            log.add(msg);
 
                        } else {
                            randomCard.increaseErrorCount();
                            msg = "Wrong answer. The correct one is \""+randomCard.getDefinition()+"\".";
                            System.out.println(msg);
                            log.add(msg);
                        }
                    }
                    break;
                case "exit":
                    msg = "Bye bye!";
                    System.out.println(msg);
                    log.add(msg);
                    break;
                case "log":
                    msg = "File name:";
                    System.out.println(msg);
                    log.add(msg);
                    msg = input.nextLine();
                    log.add(msg);
                    FileOutputStream logFile = new FileOutputStream(msg);
                    ObjectOutputStream oboutstr = new ObjectOutputStream(logFile);
                    oboutstr.writeObject(log);
                    oboutstr.close();
                    logFile.close();
                    msg = "The log has been saved.";
                    System.out.println(msg);
                    log.add(msg);
                    break;
                case "hardest card":
                    getHighestErrorCountCard();
                    break;
                case "reset stats":
                    reset();
                    msg = "Card statistics has been reset.";
                    System.out.println(msg);
                    log.add(msg);
                    break;
 
                default:
                    System.out.println("Invalid input");
 
            }
        }
 
    }
 
    static void reset() {
        for (FlashCard flashCard : deck) {
            flashCard.resetErrorCount();
        }
    }

    static void getHighestErrorCountCard() {
        int highestErrorCount = 0;
        ArrayList<FlashCard> cards = new ArrayList<>();
 
        for (FlashCard flashCard : deck) {
            if (flashCard.getErrorCount()>highestErrorCount) {
                highestErrorCount = flashCard.getErrorCount();
            }
        }
        if (highestErrorCount==0) {
            msg = "There are no cards with errors.";
            System.out.println(msg);
            log.add(msg);
        } else {
            for (FlashCard flashCard : deck) {
                if (flashCard.getErrorCount() == highestErrorCount) {
                    cards.add(flashCard);
                }
            }
            if (cards.size()==1) {
                msg = "The hardest card is \""+cards.get(0).getTerm()+"\". " +
                        "You have " + cards.get(0).getErrorCount() + " errors answering it.";
                System.out.println(msg);
                log.add(msg);
            } else {
                String termsString = "";
                for (FlashCard flashCard : cards) {
                    termsString += "\"" +flashCard.getTerm() + "\", ";
                }
                termsString = termsString.substring(0, termsString.length()-2);
                msg = "The hardest cards are "+ termsString +
                        ". You have "+highestErrorCount+" errors answering them.";
                System.out.println(msg);
                log.add(msg);
            }
        }
    }
 
    static String getTermByDefinition(ArrayList<FlashCard> deck, String definition) {
        for (FlashCard flashCard : deck) {
            if (flashCard.getDefinition().equalsIgnoreCase(definition)) {
                return flashCard.getTerm();
            }
        }
        return null;
    }
 
    static boolean containsCardValue(ArrayList<FlashCard> deck, String definition) {
        for (FlashCard flashCard : deck) {
            if (flashCard.getDefinition().equalsIgnoreCase(definition)) {
                return true;
            }
        }
        return false;
    }
}
 
class FlashCard implements Serializable{
    private String term;
    private String definition;
    private int errorCount;
 
    public FlashCard(String term, String definition) {
        this.term = term;
        this.definition = definition;
        this.errorCount = 0;
    }
 
    public String getTerm() {
        return term;
    }
 
    public String getDefinition() {
        return definition;
    }
 
    public int getErrorCount() {
        return errorCount;
    }
 
    public void increaseErrorCount() {
        this.errorCount++;
    }
 
    public void resetErrorCount() {
        this.errorCount = 0;
    }
}