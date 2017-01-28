import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
/**
 * Listen for all possible clients.
 *
 */
public class Listener implements Runnable{
    /** configuration file. */
    private Configuration myConfig;
    /** listener's name. */
    private String localName;
    
    public Listener(Configuration config, String Name) {
        this.myConfig = config;
        this.localName = Name;
    }
    
    @SuppressWarnings("resource")
    @Override
    public void run(){
        try {
            System.out.println("[listening...]");
            ServerSocket listener = new ServerSocket((myConfig.getNode(localName).get_port()));
            while (true) {
                try {
                    Socket socket = listener.accept();
                    System.out.println("[accept connection from" + 
                            socket.getRemoteSocketAddress().toString() + " " + socket.getPort() + "]");
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Thread listenFor = new Thread(new ListenFor(ois));
                    listenFor.start();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        } catch(IOException e) {
            e.printStackTrace();
        } 
    }   
}
