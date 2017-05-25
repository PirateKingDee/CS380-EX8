import java.net.ServerSocket;
import java.net.Socket;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
public class WebServer{

	public static void main(String [] args)throws Exception{
		try(ServerSocket serverSocket = new ServerSocket(8080)){
			Socket socket = serverSocket.accept();
            InputStream fromClient = socket.getInputStream();
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(fromClient));
            OutputStream toClient = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(toClient, true);

            String str = readFromClient.readLine();
            String fileName = "www"+str.split(" ")[1];
            System.out.println(fileName);
            //If file exist
            if(fileName.equals("www/hello.html")){
                File file = new File(fileName);
                BufferedReader readFile = new BufferedReader(new FileReader(file));
            
                pw.print("HTTP/1.1 200 OK\r\nContent-type: text/html\r\nContent-length: 124\r\n\r\n");
                System.out.println("HTTP/1.1 200 OK\r\nContent-type: text/html\r\nContent-length: 124\r\n\r\n");
                str = readFile.readLine();
                while(str!= null){
                    pw.print(str+"\r\n");
                    str = readFile.readLine();
                }
                readFile.close();
                pw.flush();
                serverSocket.close();
                pw.close();
            }
            //If file not exist
            else{
                File file = new File("www/NotFound.html");
                BufferedReader readFile = new BufferedReader(new FileReader(file));

                pw.print("HTTP/1.1 404 Not Found\r\nContent-type: text/html\r\nContent-length: 126\r\n\r\n");
                System.out.println("HTTP/1.1 404 Not Found\r\nContent-type: text/html\r\nContent-length: 126\r\n\r\n");
                str = readFile.readLine();
                while(str!= null){
                    //System.out.println(str);
                    pw.print(str+"\r\n");
                    str = readFile.readLine();
                }
                readFile.close();
                pw.flush();
                serverSocket.close();
                pw.close();
            }
            
            			
		}
	}
}
