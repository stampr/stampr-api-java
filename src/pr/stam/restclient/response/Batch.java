package pr.stam.restclient.response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import pr.stam.restclient.Stampr.Status;
import pr.stam.restclient.request.MailingRequest;

/**
 * Class for Batch objects 
 * json like
 * {"config_id":"4690",
 * "template":"Hello {{name}}!",
 * "status":"processing",
 * "user_id":1,
 * "batch_id":1871}
 * @author JMpaz
 *
 */
public class Batch {
	
	private Integer batch_id;
	private Integer config_id;
	private Integer user_id;
	private String template;
	private String status;
	
	private List<MailingRequest> mailingRequests;
	
	public Batch(Integer batch_id) {
		this.batch_id = batch_id;
	}

	@Override
	public String toString() {
		return "Batch [batch_id=" + batch_id + ", config_id=" + config_id
				+ ", user_id=" + user_id + ", template=" + template
				+ ", status=" + status + "]";
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getConfig_id() {
		return config_id;
	}

	public void setConfig_id(Integer config_id) {
		this.config_id = config_id;
	}

	public Integer getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(Integer batch_id) {
		this.batch_id = batch_id;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Status getStatus() {
		return status!=null?Status.fromString(status):null;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void addMailingRequest(MailingRequest mailingRequest) {
		if(mailingRequests==null) mailingRequests = new ArrayList<MailingRequest>();
		this.mailingRequests.add(mailingRequest);
	}
	
	/**
	 * Sends all mailing requests added to the batch
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public void send() throws ClientProtocolException, IOException
	{
		if(mailingRequests!=null)
		{
			for(MailingRequest mailingRequest:mailingRequests)
				mailingRequest.send();
		}
	}
	
	
	
}
