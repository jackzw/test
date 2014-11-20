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
    private static String host = "";
    private static String app = "";
    private static String stack_level = "";
    
    private static String gelfUrl = "127.0.0.1";
    private static int gelfPort = 12201;
    
    private static GelfTransports method = GelfTransports.UDP;
    private static int queueSize = 512;
    private static int connectTimeout = 5000;
    private static int reconnectDelay = 1000;
    private static int sendBufferSize = 32768;
    private static boolean tcpNoDelay = true;

    //public attributes
    public boolean blocking = false;    
    public boolean localLogging = false;

    final GelfConfiguration config = new GelfConfiguration(new InetSocketAddress(gelfUrl, gelfPort))
			    .transport(method)
			    .queueSize(queueSize)
			    .connectTimeout(connectTimeout)
			    .reconnectDelay(reconnectDelay)
			    .tcpNoDelay(tcpNoDelay)
			    .sendBufferSize(sendBufferSize);

    final GelfTransport transport = GelfTransports.create(config);
    final GelfMessageBuilder builder = new GelfMessageBuilder("", host); // Source

    final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(app);
    
    public Logger() {
    	
    }
    
	public static Logger getLogger(final String _gelfUrl, final int _gelfPort, final String _app, final String _stack_level, final String _host,
			final GelfTransports _method) {
		gelfUrl = _gelfUrl;
		gelfPort = _gelfPort;
		app = _app;
		if(stack_level != null)
			stack_level = _stack_level;
		if(_host != null) {
			host = _host;
		} else {
		    try {
		    	  InetAddress addr = InetAddress.getLocalHost();            
		    	  host = addr.getHostAddress();
		    	} catch (Exception e) {
		    		host = "localhost";
		    	}
		}
		if(_method != null)
			method = _method;  
        return new Logger();
	}
	
	public static Logger getLogger(final String gelfUrl, final int gelfPort, final String app, final String stack_level, final String host) {
		return getLogger(gelfUrl, gelfPort, app, stack_level, host, null);
	}
	
	public static Logger getLogger(final String gelfUrl, final int gelfPort, final String app, final String stack_level) {
		return getLogger(gelfUrl, gelfPort, app, stack_level, null);
	}
	
	public static Logger getLogger(final String gelfUrl, final int gelfPort, final String app) {
		return getLogger(gelfUrl, gelfPort, app, null);
	}

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
