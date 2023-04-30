package matan.connectivityTool;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LatencyAlertHandler {
    //private Properties p;
    private Map<String, Long> latencyStoreMap;

    //private final String latencyStoreFilePath = "latencyStoreFile.properties";
    private final String latencyStoreFilePath1 = "latencyStoreFile.txt";
    //Lock lock = new ReentrantLock();

    public void handleLatency(URI uri, long latencyInMillis, long latencyDegradationAlertThresholdMS){
        try{
            if(latencyStoreMap == null) loadLatencyStoreFromFile();//load store from file for the first time
            String encodedUrl = URLEncoder.encode(uri.toString(), StandardCharsets.UTF_8);
            if(latencyDegradationAlertThresholdMS > 0 && latencyStoreMap.get(encodedUrl) != null) {
                long previousLatency = latencyStoreMap.get(encodedUrl);
                long latencyDegradationInMs = latencyInMillis - previousLatency;
                if(latencyDegradationAlertThresholdMS <  latencyDegradationInMs ){
                    Main.logger.info("Alert for latency degradation, uri: " + uri + " degradation of " + latencyDegradationInMs + " ms since last execution");
                }
            }
            saveLatencyInStore(encodedUrl, latencyInMillis);
        }catch (Exception e){
            Main.logger.info("Failed to handle latency alert");
        }

    }


    private void loadLatencyStoreFromFile(){
        try {
            File storeFile = new File(latencyStoreFilePath1);
            if(!storeFile.exists()) {
                storeFile.createNewFile();
            }

            latencyStoreMap = new ConcurrentHashMap<>();
            BufferedReader reader = new BufferedReader(new FileReader(latencyStoreFilePath1));
            String line = reader.readLine();
            while (line != null) {
                String[] arr = line.split("=",2);
                if(arr[0]!=null && arr[1]!=null)
                    latencyStoreMap.put(arr[0], Long.parseLong(arr[1]));
                line = reader.readLine();
            }

        } catch (Exception ex) {
            Main.logger.info("Failed to load latency store");
            throw new RuntimeException("Failed to load latency store", ex);
        }
    }

    private void saveLatencyInStore(String uri, long latencyInMillis){
        try{
            latencyStoreMap.put(uri, latencyInMillis);
            //override entire file
            FileWriter fw = new FileWriter(latencyStoreFilePath1, false);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Map.Entry<String, Long> entry : latencyStoreMap.entrySet()) {
                bw.write(entry.getKey() + "=" + entry.getValue());
                bw.newLine();
            }
            bw.flush();
            bw.close();


        }catch (Exception e){
            Main.logger.info("Failed to save latency in store");
            throw new RuntimeException("Failed to save latency in store", e);
        }


    }


}
