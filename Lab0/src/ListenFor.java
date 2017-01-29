import java.io.ObjectInputStream;
import java.util.Queue;
import java.io.IOException;
/**
 * Listen for a specific client.
 *
 */
public class ListenFor implements Runnable{
    /** the Object Input Stream. */
    private ObjectInputStream ois;
    private Queue<Message> listenQueue;
    public ListenFor(ObjectInputStream oistream, Queue listenQ) {
        this.ois = oistream;
        this.listenQueue = listenQ;
    }
    @Override
    public void run() {
        while(true) {
            try {
                Message newMes = (Message)ois.readObject();
                //
                System.out.println("New message! --");
                System.out.println(newMes.toString());   
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } 
        }
    }

}
