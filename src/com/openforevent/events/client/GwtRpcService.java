package com.openforevent.events.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gwtrpc")
public interface GwtRpcService extends RemoteService {
	
	public HashMap<String, Object> processRequest(HashMap<String, String> parameters);
	
	public String dummy1(HashMap<String, String> parameters);
	public HashMap<String, Object> dummy2(HashMap<String, Object> parameters);
	public ArrayList<HashMap<String, String>> dummy3(HashMap<String, String> parameters);
	public ArrayList<HashMap<String, Object>> dummy4(HashMap<String, String> parameters);
	

}
