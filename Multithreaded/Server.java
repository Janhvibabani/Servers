
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer() {
        return (clientScoket)-> {
            try {
                PrintWriter toClient = new PrintWriter(clientScoket.getOutputStream());
                toClient.println("Hello from the server");
                toClient.close();
                clientScoket.close();
            }catch(IOException ex) {

            }
        };
    }
    public static void main(String[] args) {
        int port = 8010;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            
            Server server = new Server();
            System.out.println("Server is listening on port: " + port);
            while(true) {
                Socket accptedSocket = serverSocket.accept();
                Thread thread = new Thread(()->server.getConsumer().accept(accptedSocket));
                thread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
