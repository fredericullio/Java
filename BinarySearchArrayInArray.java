package com.jerrosHaven;

import java.util.Scanner;

class BinarySearchArrayInArray {
    public static void BinarySearchArrayInArray(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] arr1 = new int[sc.nextInt()];
        for (int i=0; i<arr1.length; i++) {
            arr1[i] = sc.nextInt();
        }
        int[] arr2 = new int[sc.nextInt()];
        for (int i=0; i<arr2.length; i++) {
            arr2[i] = sc.nextInt();
        }

        System.out.println(getIndexes(arr1, arr2));
    }

    static String getIndexes (int[] arr1, int[] arr2) {
        int index;
        StringBuilder result = new StringBuilder();
        for (int number : arr2) {
            index = binarySearch(arr1, 0, arr1.length - 1, number);
            result.append(index == -1 ? index : index+1).append(" ");
        }
        return result.toString();
    }

    static int binarySearch(int[] array, int left, int right, int num) {

            if (left > right) {
                return -1;
            }

            int mid = left + (right - left) / 2;

            if (num == array[mid]) {
                return mid;
            } else if (num < array[mid]) {
                return binarySearch(array, left, mid - 1, num);
            } else {
                return binarySearch(array,mid + 1, right, num);
            }
    }
}