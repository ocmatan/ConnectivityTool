package org.example;

public class LatencyTask implements ConnectivityTask{

    String _resource;
    public LatencyTask(String resource) {
        _resource = resource;
    }
    @Override
    public void execute() {
        System.out.println("LatencyTask for: " + _resource);
    }
}
