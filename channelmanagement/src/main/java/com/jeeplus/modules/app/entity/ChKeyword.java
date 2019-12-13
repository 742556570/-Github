package com.jeeplus.modules.app.entity;

import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

public class ChKeyword extends DataEntity<ChKeyword> {

	private String keyWord;
	private String cityShortName;
	private String cityName;
	private String date;
	private String channelName;
	private String channelCde;
	private String device;
	private String keyWordParameter;

	public String getKeyWord() {
	     return keyWord;
	}

	public void setKeyWord(String keyWord) {
	        this.keyWord = keyWord;
	}

	    public String getCityShortName() {
	        return cityShortName;
	    }

	    public void setCityShortName(String cityShortName) {
	        this.cityShortName = cityShortName == null ? null : cityShortName.trim();
	    }
	    public String getCityName() {
	        return cityName;
	    }

	    public void setCityName(String cityName) {
	        this.cityName = cityName == null ? null : cityName.trim();
	    }
	
	    public String getDate() {
	        return date;
	    }

	    public void setDate(String date) {
	        this.date = date == null ? null : date.trim();
	    }
	    public String getChannelName() {
	        return channelName;
	    }

	    public void setChannelName(String channelName) {
	        this.channelName = channelName == null ? null : channelName.trim();
	    }
	    
	    public String getChannelCde() {
	        return channelCde;
	    }

	    public void setChannelCde(String channelCde) {
	        this.channelCde = channelCde == null ? null : channelCde.trim();
	    }
	    
	    public String getDevice() {
	        return device;
	    }

	    public void setDevice(String device) {
	        this.device = device == null ? null : device.trim();
	    }
	    
	    public String getKeyWordParameter() {
	        return keyWordParameter;
	    }

	    public void setKeyWordParameter(String keyWordParameter) {
	        this.keyWordParameter = keyWordParameter == null ? null : keyWordParameter.trim();
	    }
	
}
