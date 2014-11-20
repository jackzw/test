package test.graylog2UseGelf;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.graylog2.gelfclient.GelfConfiguration;
import org.graylog2.gelfclient.GelfMessage;
import org.graylog2.gelfclient.GelfMessageBuilder;
import org.graylog2.gelfclient.GelfMessageLevel;
import org.graylog2.gelfclient.GelfTransports;
import org.graylog2.gelfclient.transport.GelfTransport;

public class Logger {
	
    //public attributes
    private String host = "";
    private String app = "";
    private String stack_level = "";
    
    private String gelfUrl = "127.0.0.1";
    private int gelfPort = 12201;
    
    private GelfTransports method = GelfTransports.UDP;
    private int queueSize = 512;
    private int connectTimeout = 5000;
    private int reconnectDelay = 1000;
    private int sendBufferSize = 32768;
    private boolean tcpNoDelay = true;

    //public attributes
    public boolean blocking = false;    
    public boolean localLogging = false;

	public Logger(final String gelfUrl, final int gelfPort, final String app, final String stack_level, final String host,
			final GelfTransports method) {
		this.gelfUrl = gelfUrl;
		this.gelfPort = gelfPort;
		this.app = app;
		if(stack_level != null)
			this.stack_level = stack_level;
		if(host != null) {
			this.host = host;
		} else {
		    try {
		    	  InetAddress addr = InetAddress.getLocalHost();            
		    	  this.host = addr.getHostAddress();
		    	} catch (Exception e) {
		    		this.host = "localhost";
		    	}
		}
		if(method != null)
			this.method = method;
	}
	
	public Logger(final String gelfUrl, final int gelfPort, final String app, final String stack_level, final String host) {
        this(gelfUrl, gelfPort, app, stack_level, host, null);
	}
	
	public Logger(final String gelfUrl, final int gelfPort, final String app, final String stack_level) {
        this(gelfUrl, gelfPort, app, stack_level, null);
	}
	
	public Logger(final String gelfUrl, final int gelfPort, final String app) {
        this(gelfUrl, gelfPort, app, null);
	}
	
    
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Logger.class);

    final GelfConfiguration config = new GelfConfiguration(new InetSocketAddress(gelfUrl, gelfPort))
	    .transport(method)
	    .queueSize(queueSize)
	    .connectTimeout(connectTimeout)
	    .reconnectDelay(reconnectDelay)
	    .tcpNoDelay(tcpNoDelay)
	    .sendBufferSize(sendBufferSize);

    final GelfTransport transport = GelfTransports.create(config);
    final GelfMessageBuilder builder = new GelfMessageBuilder("", host); // Source
 
    public void debug(String task, String message, String message_type, String content) throws InterruptedException {
    	Map<String,String> calls = getCallTrace();
    	post_gelf(GelfMessageLevel.DEBUG, calls, task, message, message_type, content);
    	if(localLogging)
    		logger.debug(loggerMessage(calls, task, message, message_type, content));
    }
    public void info(String task, String message, String message_type, String content) throws InterruptedException {
    	Map<String,String> calls = getCallTrace();
    	post_gelf(GelfMessageLevel.INFO, calls, task, message, message_type, content);
    	if(localLogging)
    		logger.info(loggerMessage(calls, task, message, message_type, content));
    }
    public void notice(String task, String message, String message_type, String content) throws InterruptedException {
    	Map<String,String> calls = getCallTrace();
    	post_gelf(GelfMessageLevel.NOTICE, calls, task, message, message_type, content);
    	if(localLogging)
    		logger.warn(loggerMessage(calls, task, message, message_type, content));
    }
    public void warning(String task, String message, String message_type, String content) throws InterruptedException {
    	Map<String,String> calls = getCallTrace();
    	post_gelf(GelfMessageLevel.WARNING, calls, task, message, message_type, content);
    	if(localLogging)
    		logger.warn(loggerMessage(calls, task, message, message_type, content));
    }
    public void error(String task, String message, String message_type, String content) throws InterruptedException {
    	Map<String,String> calls = getCallTrace();
    	post_gelf(GelfMessageLevel.ERROR, calls, task, message, message_type, content);
    	if(localLogging)
    		logger.error(loggerMessage(calls, task, message, message_type, content));
    }
    public void critical(String task, String message, String message_type, String content) throws InterruptedException {
    	Map<String,String> calls = getCallTrace();
    	post_gelf(GelfMessageLevel.CRITICAL, calls, task, message, message_type, content);
    	if(localLogging)
    		logger.error(loggerMessage(calls, task, message, message_type, content));
    }
    public void alert(String task, String message, String message_type, String content) throws InterruptedException {
    	Map<String,String> calls = getCallTrace();
    	post_gelf(GelfMessageLevel.ALERT, calls, task, message, message_type, content);
    	if(localLogging)
    		logger.error(loggerMessage(calls, task, message, message_type, content));
    }
    public void emergency(String task, String message, String message_type, String content) throws InterruptedException {
    	Map<String,String> calls = getCallTrace();
    	post_gelf(GelfMessageLevel.EMERGENCY, calls, task, message, message_type, content);
    	if(localLogging)
    		logger.error(loggerMessage(calls, task, message, message_type, content));
    }
    
    private Map<String,String> getCallTrace() {
		String full_trace = "";
		String trace = "";
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		int i=ste.length-1;
		if(i >= 0) {
			full_trace = ste[i].toString();
			trace = ste[i].getMethodName();
		}
		for(i=ste.length-2; i>2; i--) {
			full_trace = full_trace + "\n->" + ste[i].toString();
			trace = trace + "." + ste[i].getMethodName();
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("trace", trace);
		map.put("full_trace", full_trace);

		return map;
    }
    
    private String loggerMessage(Map<String,String> calls, String task, String message, String message_type, String content) {

		Map<String,String> map = new HashMap<String,String>();
		map.put("app", app);
		map.put("task", task);
		map.put("trace", calls.get("trace"));
		map.put("full_trace", calls.get("full_trace"));
		map.put("stack_level", stack_level);
		map.put("message", message);
		map.put("message_type", message_type);
		map.put("content", content);

		return map.toString();
    }
    
    public void post_gelf(GelfMessageLevel level, Map<String,String> calls, String task, String message, String message_type, String content) throws InterruptedException {

    	final GelfMessage gelfMessage = builder.level(level)	// Level	
           		.message(message)								// Message
    			.additionalField("_app", app) 					// app id
    			.additionalField("_task", task)					// task id
    			.additionalField("_trace", calls.get("trace")) 				// pretty call stack
    			.additionalField("_full_trace", calls.get("full_trace")) 	// call stack
     			.additionalField("_stack_level", stack_level)	// module level
     			.additionalField("_message_type", message_type)	// Message type
     			.additionalField("_content", content)			// content
                .build();

        if (blocking) {
           // Blocks until there is capacity in the queue
            transport.send(gelfMessage);
        } else {
            // Returns false if there isn't enough room in the queue
            /*boolean enqueued = */transport.trySend(gelfMessage);
        }
    }
}
