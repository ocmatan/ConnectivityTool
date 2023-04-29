package matan.connectivityTool;

import com.google.gson.Gson;

public class TaskFactory {


    public ConnectivityTask getTask(String input){
        Gson gson = new Gson();
        if(input.contains("protocol=https")){
            HttpConnectivityAndLatencyTask task = gson.fromJson(input, HttpConnectivityAndLatencyTask.class);
            return task;
        }else if(input.contains("protocol=http")){
            HttpConnectivityAndLatencyTask task = gson.fromJson(input, HttpConnectivityAndLatencyTask.class);
            return task;
        }else if (input.contains("protocol=dns")){
            DNSLookUpTask task = gson.fromJson(input, DNSLookUpTask.class);
            return task;
        }else{
            Main.logger.info("Failed to process config file input: " + input);
            return null;
        }



    }

}
