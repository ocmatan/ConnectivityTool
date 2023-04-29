package matan.connectivityTool;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectivityExecutor {

    private TaskFactory taskFactory;
    private String pathToConfig;
    public static final HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();//TODO move duration to config ?

    public ConnectivityExecutor(String path){
        ExecutorService executor = Executors.newFixedThreadPool(10);//TODO ?
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
                    Map<String,String> g = new Gson().fromJson(reader, Map.class);
                    ConnectivityTask t = taskFactory.getTask(g.toString().toLowerCase());
                    if(t != null) t.execute();//TODO use executor service
                }
                reader.endArray();

            }catch (Exception e){
                Main.logger.info("Failed to process config file: " + e.getMessage());
            }

        }


}
