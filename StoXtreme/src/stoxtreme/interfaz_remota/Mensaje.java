/**
 *  Mensaje.java This file was auto-generated from WSDL by the Apache Axis 1.3
 *  Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

/**
 *  Clase Mensaje permitimos mediante estos mensajes la comunicación del 
 *  sistema con los agentes y clientes
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
	 *  Tipo de mensaje
	 */
	public static final String GLOBAL = "GLOBAL";

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc =
			new org.apache.axis.description.TypeDesc(Mensaje.class, true);


	/**
	 *  Constructor de Mensaje
	 */
	public Mensaje() {
	}


	/**
	 *  Constructor de Mensaje
	 *
	 *@param  contenido     Contenido que se mandará en el mensaje
	 *@param  tipoMensaje   que tipo de mensaje es alerta, información...
	 *@param  destinatario  A quien va dirigido
	 */
	public Mensaje(String contenido, String tipoMensaje, String destinatario) {
		this.contenido = contenido;
		this.tipoMensaje = tipoMensaje;
		this.destinatario = destinatario;
	}


	/**
	 *  Asignamos un nuevo Contenido
	 *
	 *@param  contenido el nuevo Contenido
	 */
	public void setContenido(java.lang.String contenido) {
		this.contenido = contenido;
	}


	/**
	 *  Asignamos un nuevo tipoMensaje
	 *
	 *@param  tipoMensaje el nuevo Tipo
	 */
	public void setTipoMensaje(java.lang.String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}


	/**
	 *  Asignamos un nuevo Destinatario
	 *
	 *@param  destinatario  el nuevo Destinatario
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}


	/**
	 *  Obtiene el valor de contenido
	 *
	 *@return    Valor de contenido
	 */
	public java.lang.String getContenido() {
		return contenido;
	}


	/**
	 *  Obtiene el valor de tipoMensaje
	 *
	 *@return    Valor de tipoMensaje
	 */
	public java.lang.String getTipoMensaje() {
		return tipoMensaje;
	}


	/**
	 *  Obtiene el valor de Destinatario
	 *
	 *@return    Valor de Destinatario
	 */
	public String getDestinatario() {
		return destinatario;
	}


	/**
	 *  Comparación de dos objetos de tipo Mensaje.
	 *
	 *@param  obj  Objeto referencia al cual estamos comparando el objeto de this.
	 *@return      cierto si son el mismo objeto y falso en caso contrario.
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
	 *  Calcula el valor hash para este objeto.
	 *
	 *@return    Devuelve el valor hash.
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
	 *  DEvuelve el tipo de descripción
	 *
	 *@return    Valor de TypeDesc
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
