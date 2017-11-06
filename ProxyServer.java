import java.io.*;
import java.net.*;

public class SimpleProxyServer {
  public static void main(String[] args) throws IOException {
    try {
      String host = "localhost";
      int remoteport = 80;
      int localport = 111;
      // Print a start-up message
      System.out.println("Starting proxy for " + host + ":" + remoteport
          + " on port " + localport);
      // And start running the server
      runServer(host, remoteport, localport); // never returns
			/* host is machone, remote is google server , local proxy machine*/
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  /**
   * runs a single-threaded proxy server on
   * the specified local port. It never returns.
   */
  public static void runServer(String host, int remoteport, int localport)
      throws IOException {
    // Create a ServerSocket to listen for connections with
    ServerSocket ss = new ServerSocket(localport);

    final byte[] request = new byte[1024];
    byte[] reply = new byte[4096];
	
    while (true) {
	Socket client = null, server = null;
      try {
	  System.out.println("in loopproxy");
        // Wait for a connection on the local port
        client = ss.accept();
		if(client!=null)
		System.out.println("connected");
		else
		System.out.println("not connected");
        final InputStream streamFromClient = client.getInputStream();
        final OutputStream streamToClient = client.getOutputStream();
		
        try {
		System.out.println("in SERVER");
          server = new Socket(host, remoteport);
		  System.out.println("inserver");
        } catch (IOException e) {
          PrintWriter out = new PrintWriter(streamToClient);
          out.print("Proxy server cannot connect to " + host + ":"
              + remoteport + ":\n" + e + "\n");
          out.flush();
          client.close();
          continue;
        }

        // Get server streams.
        final InputStream streamFromServer = server.getInputStream();
        final OutputStream streamToServer = server.getOutputStream();
		int bytesRead;
		FileOutputStream ifout=null;
		System.out.println("in run" + request);
       // while ((bytesRead = streamFromClient.read(request)) != -1) {
	       		 bytesRead = streamFromClient.read(request);
			  		System.out.println("in reaading loop" + request);
                    streamToServer.write(request, 0, bytesRead);
				
				String str = new String(request);
				URL url=new URL(str);
				String file=url.getFile();
				System.out.println(file +"namm of file");
				int len=file.length();
				String filename=file.substring(1,len);
				InputStream is=new ByteArrayInputStream(filename.getBytes());
				BufferedReader br= new BufferedReader(new InputStreamReader(is));
				ifout=new FileOutputStream("filename.txt");
				int ch;
				while((ch=br.read())!=-1)
				{
					ifout.write(ch);
					
				}
				ifout.close();
				
		System.out.println("before t.start");
				
         streamToServer.flush();
              
			  
          

        // Read the server's responses
        // and pass them back to the client.
		
		System.out.println("After t.start");
		FileInputStream fstream=new FileInputStream("filename.txt");
							DataInputStream ifin=new DataInputStream(fstream);
							BufferedReader br1=new BufferedReader(new InputStreamReader(ifin));
							String naam=br1.readLine();
							System.out.println(naam);
							
				File f;
				f=new File(naam);
				if(f.createNewFile())
				{
				
				//f.close();
				
				 int bytesR;
				   int x;
							
							FileOutputStream fwriter=new FileOutputStream(naam);
							//BufferedWriter bwrite=new BufferedWriter(fwriter);
							//fwriter.write(x);
							
				         // while ((bytesR = streamFromServer.read(reply)) != -1) {
						 bytesR = streamFromServer.read(reply);
						  System.out.println("in reply section" + bytesR);
				            streamToClient.write(reply, 0, bytesR);
							
							System.out.println(naam+ "mil gya"); 
							
							String strR = new String(reply);
							System.out.println(strR); 	 
				            streamToClient.flush();
				         // }
				     System.out.println( "hello"); 
				InputStream isnw=new ByteArrayInputStream(reply);
				while((x=isnw.read())!=-1)
				{
				System.out.println(x+"Connection closed");
					fwriter.write(x);
				}
				}
				
				
				else
				{
					 int data;
        			
          						
											FileInputStream fs=new FileInputStream(naam);
											DataInputStream iin =new DataInputStream(fs);
											BufferedReader br2=new BufferedReader(new InputStreamReader(iin));
											while ((data = br2.read()) != -1) 
											{
		  								
            								streamToClient.write(data);
										
											}
						
				}
				

        // The server closed its connection to us, so we close our
        // connection to our client.
		
        streamToClient.close();
		
      } catch (IOException e) {
        System.err.println(e+"heree");
      } finally {
        try {
          if (server != null)
            server.close();
          if (client != null)
            client.close();
        } catch (IOException e) {
        }
      }
    }
  }
}
