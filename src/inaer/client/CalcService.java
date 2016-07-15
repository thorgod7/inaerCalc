package inaer.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("tobin")
public interface CalcService extends RemoteService {
	String toBin(long value) throws IllegalArgumentException;
}
