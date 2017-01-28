
public class Message {
	private String source;
	private String dest;
	private String kind;
	private Object payload;
	
	private int seqNum;
	private boolean duplicate;
	
	
	public Message(String dest, String kind, Object data) {
		this.dest = dest;
		this.kind = kind;
		this.payload = data;
		this.duplicate = false;
	}
	
	public String get_source() {
		return source;
	}

	public void set_source(String source) {
		this.source = source;
	}

	public String get_dest() {
		return dest;
	}

	public void set_dest(String dest) {
		this.dest = dest;
	}

	public int get_seqNum() {
		return seqNum;
	}

	public void set_seqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	public String get_kind() {
		return kind;
	}

	public void set_kind(String kind) {
		this.kind = kind;
	}

	public boolean get_duplicate() {
		return duplicate;
	}

	public void set_duplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}

	public Object get_payload() {
		return payload;
	}

	public void set_payload(Object data) {
		this.payload = data;
	}
	public String toString() { 
	    return "[source]"+ this.source + " [dest]"+ this.dest +" [kind]"+ this.kind + " [content]" + this.payload;
	}
}
