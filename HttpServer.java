import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.Object;
class HttpRequest
{
private Socket ClientConn;
public HttpRequest(Socket ClientConn) throws Exception
{
 this.ClientConn=ClientConn;
}
public void process() throws Exception
{
 DataInputStream din=new DataInputStream(ClientConn.getInputStream());
 PrintStream ot=new PrintStream(ClientConn.getOutputStream(),true);
 BufferedOutputStream out=new BufferedOutputStream(ot);
  while(true){
  String request=din.readLine().trim();
  System.out.println(request+ "req");
  URL url=new URL(request);
 System.out.println(url.getFile() + url.getProtocol()+ "head");
 
 try
 { 
 System.out.println( "try ke andar");
 HttpURLConnection http = (HttpURLConnection)url.openConnection();
 System.out.println( "try ke andar ke andar");
 int statusCode = http.getResponseCode();
 System.out.println( "status" + statusCode);
DataInputStream dinnw=new DataInputStream(http.getInputStream());
//String s=dinnw.readLine();
System.out.println("final");
ot.println(dinnw.readLine());
 if (statusCode==200)
        {
            System.out.println("OK");
        }
 }
 catch(Exception ex) { 
  out.close();
 ClientConn.close();
 //din.close();
 //dinnw.close();
 }
 }
 //din.close();
// dinnw.close();
 //out.close();
 //ClientConn.close();
 }
}
class HttpServer
{
public static void main(String args[]) throws Exception
{
System.out.println("\n\n\t\tThe HTTP Server is running..");
System.out.println("\n\n\t\tStop server using Ctrl + C"); 
ServerSocket soc=new ServerSocket(80);

while(true)
{
 Socket inSoc=soc.accept();
 HttpRequest request=new HttpRequest(inSoc);
 request.process();
 }
 }
 } 
