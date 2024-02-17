import java.net.*;
import java.io.*;

public class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    // constructor
    public Server(){
        try {
            server = new ServerSocket(8888); // Initialize ServerSocket object
            System.out.println("Server is ready to accept connection");
            System.out.println("waiting for the client message");
            socket=server.accept();

            br= new BufferedReader(new InputStreamReader(socket.getInputStream())); // for getting data from client
            out= new PrintWriter(socket.getOutputStream()); // sending data to client from server

            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Other methods...


    // methods

public void startReading(){
        //thread for read data 

        Runnable r1=()->{
          
            System.out.println("reader started....");
            try {
            while (true) {
              
                    String msg= br.readLine();
                    if(msg.equals("exit")){
                     System.out.println("client terminated the chat....");
                     socket.close();
                     break;
                    }
                    System.out.println("client mssg:" + msg);

                
               
            }
        } catch (IOException e ) {
            e.printStackTrace();
        }
      
        };
        new Thread(r1).start();

}
public void startWriting() {
    // Thread for writing data
    Runnable r2 = () -> {
        try {
            while (true) {
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content = br1.readLine();
                out.println(content); // Send the input to the client
                out.flush(); // Flush the output stream to ensure the message is sent immediately
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    new Thread(r2).start();
}

    public static void main(String[] args){
new Server();

    }
    
}
