package org.example;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectivityExecutor {

    TaskFactory taskFactory;
    String pathToConfig;

    public ConnectivityExecutor(String path){
        HttpClient httpClient = HttpClient.newBuilder().build();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        taskFactory = new TaskFactory();
        pathToConfig = path;
    }

    public void execute(){
            try (
                    InputStream inputStream = Files.newInputStream(Path.of(pathToConfig));
                    JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
            ) {
                reader.beginArray();
                while (reader.hasNext()) {
                    Object g = new Gson().fromJson(reader, Object.class);
                    ConnectivityTask t = taskFactory.getTask(g.toString());
                    t.execute();
                }
                reader.endArray();

            }catch (Exception e){
                System.out.println("Failed to process config file: " + e.getMessage());
            }

        }


}
