import java.io.*;
import java.net.*;
class client
{
public static void main(String args[])
{
 try{
 Socket clientSocket=null;
 PrintWriter out=null;
 BufferedReader in=null;
 clientSocket = new Socket("localhost",111);
 out=new PrintWriter(clientSocket.getOutputStream(),true);
 System.out.println("Connected to Server. Enter Messages to Server. Enter Bye to close");
 in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
BufferedReader stdin =new BufferedReader(new InputStreamReader(System.in));
 String str;
 String reply;
 while(true)
 {
 System.out.println("in loop");
 str = stdin.readLine();
 out.println(str);
 System.out.println(str+"msg");
 reply=in.readLine();
 System.out.println("Message from Server: Echo: "+reply);
 if (str.equalsIgnoreCase("bye"))
 {
 System.out.println("Connection closed");
 break;
 }
 }
 out.close();
 in.close();
 stdin.close();
 clientSocket.close();}
 catch(Exception e)
 {System.out.println(e.getMessage());
 }
}}
