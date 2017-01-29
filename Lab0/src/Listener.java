import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Queue;
/**
 * Listen for all possible clients.
 *
 */
public class Listener implements Runnable{
    /** configuration file. */
    private Configuration myConfig;
    /** listener's name. */
    private String localName;
    private Queue<Message> listenQueue;
    private Queue<Message> listenDelayQueue;
    public Listener(Configuration config, String Name, Queue<Message> receiveQueue, Queue<Message> receiveDelayQueue) {
        this.myConfig = config;
        this.localName = Name;
        this.listenQueue = receiveQueue;
        this.listenDelayQueue = receiveDelayQueue;
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
                    Thread listenFor = new Thread(new ListenFor(ois, listenQueue,listenDelayQueue,myConfig));
                    listenFor.start();
                } catch (IOException e) {
                	try{
                		if (listener != null){
                			listener.close();
                		}
                	}catch(Exception ne){
                		e.printStackTrace();
                	}
                    e.printStackTrace();
                } 
            }
        } catch(IOException e) {
            e.printStackTrace();
        } 
    }
}
