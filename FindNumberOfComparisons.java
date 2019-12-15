package com.jerrosHaven;

import java.util.Scanner;

public class FindNumberOfComparisons {
    private static int counter = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] arr = new int[sc.nextInt()];
        for (int i=0; i<arr.length; i++) {
            arr[i] = sc.nextInt();
        }
        int wantedNum = sc.nextInt();

        System.out.println(jumpSearchForBlockBoundaries(arr, wantedNum));
    }

    static int jumpSearchForBlockBoundaries (int[] arr, int wantedNum) {
        int prevRight = 0;
        int currRight = 0;
        int jumpLength = (int) Math.sqrt(arr.length);


        if (arr[currRight] == wantedNum) {
            return counter;
        }

        while (currRight < arr.length-1) {
            currRight = Math.min(arr.length-1, currRight + jumpLength);

            if (arr[currRight]>=wantedNum) {
                break;
            }
            counter++;
            prevRight = currRight;
        }
        reverseSearch(arr, prevRight, currRight, wantedNum);
        return counter;
    }

    static void reverseSearch(int[] arr, int prev, int curr, int wanted) {

        for (int i=curr; i>prev; i--) {
            counter++;
            if (arr[i]<=wanted) {
                return;
            }
        }
    }
}
