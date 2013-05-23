package pr.stam.restclient.request;

import static pr.stam.restclient.Stampr.getStampr;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import pr.stam.restclient.Stampr.Format;
import pr.stam.restclient.response.Batch;
import pr.stam.restclient.response.Mailing;

public class MailingRequest {
	
	/*required*/ private Batch batch;
	/*required*/ private String address; 
	/*required*/ private String returnAddress;
	/*required*/ private Format format = Format.PDF;
	/*optional*/ private String data;
	/*optional*/ private String md5 = null;
	
	public MailingRequest(Batch batch, String returnAddress, String address, String data) {
		getStampr().notNull(batch,"batch");
		getStampr().notNull(returnAddress,"returnAddress");
		getStampr().notNull(address,"address");
		this.batch = batch;
		this.returnAddress = returnAddress;
		this.address = address;
		this.data = data;
	}
	
	public Mailing send() throws ClientProtocolException, IOException{
		return getStampr().createMailing(batch, address, returnAddress, format, data, md5);
	}
	

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReturnAddress() {
		return returnAddress;
	}

	public void setReturnAddress(String returnAddress) {
		this.returnAddress = returnAddress;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	
	
	
}
