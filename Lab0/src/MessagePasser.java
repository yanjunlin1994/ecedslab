import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
/**
 * MessagePasser who in charge of sending and receiving message.
 * @author Team 3
 */
public class MessagePasser {
	/** MessagePasser's configuration object. */
	private Configuration myConfig;
	/** MessagePasser's local name. */
	private String myName;
	/** MessagePasser's send queue. */
	private Queue<Message> sendDelayQueue;
	/** MessagePasser's receive queue. */
	private Queue<Message> receiveQueue;
	/** MessagePasser's receive delay queue. */
    private Queue<Message> receiveDelayQueue;
	/**
	 * MessagePasser constructor.
	 * initialize local name,
	 * send queue, receive queue， receive delay queue and configuration file.
	 * start listening on a new thread.
	 */
	public MessagePasser(String configuration_filename, String local_name) {
	    myName = local_name;
	    sendDelayQueue = new ArrayDeque<Message>(10);
	    receiveQueue = new LinkedList<Message>();
	    receiveDelayQueue = new ArrayDeque<Message>(10);
		myConfig = new Configuration(configuration_filename);
		Thread listen = new Thread(new Listener(myConfig, myName, receiveQueue, receiveDelayQueue));
		listen.start(); 
		Thread receive = new Thread(new Receive(receiveQueue));
		receive.start(); 
	}
	public void runNow(){
	    while(true) {	    	
	        Message newMes = this.enterParameter(myName);
	        newMes.set_seqNum(myConfig.getNode(newMes.get_dest()).get_seqN());
	        System.out.println("[runNow:new message]" + newMes);
	        myConfig.getNode(newMes.get_dest()).incre_seqN();
	        System.out.println("[runNow:node sequence number]" + myConfig.getNode(newMes.get_dest()).get_seqN());
		    String checkResult = check(newMes); 
		    if (checkResult != null) {
		        if (checkResult.equals("drop")) {
		            continue;
		        } else if (checkResult.equals("duplicate")) {
		        	Message clone = newMes.clone();
		            send(newMes);
		            send(clone);
		            while (!sendDelayQueue.isEmpty()){
		            	Message msg = sendDelayQueue.poll();
		            	send(msg);
		            }
		        } else if(checkResult.equals("delay")){
		            sendDelayQueue.offer(newMes);
		            
		        }else {
		            System.out.println("[ATTENTION]abnormal checkResult" + checkResult); 
		        }
		    }
		    else {
		    	send(newMes);
	            while (!sendDelayQueue.isEmpty()){
	            	Message msg = sendDelayQueue.poll();
	            	send(msg);
	            }
		    }
	    }
	}
	/**
     * Construct the message from input parameters.
     * @return the message constructed from input parameters.
     */
	private Message enterParameter(String localName) {
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
//            System.out.println("Okay, so your message to be send --");
//            System.out.println("destination:" + inputParam[0] + "  kind:" + 
//                    inputParam[1] + "  content:" + inputParam[2]);
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
	private void send(Message newMes) {
	    System.out.println("[MessagePasser class: send function]");
	    if (newMes == null) {
	        System.out.println("Message is empty, can't send it");
	        return;
	    }
        ObjectOutputStream os = null;
        os = myConfig.get_OSMap(newMes.get_dest());
        if (os != null) {
            try {
                System.out.println("[MessagePasser class: send function: using exsiting output stream.]");
                System.out.println("message to be send is:" + newMes);
                os.writeObject(newMes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("[MessagePasser class: send function: create new output stream...]");
            Node me = myConfig.getNode(myName);
            Node he = myConfig.getNode(newMes.get_dest());
            Socket sck = null;
            try {
                sck = new Socket(he.get_ip(), he.get_port());
//                sck = new Socket("localhost", he.get_port());
                System.out.println("succeed");
                os = new ObjectOutputStream(sck.getOutputStream());
                myConfig.add_OSMap(newMes.get_dest(), os);
                System.out.println("message to be send is:" + newMes);
                os.writeObject(newMes);
            } catch (IOException e) {
                if (sck != null) {
                    try {
                        sck.close();
                    } catch (Exception nestedE) {
                        nestedE.printStackTrace();   
                    }
                } else {
                    e.printStackTrace();
                }
                
            }   
        }   
    }
	/**
	 * check input message against rules in rule list.
	 * @return actions should be taken.
	 */
	private String check(Message newMes) {
	    System.out.println("[check send message]");
	    for (Rule r : myConfig.sendRules) {
	        int result = r.match(newMes);
	        if (result == 1) {
	        	if (r.get_action().equals("dropAfter")){
	        		return null;
	        	}
	            return r.get_action();
	        }
	        if (result == 2){
	            //due to drop after, the after message will be dropped.
	        	return "drop";
	        }
	    }
	    return null;
	}
}
