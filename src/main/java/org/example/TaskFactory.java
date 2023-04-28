package org.example;

public class TaskFactory {


    public ConnectivityTask getTask(String input){
        return new LatencyTask(input);
        //TODO complete switch case according to input
    }

}
