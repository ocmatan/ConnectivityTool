package matan.connectivityTool;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DNSLookUpTask implements ConnectivityTask{
    public String resource;
    public String protocol;
    public String test_type;

    public DNSLookUpTask(String resource, String protocol, String test_type) {
        this.resource = resource;
        this.protocol = protocol;
        this.test_type = test_type;
    }

    @Override
    public void execute() {
        try{
            InetAddress address = InetAddress.getByName(this.resource);
            Main.logger.info("Successfully performed DNS Name lookup to: " + this.resource);
        }
        catch(UnknownHostException e){
            Main.logger.info("Failed to perform DNS Name lookup for: " + this.resource);
        }
        catch (Exception e){
            Main.logger.info("Failed to perform DNS Name lookup for: " + this.resource);
        }

    }
}
