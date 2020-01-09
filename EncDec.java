/*A encryption/decrption program working from a command line.
It is designed to accept arguments:
-mode (enc or dec)
-key (what shift of characters you want)
-data (a string you want to encrypt/decrypt)
-alg (algorithm - either simple alphabetical shift of unicode shift)
-in (alternatively to providing a string you can get a text from a file - here you provide a directory)
-out (a directory of a file you want to store the result in) 
*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
 
public class EncDec {
    public static void main(String[] args) {
        AlgorithmFactory algorithmFactory = new AlgorithmFactory();
        Algorithm alg = algorithmFactory.chooseAlgorithm("shift");
        String operation = "enc";
        String text = "";
        int key = 0;
        String outFileName = "";
        for (int i=0; i<args.length; i++) {
            if (i<args.length-1) {
                switch (args[i]) {
                    case "-mode":
                        operation = args[i+1];
                        break;
                    case "-key":
                        key = Integer.parseInt(args[i+1]);
                        break;
                    case "-data":
                        text = args[i+1];
                        break;
                    case "-in":
                        if (text.isEmpty()) {
                            try {
                                text = new String(Files.readAllBytes(Paths.get(args[i+1])));
                            } catch (IOException e) {
                                System.out.println("Cannot read file" + args[i+1]);
                            }
                        }
                        break;
                    case "-out":
                        outFileName = args[i+1];
                        break;
                    case "-alg":
                        alg = algorithmFactory.chooseAlgorithm(args[i+1]);
                        break;
                }
            }
        }
 
        Context context = new Context(alg);
        String encDecText = context.execute(text, key, operation);
 
 
        if (!outFileName.isEmpty()) {
            File file = new File(outFileName);
 
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(encDecText);
            } catch (IOException e) {
                System.out.printf("An exception occurs %s", e.getMessage());
            }
        } else {
            System.out.println(encDecText);
        }
 
    }
 
}
 
abstract class Algorithm {
    StringBuilder sb = new StringBuilder();
 
    abstract String encrypt(String string, int key);
    abstract String decrypt(String string, int key);
 
}
 
class ShiftAlgorithm extends Algorithm {
 
    @Override
    public String encrypt(String string, int key) {
        int endOfAlphabet=0;
        for (int i = 0; i < string.length(); i++) {
            if (Character.isLetter(string.charAt(i))) {
                if (Character.isUpperCase(string.charAt(i))) {
                    endOfAlphabet =  90;
                } else if (Character.isLowerCase(string.charAt(i))) {
                    endOfAlphabet = 122;
                }
                super.sb.append((char)(string.charAt(i) + key > endOfAlphabet ?
                        string.charAt(i) + key - 26 : string.charAt(i) + key));
            } else {
                super.sb.append(string.charAt(i));
            }
        }
        return super.sb.toString();
    }
 
    @Override
    public String decrypt(String string, int key) {
        int beginningOfAlphabet = 0;
        int endOfAlphabet = 0;
        for (int i = 0; i < string.length(); i++) {
            if (Character.isLetter(string.charAt(i))) {
                if (Character.isUpperCase(string.charAt(i))) {
                    beginningOfAlphabet = 65;
                    endOfAlphabet = 91;
                } else if (Character.isLowerCase(string.charAt(i))) {
                    beginningOfAlphabet = 97;
                    endOfAlphabet = 123;
                }
                super.sb.append((char)(string.charAt(i) - key < beginningOfAlphabet ?
                        endOfAlphabet + string.charAt(i) - key -beginningOfAlphabet : string.charAt(i) - key));
            }
            else {
                super.sb.append(string.charAt(i));
            }
        }
        return super.sb.toString();
    }
}
 
class UnicodeAlgorithm extends Algorithm{
    @Override
    public String encrypt(String string, int key) {
        for (int i = 0; i < string.length(); i++) {
            super.sb.append((char) (string.charAt(i)+key));
        }
        return super.sb.toString();
    }
 
    @Override
    public String decrypt(String string, int key) {
        for (int i = 0; i < string.length(); i++) {
            super.sb.append((char)(string.charAt(i)-key));
        }
        return super.sb.toString();
    }
}
 
class AlgorithmFactory {
 
    Algorithm chooseAlgorithm(String type) {
        if (type.equals("unicode")) {
            return new UnicodeAlgorithm();
        } else if (type.equals("shift")) {
            return new ShiftAlgorithm();
        }
        return null;
    }
}
 
class Context {
    private Algorithm algorithm;
 
    public Context(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
 
    public String execute (String string, int key, String op) {
        if (op.equals("enc")) {
            return this.algorithm.encrypt(string, key);
        } else if (op.equals("dec")) {
            return this.algorithm.decrypt(string, key);
        }
        return null;
    }
}