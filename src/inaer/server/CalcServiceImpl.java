package inaer.server;

import inaer.client.CalcService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CalcServiceImpl extends RemoteServiceServlet implements CalcService {

	public String toBin(long value) throws IllegalArgumentException {
		String res;
		try {
			res = Long.toBinaryString(value);
		}
		catch(Exception e) {
			throw new IllegalArgumentException("Error in binary conversion");
		}
		return res;
	}
}
