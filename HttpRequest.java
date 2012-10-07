import java.io.* ;
import java.net.* ;
import java.util.* ;

final class HttpRequest implements Runnable
{
    private HtmlDoc   html;
    private MIMETyper mime;
    private Socket    connection;
    private String    serversig; //server's signature

    public HttpRequest(Socket http_tcp, int port) throws Exception 
    {
	html       = new HtmlDoc();
	mime       = new MIMETyper();
	connection = http_tcp;
	serversig  = server_info(port);
    }

    public void run()
    {
	try {
	    DataOutputStream response = new DataOutputStream(connection.getOutputStream());
	    BufferedReader   request  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    
	    serve_mode(resolve_url(request), response); //debug_mode(br);
	    
	    response.close();
	    request.close();
	    connection.close();
	} catch (Exception e) {
	    System.out.println(e);
	}
    }

    public String resolve_url(BufferedReader request) throws Exception
    {
	StringTokenizer tokens = new StringTokenizer(request.readLine()); //Request Line
	System.out.println(tokens.nextToken() + // alerts and skips over "GET" token
			   " request from: " + connection.getRemoteSocketAddress());
	String url = tokens.nextToken();
	if (url.equals("/"))
	    url += "index.html";
	return url;
    }

    
    public String resolve_file(String url)
    {
	if (url.indexOf("/") == -1)
	    return url;
	return url.substring(url.lastIndexOf("/") + 1, url.length());
    }
    

    public void serve_mode(String url, DataOutputStream os) throws Exception
    {
	if (new File(resolve_file(url)).exists()) {
	    FileInputStream fis = null;
	    try { // Construct the response message.
		fis = new FileInputStream("." + url);
		os.writeBytes("HTTP/1.1 200 OK");
		os.writeBytes("Content-type: " + mime.type(url) + "\n\n");
		sendBytes(fis, os);
		fis.close();
	    } catch (FileNotFoundException e) {
		os.writeBytes("HTTP/1.1 500 Internal Server Error");
		os.writeBytes("Content-type: text/html\n\n");
		os.writeBytes(html.error500(serversig));
		System.out.println(e);
	    }
	} else {
	    os.writeBytes("HTTP/1.1 404 Not Found");
	    os.writeBytes("Content-type: text/html\n\n");
	    os.writeBytes(html.error404(url, serversig));
	}
    }


    private void sendBytes(FileInputStream fis, OutputStream os) throws Exception
    {
	// Construct a 1K buffer to hold bytes on their way to the socket.
	byte[] buffer = new byte[1024];
	int bytes = 0;
	
	// Copy requested file into the socket's output stream.
	while((bytes = fis.read(buffer)) != -1 ) {
	    os.write(buffer, 0, bytes);
	}
    }


    public String server_info(int port)
    {
	return "[PoCW3] Piece-of-Crap/0.0.2 (" + System.getProperty("os.name") + ")" + 
	    " Port " + port;
    }


    public void debug_mode(BufferedReader br) throws Exception
    {
	System.out.println("\nRequest: " + br.readLine()); // Display the request line.

	String   headerLine = null; // Get and display the header lines.
	int           count = 0;
	
	while ((headerLine = br.readLine()) != null) {
	    System.out.println("[" + count + "] Header: " + headerLine);
	    count++;
	}
    }    
}



