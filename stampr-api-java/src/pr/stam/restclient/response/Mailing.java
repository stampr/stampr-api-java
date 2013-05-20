package pr.stam.restclient.response;

import pr.stam.restclient.Stampr.Format;

/**
 * Class for Mailing objects 
 * json like
 * {"batch_id":"1874",
 * "address":"1 MICROSOFT WAY",
 * "returnaddress":"1 MICROSOFT WAY",
 * "format":"pdf","data":"�����-",
 * "user_id":1,
 * "printer_id":null,
 * "pdf":null,
 * "status":"render",
 * "initiated":"2013-05-15T18:09:30.487Z",
 * "mailing_id":1028}
 * @author JMpaz
 *
 */
public class Mailing {
	private Integer mailing_id;
	private Integer batch_id;
	private Integer printer_id;
	private Integer user_id;
	private String initiated;
	private String address;
	private String returnaddress;
	private String format;
	private String data;
	private String pdf;
	private String status;
	
	@Override
	public String toString() {
		return "Mailing [mailing_id=" + mailing_id + ", batch_id=" + batch_id
				+ ", printer_id=" + printer_id + ", user_id=" + user_id
				+ ", initiated=" + initiated + ", address=" + address
				+ ", returnaddress=" + returnaddress + ", format=" + format
				+ ", data=" + data + ", pdf=" + pdf + ", status=" + status
				+ "]";
	}
	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(Integer batch_id) {
		this.batch_id = batch_id;
	}
	public Integer getPrinter_id() {
		return printer_id;
	}
	public void setPrinter_id(Integer printer_id) {
		this.printer_id = printer_id;
	}
	public Integer getMailing_id() {
		return mailing_id;
	}
	public void setMailing_id(Integer mailing_id) {
		this.mailing_id = mailing_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getReturnaddress() {
		return returnaddress;
	}
	public void setReturnaddress(String returnaddress) {
		this.returnaddress = returnaddress;
	}
	public Format getFormat() {
		return Format.fromString(format);
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInitiated() {
		return initiated;
	}
	public void setInitiated(String initiated) {
		this.initiated = initiated;
	}
	public String getPdf() {
		return pdf;
	}
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	
	
	
	
}
