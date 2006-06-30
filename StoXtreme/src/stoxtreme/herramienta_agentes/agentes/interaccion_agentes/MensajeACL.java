package stoxtreme.herramienta_agentes.agentes.interaccion_agentes;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.agentes.IDAgente;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
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
	private Object contenidoObjeto;

	// Identificador de la conversacion
	private IDConversacion conversation_id = null;

	// El mensaje con el que debe responder
	private String reply_with = null;
	// El mensaje al que responde
	private String in_reply_to = null;

	// Numero de ticks que puede sobrevivir el mensaje
	private int reply_by = -1;


	/*
	 *  GETTERS AND SETTERS
	 */
	/**
	 *  Sets the Performative attribute of the MensajeACL object
	 *
	 *@param  performative  The new Performative value
	 */
	public void setPerformative(Performative performative) {
		this.performative = performative;
	}


	/**
	 *  Sets the Protocol attribute of the MensajeACL object
	 *
	 *@param  protocol  The new Protocol value
	 */
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}


	/**
	 *  Sets the Sender attribute of the MensajeACL object
	 *
	 *@param  sender  The new Sender value
	 */
	public void setSender(IDAgente sender) {
		this.sender = sender;
	}


	/**
	 *  Sets the Reply_to attribute of the MensajeACL object
	 *
	 *@param  reply_to  The new Reply_to value
	 */
	public void setReply_to(IDAgente reply_to) {
		this.reply_to = reply_to;
	}


	/**
	 *  Sets the ContenidoString attribute of the MensajeACL object
	 *
	 *@param  contenidoString  The new ContenidoString value
	 */
	public void setContenidoString(String contenidoString) {
		this.contenidoString = contenidoString;
	}


	/**
	 *  Sets the ContenidoObjeto attribute of the MensajeACL object
	 *
	 *@param  contenidoObjeto  The new ContenidoObjeto value
	 */
	public void setContenidoObjeto(Object contenidoObjeto) {
		this.contenidoObjeto = contenidoObjeto;
	}


	/**
	 *  Sets the Conversation_id attribute of the MensajeACL object
	 *
	 *@param  conversation_id  The new Conversation_id value
	 */
	public void setConversation_id(IDConversacion conversation_id) {
		this.conversation_id = conversation_id;
	}


	/**
	 *  Sets the Reply_with attribute of the MensajeACL object
	 *
	 *@param  reply_with  The new Reply_with value
	 */
	public void setReply_with(String reply_with) {
		this.reply_with = reply_with;
	}


	/**
	 *  Sets the In_reply_to attribute of the MensajeACL object
	 *
	 *@param  in_reply_to  The new In_reply_to value
	 */
	public void setIn_reply_to(String in_reply_to) {
		this.in_reply_to = in_reply_to;
	}


	/**
	 *  Sets the Reply_by attribute of the MensajeACL object
	 *
	 *@param  reply_by  The new Reply_by value
	 */
	public void setReply_by(int reply_by) {
		this.reply_by = reply_by;
	}


	/**
	 *  Sets the Receivers attribute of the MensajeACL object
	 *
	 *@param  ids  The new Receivers value
	 */
	public void setReceivers(ArrayList<IDAgente> ids) {
		this.receiver = ids;
	}


	/**
	 *  Gets the Performative attribute of the MensajeACL object
	 *
	 *@return    The Performative value
	 */
	public Performative getPerformative() {
		return performative;
	}


	/**
	 *  Gets the Protocol attribute of the MensajeACL object
	 *
	 *@return    The Protocol value
	 */
	public Protocol getProtocol() {
		return protocol;
	}


	/**
	 *  Gets the Sender attribute of the MensajeACL object
	 *
	 *@return    The Sender value
	 */
	public IDAgente getSender() {
		return sender;
	}


	/**
	 *  Gets the Reply_to attribute of the MensajeACL object
	 *
	 *@return    The Reply_to value
	 */
	public IDAgente getReply_to() {
		return reply_to;
	}


	/**
	 *  Gets the Receiver attribute of the MensajeACL object
	 *
	 *@return    The Receiver value
	 */
	public ArrayList<IDAgente> getReceiver() {
		return receiver;
	}


	/**
	 *  Gets the ContenidoString attribute of the MensajeACL object
	 *
	 *@return    The ContenidoString value
	 */
	public String getContenidoString() {
		return contenidoString;
	}


	/**
	 *  Gets the ContenidoObjeto attribute of the MensajeACL object
	 *
	 *@return    The ContenidoObjeto value
	 */
	public Object getContenidoObjeto() {
		return contenidoObjeto;
	}


	/**
	 *  Gets the Conversation_id attribute of the MensajeACL object
	 *
	 *@return    The Conversation_id value
	 */
	public IDConversacion getConversation_id() {
		return conversation_id;
	}


	/**
	 *  Gets the Reply_with attribute of the MensajeACL object
	 *
	 *@return    The Reply_with value
	 */
	public String getReply_with() {
		return reply_with;
	}


	/**
	 *  Gets the In_reply_to attribute of the MensajeACL object
	 *
	 *@return    The In_reply_to value
	 */
	public String getIn_reply_to() {
		return in_reply_to;
	}


	/**
	 *  Gets the Reply_by attribute of the MensajeACL object
	 *
	 *@return    The Reply_by value
	 */
	public int getReply_by() {
		return reply_by;
	}


	/**
	 *  Gets the Contenido attribute of the MensajeACL object
	 *
	 *@return    The Contenido value
	 */
	public Object getContenido() {
		if (this.contenidoObjeto != null) {
			return contenidoObjeto;
		}
		else if (this.contenidoString != null) {
			return contenidoString;
		}
		else {
			return null;
		}
	}


	// Constructores de los mensajes

	/**
	 *  Description of the Method
	 *
	 *@param  performative  Description of Parameter
	 *@return               Description of the Returned Value
	 */
	public MensajeACL createResponse(Performative performative) {
		MensajeACL m = new MensajeACL();
		m.setIn_reply_to(reply_with);
		m.setPerformative(performative);
		// El protocolo es el mismo si es una respuesta
		m.setProtocol(protocol);
		m.setConversation_id(conversation_id);

		// Si esta especificado a quien hacer la replica, pues la hace
		if (reply_to != null) {
			m.addReceiver(reply_to);
		}
		// Sino, la respuesta la manda al emisor
		else if (sender != null) {
			m.addReceiver(sender);
		}
		return m;
	}


	// Comprueba si el mensaje es correcto
	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public boolean validarMensaje() {
		// Comprueba los campos obligatorios
		return (receiver.size() != 0 &&
				performative != null &&
				conversation_id != null);
	}


	/**
	 *  Adds a feature to the Receiver attribute of the MensajeACL object
	 *
	 *@param  aid  The feature to be added to the Receiver attribute
	 */
	public void addReceiver(IDAgente aid) {
		receiver.add(aid);
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(sender);
		buf.append(",");
		buf.append(receiver);
		buf.append(",");
		buf.append(contenidoString);
		buf.append(",");
		buf.append(protocol);
		buf.append(",");
		buf.append(performative);
		buf.append(",");
		return buf.toString();
	}
}
