
package com.triggle.fitnesse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import com.triggle.utils.SortJson;
import com.triggle.utils.ManageMessages;
import com.triggle.utils.JSONPath;
import java.text.SimpleDateFormat;
import javax.ws.rs.core.MediaType;
import org.apache.commons.lang.StringUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.IOException;


import java.util.concurrent.TimeUnit;


/**
 *
 * @author jose.alvarez
 */

    



public class GraphQlController extends fit.Fixture {
    

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

	public GraphQlController() {
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

        public void setDelay(String strDelay) throws InterruptedException {
            TimeUnit.SECONDS.sleep(this.getTime());
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
	/**public void ManageMessages.printMessage(int iLevel, String strMessage) {
	  if (this.iLevel >= iLevel) {
	    String strDate = sdf.format(new java.util.Date());
	    System.out.println(this.getClass().getName() + ": " + strDate + " " + strMessage);
	  }
	}**/

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
            ClientResponse response;
            Client client;
            JsonElement jsonElement;
            Gson gson;
            String service = null;
            
            try {

                if ("".equals(this.getBody()) && "".equals(this.getHost()) && "".equals(this.getPort())) {

                    strOutput = "-";
                    this.setStatus(0);

                } else {

                    ManageMessages.printMessage(1, "........................." + this.getTestCase() + ".........................\n", this.getClass().getName());
                    ManageMessages.printMessage(1, ".......... Body : " + this.getBody(), this.getClass().getName());
                    ManageMessages.printMessage(1, ".......... URI :" + this.getHost() + ":" + this.getPort() + this.getService() + this.getParameters(), this.getClass().getName());
                    ManageMessages.printMessage(1, ".......... Http Method : " + this.getHttpMethod(), this.getClass().getName());
                    ManageMessages.printMessage(1, ".......................................................... \n", this.getClass().getName());
                    
                    if ((this.getService().contains("graphql") && this.getBody().contains("login")) && this.getHttpMethod().contains("post")) {

                        service = this.getService();



                        response = this.post(this.getHost() + ":" + this.getPort() + this.getService() + this.getParameters(), this.getBody());
                        strOutput = response.getEntity(String.class);
                        this.setStatus(response.getStatus());
                        ManageMessages.printMessage(1, "Output from server1: ...." + strOutput + "\n", this.getClass().getName());
                        System.out.println("Hola Mundo1");
                        JSONObject jsonRS = (JSONObject) JSONSerializer.toJSON(strOutput);
                        System.out.println("Hola Mundo2");
                        JSONObject jsonData = jsonRS.getJSONObject("data");
                        JSONObject jsonlogin = jsonData.getJSONObject("login");
                        System.out.println("Hola Mundo3 "+ jsonlogin.getString("token"));
                       // JSONArray jsonArr = (JSONArray) JSONSerializer.toJSON(strOutput);
                        this.setToken(jsonlogin.getString("token"));
                        this.setResponse(SortJson.sort(strOutput));
                    } else if((this.getService().contains("graphql")) && this.getHttpMethod().contains("post")) {
                        
                        response = this.post(this.getHost() + ":" + this.getPort() + this.getService() + this.getParameters(), this.getBody());
                        strOutput = response.getEntity(String.class);
                        this.setStatus(response.getStatus());
                        ManageMessages.printMessage(1, "Output from server1: ...." + strOutput + "\n", this.getClass().getName());
                        System.out.println("Hola Mundo1");
                        
                        
                    } else if ((this.getService().contains("list")) && this.getHttpMethod().contains("post")) {

                        service = this.getService();

                        if ((this.getService().contains("[0]"))) {
                            this.setService(this.getService().substring(0, this.getService().indexOf("[")));
                        }

                        response = this.post(this.getHost() + ":" + this.getPort() + this.getService() + this.getParameters(), this.getBody());
                        strOutput = response.getEntity(String.class);
                        this.setStatus(response.getStatus());
                        ManageMessages.printMessage(1, "Output from server2: ...." + strOutput + "\n", this.getClass().getName());
                        JSONArray jsonArr = (JSONArray) JSONSerializer.toJSON(strOutput);

                        if ((service.contains("[0]"))) {
                            strOutput = jsonArr.optString(0);
                        }

                        jsonElement = new JsonParser().parse(SortJson.sort(strOutput));
                        gson = new GsonBuilder().setPrettyPrinting().create();
                        strOutput = gson.toJson(jsonElement);

                    }else if ((this.getService().contains("user") || this.getService().contains("stock") || this.getService().contains("company")) && this.getHttpMethod().contains("post")) {

                        response = this.post(this.getHost() + ":" + this.getPort() + this.getService() + this.getParameters(), this.getBody());
                        strOutput = response.getEntity(String.class);
                        this.setStatus(response.getStatus());
                        ManageMessages.printMessage(1, "Output from server3: ...." + strOutput + "\n", this.getClass().getName());
                        JSONObject jsonRS = (JSONObject) JSONSerializer.toJSON(strOutput);

                    } else if (this.getService().contains("login") && this.getHttpMethod().contains("post")) {

                        client = Client.create();
                        WebResource webResource = client.resource(HOST + ":" + this.getPort() + this.getService());
                        response = webResource.header("Content-Type", MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, this.getBody());
                        strOutput = response.getEntity(String.class);
                        this.setStatus(response.getStatus());
                        ManageMessages.printMessage(1, "User login output from server: ...." + strOutput + "\n", this.getClass().getName());
                        JSONObject jsonRS = (JSONObject) JSONSerializer.toJSON(strOutput);
                        this.setToken(jsonRS.getString("token"));

                    } else if (this.getHttpMethod().contains("delete")) {

                        client = Client.create();
                        WebResource webResource = client.resource(HOST + ":" + this.getPort() + this.getService() + this.getParameters());
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

                        response = this.get(this.getHost() + ":" + this.getPort() + this.getService() + this.getParameters());
                        strOutput = response.getEntity(String.class);
                        this.setStatus(response.getStatus());
                        ManageMessages.printMessage(1, "Output from server6: .... with status: " + response.getStatus(), this.getClass().getName());
                        ManageMessages.printMessage(1, "Response: " + strOutput + "\n", this.getClass().getName());
                        jsonElement = new JsonParser().parse(SortJson.sort(strOutput));
                        gson = new GsonBuilder().setPrettyPrinting().create();
                        strOutput = gson.toJson(jsonElement);

                    }else{
                    
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
            
             if(strOutput.length() > 10000) strOutput = "too large to show: " + strOutput.length() + " caracteres";
            
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
                


	public static void main(String[] args) {
		RestController rc = new RestController();
		rc.setUsername("ADMIN");
		rc.setPassword("1234");
		rc.login();
		//rc.setUserId(50384);
		//rc.checkUserId();
		//rc.verifyToken();
		rc.listUser();
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
