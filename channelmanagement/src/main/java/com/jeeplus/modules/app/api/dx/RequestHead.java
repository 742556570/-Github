/**
 * RequestHead.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jeeplus.modules.app.api.dx;

import com.jeeplus.modules.app.api.product.PropertiesUtil;

public class RequestHead  implements java.io.Serializable {
    private java.lang.String grade;

    private java.lang.String pwd;

    private java.lang.String requestCode;

    private java.lang.String requestTime;

    private java.lang.String serialNo;

    private java.lang.String sysFlag;

    private java.lang.String userId;

    public RequestHead() {
    }

    public RequestHead(
           java.lang.String grade,
           java.lang.String pwd,
           java.lang.String requestCode,
           java.lang.String requestTime,
           java.lang.String serialNo,
           java.lang.String sysFlag,
           java.lang.String userId) {
           this.grade = grade;
           this.pwd = pwd;
           this.requestCode = requestCode;
           this.requestTime = requestTime;
           this.serialNo = serialNo;
           this.sysFlag = sysFlag;
           this.userId = userId;
    }


    /**
     * Gets the grade value for this RequestHead.
     * 
     * @return grade
     */
    public java.lang.String getGrade() {
        return grade;
    }


    /**
     * Sets the grade value for this RequestHead.
     * 
     * @param grade
     */
    public void setGrade(java.lang.String grade) {
        this.grade = grade;
    }


    /**
     * Gets the pwd value for this RequestHead.
     * 
     * @return pwd
     */
    public java.lang.String getPwd() {
        return pwd;
    }


    /**
     * Sets the pwd value for this RequestHead.
     * 
     * @param pwd
     */
    public void setPwd(java.lang.String pwd) {
        this.pwd = pwd;
    }


    /**
     * Gets the requestCode value for this RequestHead.
     * 
     * @return requestCode
     */
    public java.lang.String getRequestCode() {
        return requestCode;
    }


    /**
     * Sets the requestCode value for this RequestHead.
     * 
     * @param requestCode
     */
    public void setRequestCode(java.lang.String requestCode) {
        this.requestCode = requestCode;
    }


    /**
     * Gets the requestTime value for this RequestHead.
     * 
     * @return requestTime
     */
    public java.lang.String getRequestTime() {
        return requestTime;
    }


    /**
     * Sets the requestTime value for this RequestHead.
     * 
     * @param requestTime
     */
    public void setRequestTime(java.lang.String requestTime) {
        this.requestTime = requestTime;
    }


    /**
     * Gets the serialNo value for this RequestHead.
     * 
     * @return serialNo
     */
    public java.lang.String getSerialNo() {
        return serialNo;
    }


    /**
     * Sets the serialNo value for this RequestHead.
     * 
     * @param serialNo
     */
    public void setSerialNo(java.lang.String serialNo) {
        this.serialNo = serialNo;
    }


    /**
     * Gets the sysFlag value for this RequestHead.
     * 
     * @return sysFlag
     */
    public java.lang.String getSysFlag() {
        return sysFlag;
    }


    /**
     * Sets the sysFlag value for this RequestHead.
     * 
     * @param sysFlag
     */
    public void setSysFlag(java.lang.String sysFlag) {
        this.sysFlag = sysFlag;
    }


    /**
     * Gets the userId value for this RequestHead.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this RequestHead.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequestHead)) return false;
        RequestHead other = (RequestHead) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.grade==null && other.getGrade()==null) || 
             (this.grade!=null &&
              this.grade.equals(other.getGrade()))) &&
            ((this.pwd==null && other.getPwd()==null) || 
             (this.pwd!=null &&
              this.pwd.equals(other.getPwd()))) &&
            ((this.requestCode==null && other.getRequestCode()==null) || 
             (this.requestCode!=null &&
              this.requestCode.equals(other.getRequestCode()))) &&
            ((this.requestTime==null && other.getRequestTime()==null) || 
             (this.requestTime!=null &&
              this.requestTime.equals(other.getRequestTime()))) &&
            ((this.serialNo==null && other.getSerialNo()==null) || 
             (this.serialNo!=null &&
              this.serialNo.equals(other.getSerialNo()))) &&
            ((this.sysFlag==null && other.getSysFlag()==null) || 
             (this.sysFlag!=null &&
              this.sysFlag.equals(other.getSysFlag()))) &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId())));
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
        if (getGrade() != null) {
            _hashCode += getGrade().hashCode();
        }
        if (getPwd() != null) {
            _hashCode += getPwd().hashCode();
        }
        if (getRequestCode() != null) {
            _hashCode += getRequestCode().hashCode();
        }
        if (getRequestTime() != null) {
            _hashCode += getRequestTime().hashCode();
        }
        if (getSerialNo() != null) {
            _hashCode += getSerialNo().hashCode();
        }
        if (getSysFlag() != null) {
            _hashCode += getSysFlag().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RequestHead.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.namespace"), "requestHead"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("grade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "grade"));
        elemField.setXmlType(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.xs"), "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pwd");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pwd"));
        elemField.setXmlType(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.xs"), "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestCode"));
        elemField.setXmlType(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.xs"), "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestTime"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sysFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sysFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName(PropertiesUtil.getString("dx.xs"), "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
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
