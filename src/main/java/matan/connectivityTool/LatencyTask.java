package matan.connectivityTool;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class LatencyTask implements ConnectivityTask{

    public String resource;
    public String protocol;
    public String test_type;

    public LatencyTask(String resource, String protocol, String test_type) {
        this.resource = resource;
        this.protocol = protocol;
        this.test_type = test_type;
    }

    @Override
    public void execute() {
        try {
            URI uri = URI.create(this.protocol + "://" + this.resource);//TODO input validation
            HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();
            long startTimeInMillis = System.currentTimeMillis();
            CompletableFuture<HttpResponse<String>> response = ConnectivityExecutor.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            String result = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
            response.whenComplete((r,t) -> logLatencyTestResult(uri.toString(), startTimeInMillis));

        }catch (Exception e){
            Main.logger.info("Failed to check latency for: " + this.toString());
        }


    }

    private void logLatencyTestResult(String uri, long startTimeInMillis){
        long latencyInMillis = startTimeInMillis - System.currentTimeMillis();
        Main.logger.info("latency result: uri - " + uri + " , latency - " + latencyInMillis);
    }


}