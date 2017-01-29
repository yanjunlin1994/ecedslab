import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.yaml.snakeyaml.Yaml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Configuration object.
 * Contains send rules, receive rules, nodes map and output stream map.
 * @author Team 3
 */
public class Configuration {
	ArrayList<Rule> sendRules = new ArrayList<Rule>();
	ArrayList<Rule> receiveRules = new ArrayList<Rule>();
	HashMap<String,Node> nodeMap = new HashMap<String,Node>();
	HashMap<String,ObjectOutputStream> OSMap = new HashMap<String,ObjectOutputStream>();
	/**
	 * Configuration constructor based on configuration file.
	 * @param config_fileName configuration file
	 */
	public Configuration(String config_fileName){
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
			Node newNode = new Node((String)node.get("name"), (int)node.get("port"), 
			                       (String)node.get("ip"));
			nodeMap.put((String)node.get("name"),newNode);
		}
		List<HashMap<String, Object>> sRules = (List<HashMap<String, Object>> )data.get("sendRules");
		for (HashMap<String,Object> rule : sRules){
			Rule newRule = new Rule();
			newRule.set_action((String)rule.get("action"));
			newRule.set_dst((String)rule.get("dest"));
			newRule.set_src((String)rule.get("src"));
			newRule.set_kind((String)rule.get("kind"));
			if (rule.get("seqNum") != null) {
			    newRule.set_seqNum((int)rule.get("seqNum"));
			}
			sendRules.add(newRule);
		}
		List<HashMap<String, Object>> rRules = (List<HashMap<String, Object>> )data.get("receiveRules");
		for (HashMap<String,Object> rule : rRules){
			Rule newRule = new Rule();
			newRule.set_action((String)rule.get("action"));
			newRule.set_src((String)rule.get("src"));
			if (rule.get("seqNum") != null) {
			    newRule.set_seqNum((int)rule.get("seqNum"));
			}
			if (rule.get("duplicate") != null) {
			    newRule.set_duplicate((Boolean)rule.get("duplicate")); 
			}
			
			receiveRules.add(newRule);
		}
	}
	public HashMap<String,Node> get_NodeMap(){
		return this.nodeMap;
	}
	public ArrayList<Rule> get_sendRules(){
		return this.sendRules;
	}
	public ArrayList<Rule> get_receiveRules(){
		return this.receiveRules;
	}
	public Node getNode(String name){
		if (nodeMap.containsKey(name)){
			return nodeMap.get(name);
		}
		else{
			return null;
		}
	}
	public ObjectOutputStream get_OSMap(String dest) {
	    if (OSMap.containsKey(dest)) {
	        return OSMap.get(dest);
	    } else {
	        return null;
	    }
	}
	public void add_OSMap(String dest, ObjectOutputStream oos) {
        OSMap.put(dest, oos);
        return;
    }
}
