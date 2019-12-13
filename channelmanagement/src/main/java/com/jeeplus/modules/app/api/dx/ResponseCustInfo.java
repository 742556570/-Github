/**
 * ResponseCustInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jeeplus.modules.app.api.dx;

import com.jeeplus.modules.app.api.product.PropertiesUtil;

public class ResponseCustInfo  implements java.io.Serializable {
    private java.lang.String custID;

    private java.lang.String retCode;

    private java.lang.String retDescript;

    public ResponseCustInfo() {
    }

    public ResponseCustInfo(
           java.lang.String custID,
           java.lang.String retCode,
           java.lang.String retDescript) {
           this.custID = custID;
           this.retCode = retCode;
           this.retDescript = retDescript;
    }


    /**
     * Gets the custID value for this ResponseCustInfo.
     * 
     * @return custID
     */
    public java.lang.String getCustID() {
        return custID;
    }


    /**
     * Sets the custID value for this ResponseCustInfo.
     * 
     * @param custID
     */
    public void setCustID(java.lang.String custID) {
        this.custID = custID;
    }


    /**
     * Gets the retCode value for this ResponseCustInfo.
     * 
     * @return retCode
     */
    public java.lang.String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this ResponseCustInfo.
     * 
     * @param retCode
     */
    public void setRetCode(java.lang.String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the retDescript value for this ResponseCustInfo.
     * 
     * @return retDescript
     */
    public java.lang.String getRetDescript() {
        return retDescript;
    }


    /**
     * Sets the retDescript value for this ResponseCustInfo.
     * 
     * @param retDescript
     */
    public void setRetDescript(java.lang.String retDescript) {
        this.retDescript = retDescript;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseCustInfo)) return false;
        ResponseCustInfo other = (ResponseCustInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.custID==null && other.getCustID()==null) || 
             (this.custID!=null &&
              this.custID.equals(other.getCustID()))) &&
            ((this.retCode==null && other.getRetCode()==null) || 
             (this.retCode!=null &&
              this.retCode.equals(other.getRetCode()))) &&
            ((this.retDescript==null && other.getRetDescript()==null) || 
             (this.retDescript!=null &&
              this.retDescript.equals(other.getRetDescript())));
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
        if (getCustID() != null) {
            _hashCode += getCustID().hashCode();
        }
        if (getRetCode() != null) {
            _hashCode += getRetCode().hashCode();
        }
        if (getRetDescript() != null) {
            _hashCode += getRetDescript().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseCustInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.namespace"), "responseCustInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "custID"));
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
