package test.graylog2UseGelf;

import java.net.InetSocketAddress;
import java.util.Map;

import org.graylog2.gelfclient.GelfConfiguration;
import org.graylog2.gelfclient.GelfMessage;
import org.graylog2.gelfclient.GelfMessageBuilder;
import org.graylog2.gelfclient.GelfMessageLevel;
import org.graylog2.gelfclient.GelfTransports;
import org.graylog2.gelfclient.transport.GelfTransport;

public class Logger {
    public String app = "test_app";

    public boolean blocking = false;
    public String host = "test_localhost";
    
    public String gelfUrl = "127.0.0.1";
    public int gelfPort = 12201;
    public GelfTransports method = GelfTransports.UDP;
    public int queueSize = 512;
    public int connectTimeout = 5000;
    public int reconnectDelay = 1000;
    public int sendBufferSize = 32768;
    public boolean tcpNoDelay = true;
    
    final GelfConfiguration config = new GelfConfiguration(new InetSocketAddress(gelfUrl, gelfPort))
	    .transport(method)
	    .queueSize(queueSize)
	    .connectTimeout(connectTimeout)
	    .reconnectDelay(reconnectDelay)
	    .tcpNoDelay(tcpNoDelay)
	    .sendBufferSize(sendBufferSize);

    final GelfTransport transport = GelfTransports.create(config);
    final GelfMessageBuilder builder = new GelfMessageBuilder("", host); // Source
 
    public void debug(String jobid, String msg, String message_type) throws InterruptedException {
    	post_gelf(GelfMessageLevel.DEBUG, jobid, msg, message_type);
    }
    public void info(String jobid, String msg, String message_type) throws InterruptedException {
    	post_gelf(GelfMessageLevel.INFO, jobid, msg, message_type);
    }
    public void notice(String jobid, String msg, String message_type) throws InterruptedException {
    	post_gelf(GelfMessageLevel.NOTICE, jobid, msg, message_type);
    }
    public void warning(String jobid, String msg, String message_type) throws InterruptedException {
    	post_gelf(GelfMessageLevel.WARNING, jobid, msg, message_type);
    }
    public void error(String jobid, String msg, String message_type) throws InterruptedException {
    	post_gelf(GelfMessageLevel.ERROR, jobid, msg, message_type);
    }
    public void critical(String jobid, String msg, String message_type) throws InterruptedException {
    	post_gelf(GelfMessageLevel.CRITICAL, jobid, msg, message_type);
    }
    public void alert(String jobid, String msg, String message_type) throws InterruptedException {
    	post_gelf(GelfMessageLevel.ALERT, jobid, msg, message_type);
    }
    
    public void post_gelf(GelfMessageLevel log_level, String jobid, String msg, String message_type) throws InterruptedException {
		String methodName = "Unknown";
		String calls = "";
		String pretty = "";
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		if(ste.length > 0)
			methodName = ste[ste.length - 1].getMethodName();
		for(int i=ste.length-1; i>2; i--) {
			calls = calls + "->" + ste[i].toString() + "\n";
			pretty = pretty + "->" + ste[i].getMethodName();
		}

    	final GelfMessage message = builder.level(log_level)	// Level	
           		.message(msg)									// Message
     			.additionalField("_message_type", message_type)	// Message type
    			.additionalField("_jobid", jobid)				// Job id
    			.additionalField("_app", app+"."+methodName) 	// app
    			.additionalField("_pretty", pretty) 			// pretty call stack
    			.additionalField("_calls", calls) 				// calls stack
                .build();

        if (blocking) {
           // Blocks until there is capacity in the queue
            transport.send(message);
        } else {
            // Returns false if there isn't enough room in the queue
            /*boolean enqueued = */transport.trySend(message);
        }
    }
}
