import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainTest {
	public static void main(String args[]){
	    String configFileName = args[0];
	    String myName = args[1];
	    MessagePasser mp = new MessagePasser(configFileName, myName);
	    mp.runNow();
	}
	
}
