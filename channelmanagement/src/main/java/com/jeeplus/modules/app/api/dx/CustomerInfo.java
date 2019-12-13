/**
 * CustomerInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jeeplus.modules.app.api.dx;

public class CustomerInfo  implements java.io.Serializable {
    private com.jeeplus.modules.app.api.dx.RequestCustInfo[] requestBody;

    private com.jeeplus.modules.app.api.dx.RequestHead requestHead;

    public CustomerInfo() {
    }

    public CustomerInfo(
    		com.jeeplus.modules.app.api.dx.RequestCustInfo[] requestBody,
    		com.jeeplus.modules.app.api.dx.RequestHead requestHead) {
           this.requestBody = requestBody;
           this.requestHead = requestHead;
    }


    /**
     * Gets the requestBody value for this CustomerInfo.
     * 
     * @return requestBody
     */
    public com.jeeplus.modules.app.api.dx.RequestCustInfo[] getRequestBody() {
        return requestBody;
    }


    /**
     * Sets the requestBody value for this CustomerInfo.
     * 
     * @param requestBody
     */
    public void setRequestBody(com.jeeplus.modules.app.api.dx.RequestCustInfo[] requestBody) {
        this.requestBody = requestBody;
    }


    /**
     * Gets the requestHead value for this CustomerInfo.
     * 
     * @return requestHead
     */
    public com.jeeplus.modules.app.api.dx.RequestHead getRequestHead() {
        return requestHead;
    }


    /**
     * Sets the requestHead value for this CustomerInfo.
     * 
     * @param requestHead
     */
    public void setRequestHead(com.jeeplus.modules.app.api.dx.RequestHead requestHead) {
        this.requestHead = requestHead;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomerInfo)) return false;
        CustomerInfo other = (CustomerInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.requestBody==null && other.getRequestBody()==null) || 
             (this.requestBody!=null &&
              java.util.Arrays.equals(this.requestBody, other.getRequestBody()))) &&
            ((this.requestHead==null && other.getRequestHead()==null) || 
             (this.requestHead!=null &&
              this.requestHead.equals(other.getRequestHead())));
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
        if (getRequestBody() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRequestBody());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRequestBody(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRequestHead() != null) {
            _hashCode += getRequestHead().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomerInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webServiceInterface.webServiceCXF.itm.com/", "customerInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestBody");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestBody"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webServiceInterface.webServiceCXF.itm.com/", "requestCustInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "custInfoList"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestHead");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestHead"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://webServiceInterface.webServiceCXF.itm.com/", "requestHead"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
