/**
 * Message class.
 * 
 *
 */
public class Message {
	private String source;
	private String dest;
	private String kind;
	private Object payload;
	
	private int seqNum;
	private boolean duplicate;
	
	
	public Message(String s, String d, String k, Object data) {
	    this.source = s;
		this.dest = d;
		this.kind = k;
		this.payload = data;
		this.duplicate = false;
		this.seqNum = 0;
	}
	
	public String get_source() {
		return source;
	}

	public void set_source(String source) {
		this.source = source;
		return;
	}

	public String get_dest() {
		return dest;
	}

	public void set_dest(String dest) {
		this.dest = dest;
		return;
	}

	public int get_seqNum() {
		return seqNum;
	}

	public void set_seqNum(int seqNum) {
		this.seqNum = seqNum;
		return;
	}

	public String get_kind() {
		return kind;
	}

	public void set_kind(String kind) {
		this.kind = kind;
		return;
	}

	public boolean get_duplicate() {
		return duplicate;
	}

	public void set_duplicate(boolean duplicate) {
		this.duplicate = duplicate;
		return;
	}

	public Object get_payload() {
		return payload;
	}

	public void set_payload(Object data) {
		this.payload = data;
		return;
	}
	public String toString() { 
	    return "[source]"+ this.source + " [dest]"+ this.dest +" [kind]"+ this.kind + " [content]" + this.payload;
	}
}
