package com.jerrosHaven;

import java.util.Random;
import java.util.Scanner;

public class PasswordGenerator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        StringBuilder password = new StringBuilder();
        Random numGen = new Random();
        int charNum;
        //The program requires four numbers from the standard input: a minimal number of uppercase letters,
        // a minimal number of lowercase letters, a minimal number of digits and an overall length of a password.
        int upperCase = sc.nextInt();
        int lowerCase = sc.nextInt();
        int digits = sc.nextInt();
        int passwordLength = sc.nextInt();

        while (password.length() < passwordLength) {
            charNum = numGen.nextInt(74) + 48;
            if (!password.toString().isEmpty() && charNum == password.charAt(password.length()-1) ||
                    charNum>58 && charNum<65 || charNum>90 && charNum<97) {
                    continue;
            }
            digits = checkRequirements(password, charNum, 48, 57, digits, lowerCase, upperCase);
            upperCase = checkRequirements(password, charNum, 65, 90, upperCase, digits, lowerCase);
            lowerCase = checkRequirements(password, charNum, 97, 122, lowerCase, digits, upperCase);
        }
        System.out.println(password.toString());

    }

    static int checkRequirements(StringBuilder sb, int charNum, int asciiFromInc, int asciiToInc,
                                  int req1, int req2, int req3) {
        if (charNum >= asciiFromInc && charNum <= asciiToInc && (req1>0 || req2==0 && req3==0)) {
            sb.append((char) charNum);
            if (req1>0) {req1--;}
        }
        return req1;
    }
}
