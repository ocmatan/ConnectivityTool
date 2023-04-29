package matan.connectivityTool;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LatencyAlertHandler {
    private Properties p;

    private final String latencyStoreFilePath = "latencyStoreFile.properties";
    Lock lock = new ReentrantLock();

    public void handleLatency(URI uri, long latencyInMillis, long latencyDegradationAlertThresholdMS){
        try{
            lock.lock();
            if(p == null) loadLatencyStoreFromFile();//load store from file for the first time
            String encodedUrl = URLEncoder.encode(uri.toString(), StandardCharsets.UTF_8);
            if(latencyDegradationAlertThresholdMS >= 0 && p.get(encodedUrl) != null) {
                long previousLatency = Long.parseLong(p.getProperty(encodedUrl));
                long latencyDegradationInMs = latencyInMillis - previousLatency;
                if(latencyDegradationAlertThresholdMS <  latencyDegradationInMs ){
                    Main.logger.info("Alert for latency degradation, uri: " + uri + " degradation of " + latencyDegradationInMs + " ms since last execution");
                }
            }
            saveLatencyInStore(encodedUrl, latencyInMillis);
        }catch (Exception e){
            Main.logger.info("Failed to handle latency alert");
        }finally {
            lock.unlock();
        }


    }


    private void loadLatencyStoreFromFile(){
        try {
            File storeFile = new File(latencyStoreFilePath);
            if(!storeFile.exists()) {
                storeFile.createNewFile();
            }
            p = new Properties();
            p.load(new FileInputStream(latencyStoreFilePath));

        } catch (Exception ex) {
            Main.logger.info("Failed to load latency store");
            throw new RuntimeException("Failed to load latency store", ex);
        }
    }

    private void saveLatencyInStore(String uri, long latencyInMillis){
        try{
            p.put(uri, String.valueOf(latencyInMillis));
            try (OutputStream output = new FileOutputStream(latencyStoreFilePath)) {
                p.store(output, null);

            } catch (IOException io) {
                io.printStackTrace();
            }

        }catch (Exception e){
            Main.logger.info("Failed to save latency in store");
            throw new RuntimeException("Failed to save latency in store", e);
        }


    }


}
