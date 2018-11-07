package com.triggle.fitnesse;

import org.apache.commons.lang.StringUtils;

public class ActionController extends fit.Fixture {

	private RestController rc = new RestController();
	private int iUserId = -1;
	private String strToken = null;
	private String strUsername = null;
	private String strPassword = null;
	private String strName = null;
	private String strSurname = null;
	private String strPhone = null;
	private String strEmail = null;
	private int iStatus = -1;
        private String strUrl = null;
        private String strTermsAccepted = null;

        public String getTermsAccepted() {
            return this.strTermsAccepted;
        }

        public void setTermsAccepted(String strTermsAccepted) {
            this.strTermsAccepted = strTermsAccepted;
        }

        public String getUrl() {
            return this.strUrl;
        }

        public void setUrl(String strUrl) {
            this.strUrl = strUrl;
        }
        
	public void setUsername(String strUsername) {
		this.strUsername = StringUtils.replace(strUsername, "'", "");
	}
	public String getUsername() {
		return this.strUsername;
	}
	public void setPassword(String strPassword) {
		this.strPassword = strPassword;
	}
	public String getPassword() {
		return strPassword;
	}
	public String getName() {
		return strName;
	}
	public String getSurname() {
		return strSurname;
	}
	public String getPhone() {
		return strPhone;
	}
	public String getEmail() {
		return strEmail;
	}
	public int getUserId() {
		return this.iUserId;
	}
	public int getStatus() {
		return this.iStatus;
	}
	public void setName(String strName) {
		this.strName = strName;
	}
	public void setSurname(String strSurname) {
		this.strSurname = strSurname;
	}
	public void setPhone(String strPhone) {
		this.strPhone = strPhone;
	}
	public void setEmail(String strEmail) {
		this.strEmail = strEmail;
	}
	public void setUserId(int iUserId) {
		this.iUserId = iUserId;
	}
	public void setStatus(int iStatus) {
		this.iStatus = iStatus;
	}

	//// Methods ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void login() {
		rc.setUsername(this.getUsername());
		rc.setPassword(this.getPassword());
		rc.login();
		this.strToken = rc.getToken();
	}
	
	public void createUser() {
		rc.setName(this.getName());
		rc.setSurname(this.getSurname());
		rc.setEmail(this.getEmail());
		rc.setPassword(this.getPassword());
		rc.setUsername(this.getUsername());
                rc.setPhone(this.getPhone());
                rc.setUrl(this.getUrl());
                rc.setTermsAccepted(this.getTermsAccepted());
		this.setUserId(rc.createUser());
	}
	
	public int checkUserId() {
		rc.setUserId(this.getUserId());
		this.setUsername(rc.checkUserId());
		this.setStatus(rc.getStatus());
		return this.getStatus();
	}

	public int deleteUser() {
		rc.setUserId(this.getUserId());
		this.setStatus(rc.deleteUser());
		return this.getStatus();
	}
	
	public int updateUser() {
		rc.setUserId(this.getUserId());
		this.setStatus(rc.updateUser());
		return this.getStatus();
	}
	
	public int verifyToken() {
		rc.verifyToken();
		return this.getStatus();
	}
	
	public void changePassword(String strNewPassword) {
		try {
			String strReturn = rc.changePassword();
			if (StringUtils.isNotEmpty(strReturn))
				throw new RuntimeException(strReturn);
		} catch (Exception e) {
			System.out.println("changePassword catched Exception");
			throw new RuntimeException(e.toString());
		}
	}

	public String logindata() {
		return (rc.getToken() != null && rc.getToken().length() > 0) ? "successful" : "message";
	}
	
}
