/**
 *  Mensaje.java This file was auto-generated from WSDL by the Apache Axis 1.3
 *  Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class Mensaje implements java.io.Serializable {
	private String contenido;
	private String tipoMensaje;
	private String destinatario;

	private java.lang.Object __equalsCalc = null;

	private boolean __hashCodeCalc = false;
	/**
	 *  Description of the Field
	 */
	public static final String GLOBAL = "GLOBAL";

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc =
			new org.apache.axis.description.TypeDesc(Mensaje.class, true);


	/**
	 *  Constructor for the Mensaje object
	 */
	public Mensaje() {
	}


	/**
	 *  Constructor for the Mensaje object
	 *
	 *@param  contenido     Description of Parameter
	 *@param  tipoMensaje   Description of Parameter
	 *@param  destinatario  Description of Parameter
	 */
	public Mensaje(String contenido, String tipoMensaje, String destinatario) {
		this.contenido = contenido;
		this.tipoMensaje = tipoMensaje;
		this.destinatario = destinatario;
	}


	/**
	 *  Sets the contenido value for this Mensaje.
	 *
	 *@param  contenido
	 */
	public void setContenido(java.lang.String contenido) {
		this.contenido = contenido;
	}


	/**
	 *  Sets the tipoMensaje value for this Mensaje.
	 *
	 *@param  tipoMensaje
	 */
	public void setTipoMensaje(java.lang.String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}


	/**
	 *  Sets the Destinatario attribute of the Mensaje object
	 *
	 *@param  destinatario  The new Destinatario value
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}


	/**
	 *  Gets the contenido value for this Mensaje.
	 *
	 *@return    contenido
	 */
	public java.lang.String getContenido() {
		return contenido;
	}


	/**
	 *  Gets the tipoMensaje value for this Mensaje.
	 *
	 *@return    tipoMensaje
	 */
	public java.lang.String getTipoMensaje() {
		return tipoMensaje;
	}


	/**
	 *  Gets the Destinatario attribute of the Mensaje object
	 *
	 *@return    The Destinatario value
	 */
	public String getDestinatario() {
		return destinatario;
	}


	/**
	 *  Compares this to the parameter.
	 *
	 *@param  obj  the reference object with which to compare.
	 *@return      <tt>true</tt> if this object is the same as the obj argument;
	 *      <tt>false</tt> otherwise.
	 */
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Mensaje)) {
			return false;
		}
		Mensaje other = (Mensaje) obj;
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true &&
				((this.contenido == null && other.getContenido() == null) ||
				(this.contenido != null &&
				this.contenido.equals(other.getContenido()))) &&
				((this.tipoMensaje == null && other.getTipoMensaje() == null) ||
				(this.tipoMensaje != null &&
				this.tipoMensaje.equals(other.getTipoMensaje()))) &&
				((this.destinatario == null && other.getDestinatario() == null) ||
				(this.destinatario != null &&
				this.destinatario.equals(other.getDestinatario())));
		__equalsCalc = null;
		return _equals;
	}


	/**
	 *  Computes a hash value for this object.
	 *
	 *@return    The hash value for this object.
	 */
	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		if (getContenido() != null) {
			_hashCode += getContenido().hashCode();
		}
		if (getTipoMensaje() != null) {
			_hashCode += getTipoMensaje().hashCode();
		}
		if (getDestinatario() != null) {
			_hashCode += getDestinatario().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}


	/**
	 *  Return type metadata object
	 *
	 *@return    The TypeDesc value
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}


	/**
	 *  Get Custom Serializer
	 *
	 *@param  mechType   Description of Parameter
	 *@param  _javaType  Description of Parameter
	 *@param  _xmlType   Description of Parameter
	 *@return            The Serializer value
	 */
	public static org.apache.axis.encoding.Serializer getSerializer(
			java.lang.String mechType,
			java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return
				new org.apache.axis.encoding.ser.BeanSerializer(
				_javaType, _xmlType, typeDesc);
	}


	/**
	 *  Get Custom Deserializer
	 *
	 *@param  mechType   Description of Parameter
	 *@param  _javaType  Description of Parameter
	 *@param  _xmlType   Description of Parameter
	 *@return            The Deserializer value
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(
			java.lang.String mechType,
			java.lang.Class _javaType,
			javax.xml.namespace.QName _xmlType) {
		return
				new org.apache.axis.encoding.ser.BeanDeserializer(
				_javaType, _xmlType, typeDesc);
	}

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "Mensaje"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("contenido");
		elemField.setXmlName(new javax.xml.namespace.QName("", "contenido"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("tipoMensaje");
		elemField.setXmlName(new javax.xml.namespace.QName("", "tipoMensaje"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("destinatario");
		elemField.setXmlName(new javax.xml.namespace.QName("", "destinatario"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}

}
