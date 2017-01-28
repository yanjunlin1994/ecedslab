import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainTest {
	public static void main(String args[] ){
	    String configFileName = args[0];
	    String myName = args[1];
	    MessagePasser mp = new MessagePasser(configFileName, myName);
	    while(true) {
	        Message newMes = enterParameter(myName);
	        if (newMes != null) {
	            mp.send(newMes);
	        }    
	    }
	}
	private static Message enterParameter(String localName) {
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
}
