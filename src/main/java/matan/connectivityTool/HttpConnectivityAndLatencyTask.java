package matan.connectivityTool;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class HttpConnectivityAndLatencyTask implements ConnectivityTask{

    public String resource;
    public String protocol;
    public String test_type;
    public long latency_degradation_alert_threshold_ms;

    @Override
    public void execute() {
        try {
            URI uri = URI.create(this.protocol + "://" + this.resource);//TODO input validation
            HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();
            long startTimeInMillis = System.currentTimeMillis();
            CompletableFuture<HttpResponse<String>> response = ConnectivityExecutor.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            String result = response.thenApply(HttpResponse::body).get(10, TimeUnit.SECONDS);
            response.whenComplete((r, ex) -> {
                if (ex != null) {
                    Main.logger.info("Failed to check connectivity to : " + uri);
                } else {
                    long latencyInMillis = System.currentTimeMillis() - startTimeInMillis;
                    Main.logger.info("Successful " + this.protocol + " connection to: " + uri +", latency in ms: " + latencyInMillis);
                    ConnectivityExecutor.LatencyAlertHandler.handleLatency(uri, latencyInMillis, this.latency_degradation_alert_threshold_ms);
                }
            });

        }catch (Exception e){
            Main.logger.info("Failed to check connectivity to : " + protocol + "://" + resource );
        }

    }


}