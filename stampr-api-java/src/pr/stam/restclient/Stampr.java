package pr.stam.restclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import pr.stam.restclient.response.Batch;
import pr.stam.restclient.response.Config;
import pr.stam.restclient.response.Mailing;

import com.google.gson.Gson;


public class Stampr {
	
	private final String apiUrl = "https://testing.dev.stam.pr/";
	private final String username;
	private final String password;
	
	private HttpClient httpClient = new DefaultHttpClient();
	private Gson gson = new Gson();
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
	
	private static final String URL_PING = "api/test/ping";
	private static final String URL_HEALTH = "api/health";
	
	//CONFIG RELATED
	private static final String URL_CREATE_CONFIG="api/configs";
	
	private static final String URL_LIST_CONFIGS_ID = "api/configs/{0}";
	private static final String URL_LIST_CONFIGS_ALL = "api/configs/all";
	private static final String URL_LIST_CONFIGS_ALL_PAGING = "api/configs/all/{0}";

	//BATCH RELATED
	private static final String URL_CREATE_BATCH="/api/batches";
	private static final String URL_MODIFY_BATCH_ID = "/api/batches/{0}";
	private static final String URL_DELETE_BATCH_ID = "/api/batches/{0}";//DELETE
	
	private static final String URL_LIST_BATCH_ID = "api/batches/{0}";//GET /api/batches/:id
	private static final String URL_LIST_BATCH_STATUS = "api/batches/{0}"; //GET /api/batches/:status
	private static final String URL_LIST_BATCH_STATUS_START = "api/batches/{0}/{1}"; //GET /api/batches/:status/:start
	private static final String URL_LIST_BATCH_STATUS_START_END = "api/batches/{0}/{1}/{2}"; //GET /api/batches/:status/:start/:end
	private static final String URL_LIST_BATCH_STATUS_START_END_PAGING = "api/batches/{0}/{1}/{2}"; //GET /api/batches/:status/:start/:end/paging
	private static final String URL_LIST_BATCH_START = "api/batches/{0}"; //GET /api/batches/:start
	private static final String URL_LIST_BATCH_START_END = "api/batches/{0}/{1}";//GET /api/batches/:start/:end
	private static final String URL_LIST_BATCH_START_END_PAGING = "api/batches/{0}/{1}/{2}";//GET /api/batches/:start/:end/:paging
	
	//MAILING RELATED
	private static final String URL_CREATE_MAILING="api/mailings";
	private static final String URL_DELETE_MAILING_ID = "api/mailings/{0}";//DELETE/api/mailings/:id
	
	private static final String URL_LIST_BATCH_MAILING = "api/batches/{0}/mailings";//GET/api/batches/:id/mailings
	private static final String URL_LIST_BATCH_MAILING_STATUS = "api/batches/{0}/mailings/{1}";//GET/api/batches/:id/mailings/:status
	private static final String URL_LIST_BATCH_MAILING_STATUS_START = "api/batches/{0}/mailings/{1}/{2}";//GET/api/batches/:id/mailings/:status/:start
	private static final String URL_LIST_BATCH_MAILING_STATUS_START_END = "api/batches/{0}/mailings/{1}/{2}/{3}";//GET/api/batches/:id/mailings/:status/:start/:end
	private static final String URL_LIST_BATCH_MAILING_STATUS_START_END_PAGING = "api/batches/{0}/mailings/{1}/{2}/{3}/{4}";//GET/api/batches/:id/mailings/:status/:start/:end/:paging
	private static final String URL_LIST_BATCH_MAILING_START = "api/batches/{0}/mailings/{1}";//GET/api/batches/:id/mailings/:start
	private static final String URL_LIST_BATCH_MAILING_START_END = "api/batches/{0}/mailings/{1}/{2}";//GET/api/batches/:id/mailings/:start/:end
	private static final String URL_LIST_BATCH_MAILING_START_END_PAGING = "api/batches/{0}/mailings/{1}/{2}/{3}}";//GET/api/batches/:id/mailings/:start/:end/:paging
	
	private static final String URL_LIST_MAILING_ID = "api/mailings/{0}";//GET/api/mailings/:id
	private static final String URL_LIST_MAILING_STATUS = "/api/mailings/{0}";//GET/api/mailings/:status
	private static final String URL_LIST_MAILING_STATUS_START = "/api/mailings/{0}/{1}";//GET/api/mailings/:status/:start
	private static final String URL_LIST_MAILING_STATUS_START_END = "api/mailings/{0}/{1}/{2}";//GET/api/mailings/:status/:start/:end
	private static final String URL_LIST_MAILING_STATUS_START_END_PAGING = "api/mailings/{0}/{1}/{2}/{3}";//GET/api/mailings/:status/:start/:end/:paging
	private static final String URL_LIST_MAILING_START = "api/mailings/{0}";//GET/api/mailings/:start
	private static final String URL_LIST_MAILING_START_END = "api/mailings/{0}/{1}";//GET/api/mailings/:start/:end
	private static final String URL_LIST_MAILING_START_END_PAGING = "api/mailings/{0}/{1}/{2}";//GET/api/mailings/:start/:end/:paging
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";//YYYY-MM-DD HH:mm:ss
	
	public static enum Status{
		PROCESSING("processing"),
		HOLD("hold"),
		ARCHIVE("archive");
		
		String value;
		Status(String value) {
			this.value = value;
		}
		public static Status fromString(String string) {
			if(string==null) return null;
			for(Status status:Status.values())
			{
				if(status.value.equals(string))
					return status;
			}
			return null;
		}
	}
	
	public static enum Format{
		JSON("json"),
		HTML("html"),
		PDF("pdf"),
		NONE("none");
		
		String value;
		Format(String value) {
			this.value = value;
		}
		public static Format fromString(String string) {
			if(string==null) return null;
			for(Format status:Format.values())
			{
				if(status.value.equals(string))
					return status;
			}
			return null;
		}
		
	}
	
	//Singleton
	private static Stampr stampr = null;
	
	public static Stampr getStampr() {
		return stampr;
	}
	
	public static Stampr getStampr(String username,String password)
	{
		stampr = new Stampr(username,password); 
		return stampr;
	}
	
	/**
	 * Create the stampr factory
	 * @param username
	 * @param password
	 */
	private Stampr(String username, String password) {
		notNull(username, "username");
		notNull(password, "password");
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Execute a HTTP POST operation
	 * @param relativeURL
	 * @param nvps
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	private String getPOSTResponseText(String relativeURL, List<NameValuePair> nvps) throws ClientProtocolException, IOException
	{
		StringBuilder resp = new StringBuilder();
	    
		HttpPost httpPost = new HttpPost(apiUrl + relativeURL);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		httpPost.setHeader("Authorization", getAuthorization());
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		
		if(entity!=null){
			InputStream instream = null;
			try {
				instream = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
				
				String line;
				while ((line = reader.readLine()) != null) {
					resp.append(line);
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
				throw e;
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			} finally{
				instream.close();
			}
		}
	    return resp.toString();
	}
	
	/**
	 * execute a HTTP GET operation
	 * @param relativeURL
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String getGETResponseText(String relativeURL) throws ClientProtocolException, IOException  {
		
		StringBuilder resp = new StringBuilder();
		HttpGet httpGet = new HttpGet(apiUrl + relativeURL);
		httpGet.setHeader("Authorization", getAuthorization());
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		
		if(entity!=null){
			InputStream instream = null;
			try {
				instream = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
				String line;
				while ((line = reader.readLine()) != null) {
					resp.append(line);
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
				throw e;
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			} finally{
				instream.close();
			}
		}
	    return resp.toString();
		
	}
	
	/**
	 * Execute a HTTP DELETE operation
	 * @param relativeURL
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String getDELETEResponseText(String relativeURL) throws ClientProtocolException, IOException  {
		
		StringBuilder resp = new StringBuilder();
		HttpDelete httpDelete = new HttpDelete(apiUrl + relativeURL);
		httpDelete.setHeader("Authorization", getAuthorization());
		HttpResponse response = httpClient.execute(httpDelete);
		HttpEntity entity = response.getEntity();
		
		if(entity!=null){
			InputStream instream = null;
			try {
				instream = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
				String line;
				while ((line = reader.readLine()) != null) {
					resp.append(line);
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
				throw e;
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			} finally{
				instream.close();
			}
		}
	    return resp.toString();
		
	}

	
	/**
	 * Check API health
	 * Returns: String, the exact string "ok" (with quotes) when everything is ok
	 * @return
	 * @throws ClientProtocolException 
	 * @throws IOException
	 */
	public String health() throws ClientProtocolException, IOException  {
		return getGETResponseText(URL_HEALTH);
	}

	
	/**
	 * Simple Round Trip Test 
	 * Returns: String with single key "pong" and a value of the current Date Time of the server
	 * @return
	 * @throws ClientProtocolException 
	 * @throws IOException
	 */
	public String ping() throws ClientProtocolException, IOException 
	{	
		return getGETResponseText(URL_PING);	
	}

	/**
	 * Create a new mailing configuration to be used with Batches.
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Config createConfig() throws ClientProtocolException, IOException 
	{
		List <NameValuePair> nvps = new ArrayList<NameValuePair>();
	    nvps.add(new BasicNameValuePair("size", "standard"));
	    nvps.add(new BasicNameValuePair("turnaround", "threeday"));
	    nvps.add(new BasicNameValuePair("style", "color"));
	    nvps.add(new BasicNameValuePair("output", "single"));
	    nvps.add(new BasicNameValuePair("returnenvelope", "false"));
		
		String json = getPOSTResponseText(URL_CREATE_CONFIG, nvps);
		Config config = gson.fromJson(json, Config.class);
		return config;
	}
	
	/**
	 * List config by id
	 * Returns: Config
	 * @param config_id
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public Config getConfigById(Integer config_id) throws ClientProtocolException, IOException
	{
		notNull(config_id, "config_id");
		String json = getGETResponseText(fillCallParameters(URL_LIST_CONFIGS_ID,config_id));
		if(json==null||json.trim().length()==0) return null;
		Config[] configs = gson.fromJson(json,(new Config[0]).getClass());
		if(configs==null||configs.length==0) return null;
		return configs[0];
	}
	
	/**
	 * List all configs
	 * Returns: Config[] (Paged Array of Config Objects)
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public Config[] listConfigs() throws ClientProtocolException, IOException
	{
		String json = getGETResponseText(URL_LIST_CONFIGS_ALL);
		System.out.println(">>>" + json);
		if(json==null||json.trim().length()==0) return null;
		Config[] configs = gson.fromJson(json,(new Config[0]).getClass());
		return configs;
	}
	
	/**
	 * List all configs paged
	 * Returns: Config[] (Paged Array of Config Objects)
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public Config[] listConfigs(Integer paging) throws ClientProtocolException, IOException
	{
		notNull(paging, "paging");
		String json = getGETResponseText(fillCallParameters(URL_LIST_CONFIGS_ALL_PAGING,paging));
		System.out.println(">>>" + json);
		if(json==null||json.trim().length()==0) return null;
		Config[] configs = gson.fromJson(json,(new Config[0]).getClass());
		return configs;
	}

	/**
	 * Create a new mailing configuration to be used with Batches.
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Batch createBatch(
			/*required*/ Config config,
			/*optional*/ String template,
			/*optional*/ Status status) throws ClientProtocolException, IOException 
	{
		notNull(config, "config");
		
		List <NameValuePair> nvps = new ArrayList<NameValuePair>();
	    nvps.add(new BasicNameValuePair("config_id", String.valueOf(config.getConfig_id())));
	   
	    if(template!=null&&template.trim().length()>0)
	    nvps.add(new BasicNameValuePair("template", template));
	    
	    if(status!=null)
	    nvps.add(new BasicNameValuePair("status", status.value));
		
		String json = getPOSTResponseText(URL_CREATE_BATCH, nvps);
		Batch batch = gson.fromJson(json, Batch.class);
		return batch;
	}
	
	/**
	 * Modify Batch by Batch ID
	 * @param batch_id
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public Boolean modifyBatch(Integer batch_id, Status status) throws ClientProtocolException, IOException
	{
		notNull(batch_id, "batch_id"); 
		notNull(status, "status"); 
		
		List <NameValuePair> nvps = new ArrayList<NameValuePair>();
	    nvps.add(new BasicNameValuePair("batch_id", String.valueOf(batch_id)));
	    nvps.add(new BasicNameValuePair("status", status.value));
	    
	    String json = getPOSTResponseText(fillCallParameters(URL_MODIFY_BATCH_ID,batch_id), nvps);
	    System.out.println(">>>" + json);
		Boolean resp = gson.fromJson(json, Boolean.class);
		return resp;
	}
	
	/**
	 * Delete batch by id
	 * Batches that contain mailings cannot be deleted. Only empty batches are allowed.
	 * @param batch_id
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Boolean deleteBatch(Integer batch_id) throws ClientProtocolException, IOException
	{
		notNull(batch_id, "batch_id");
		
		String json = getDELETEResponseText(fillCallParameters(URL_DELETE_BATCH_ID, batch_id));
		Boolean resp = gson.fromJson(json, Boolean.class);
		return resp;
	}
	
	/**
	 * Get batch by id
	 * Returns: Batch
	 * @param batch_id
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public Batch getBatchById(Integer batch_id) throws ClientProtocolException, IOException
	{
		notNull(batch_id, "batch_id");
		String json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_ID,batch_id));
		if(json==null||json.trim().length()==0) return null;
		Batch[] batches = gson.fromJson(json,(new Batch[0]).getClass());
		if(batches==null||batches.length==0) return null;
		return batches[0];
	}
	
	/**
	 * Get a list of batches
	 * @param status
	 * @param start
	 * @param end
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Batch[] listBatches(Status status, Date start, Date end) throws ClientProtocolException, IOException
	{
		return listBatches(status, start, end, null);
	}
	
	/**
	 * Get a list of batches
	 * @param status
	 * @param start
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Batch[] listBatches(Status status, Date start) throws ClientProtocolException, IOException
	{
		return listBatches(status, start, null, null);
	}
	
	/**
	 * Get a list of batches
	 * @param status
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Batch[] listBatches(Date start, Date end, Integer paging) throws ClientProtocolException, IOException
	{
		return listBatches(null, start, end, paging);
	}
	
	/**
	 * Get a list of batches
	 * @param status
	 * @param start
	 * @param end
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Batch[] listBatches(Date start, Date end) throws ClientProtocolException, IOException
	{
		return listBatches(null, start, end, null);
	}
	
	/**
	 * Get a list of batches
	 * @param status
	 * @param start
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Batch[] listBatches(Date start) throws ClientProtocolException, IOException
	{
		return listBatches(null, start, null, null);
	}
	
	/**
	 * Get a list of batches
	 * @param status
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Batch[] listBatches(Status status) throws ClientProtocolException, IOException
	{
		return listBatches(status, null, null, null);
	}
	
	/**
	 * Get a list of batches
	 * @param status
	 * @param start
	 * @param end
	 * @param paging
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Batch[] listBatches(Status status, Date start, Date end, Integer paging) throws ClientProtocolException, IOException
	{
		
		boolean hasStatus = status!=null;
		boolean hasStart = start!=null;
		boolean hasEnd = end!=null;
		boolean hasPaging = paging!=null;
		
		boolean version0 = allTrue(hasStatus,!hasStart,!hasEnd,!hasPaging);
		boolean version1 = allTrue(hasStatus,hasStart,!hasEnd,!hasPaging);
		boolean version2 = allTrue(hasStatus,hasStart,hasEnd,!hasPaging);
		boolean version3 = allTrue(hasStatus,hasStart,hasEnd,hasPaging);
		boolean version4 = allTrue(!hasStatus,hasStart,!hasEnd,!hasPaging);
		boolean version5 = allTrue(!hasStatus,hasStart,hasEnd,!hasPaging);
		boolean version6 = allTrue(!hasStatus,hasStart,hasEnd,hasPaging);
		
		String json = null;
		
		if(version3)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_STATUS_START_END_PAGING,status,start,end,paging));
		else if(version2)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_STATUS_START_END,status,start,end));
		else if(version1)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_STATUS_START,status,start));
		else if(version0)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_STATUS,status));
		else if(version4)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_START,start));
		else if(version5)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_START_END,start,end));
		else if(version6)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_START_END_PAGING,start,end,paging));
		
		System.out.println(">>> List " + json);
		
		if(json==null||json.trim().length()==0) return null;
		Batch[] batches = gson.fromJson(json,(new Batch[0]).getClass());
		return batches;
		
	}
	
	
	
	/**
	 * Create a new Mailing Object
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing createMailing(
			/*required*/ Batch batch,
			/*required*/ String address,
			/*required*/ String returnAddress,
			/*required*/ Format format,
			/*optional*/ String data,
			/*optional*/ String md5) throws ClientProtocolException, IOException 
	{
		
		notNull(batch, "batch");
		notNull(address, "address");
		notNull(returnAddress, "returnAddress");
		notNull(format, "format");
		
		List <NameValuePair> nvps = new ArrayList<NameValuePair>();
	    nvps.add(new BasicNameValuePair("batch_id", String.valueOf(batch.getBatch_id())));
	    nvps.add(new BasicNameValuePair("address", address));
	    nvps.add(new BasicNameValuePair("returnaddress", returnAddress));
	    nvps.add(new BasicNameValuePair("format", format.value));
	    
	    if(data!=null&&data.trim().length()>0)
	    nvps.add(new BasicNameValuePair("data", data));
	    
	    if(md5!=null&&md5.trim().length()>0)
		nvps.add(new BasicNameValuePair("md5", md5));
		
		String json = getPOSTResponseText(URL_CREATE_MAILING, nvps);
		Mailing mailing = gson.fromJson(json, Mailing.class);
		return mailing;
	}
	
	/**
	 * Get the list of mailings in a batch
	 * @param batch_id
	 * @param status
	 * @param start
	 * @param end
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listBatchMailings(Integer batch_id,Status status, Date start, Date end) throws ClientProtocolException, IOException
	{
		return listBatchMailings(batch_id, status, start, end, null);
	}
	
	/**
	 * Get the list of mailings in a batch
	 * @param batch_id
	 * @param status
	 * @param start
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listBatchMailings(Integer batch_id,Status status, Date start) throws ClientProtocolException, IOException
	{
		return listBatchMailings(batch_id, status, start, null, null);
	}
	
	/**
	 * Get the list of mailings in a batch
	 * @param batch_id
	 * @param status
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listBatchMailings(Integer batch_id,Status status) throws ClientProtocolException, IOException
	{
		return listBatchMailings(batch_id, status, null, null, null);
	}
	
	/**
	 * Get the list of mailings in a batch
	 * @param batch_id
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listBatchMailings(Integer batch_id) throws ClientProtocolException, IOException
	{
		return listBatchMailings(batch_id, null, null, null, null);
	}
	
	/**
	 * Get the list of mailings in a batch
	 * @param batch_id
	 * @param start
	 * @param end
	 * @param paging
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listBatchMailings(Integer batch_id, Date start, Date end, Integer paging) throws ClientProtocolException, IOException
	{
		return listBatchMailings(batch_id, null, start, end, paging);
	}
	
	/**
	 * Get the list of mailings in a batch
	 * @param batch_id
	 * @param start
	 * @param end
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listBatchMailings(Integer batch_id, Date start, Date end) throws ClientProtocolException, IOException
	{
		return listBatchMailings(batch_id, null, start, end, null);
	}
	
	/**
	 * Get the list of mailings in a batch
	 * @param batch_id
	 * @param start
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listBatchMailings(Integer batch_id, Date start) throws ClientProtocolException, IOException
	{
		return listBatchMailings(batch_id, null, start, null, null);
	}
	
	/**
	 * Get the list of mailings in a batch
	 * @param batch_id
	 * @param status
	 * @param start
	 * @param end
	 * @param paging
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listBatchMailings(Integer batch_id,Status status, Date start, Date end, Integer paging) throws ClientProtocolException, IOException
	{
		notNull(batch_id, "batch_id");
		
		boolean hasStatus = status!=null;
		boolean hasStart = start!=null;
		boolean hasEnd = end!=null;
		boolean hasPaging = paging!=null;
		
		boolean version0 = allTrue(hasStatus,!hasStart,!hasEnd,!hasPaging);
		boolean version1 = allTrue(hasStatus,hasStart,!hasEnd,!hasPaging);
		boolean version2 = allTrue(hasStatus,hasStart,hasEnd,!hasPaging);
		boolean version3 = allTrue(hasStatus,hasStart,hasEnd,hasPaging);
		boolean version4 = allTrue(!hasStatus,hasStart,!hasEnd,!hasPaging);
		boolean version5 = allTrue(!hasStatus,hasStart,hasEnd,!hasPaging);
		boolean version6 = allTrue(!hasStatus,hasStart,hasEnd,hasPaging);
		boolean version7 = allTrue(!hasStatus,!hasStart,!hasEnd,!hasPaging);
		
		String json = null;

		if(version0)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_MAILING_STATUS,batch_id,status));
		else if(version1)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_MAILING_STATUS_START,batch_id,status,start));
		else if(version2)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_MAILING_STATUS_START_END,batch_id,status,start,end));
		else if(version3)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_MAILING_STATUS_START_END_PAGING,batch_id,status,start,end,paging));
		else if(version4)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_MAILING_START,batch_id,start));
		else if(version5)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_MAILING_START_END,batch_id,start,end));
		else if(version6)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_MAILING_START_END_PAGING,batch_id,start,end,paging));
		else if(version7)
			json = getGETResponseText(fillCallParameters(URL_LIST_BATCH_MAILING,batch_id));
		
		if(json==null||json.trim().length()==0) return null;
		Mailing[] mailings = gson.fromJson(json,(new Mailing[0]).getClass());
		
		return mailings;
	}
	
	/**
	 * Get mailing by id
	 * Returns: Mailing
	 * @param batch_id
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public Mailing getMailingById(Integer mailing_id) throws ClientProtocolException, IOException
	{
		notNull(mailing_id, "mailing_id");
		String json = getGETResponseText(fillCallParameters(URL_LIST_MAILING_ID,mailing_id));
		if(json==null||json.trim().length()==0) return null;
		Mailing[] mailing = gson.fromJson(json,(new Mailing[0]).getClass());
		if(mailing==null||mailing.length==0) return null;
		return mailing[0];
	}

	/**
	 * Get the list of mailings
	 * @param status
	 * @param start
	 * @param end
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listMailings(Status status, Date start, Date end) throws ClientProtocolException, IOException {
		return listMailings(status, start, end, null);
	}
	
	/**
	 * Get the list of mailings
	 * @param status
	 * @param start
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listMailings(Status status, Date start) throws ClientProtocolException, IOException {
		return listMailings(status, start, null, null);
	}
	
	/**
	 * Get the list of mailings
	 * @param status
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listMailings(Status status) throws ClientProtocolException, IOException {
		return listMailings(status, null, null, null);
	}
	
	/**
	 * Get the list of mailings
	 * @param start
	 * @param end
	 * @param paging
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listMailings(Date start, Date end,Integer paging) throws ClientProtocolException, IOException {
		return listMailings(null, start, end, paging);
	}
	
	/**
	 * Get the list of mailings
	 * @param start
	 * @param end
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listMailings(Date start, Date end) throws ClientProtocolException, IOException {
		return listMailings(null, start, end, null);
	}
	
	/**
	 * Get the list of mailings
	 * @param start
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listMailings(Date start) throws ClientProtocolException, IOException {
		return listMailings(null, start, null, null);
	}
	
	/**
	 * Get the list of mailings
	 * @param status
	 * @param start
	 * @param end
	 * @param paging
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing[] listMailings(Status status, Date start, Date end, Integer paging) throws ClientProtocolException, IOException
	{
//		private static final String URL_LIST_MAILING_STATUS = "/api/mailings/:status";//GET/api/mailings/:status
//		private static final String URL_LIST_MAILING_STATUS_START = "/api/mailings/:status/:start";//GET/api/mailings/:status/:start
//		private static final String URL_LIST_MAILING_STATUS_START_END = "api/mailings/:status/:start/:end";//GET/api/mailings/:status/:start/:end
//		private static final String URL_LIST_MAILING_STATUS_START_END_PAGING = "api/mailings/:status/:start/:end/:paging";//GET/api/mailings/:status/:start/:end/:paging
//		private static final String URL_LIST_MAILING_START = "api/mailings/:start";//GET/api/mailings/:start
//		private static final String URL_LIST_MAILING_START_END = "api/mailings/:start/:end";//GET/api/mailings/:start/:end
//		private static final String URL_LIST_MAILING_START_END_PAGING = "api/mailings/:start/:end/:paging";//GET/api/mailings/:start/:end/:paging
		
		
		boolean hasStatus = status!=null;
		boolean hasStart = start!=null;
		boolean hasEnd = end!=null;
		boolean hasPaging = paging!=null;
		
		boolean version0 = allTrue(hasStatus,!hasStart,!hasEnd,!hasPaging);
		boolean version1 = allTrue(hasStatus,hasStart,!hasEnd,!hasPaging);
		boolean version2 = allTrue(hasStatus,hasStart,hasEnd,!hasPaging);
		boolean version3 = allTrue(hasStatus,hasStart,hasEnd,hasPaging);
		boolean version4 = allTrue(!hasStatus,hasStart,!hasEnd,!hasPaging);
		boolean version5 = allTrue(!hasStatus,hasStart,hasEnd,!hasPaging);
		boolean version6 = allTrue(!hasStatus,hasStart,hasEnd,hasPaging);
		
		String json = null;

		if(version0)
			json = getGETResponseText(fillCallParameters(URL_LIST_MAILING_STATUS,status));
		else if(version1)
			json = getGETResponseText(fillCallParameters(URL_LIST_MAILING_STATUS_START,status,start));
		else if(version2)
			json = getGETResponseText(fillCallParameters(URL_LIST_MAILING_STATUS_START_END,status,start,end));
		else if(version3)
			json = getGETResponseText(fillCallParameters(URL_LIST_MAILING_STATUS_START_END_PAGING,status,start,end,paging));
		else if(version4)
			json = getGETResponseText(fillCallParameters(URL_LIST_MAILING_START,start));
		else if(version5)
			json = getGETResponseText(fillCallParameters(URL_LIST_MAILING_START_END,start,end));
		else if(version6)
			json = getGETResponseText(fillCallParameters(URL_LIST_MAILING_START_END_PAGING,start,end,paging));
		
		if(json==null||json.trim().length()==0) return null;
		Mailing[] mailings = gson.fromJson(json,(new Mailing[0]).getClass());
		
		return mailings;
	}
	
	/**
	 * Delete Mailing by Id
	 * @param mailing_id
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Boolean deleteMailing(Integer mailing_id) throws ClientProtocolException, IOException
	{
		notNull(mailing_id, "mailing_id");
		
		String json = getDELETEResponseText(fillCallParameters(URL_DELETE_MAILING_ID, mailing_id));
		System.out.println("delete mailing: " + json);
		Boolean resp = gson.fromJson(json, Boolean.class);
		return resp;
	}
	
	/**
	 * Simple send version 1
	 * @param from
	 * @param to
	 * @param body
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing send(String from, String to, String body) throws ClientProtocolException, IOException
	{
		notNull(from, "from");
		notNull(to, "to");
		
		Config config = createConfig();
		Batch batch = createBatch(config, null, null);
		Mailing mailing = createMailing(batch, to, from, Format.PDF, body, null);
		return mailing;
	}
	
	/**
	 * Simple send version 2, uses pdf as default
	 * @param from
	 * @param to
	 * @param body
	 * @param batch
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing send(String from, String to, String body, Batch batch) throws ClientProtocolException, IOException
	{
		return send(from, to, body, batch, Format.PDF);
	}
	
	/**
	 * Simple send version 3
	 * @param from
	 * @param to
	 * @param body
	 * @param batch
	 * @param format
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Mailing send(String from, String to, String body, Batch batch, Format format) throws ClientProtocolException, IOException
	{
		notNull(from, "from");
		notNull(to, "to");
		notNull(batch, "batch");
		notNull(format, "format");

		Mailing mailing = createMailing(batch, to, from, format, body, null);
		return mailing;
	}
	
//	/**
//	 * Simple send version 3
//	 * @param from
//	 * @param to
//	 * @param body
//	 * @param batch
//	 * @param config
//	 * @return
//	 * @throws ClientProtocolException
//	 * @throws IOException
//	 */
//	public Mailing send(String from, String to, String body, Batch batch, Config config) throws ClientProtocolException, IOException
//	{
//		notNull(from, "from");
//		notNull(to, "to");
//		notNull(body, "body");
//		notNull(batch, "batch");
//		notNull(config, "config");
//		
//		Mailing mailing = createMailing(batch, to, from, Format.PDF, body, null);
//		return mailing;
//	}

	private String get64EncodedString(String string)
	{
       byte[] encoded = Base64.encodeBase64(string.getBytes());     
       return new String(encoded);
	}
	
	private String getAuthorization()
	{
		return "Basic " + get64EncodedString(username+":"+password);
	}
	
	public void notNull(Object object,String name) throws  IllegalArgumentException {
		if(object==null) throw new IllegalArgumentException("Parameter " + name + " can't be null on method invocation"); 
	}
	
	/**
	 * Shutdown connections, you cannot make any more
	 * service calls using this service client
	 */
	public void shutdown()
	{
		httpClient.getConnectionManager().shutdown();
	}
	
	/**
	 * Replace call parameters in order
	 * @param string
	 * @param replacements
	 * @return
	 */
	private String fillCallParameters(String string, Object... replacements) {
		
		String newString = string;
		for(int i=0;i<replacements.length;i++)
		{
			Object object = replacements[i];
			boolean isDate = object instanceof Date;
			String objectS = isDate?dateFormat.format((Date)object):String.valueOf(replacements[i]);
			newString = replaceAll(newString, "{".concat(String.valueOf(i)).concat("}"),objectS);
		}
		return newString;
	}
	
	/**
	 * Replace any substring ocurrence
	 * @param strOrig
	 * @param strFind
	 * @param strReplace
	 * @return
	 */
	public String replaceAll(String strOrig, String strFind, String strReplace) {
		if (strOrig == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer(strOrig);
		String toReplace = "";
		if (strReplace == null)
			toReplace = "";
		else
			toReplace = strReplace;
		int pos = strOrig.length();
		while (pos > -1) {
			pos = strOrig.lastIndexOf(strFind, pos);
			if (pos > -1)
				sb.replace(pos, pos + strFind.length(), toReplace);
			pos = pos - strFind.length();
		}
		return sb.toString();
	}
	

	private boolean allTrue(boolean... flags) {
		boolean resp = true;
		
		for(boolean flag:flags)
			resp = resp&&flag;
		
		return resp;
	}
	


	

}
