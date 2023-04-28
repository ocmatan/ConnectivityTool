package org.example;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world! - ConnectivityTool");

        if (args.length == 0) {
            System.out.println("Usage: missing config file path");
            System.exit(0);
        }
        try {
            new ConnectivityExecutor(args[0]).execute();
        }catch (Exception e) {
            System.out.println("Failed to execute program: " + e.getMessage());
        }







    }
}