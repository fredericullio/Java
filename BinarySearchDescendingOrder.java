package com.jerrosHaven;

/*A method performs binary search throughout a descendingly sorted array of integers and returns 
an index of the leftmost instance of the sought number.*/

import java.util.ArrayList;
import java.util.Scanner;

public class BinarySearchDescendingOrder {

    public static int binarySearch(int elem, int[] array, int left, int right) {

        if (right<left) {
            return -1;
        }

        int mid = right + (left-right) / 2;

        if (elem == array[mid]) {
            while (mid!=0 && array[mid-1]==elem) {
                mid = mid-1;
            }
            return mid;
        } else if (elem < array[mid]) {
            return binarySearch(elem, array, mid+1, right);
        } else {
            return binarySearch(elem, array, left, mid-1);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int elem = scanner.nextInt();
        ArrayList<Integer> list = new ArrayList<>();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        System.out.println(binarySearch(elem, array, 0, array.length-1));
    }
}
