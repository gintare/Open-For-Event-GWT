package com.openforevent.events.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GwtRpcServiceAsync {

	void dummy1(HashMap<String, String> parameters,
			AsyncCallback<String> callback);

	void dummy2(HashMap<String, String> parameters,
			AsyncCallback<HashMap<String, String>> callback);

	void dummy3(HashMap<String, String> parameters,
			AsyncCallback<ArrayList<HashMap<String, String>>> callback);

	void dummy4(HashMap<String, String> parameters,
			AsyncCallback<ArrayList<HashMap<String, Object>>> callback);

}
