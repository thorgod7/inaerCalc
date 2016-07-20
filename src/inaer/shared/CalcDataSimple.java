package inaer.shared;

/**
 * Class used to send data from the server to the client.
 * 
 * @author samuelo
 */
public class CalcDataSimple implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  public String timestamp;
  public double input;
  public String output;

  public String toString() {
    return "Date: " + timestamp + ", input: " + input + " -> output: " + output;
  }
}
