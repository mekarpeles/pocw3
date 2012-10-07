import java.io.*   ;
import java.net.*  ;
import java.util.* ;

public final class WebServer
{
    public static void main(String args[]) throws Exception
    {
	int tcp_port = 31173; //Elite
	if (args.length > 0)
	    tcp_port = Integer.parseInt(args[0]);
	ServerSocket welcomer = new ServerSocket(tcp_port);
	
	// Process HTTP service requests in an infinite loop.
	while (true) {
	    Socket  client = welcomer.accept();
	    Thread session = new Thread(new HttpRequest(client, tcp_port));
	    session.start(); // Start the thread.
	}
    }
}