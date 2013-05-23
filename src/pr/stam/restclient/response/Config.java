package pr.stam.restclient.response;

/**
 * Class for Config objects 
 * json like
 * {"size":"standard",
 * "turnaround":"threeday",
 * "style":"color",
 * "output":"single",
 * "returnenvelope":false,
 * "user_id":1,
 * "config_id":4688}
 * @author JMpaz
 *
 */
public class Config {
	private Integer config_id;
	private Integer user_id;
	private String size;
	private String turnaround;
	private String style;
	private String output;
	private Boolean returnenvelope;
	
	private Config(Integer config_id) {
		this.config_id = config_id;
	}

	@Override
	public String toString() {
		return "Config [config_id=" + config_id + ", user_id=" + user_id
				+ ", size=" + size + ", turnaround=" + turnaround + ", style="
				+ style + ", output=" + output + ", returnenvelope="
				+ returnenvelope + "]";
	}
	
	public Integer getConfig_id() {
		return config_id;
	}
	public void setConfig_id(Integer config_id) {
		this.config_id = config_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	public String getTurnaround() {
		return turnaround;
	}

	public void setTurnaround(String turnaround) {
		this.turnaround = turnaround;
	}

	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public Boolean getReturnenvelope() {
		return returnenvelope;
	}
	public void setReturnenvelope(Boolean returnenvelope) {
		this.returnenvelope = returnenvelope;
	}
	
	
}
