import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MessagePasser {
	private String configFileName;
	private Configuration myConfig;
	private String localName;
	public MessagePasser(String configuration_filename, String local_name) {
	    localName = local_name;
		myConfig = new Configuration();
		myConfig.config(configuration_filename);
		
		Thread listen = new Thread(new Listener(myConfig, localName));
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
            Node me = myConfig.getNode(localName);
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
