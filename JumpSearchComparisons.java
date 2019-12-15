package com.jerrosHaven;

import java.util.Scanner;

public class JumpSearchComparisons {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int inputNum = sc.nextInt();
        int sqrt = (int) Math.sqrt(inputNum);

        for (int i=0; i<inputNum; i++) {
            if (i==0) {
                System.out.print(1 + " ");
            } else if (i%sqrt==0) {
                System.out.print(((i/sqrt)+1) + " ");
            } else if (i > inputNum-1 - (inputNum-1)%sqrt) {
                System.out.print((i/sqrt+1 + (inputNum-i)) + " ");
            } else {
                System.out.print(((i/sqrt+2) + ((i/sqrt+1)*sqrt)-i) + " ");
            }
        }
    }
}
