/**
 * Rule class
 * contains send and receive rule.
 *
 */
public class Rule {
	private String action;
	private String src;
	private String dst;
	private String kind;
	private Integer seqNum;
	
	public Rule() {
	    this.seqNum = -1;
	}
	public Rule(String a, String s, String d, String k, Integer sN) {
	    this.action = a;
	    this.src = s;
	    this.dst = d;
	    this.kind = k;
	    this.seqNum = sN;
	}
	public String get_action(){
        return this.action;
    }
	public void set_action(String a){
		this.action = a;
	}
	public String get_src(){
		return this.src;
	}
	public void set_src(String s){
		this.src = s;
	}
	public String get_dst(){
		return this.dst;
	}
	public void set_dst(String d){
		this.dst = d;
	}
	public String get_kind(){
		return this.kind;
	}
	public void set_kind(String k){
		this.kind = k;
	}
	public Integer get_seqNum(){
		return this.seqNum;
	}
	public void set_seqNum(Integer se){
		this.seqNum = se;
	}
}
