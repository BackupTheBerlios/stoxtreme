package sist_mensajeria.mensajes;
import java.util.Hashtable;

public class Mensaje {
	private String msg;
	
	public Mensaje(String msg){
		setMsg(msg);
	}

	public Mensaje(Hashtable ht){
		setMsg((String)ht.get("msg"));
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
	
	public Hashtable toHashtable(){
		Hashtable h = new Hashtable();
		h.put("msg", getMsg());
		return h;
	}
}
