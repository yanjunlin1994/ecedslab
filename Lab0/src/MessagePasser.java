import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
/**
 * MessagePasser who in charge of sending and receiving message.
 * @author Team 3
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
	public Message enterParameter(String localName) {
        System.out.println("Enter destination, "
                + "message kind and the message content, seperate them with slash :)");
        InputStreamReader isrd = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isrd);
        String[] inputParam = null;
        try {
            String temp = br.readLine();
            inputParam = temp.split("/");
            if (inputParam.length < 3) {
                //wrong input
                System.out.println("oops, illegal input.");
                return null;
            }
            System.out.println("Okay, so your message to be send --");
            System.out.println("destination:" + inputParam[0] + "  kind:" + 
                    inputParam[1] + "  content:" + inputParam[2]);
        } catch(Exception e) {
            e.printStackTrace();
        }   
        try {
            Message newM = new Message(localName, inputParam[0], inputParam[1], inputParam[2]); 
            return newM;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	/**
	 * Send message to a particular destination.
	 * @param dest destination
	 * @param kind message kind
	 * @param data the data in message
	 */
	public void send(Message newMes) {
	    System.out.println("[MessagePasser class: send function]");
	    
        ObjectOutputStream os = null;
        os = myConfig.get_OSMap(newMes.get_dest());
        if (os != null) {
            try {
                System.out.println("[MessagePasser class: send function: using exsiting output stream.]");
                //increment sequence number
                myConfig.getNode(newMes.get_dest()).incre_seqN();
                newMes.set_seqNum(myConfig.getNode(newMes.get_dest()).get_seqN());
                System.out.println(newMes);
                os.writeObject(newMes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("[MessagePasser class: send function: create new output stream...]");
            Node me = myConfig.getNode(myName);
            Node he = myConfig.getNode(newMes.get_dest());
            try {
//                Socket sck = new Socket(he.get_ip(), he.get_port());
                Socket sck = new Socket("localhost", he.get_port());
                System.out.println("succeed");
                os = new ObjectOutputStream(sck.getOutputStream());
                myConfig.add_OSMap(newMes.get_dest(), os);
                System.out.println(newMes);
                os.writeObject(newMes);
            } catch (IOException e) {
                e.printStackTrace();
            }   
        }   
    }	
}
