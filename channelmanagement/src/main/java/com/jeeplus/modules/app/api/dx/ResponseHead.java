/**
 * ResponseHead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jeeplus.modules.app.api.dx;

import com.jeeplus.modules.app.api.product.PropertiesUtil;

public class ResponseHead  implements java.io.Serializable {
    private java.lang.String requestCode;

    private java.lang.String retCode;

    private java.lang.String retDescript;

    private java.lang.String serialNo;

    public ResponseHead() {
    }

    public ResponseHead(
           java.lang.String requestCode,
           java.lang.String retCode,
           java.lang.String retDescript,
           java.lang.String serialNo) {
           this.requestCode = requestCode;
           this.retCode = retCode;
           this.retDescript = retDescript;
           this.serialNo = serialNo;
    }


    /**
     * Gets the requestCode value for this ResponseHead.
     * 
     * @return requestCode
     */
    public java.lang.String getRequestCode() {
        return requestCode;
    }


    /**
     * Sets the requestCode value for this ResponseHead.
     * 
     * @param requestCode
     */
    public void setRequestCode(java.lang.String requestCode) {
        this.requestCode = requestCode;
    }


    /**
     * Gets the retCode value for this ResponseHead.
     * 
     * @return retCode
     */
    public java.lang.String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this ResponseHead.
     * 
     * @param retCode
     */
    public void setRetCode(java.lang.String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the retDescript value for this ResponseHead.
     * 
     * @return retDescript
     */
    public java.lang.String getRetDescript() {
        return retDescript;
    }


    /**
     * Sets the retDescript value for this ResponseHead.
     * 
     * @param retDescript
     */
    public void setRetDescript(java.lang.String retDescript) {
        this.retDescript = retDescript;
    }


    /**
     * Gets the serialNo value for this ResponseHead.
     * 
     * @return serialNo
     */
    public java.lang.String getSerialNo() {
        return serialNo;
    }


    /**
     * Sets the serialNo value for this ResponseHead.
     * 
     * @param serialNo
     */
    public void setSerialNo(java.lang.String serialNo) {
        this.serialNo = serialNo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseHead)) return false;
        ResponseHead other = (ResponseHead) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.requestCode==null && other.getRequestCode()==null) || 
             (this.requestCode!=null &&
              this.requestCode.equals(other.getRequestCode()))) &&
            ((this.retCode==null && other.getRetCode()==null) || 
             (this.retCode!=null &&
              this.retCode.equals(other.getRetCode()))) &&
            ((this.retDescript==null && other.getRetDescript()==null) || 
             (this.retDescript!=null &&
              this.retDescript.equals(other.getRetDescript()))) &&
            ((this.serialNo==null && other.getSerialNo()==null) || 
             (this.serialNo!=null &&
              this.serialNo.equals(other.getSerialNo())));
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
        if (getRequestCode() != null) {
            _hashCode += getRequestCode().hashCode();
        }
        if (getRetCode() != null) {
            _hashCode += getRetCode().hashCode();
        }
        if (getRetDescript() != null) {
            _hashCode += getRetDescript().hashCode();
        }
        if (getSerialNo() != null) {
            _hashCode += getSerialNo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseHead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.namespace"), "responseHead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestCode"));
        elemField.setXmlType(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.xs"), "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "retCode"));
        elemField.setXmlType(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.xs"), "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retDescript");
        elemField.setXmlName(new javax.xml.namespace.QName("", "retDescript"));
        elemField.setXmlType(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.xs"), "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serialNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serialNo"));
        elemField.setXmlType(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.xs"), "string"));
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
