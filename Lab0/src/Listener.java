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
            System.out.println("[listner:run:try]");
            System.out.println("listener name: " + myConfig.getNode(localName).get_name());
            System.out.println("port: " + myConfig.getNode(localName).get_port());
            ServerSocket listener = new ServerSocket((myConfig.getNode(localName).get_port()));
            while (true) {
                try {
                    System.out.println("[listner:run:try:try]");
                    Socket socket = listener.accept();
                    System.out.println("[listner:run:try:try:accept]");
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
