import java.io.ObjectInputStream;
import java.io.IOException;
/**
 * Listen for a specific client.
 *
 */
public class ListenFor implements Runnable{
    /** the Object Input Stream. */
    private ObjectInputStream ois;
    public ListenFor(ObjectInputStream oistream) {
        this.ois = oistream;
    }
    @Override
    public void run() {
        while(true) {
            try {
                Message newMes = (Message)ois.readObject();
                System.out.println("New message: " + newMes);   
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } 
        }
    }

}
