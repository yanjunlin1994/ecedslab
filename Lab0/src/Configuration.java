import java.io.*;
import org.yaml.snakeyaml.Yaml;
import java.util.*;

public class Configuration {
	ArrayList<Rule> sendRules = new ArrayList<Rule>();
	ArrayList<Rule> receiveRules = new ArrayList<Rule>();
	//ArrayList<Node> Nodes = new ArrayList<Node>();
	HashMap<String,Node> nodeName = new HashMap<String,Node>();
	public void config(String config_fileName){
		InputStream IS = null;
		try {
			IS = new FileInputStream(new File(config_fileName));
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		Yaml yaml = new Yaml();
		Map<String, Object> data = (Map<String, Object>) yaml.load(IS); 
		List<HashMap<String, Object>> nodes = (List<HashMap<String, Object>> )data.get("configuration");
		for (HashMap<String, Object> node : nodes){
			Node newNode = new Node();
		}
	}
}
