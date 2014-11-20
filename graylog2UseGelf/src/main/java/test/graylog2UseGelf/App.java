package test.graylog2UseGelf;

import java.net.InetAddress;

/**
 * Hello world!
 *
 */
public class App 
{
    private static String gelfUrl = "127.0.0.1";
    private static int gelfPort = 12201;
    
    
	final static Logger logger = Logger.getLogger(gelfUrl, gelfPort, "test_App", "App-main");

	public static void main( String[] args ) throws InterruptedException
    {
        System.out.println( "Hello World!" );
		mywork();
        
    }
	public static void mywork() throws InterruptedException {
		logger.localLogging = false;
		logger.error("task_1","This is a message from test_App","message_type-1","this is the content 1");
		logger.localLogging = true;
		mywork2();
	}
	public static void mywork2() throws InterruptedException {
		logger.error("jobid_2","This is a message from test_App","message_type-2","this is the content 2");
	}
}
