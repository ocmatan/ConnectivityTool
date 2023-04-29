package matan.connectivityTool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    public static Logger logger = LogManager.getLogger("logger");
    public static void main(String[] args) {
        if (args.length == 0) {
            logger.info("Usage: missing config file path");
            System.exit(0);
        }
        try {
            logger.info("Initiating Connectivity Tool with configuration file: " + args[0]);
            new ConnectivityExecutor(args[0]).execute();
            logger.info("Connectivity Tool completed");
        }catch (Exception e) {
            logger.info("Failed to execute program: " + e.getMessage());
        }







    }
}