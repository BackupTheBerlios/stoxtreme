package stoxtreme.herramienta_agentes.agentes.interaccion_agentes;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.agentes.IDAgente;

public class MensajeACL {
	// Tipo de acto de comunicacion
	private Performative performative = null;
	// Protocolo
	private Protocol protocol = null;

	// ID del agente emisor
	private IDAgente sender = null;
	// ID del agente al que tiene que madar la replica
	private IDAgente reply_to = null;
	// ID de los agente receptor
	private ArrayList<IDAgente> receiver = new ArrayList<IDAgente>();
	
	// Contenido del mensaje
	private String contenidoString;
	private Object contenidoObject;
	
	
	// Identificador de la conversacion
	private IDConversacion conversation_id = null;
	
	// El mensaje con el que debe responder
	private String reply_with = null;
	// El mensaje al que responde
	private String in_reply_to = null;
	
	// Numero de ticks que puede sobrevivir el mensaje
	private int reply_by = -1;
	
	// Constructores de los mensajes
	
	public MensajeACL createResponse(Performative performative){
		MensajeACL m = new MensajeACL();
		m.setIn_reply_to(reply_with);
		m.setPerformative(performative);
		// El protocolo es el mismo si es una respuesta
		m.setProtocol(protocol);
		m.setConversation_id(conversation_id);
		
		// Si esta especificado a quien hacer la replica, pues la hace
		if(reply_to != null){
			m.addReceiver(reply_to);
		}
		// Sino, la respuesta la manda al emisor
		else if (sender != null){
			m.addReceiver(sender);
		}
		return m;
	}
	
	// Comprueba si el mensaje es correcto
	public boolean validarMensaje(){
		// Comprueba los campos obligatorios
		return(	receiver.size()!=0	&& 
				performative != null && 
				conversation_id != null);
	}
	
	/* GETTERS AND SETTERS*/
	public void setPerformative(Performative performative) {
		this.performative = performative;
	}

	public Performative getPerformative() {
		return performative;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setSender(IDAgente sender) {
		this.sender = sender;
	}

	public IDAgente getSender() {
		return sender;
	}

	public void setReply_to(IDAgente reply_to) {
		this.reply_to = reply_to;
	}

	public IDAgente getReply_to() {
		return reply_to;
	}

	public void addReceiver(IDAgente aid) {
		receiver.add(aid);
	}

	public ArrayList<IDAgente> getReceiver() {
		return receiver;
	}

	public void setContenidoString(String contenidoString) {
		this.contenidoString = contenidoString;
	}

	public String getContenidoString() {
		return contenidoString;
	}

	public void setContenidoObject(Object contenidoObject) {
		this.contenidoObject = contenidoObject;
	}

	public Object getContenidoObject() {
		return contenidoObject;
	}

	public void setConversation_id(IDConversacion conversation_id) {
		this.conversation_id = conversation_id;
	}

	public IDConversacion getConversation_id() {
		return conversation_id;
	}

	public void setReply_with(String reply_with) {
		this.reply_with = reply_with;
	}

	public String getReply_with() {
		return reply_with;
	}

	public void setIn_reply_to(String in_reply_to) {
		this.in_reply_to = in_reply_to;
	}

	public String getIn_reply_to() {
		return in_reply_to;
	}

	public void setReply_by(int reply_by) {
		this.reply_by = reply_by;
	}

	public int getReply_by() {
		return reply_by;
	}

	public void setReceivers(ArrayList<IDAgente> ids) {
		this.receiver = ids;
	}

	public Object getContenido() {
		if(this.contenidoObject != null){
			return contenidoObject;
		}
		else if(this.contenidoString != null){
			return contenidoString;
		}
		else return null;
	}
}
