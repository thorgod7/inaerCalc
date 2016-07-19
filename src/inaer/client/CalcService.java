package inaer.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import inaer.shared.CalcDataSimple;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("tobin")
public interface CalcService extends RemoteService {
	String toBin(long value) throws IllegalArgumentException;
	List<CalcDataSimple> getData();
}
