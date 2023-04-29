package matan.connectivityTool;

import com.google.gson.Gson;

public class TaskFactory {


    public ConnectivityTask getTask(String input){
        Gson gson = new Gson();
        if(input.contains("protocol=https")){
            LatencyTask task = gson.fromJson(input, LatencyTask.class);
            return task;
        }else if(input.contains("protocol=http")){
            LatencyTask task = gson.fromJson(input, LatencyTask.class);
            return task;
        }else if (input.contains("protocol=dns")){
            DNSTask task = gson.fromJson(input, DNSTask.class);
            return task;
        }else{
            Main.logger.info("Failed to process config file input: " + input);
            return null;
        }



    }

}
