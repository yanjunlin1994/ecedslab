import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectOutputStream;

public class MessagePasser {
	private String configFileName;
	private Configuration myConfig;
	private String localName;
	public MessagePasser(String configuration_filename, String local_name) {
		myConfig = new Configuration();
		myConfig.config(configuration_filename);
		Thread listen = new Thread(new Listener(myConfig, localName));
		listen.start(); 
	}
	public void send(String dest, String kind, Object data) {
        Message newMes = new Message(dest, kind, data);
        ObjectOutputStream os = myConfig.get_OSMap(dest);
        if (os != null) {
            
        } else {
            
        }
        
    }

	
	
}
