/**
 * ResponseInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jeeplus.modules.app.api.dx;

import com.jeeplus.modules.app.api.product.PropertiesUtil;

public class ResponseInfo  implements java.io.Serializable {
    private com.jeeplus.modules.app.api.dx.ResponseCustInfo[] responseBody;

    private com.jeeplus.modules.app.api.dx.ResponseHead responseHead;

    public ResponseInfo() {
    }

    public ResponseInfo(
    		com.jeeplus.modules.app.api.dx.ResponseCustInfo[] responseBody,
    		com.jeeplus.modules.app.api.dx.ResponseHead responseHead) {
           this.responseBody = responseBody;
           this.responseHead = responseHead;
    }


    /**
     * Gets the responseBody value for this ResponseInfo.
     * 
     * @return responseBody
     */
    public com.jeeplus.modules.app.api.dx.ResponseCustInfo[] getResponseBody() {
        return responseBody;
    }


    /**
     * Sets the responseBody value for this ResponseInfo.
     * 
     * @param responseBody
     */
    public void setResponseBody(com.jeeplus.modules.app.api.dx.ResponseCustInfo[] responseBody) {
        this.responseBody = responseBody;
    }


    /**
     * Gets the responseHead value for this ResponseInfo.
     * 
     * @return responseHead
     */
    public com.jeeplus.modules.app.api.dx.ResponseHead getResponseHead() {
        return responseHead;
    }


    /**
     * Sets the responseHead value for this ResponseInfo.
     * 
     * @param responseHead
     */
    public void setResponseHead(com.jeeplus.modules.app.api.dx.ResponseHead responseHead) {
        this.responseHead = responseHead;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseInfo)) return false;
        ResponseInfo other = (ResponseInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.responseBody==null && other.getResponseBody()==null) || 
             (this.responseBody!=null &&
              java.util.Arrays.equals(this.responseBody, other.getResponseBody()))) &&
            ((this.responseHead==null && other.getResponseHead()==null) || 
             (this.responseHead!=null &&
              this.responseHead.equals(other.getResponseHead())));
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
        if (getResponseBody() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResponseBody());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResponseBody(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getResponseHead() != null) {
            _hashCode += getResponseHead().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.namespace"), "responseInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseBody");
        elemField.setXmlName(new javax.xml.namespace.QName("", "responseBody"));
        elemField.setXmlType(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.namespace"), "responseCustInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "custInfoList"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseHead");
        elemField.setXmlName(new javax.xml.namespace.QName("", "responseHead"));
        elemField.setXmlType(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.namespace"), "responseHead"));
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
