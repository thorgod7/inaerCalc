package inaer.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import inaer.shared.CalcDataSimple;

/**
 * The async counterpart of <code>CalcService</code>.
 */
public interface CalcServiceAsync {
	void toBin(long value, AsyncCallback<String> callback) throws IllegalArgumentException;
	void getData(AsyncCallback<List<CalcDataSimple>> callback);
}
