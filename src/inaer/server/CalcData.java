package inaer.server;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.appengine.api.datastore.Key;

@Entity
public class CalcData {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	private double input;
	private String output;
	
	public Key getKey() {
		return key;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public double getInput() {
		return input;
	}
	public void setInput(double input) {
		this.input = input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	
	public String toString() {
		return  "Key: "+key+" Date: "+timestamp.toString()+", input: "+input+" -> output: "+output;
	}
	
}
