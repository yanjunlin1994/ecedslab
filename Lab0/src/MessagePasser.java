import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectOutputStream;
/**
 * MessagePasser who in charge of sending and receiving message.
 *
 */
public class MessagePasser {
	/** MessagePasser's configuration object. */
	private Configuration myConfig;
	/** MessagePasser's local name. */
	private String myName;
	/**
	 * MessagePasser constructor.
	 * @param configuration_filename
	 * @param local_name
	 */
	public MessagePasser(String configuration_filename, String local_name) {
	    myName = local_name;
		myConfig = new Configuration(configuration_filename);
		Thread listen = new Thread(new Listener(myConfig, myName));
		listen.start(); 
	}
	/**
	 * Send message to a particular destination.
	 * @param dest destination
	 * @param kind message kind
	 * @param data the data in message
	 */
	public void send(Message newMes) {
        //Message newMes = new Message(localName, dest, kind, data);
        ObjectOutputStream os = null;
        os = myConfig.get_OSMap(newMes.get_dest());
        if (os != null) {
            try {
                //increment sequence number
                myConfig.getNode(newMes.get_dest()).incre_seqN();
                os.writeObject(newMes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } else {
            Node me = myConfig.getNode(myName);
            try {
                Socket sck = new Socket(me.get_ip(), me.get_port());
                os = new ObjectOutputStream(sck.getOutputStream());
                myConfig.add_OSMap(newMes.get_dest(), os);
                os.writeObject(newMes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        
    }

	
	
}
