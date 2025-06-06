package com.lukasimonishvili;

import java.util.Scanner;

public class ScannerSingleton {
    private Scanner scanner;
    private static ScannerSingleton instance;
    
    private ScannerSingleton() {
        scanner = new Scanner(System.in);
    }

    public static ScannerSingleton getInstance() {
        if (instance == null) {
            instance = new ScannerSingleton();
        }
        return instance;
    }

    public Scanner getScanner() {
        return scanner;
    }
}
