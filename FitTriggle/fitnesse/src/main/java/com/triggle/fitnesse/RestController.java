package com.triggle.fitnesse;


import com.triggle.utils.SortJson;
import com.triggle.utils.ManageMessages;
import com.triggle.utils.JSONPath;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import java.text.SimpleDateFormat;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.util.concurrent.TimeUnit;


public class RestController extends fit.Fixture {

	private final static String DEAULTUSER = "fitnesse.admin";
	private final static String DEAULTPASSWORD = "1234";
	private final static String HOST = "http://srvaxitrgkub010";
	private String strContent = "";
	private int iUserId = -1;
	private int iStatus = -1;
	private int iPage = 1;
	private int iStockId = 0;
	private String strStockName = null;
	private String strUsername = null;
	private String strName = null;
	private String strSurname = null;
	private String strPhone = null;
	private String strEmail = null;
	private String strPassword = null;
	private String strVerificationToken = null;
        private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        private final int iLevel = 1;
        private String strToken = null;
        private String strUrl = null;
	private String strTermsAccepted = null;

        private String body = null;
        private String parameters = null;
        private String host = null;
        private String port = null;
        private String httpMethod = null;
        private String service = null;
        private String response = null;
        private String status = null;
        
        private String pathExpression = null;
        private String testCase = null;
        private int strTime;

	public RestController() {
	}

	// ---------------------------------------------------------------------------------------------------------------
	// Getter and Setter
	// -------------------------- -------------------------------------------------------------------------------------

        public String getTestCase() {
            return this.testCase;
        }

        public void setTestCase(String testCase) {
            this.testCase = testCase;
        }

        public String getPathExpression() {
            return this.pathExpression;
        }

        public void setPathExpression(String pathExpression) {
            this.pathExpression = pathExpression;
        }

    
        public int getTime() {
            return strTime;
        }

        public void setTime(int strTime) {
            this.strTime = strTime;
        }

         public String setDelay() throws InterruptedException {
            TimeUnit.SECONDS.sleep(this.getTime());
            return "OK";
        }
        
        public String getBody() {
            return this.body;
        }

        public void setBody(String body) {
            this.body = body;
        }
        
       public String getParameters() {
            return this.parameters;
        }

        public void setParameters(String parameters) {
            this.parameters = parameters;
        }

        public String getHost() {
            return this.host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPort() {
            return this.port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getHttpMethod() {
            return this.httpMethod;
        }

        public void setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
        }

        public String getService() {
            return this.service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getResponse() {
            return this.response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

	public int getUserId() {
		return iUserId;
	}
	public void setUserId(int iUserId) {
		this.iUserId = iUserId;
	}
        
	public String getUrl() {
		return this.strUrl;
	}
	public void setUrl(String strUrl) {
		this.strUrl = strUrl;
	}
        
       	public String getTermsAccepted() {
		return this.strTermsAccepted;
	}
	public void setTermsAccepted(String strTermsAccepted) {
		this.strTermsAccepted = strTermsAccepted;
	}
         
	public int getStockId() {
		return iStockId;
	}
	public void setStockId(int iStockId) {
		this.iStockId = iStockId;
	}
	public int getPage() {
		return iPage;
	}
	public void setPage(int iPage) {
		this.iPage = iPage;
	}
	public String getStockName() {
		return this.strStockName;
	}
	public void setStockName(String strStockName) {
		this.strStockName = strStockName;
	}
	public int getStatus() {
		return iStatus;
	}
	public void setStatus(int iStatus) {
		this.iStatus = iStatus;
	}
	public String getContent() {
		return strContent;
	}
	public void setContent(String strContent) {
		this.strContent = strContent;
	}
	public String getUsername() {
		return strUsername;
	}
	public void setUsername(String strUsername) {
		this.strUsername = StringUtils.replace(strUsername, "'", "");
	}
	public String getPassword() {
		return strPassword;
	}
	public void setPassword(String strPassword) {
		this.strPassword = strPassword;
	}
	public String getToken() {
		return strToken;
	}
	public void setToken(String strToken) {
		this.strToken = strToken;
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
	public String getVerificationToken() {
		return strVerificationToken;
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
	public void setVerificationToken(String strVerificationToken) {
		this.strVerificationToken = strVerificationToken;
	}

	// ---------------------------------------------------------------------------------------------------------------
	// Helper methods
	// ---------------------------------------------------------------------------------------------------------------


	public ClientResponse get(String strUrl) throws Exception {
		Client client = Client.create();
		WebResource webResource = client.resource(strUrl);
          
                              ClientResponse response = webResource
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.header("Authorization", "Basic " + this.getToken())
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		return response;
	}

        public ClientResponse post(String strUrl, String strBody) throws Exception {

            Client client = Client.create();
            WebResource webResource = client.resource(strUrl);

            ManageMessages.printMessage(1, "........................." + this.getTestCase() + ".........................\n", this.getClass().getName());
            ManageMessages.printMessage(1, ".......... Body : " + strBody, this.getClass().getName());
            ManageMessages.printMessage(1, ".......... URI :" + strUrl, this.getClass().getName());
            ManageMessages.printMessage(1, ".......... Http Method : " + this.getHttpMethod(), this.getClass().getName());
            ManageMessages.printMessage(1, ".......... Token : " + this.getToken(), this.getClass().getName());
            ManageMessages.printMessage(1, ".......................................................... \n", this.getClass().getName());

            ClientResponse response = webResource
                    .header("Content-Type", MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + this.getToken())
                    .accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, strBody);

            return response;
        }

	public String login(String strUsername, String strPassword) {
		this.setUsername(strUsername);
		this.setPassword(strPassword);
		return this.login();
	}

	public String login() {
		try {


			JSONObject jsonRQ = new JSONObject();
			jsonRQ.element("username", this.getUsername());
			jsonRQ.element("password", this.getPassword());
			jsonRQ.element("minutes", 100);

			ManageMessages.printMessage(1, "login with username: " + this.getUsername() + "/" + this.getPassword(),this.getClass().getName());	
			Client client = Client.create();
			WebResource webResource = client.resource(HOST + ":30020/login");

			ClientResponse response = webResource.header("Content-Type", MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, jsonRQ.toString());

			String strOutput = response.getEntity(String.class);
			ManageMessages.printMessage(1, "login output from server: .... \n",this.getClass().getName());
			ManageMessages.printMessage(1, strOutput,this.getClass().getName());
			JSONObject jsonRS = (JSONObject) JSONSerializer.toJSON(strOutput);

			if (jsonRS.has("code")) {
				return jsonRS.getString("code");
			}

			this.setToken(jsonRS.getString("token"));
			ManageMessages.printMessage(1, "Token: " + this.getToken(),this.getClass().getName());
			return (this.getToken().length() > 0) ? "true" : "false";

	  } catch (Exception e) {
	  	ManageMessages.printMessage(2, "Error executing login: " + e.toString(),this.getClass().getName());
	  	e.printStackTrace();
	  }
		return "";
	}

	// ---------------------------------------------------------------------------------------------------------------
	// Implementation of Fitnesse Services
	// ---------------------------------------------------------------------------------------------------------------
        
        /*
             * get the json response
         */
        
        public String checkResponse() throws IOException {

            String strOutput = null;
            ClientResponse response = null;
            Client client;
            JsonElement jsonElement;
            Gson gson;
            String service = null;

            try {

                if ("".equals(this.getBody()) && "".equals(this.getHost()) && "".equals(this.getPort())) {

                    strOutput = "-";
                    this.setStatus(0);

                } else {

                    if ((this.getService().contains("graphql") && this.getBody().contains("login")) && this.getHttpMethod().contains("post")) {

                        service = this.getService();
                        response = this.post(this.getHost() + ":" + this.getPort() + this.getService() + this.getParameters(), this.getBody());
                        strOutput = response.getEntity(String.class);
                        this.setStatus(response.getStatus());
                        ManageMessages.printMessage(1, "Output from server1: ...." + strOutput + "\n", this.getClass().getName());
                        JSONObject jsonRS = (JSONObject) JSONSerializer.toJSON(strOutput);
                        JSONObject jsonData = jsonRS.getJSONObject("data");
                        JSONObject jsonlogin = jsonData.getJSONObject("login");
                        this.setToken(jsonlogin.getString("token"));
                        this.setResponse(SortJson.sort(strOutput));
                        
                    } else if ((this.getService().contains("graphql")) && this.getHttpMethod().contains("post")) {

                        response = this.post(this.getHost() + ":" + this.getPort() + this.getService() + this.getParameters(), this.getBody());
                        strOutput = response.getEntity(String.class);
                        this.setStatus(response.getStatus());
                        ManageMessages.printMessage(1, "Output from server1: ...." + strOutput + "\n", this.getClass().getName());


                    } else if ((this.getService().contains("list")) && this.getHttpMethod().contains("post")) {

                        service = this.getService();

                        if ((this.getService().contains("[0]"))) {
                            this.setService(this.getService().substring(0, this.getService().indexOf("[")));
                        }

                        
                        response = this.post(this.getHost() + ":" + this.getPort() + this.getService() + this.getParameters(), this.getBody());
                        strOutput = response.getEntity(String.class);
                        this.setStatus(response.getStatus());
                        ManageMessages.printMessage(1, "Output from server2: ...." + strOutput + "\n", this.getClass().getName());
                        //JSONArray jsonArr = (JSONArray) JSONSerializer.toJSON(strOutput);
                         //JSONObject jsonRS = (JSONObject) JSONSerializer.toJSON(strOutput);
                        //if ((service.contains("[0]"))) {
                        //}
                        
                        jsonElement = new JsonParser().parse(SortJson.sort(strOutput));
                        gson = new GsonBuilder().setPrettyPrinting().create();
                        strOutput = gson.toJson(jsonElement);
 

                    } else if ((this.getService().contains("user") || this.getService().contains("region") || this.getService().contains("resort") || this.getService().contains("stock") || this.getService().contains("company")) && this.getHttpMethod().contains("post")) {

                        if (this.getParameters() != null) {
                            response = this.post(this.getHost() + ":" + this.getPort() + this.getService() + this.getParameters(), this.getBody().trim());
                        } else {
                            response = this.post(this.getHost() + ":" + this.getPort() + this.getService(), this.getBody().trim());

                        }

                        strOutput = response.getEntity(String.class);
                        this.setStatus(response.getStatus());
                        ManageMessages.printMessage(1, "Output from server: ...." + strOutput + "\n", this.getClass().getName());


                    } else if (this.getService().contains("login") && this.getHttpMethod().contains("post")) {

                        client = Client.create();
                        WebResource webResource = client.resource(this.getHost() + ":" + this.getPort() + this.getService());
                        response = webResource.header("Content-Type", MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, this.getBody());
                        strOutput = response.getEntity(String.class);
                        this.setStatus(response.getStatus());
                        ManageMessages.printMessage(1, "User login output from server··: ...." + strOutput + "\n", this.getClass().getName());
                        JSONObject jsonRS = (JSONObject) JSONSerializer.toJSON(strOutput);
                        JSONObject jsonData = jsonRS.getJSONObject("data");
                        this.setToken(jsonData.getString("token"));
                        this.setResponse(SortJson.sort(strOutput));

                    } else if (this.getHttpMethod().contains("delete")) {

                        client = Client.create();
                        this.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBRE1JTiIsImp0aSI6IjUwMTU2IiwiZXhwIjoxNTcxNzQ5NjAyLCJtaW51dGVzIjo1MjU2MDAsInRva2VuUGFyYW1ldGVycyI6IntcInVzZXJJZFwiOjUwMTU2LFwiYXV0aFJvbGVzXCI6W3tcInJvbGVOYW1lXCI6XCJQQVJUTkVSXCIsXCJjcnVkZVwiOjMxLFwiY2xhc3NOYW1lXCI6XCJjb20udHJpZ2dsZS5taWRkbGV3YXJlLm1vZGVsLmF1dGguUm9sZURlZmluaXRpb25cIn0se1wicm9sZU5hbWVcIjpcIkFETUlOXCIsXCJjcnVkZVwiOjMxLFwiY2xhc3NOYW1lXCI6XCJjb20udHJpZ2dsZS5taWRkbGV3YXJlLm1vZGVsLmF1dGguUm9sZURlZmluaXRpb25cIn1dLFwicm9sZXNCeUtleVwiOnt9fSJ9.ZDcxdg7DJp6BTf5K-x--4Ynzfjwak88QUDXgRbe9Wg0");        
                        WebResource webResource = client.resource(this.getHost() + ":" + this.getPort() + this.getService() + this.getParameters());
                        response = webResource.header("Content-Type", MediaType.APPLICATION_JSON).header("Authorization", "Basic " + this.getToken()).accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
                        strOutput = response.getEntity(String.class);
                        ManageMessages.printMessage(1, "Delete User output from server4: ...." + strOutput + "\n", this.getClass().getName());
                        this.setStatus(response.getStatus());

                    } else if (this.getService().contains("verify")) {

                        response = this.get(this.getHost() + ":" + this.getPort() + this.getService() + "?token=" + this.getParameters());
                        strOutput = response.getEntity(String.class);
                        this.setStatus(response.getStatus());
                        ManageMessages.printMessage(1, "verifyToken output from server5: .... with status: " + response.getStatus(), this.getClass().getName());
                        ManageMessages.printMessage(1, "verifyToken response: " + strOutput + "\n", this.getClass().getName());

                    } else if (this.getHttpMethod().contains("get")) {

                        if (this.getParameters() != null) {
                            response = this.get(this.getHost() + ":" + this.getPort() + this.getService() + this.getParameters());
                        } else {
                            response = this.get(this.getHost() + ":" + this.getPort() + this.getService());

                        }
                                 
                        strOutput = response.getEntity(String.class);
                        this.setStatus(response.getStatus());
                        ManageMessages.printMessage(1, "Output from server6: .... with status: " + response.getStatus(), this.getClass().getName());
                        ManageMessages.printMessage(1, "Response: " + strOutput + "\n", this.getClass().getName());
                        jsonElement = new JsonParser().parse(SortJson.sort(strOutput));
                        gson = new GsonBuilder().setPrettyPrinting().create();
                        strOutput = gson.toJson(jsonElement);

                    } else {

                        service = this.getService();

                        if ((this.getService().contains("[0]"))) {
                            this.setService(this.getService().substring(0, this.getService().indexOf("[")));
                        }

                        response = this.post(this.getHost() + ":" + this.getPort() + this.getService() + this.getParameters(), this.getBody());
                        strOutput = response.getEntity(String.class);
                        this.setStatus(response.getStatus());
                        ManageMessages.printMessage(1, "Output from server7: ...." + strOutput + "\n", this.getClass().getName());
                        JSONArray jsonArr = (JSONArray) JSONSerializer.toJSON(strOutput);

                        if ((service.contains("[0]"))) {
                            
                            strOutput = jsonArr.optString(0);
                        }

                        jsonElement = new JsonParser().parse(SortJson.sort(strOutput));
                        gson = new GsonBuilder().setPrettyPrinting().create();
                        strOutput = gson.toJson(jsonElement);

                        this.setResponse(SortJson.sort(strOutput));

                    }

                    this.setResponse(SortJson.sort(strOutput));

                }

            } catch (Exception e) {

                e.printStackTrace();
            }

            return strOutput;
        }
                
         
        /*
             * get JSONPath
         */
        
        public String jsonPath() {
            

            String originalJson = this.getResponse();
            String jsonPath = null;

            jsonPath = this.getPathExpression();

            return JSONPath.executeJsonPath(jsonPath, originalJson);

        }
                
	/*
	 * get the user data by id and return the username 
	 */
        
	public String checkUserId() {
            
		try {
                    
			this.login(this.getUsername(), this.getPassword());
			ClientResponse response = this.get(HOST + ":30020/user/" + this.getUserId());

			if (response.getStatus() != 200) {
			   throw new RuntimeException("checkUserId Failed : HTTP message code : " + response.getStatus());
			}

			String output = response.getEntity(String.class);
			JSONObject json = (JSONObject) JSONSerializer.toJSON(output);
			this.setStatus(response.getStatus());

			ManageMessages.printMessage(1, "checkUserId Output from Server .... \n",this.getClass().getName());
			ManageMessages.printMessage(1, output,this.getClass().getName());

			this.setUsername(json.getString("username"));
			
			if (json.has("verifications")) {
				JSONArray arr = json.getJSONArray("verifications");
				if (arr.size() > 0) {
					JSONObject jsonVerification = arr.getJSONObject(0);
					this.setVerificationToken(jsonVerification.getString("token"));
					ManageMessages.printMessage(1, "Setting Verificationtoken: " + this.getVerificationToken(),this.getClass().getName());
				}
			}
			ManageMessages.printMessage(1, "checkUserId Username: " + this.getUsername(),this.getClass().getName());

	  } catch (Exception e) {
	  	this.setStatus(-2);
	  	e.printStackTrace();
	  }
		return this.getUsername();
	}

	/*
	 * get the user data by id and return the username 
	 */
	public int listUser() {
		int iTotalLines = -1;
		try {
			this.login(this.getUsername(), this.getPassword());

			JSONObject json = new JSONObject();
			JSONArray arr = new JSONArray();
			arr.add("name");
			json.element("orderBy", arr);

			ClientResponse response = this.get(HOST + ":30020/user/list?pageSize=50&numberPage=1");

			if (response.getStatus() != 200) {
			   throw new RuntimeException("listUser Failed : HTTP error code : " + response.getStatus() + " RQ: " + json.toString());
			}

			String output = response.getEntity(String.class);
			JSONArray jsonArr = (JSONArray) JSONSerializer.toJSON(output);
			ManageMessages.printMessage(1, "listUser Output from Server ...." + output,this.getClass().getName());
			iTotalLines = jsonArr.size();
			ManageMessages.printMessage(1, "listUser no of total lines: " + iTotalLines,this.getClass().getName());
			
			this.setStatus(response.getStatus());


	  } catch (Exception e) {
	  	this.setStatus(-2);
	  	e.printStackTrace();
	  }
		return iTotalLines;
	}

	public int createUser() {
		String strOldUsername = this.getUsername();
		String strOldPassword = this.getPassword();

		//this.login(this.DEAULTUSER, this.DEAULTPASSWORD);

		this.setUsername(strOldUsername);
		this.setPassword(strOldPassword);

		try {

			JSONObject jsonRQ = new JSONObject();
			jsonRQ.element("username", this.getUsername());
			jsonRQ.element("name", this.getName());
			jsonRQ.element("surname", this.getSurname());
			jsonRQ.element("password", this.getPassword());
			jsonRQ.element("email", this.getEmail());
			jsonRQ.element("genderCode", "MALE");
			jsonRQ.element("phone", "");
			jsonRQ.element("termsAccepted", this.getTermsAccepted());
			jsonRQ.element("url", this.getUrl());
			jsonRQ.element("languageCode", "EN");
			//jsonRQ.element("resortOfficeCode", "PMI");
			ManageMessages.printMessage(1, jsonRQ.toString(),this.getClass().getName());

			ClientResponse response = this.post(HOST + ":30020/user", jsonRQ.toString());

			String strOutput = response.getEntity(String.class);
			ManageMessages.printMessage(1, "createUser output from server: ....\n" + strOutput,this.getClass().getName());
			JSONObject jsonRS = (JSONObject) JSONSerializer.toJSON(strOutput);
			this.setUserId(jsonRS.getInt("id"));
	  } catch (Exception e) {
	  	ManageMessages.printMessage(2, "Error executing createUser: " + e.toString(),this.getClass().getName());
	  	e.printStackTrace();
	  }
		return this.getUserId();
	}

	public int updateUser() {
		int iReturnStatus = -1;
		try {

			JSONObject jsonRQ = new JSONObject();
			//jsonRQ.element("id", this.getUserId());
			jsonRQ.element("username", this.getUsername());
			jsonRQ.element("name", this.getName());
			jsonRQ.element("surname", this.getSurname());
			jsonRQ.element("password", this.getPassword());
			jsonRQ.element("email", this.getEmail());
			//jsonRQ.element("genderCode", "MALE");
			//jsonRQ.element("phone", "");
			//jsonRQ.element("languageCode", "EN");
			//jsonRQ.element("resortOfficeCode", "PMI");

			ManageMessages.printMessage(1, "updateUser with id: " + this.getUserId(),this.getClass().getName());
			ClientResponse response = this.post(HOST + ":30020/user/" + this.getUserId(), jsonRQ.toString());

			ManageMessages.printMessage(1, jsonRQ.toString(),this.getClass().getName());
			iReturnStatus = response.getStatus();

			String strOutput = response.getEntity(String.class);
			ManageMessages.printMessage(1, "updateUser output from server: ....\n" + strOutput,this.getClass().getName());
			JSONObject jsonRS = (JSONObject) JSONSerializer.toJSON(strOutput);

	  } catch (Exception e) {
	  	ManageMessages.printMessage(2, "Error executing updateUser: " + e.toString(),this.getClass().getName());
	  	e.printStackTrace();
	  }
		return iReturnStatus;
	}

	public int deleteUser() {
		int iReturnStatus = -1;
		try {

			Client client = Client.create();

			WebResource webResource = client.resource(HOST + ":30020/user/" + this.getUserId());

			ClientResponse response = webResource
					.header("Content-Type", MediaType.APPLICATION_JSON)
					.header("Authorization", "Basic " + this.getToken())
					.accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

			String strOutput = response.getEntity(String.class);
			iReturnStatus = response.getStatus();
			ManageMessages.printMessage(1, "deleteUser output from server: .... with status: " + iReturnStatus + "\n",this.getClass().getName());
			ManageMessages.printMessage(1, "deleteUser response: " + strOutput,this.getClass().getName());
			//JSONObject jsonRS = (JSONObject) JSONSerializer.toJSON(strOutput);

	  } catch (Exception e) {
	  	ManageMessages.printMessage(2, "Error executing deleteUser: " + e.toString(),this.getClass().getName());
	  	e.printStackTrace();
	  	iReturnStatus = -2;
	  }
		return iReturnStatus;
	}


	public String getStock() {
		int iReturnStatus = -1;
		try {

			this.login(RestController.DEAULTUSER, RestController.DEAULTPASSWORD);

			ClientResponse response = this.get(HOST + ":30040/stock/" + this.getStockId());

			String strOutput = response.getEntity(String.class);
			iReturnStatus = response.getStatus();
			ManageMessages.printMessage(1, "getStock output from server: .... with status: " + iReturnStatus + "\n",this.getClass().getName());
			ManageMessages.printMessage(1, "getStock response: " + strOutput,this.getClass().getName());
			JSONObject jsonRS = (JSONObject) JSONSerializer.toJSON(strOutput);
			this.setStockName(jsonRS.getString("name"));
			ManageMessages.printMessage(1, "getStock stockname: " + this.getStockName(),this.getClass().getName());
			return this.getStockName();
	  } catch (Exception e) {
	  	ManageMessages.printMessage(2, "Error executing getStock: " + e.toString(),this.getClass().getName());
	  	e.printStackTrace();
	  }
		return "";
	}
	
	public int verifyToken() {
		int iReturnStatus = -1;
		try {

			//this.login(RestController.DEAULTUSER, RestController.DEAULTPASSWORD);
			ClientResponse response = this.get(HOST + ":30020/user/verify?token=" + this.getVerificationToken());
						
			String strOutput = response.getEntity(String.class);
			iReturnStatus = response.getStatus();
			ManageMessages.printMessage(1, "verifyToken output from server: .... with status: " + iReturnStatus,this.getClass().getName());
			ManageMessages.printMessage(1, "verifyToken response: " + strOutput + "\n",this.getClass().getName());			
			JSONObject json = (JSONObject) JSONSerializer.toJSON(strOutput);
			iReturnStatus = Integer.parseInt(((json.has("code")) ? json.getString("code") : "0"));
			this.setStatus(iReturnStatus);
			
	  } catch (Exception e) {
	  	ManageMessages.printMessage(2, "Error executing verifyToken: " + e.toString(),this.getClass().getName());
	  	e.printStackTrace();
	  }		
		return iReturnStatus;		
	}
	
	public int getStockSummary() {
		int iReturnStatus = -1;
		try {

			this.login(RestController.DEAULTUSER, RestController.DEAULTPASSWORD);
			JSONObject json = new JSONObject();
			JSONArray arr = new JSONArray();
			arr.add("name");
			json.element("orderBy", arr);
			long p1 = System.currentTimeMillis();
			ClientResponse response = this.post(HOST + ":30040/stock/list?pageSize=50&numberPage=" + this.getPage(), json.toString());
			String strOutput = response.getEntity(String.class);
			iReturnStatus = response.getStatus();
			ManageMessages.printMessage(1, "getStockSummary output from server: .... with status: " + iReturnStatus + "\n",this.getClass().getName());
			ManageMessages.printMessage(4, "getStockSummary response: " + strOutput,this.getClass().getName());
			JSONArray jsonArr = (JSONArray) JSONSerializer.toJSON(strOutput);
			ManageMessages.printMessage(1, "getStockSummary number of elements: " + jsonArr.size() + " after " + (System.currentTimeMillis()-p1) + "ms",this.getClass().getName());
			return jsonArr.size();

	  } catch (Exception e) {
	  	ManageMessages.printMessage(2, "Error executing getStockSummary: " + e.toString(),this.getClass().getName());
	  	e.printStackTrace();
	  }
		return -1;
	}

	public String changePassword() {
		try {

			Client client = Client.create();

			String strRequest = "{" +
					"\"query\": \"mutation ($oldPassword: String!, $newPassword: String!) { authChangePassword(oldPassword: $oldPassword, newPassword: $newPassword) }\"," +
					"\"variables\": {" +
					"            \"oldPassword\" : \"" + this.getPassword() + "\"," +
					"            \"newPassword\" : \"" + this.getPassword() + "\"" +
					"  }" +
					"}";

			String strEndpoint = HOST + ":30020/graphql/auth";
			ManageMessages.printMessage(1, "changing password with token: " + this.getToken(),this.getClass().getName());
			ManageMessages.printMessage(1, "body --->\n" + strRequest,this.getClass().getName());
			ManageMessages.printMessage(1, "endpoint ---> " + strEndpoint,this.getClass().getName());
			WebResource webResource = client.resource(strEndpoint);

			ClientResponse response = webResource
					.header("Content-Type", MediaType.APPLICATION_JSON)
					.header("Authorization", "Basic " + this.getToken())
					.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, strRequest);

			String strOutput = response.getEntity(String.class);
			ManageMessages.printMessage(1, "changePassword output from server: .... \n",this.getClass().getName());
			ManageMessages.printMessage(1, strOutput,this.getClass().getName());

			JSONObject jsonRS = (JSONObject) JSONSerializer.toJSON(strOutput);
			JSONObject jsonData = jsonRS.getJSONObject("data");
			if (jsonRS.has("errors")) {
				ManageMessages.printMessage(1, "changePassword has errors",this.getClass().getName());
				JSONArray arr = jsonRS.getJSONArray("errors");
				JSONObject jsonError = arr.getJSONObject(0);
				//throw new Exception(jsonError.getString("message"));
				return jsonError.getString("message");
			}

			ManageMessages.printMessage(1, "changePassword successful: " + strOutput,this.getClass().getName());
			//return (this.getToken().length() > 0) ? "true" : "false";

	  } catch (Exception e) {
	  	ManageMessages.printMessage(2, "Error executing changePassword: " + e.toString(),this.getClass().getName());
	  	e.printStackTrace();
			return e.toString();
	  }
		return "";
	}
        
        

            

	public static void main(String[] args) throws IOException {
            
            
		RestController rc = new RestController();
		rc.setBody("{\"displayName\":\"FitNesse Company\",\"legalName\":\"A new legal FitNesse Company\",\"registrationTaxNumber\":\"FIT\"}");
		rc.setHost("http://srvaxitrgkub010");
                rc.setPort("30075");
                rc.setHttpMethod("post");
                rc.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmaXRuZXNzZS50cmlnZ2xlIiwianRpIjoiNTM0NTQiLCJleHAiOjE1NDAzMDUyODgsIm1pbnV0ZXMiOjYsInRva2VuUGFyYW1ldGVycyI6IntcInVzZXJJZFwiOjUzNDU0LFwiYXV0aFJvbGVzXCI6W3tcInJvbGVOYW1lXCI6XCJVU0VSXCIsXCJjcnVkZVwiOjMxLFwiY2xhc3NOYW1lXCI6XCJjb20udHJpZ2dsZS5taWRkbGV3YXJlLm1vZGVsLmF1dGguUm9sZURlZmluaXRpb25cIn1dLFwicm9sZXNCeUtleVwiOnt9fSJ9.fF0lsA--2k-By4EslUYGS3XQ1ecsWNO8-u6nahla8ts");
                
                rc.setService("/company");
		//rc.login();
		//rc.setUserId(50384);
		//rc.checkUserId();
		//rc.verifyToken();

                System.out.println("xxxx   "+ rc.checkResponse());
                
                
         
               /** rc.setBody("{\"name\":\"FitNesse Company\",\"companyTypeCode\":\"GE\",\"legalName\":\"A new legal FitNesse Company\",\"registrationTaxNumber\":\"FIT\"}");
		rc.setHost("http://srvaxitrgkub010");
                rc.setPort("30075");
                rc.setHttpMethod("post");
                rc.setService("/company");
                rc.setParameters("");
		System.out.println("response   "+ rc.checkResponse());**/
		//rc.setUsername("krisdunker");
		//rc.createUser();

		//rc.setUserId(50224);
		//rc.deleteUser();
		//rc.updateUser();
		//rc.setStockId(459642);
		//rc.getStock();
		//rc.getStockSummary();
		try {
			//rc.changePassword();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
