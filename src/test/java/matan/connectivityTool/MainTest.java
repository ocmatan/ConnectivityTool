package matan.connectivityTool;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


class MainTest {

    @Test
    void test(){

        try {

            //create seed
            String[] resources = new String[]{"www.hello.td", "www.nyse.com", "www.bbc.com", "www.ebay.com", "www.dummy.gov.kr","www.yahoo.com", "twitter.com","wikipedia.org", "bing.com","bing.co.uk"};
            String[] protocols = new String[]{"http", "https", "dns"};

            //generate new file
            FileOutputStream fos = new FileOutputStream("example_100_records.json");
            OutputStreamWriter ow = new OutputStreamWriter(fos);
            ow.write("[");

            for (int i = 0; i < 100; i++) {
                if (i != 0) {
                    ow.write(",");
                }
                int randomNum1 = (int) (Math.random() * (resources.length-1));
                int randomNum2 = (int) (Math.random() * (protocols.length-1));
                String resource = resources[randomNum1];
                String protocol = protocols[randomNum2];
                String str;
                if (protocol.equals("dns"))
                    str = "{\"resource\":\""+ resource +"\","+ "\"protocol\":" + "\"" + protocol + "\","+ "\"test_type\":lookup" +"}";
                else
                    str = "{\"resource\":\""+ resource +"\","+ "\"protocol\":" + "\"" + protocol + "\","+ "\"test_type\":\"latency\","+ "\"latency_degradation_alert_threshold_ms\": 10" +"}";
                ow.write(str);
            }
            ow.write("]");
            ow.flush();
        }catch (Exception e) {
            System.out.println("error in CrateLargeFile");
        }
    }



}