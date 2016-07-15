package inaer.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>CalcService</code>.
 */
public interface CalcServiceAsync {
	void toBin(long value, AsyncCallback<String> callback) throws IllegalArgumentException;
}
