
public class Node {
	private String name;
	private Integer port;
	private String ip;

	public String get_name(){
		return this.name;
	}
	public void set_name(String n){
		this.name = n;
	}
	public Integer get_port(){
		return this.port;
	}
	public void set_port(Integer p){
		this.port = p;
	}
	public String get_ip(){
		return this.ip;
	}
	public void set_ip(String i){
		this.ip = i;
	}
}
