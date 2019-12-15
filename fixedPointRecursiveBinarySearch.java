package com.jerrosHaven;

import java.util.Scanner;

class fixedPointRecursiveBinarySearch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] arr = new int[sc.nextInt()];
        for (int i=0; i<arr.length; i++) {
            arr[i] = sc.nextInt();
        }

        System.out.println(binarySearch(arr, 0, arr.length-1));
    }

    static boolean binarySearch(int[] array, int left, int right) {
        if (left > right) {
            return false;
        }

        int mid = left + (right - left) / 2;

        if (array[mid] == mid) {
            return true;
        } else if (array[mid] < mid) {
            return binarySearch(array, mid + 1, right);
        } else {
            return binarySearch(array, left, mid-1);
        }
    }
}