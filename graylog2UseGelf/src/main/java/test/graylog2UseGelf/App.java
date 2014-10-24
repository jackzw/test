package test.graylog2UseGelf;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
        System.out.println( "Hello World!" );
		mywork();
        
    }
	public static void mywork() throws InterruptedException {
		final Logger logger = new Logger();
		logger.app = "test_App";
		logger.error("jobid_2","This is a message from test_App","message_type-2");
	}
}
