import java.io.*;
import org.yaml.snakeyaml.Yaml;
import java.util.*;

public class Configuration {
	ArrayList<Rule> sendRules = new ArrayList<Rule>();
	ArrayList<Rule> receiveRules = new ArrayList<Rule>();
	//ArrayList<Node> Nodes = new ArrayList<Node>();
	HashMap<String,Node> nodeMap = new HashMap<String,Node>();
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
//		System.out.println("buildmap");
		List<HashMap<String, Object>> nodes = (List<HashMap<String, Object>> )data.get("configuration");
		for (HashMap<String, Object> node : nodes){
			Node newNode = new Node();
			String name = (String)node.get("name");
			newNode.set_name(name);
			newNode.set_ip((String)node.get("ip"));
			newNode.set_port((Integer)node.get("port"));
			nodeMap.put(name,newNode);
			//System.out.println(NodetoString(newNode));
		}
		List<HashMap<String, Object>> sRules = (List<HashMap<String, Object>> )data.get("sendRules");
		for (HashMap<String,Object> rule : sRules){
			Rule newRule = new Rule();
			newRule.set_action((String)rule.get("action"));
			newRule.set_dst((String)rule.get("dest"));
			newRule.set_src((String)rule.get("src"));
			newRule.set_kind((String)rule.get("kind"));
			newRule.set_seqNum((Integer)rule.get("seqNum"));
			sendRules.add(newRule);
		}
		List<HashMap<String, Object>> rRules = (List<HashMap<String, Object>> )data.get("receiveRules");
		for (HashMap<String,Object> rule : rRules){
			Rule newRule = new Rule();
			newRule.set_action((String)rule.get("action"));
			newRule.set_src((String)rule.get("src"));
			newRule.set_seqNum((Integer)rule.get("seqNum"));
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
	
	public String NodetoString(Node e){
		StringBuilder sb = new StringBuilder();
		sb.append("name: "+e.get_name()+" ");
		sb.append("ip: "+e.get_ip()+" ");
		sb.append("port: "+e.get_port()+" ");
		return sb.toString();
	}
	
}
