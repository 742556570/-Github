package com.jeeplus.modules.app.api.account.CreditLoanApplyQuery.response;

import com.jeeplus.modules.app.api.account.header.DataHeader;
import com.thoughtworks.xstream.XStream;

public class XStreamUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XStream xstream = new XStream();
		xstream.processAnnotations(CreditLoanApplyQueryResp.class);
		CreditLoanApplyQueryResp obj = new CreditLoanApplyQueryResp();
		DataHeader header = new DataHeader();
		obj.setDataHeader(header);
		header.setPatch("LN20171219152226579812");
		header.setTranBank("FOTIC");
		header.setTranCode("YG0044");
		header.setDataSource("ISP");
		header.setTranTime("20180313134508");
		CreditLoanApplyRespDataBody dataBody = new CreditLoanApplyRespDataBody();
		CreditLoanApplyRespDataRecord dataRecord = new CreditLoanApplyRespDataRecord();
		dataBody.setDataRecord(dataRecord);
		dataRecord.setBusinessId("C820920180122052675");
		dataRecord.setBusidesc("贷款申请成功");
		dataRecord.setBusiStatus("1");
		dataRecord.setHandleCode("00006");
		dataRecord.setHandleInfo("受理成功");
		obj.setDataBody(dataBody);
		 
		String xml = xstream.toXML(obj);
		System.out.println(xml);
		CreditLoanApplyQueryResp obj1 = (CreditLoanApplyQueryResp)xstream.fromXML(xml);
		System.out.println(obj1.getDataHeader().toString());
		System.out.println(obj1.getDataBody().toString());
	}

}
