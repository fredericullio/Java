package com.jerrosHaven;

import java.util.Scanner;

public class VectorAngleCalculator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double ax = sc.nextDouble();
        double ay = sc.nextDouble();
        double bx = sc.nextDouble();
        double by = sc.nextDouble();

        System.out.println(Math.toDegrees(Math.acos((ax*bx + ay*by)/(Math.hypot(ax, ay) * Math.hypot(bx, by)))));

    }
}
