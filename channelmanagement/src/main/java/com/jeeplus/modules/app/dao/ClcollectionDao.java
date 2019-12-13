package com.jeeplus.modules.app.dao;
import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClAddressDetails;
import com.jeeplus.modules.app.entity.ClCustomerDetails;
import com.jeeplus.modules.app.entity.ClMobileDetails;
import com.jeeplus.modules.app.entity.ClPolicyDetails;

@MyBatisDao
public interface ClcollectionDao extends CrudDao<ClCustomerDetails> {
    /**
     * 获取客户信息
     * @param CUST_ID
     * @return
     */
	public ClCustomerDetails getCustomerDetails(String CUST_ID);
	/**
	 * 获取地址信息
	 * @param CUST_ID
	 * @return
	 */
	public ClAddressDetails getAddressDetails(String CUST_ID);
	/**
	 * 获取电话信息
	 * @param CUST_ID
	 * @return
	 */
	public ClMobileDetails getMobileDetails(String CUST_ID);
	/**
	 * 获取保单信息
	 * @param CUST_ID
	 * @return
	 */
	public ClPolicyDetails getPolicyDetails(String CUST_ID);


}
