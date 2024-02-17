import java.io.*;
import java.net.*;

public class Client {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Client(){
        try{
        System.out.println("sending request to server");
        socket =new Socket("127.0.0.1",8888);
        System.out.println("Connection done");

         br= new BufferedReader(new InputStreamReader(socket.getInputStream())); // for getting data from client
        out= new PrintWriter(socket.getOutputStream()); // sending data to client from server

    startReading();
    startWriting();
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
    public void startReading() {
        // Thread for reading data
        Runnable r1 = () -> {
            System.out.println("Reader started....");
            try {
            while (true) {
               
                    String msg = br.readLine();
                    if (msg == null) {
                        System.out.println("Server closed the connection");
                        break;
                    } else if (msg.equals("exit")) {
                        System.out.println("Server terminated the chat....");
                        break;
                    }
                    System.out.println("Server message: " + msg);
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        };
        new Thread(r1).start();
    }
    
public void startWriting(){
    // thread for write data
    Runnable r2=()->{
        try{
        while (true) {
            
                BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));
                String content=br1.readLine();
                out.println(content); // Sending message to server
                out.flush();
           
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    };
    new Thread(r2).start();
}



    public static void main(String[] args){

        new Client();
    }
}
