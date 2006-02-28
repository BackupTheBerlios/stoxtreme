/**
 * Mensaje.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

public class Mensaje  implements java.io.Serializable {
    private java.lang.String contenido;

    private java.lang.String tipoMensaje;

    public Mensaje() {
    }

    public Mensaje(
           java.lang.String contenido,
           java.lang.String tipoMensaje) {
           this.contenido = contenido;
           this.tipoMensaje = tipoMensaje;
    }


    /**
     * Gets the contenido value for this Mensaje.
     * 
     * @return contenido
     */
    public java.lang.String getContenido() {
        return contenido;
    }


    /**
     * Sets the contenido value for this Mensaje.
     * 
     * @param contenido
     */
    public void setContenido(java.lang.String contenido) {
        this.contenido = contenido;
    }


    /**
     * Gets the tipoMensaje value for this Mensaje.
     * 
     * @return tipoMensaje
     */
    public java.lang.String getTipoMensaje() {
        return tipoMensaje;
    }


    /**
     * Sets the tipoMensaje value for this Mensaje.
     * 
     * @param tipoMensaje
     */
    public void setTipoMensaje(java.lang.String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Mensaje)) return false;
        Mensaje other = (Mensaje) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.contenido==null && other.getContenido()==null) || 
             (this.contenido!=null &&
              this.contenido.equals(other.getContenido()))) &&
            ((this.tipoMensaje==null && other.getTipoMensaje()==null) || 
             (this.tipoMensaje!=null &&
              this.tipoMensaje.equals(other.getTipoMensaje())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Mensaje.class, true);

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
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
