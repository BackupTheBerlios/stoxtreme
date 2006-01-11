package interfaz_remota;
import java.util.Hashtable;

public class Mensaje {
	/**
	 * @uml.property  name="msg"
	 */
	private String msg;
	
	public Mensaje(String msg){
		setMsg(msg);
	}

	public Mensaje(Hashtable ht){
		setMsg((String)ht.get("msg"));
	}
	/**
	 * @param msg  The msg to set.
	 * @uml.property  name="msg"
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return  Returns the msg.
	 * @uml.property  name="msg"
	 */
	public String getMsg() {
		return msg;
	}
	
	public Hashtable toHashtable(){
		Hashtable h = new Hashtable();
		h.put("msg", getMsg());
		return h;
	}
}
