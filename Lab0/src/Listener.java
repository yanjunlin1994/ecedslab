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
    
    @Override
    public void run(){
        ServerSocket listener = new ServerSokect(myconfig.getNode(localName).getPort());
        //TODO: should implements Configuration getNode method in Configuration class.
        try {
            while(true) {
                Socket socket = listener.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Thread listenFor = new Thread(new ListenFor(ois));
                listenFor.start();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } 
    }
    
}
