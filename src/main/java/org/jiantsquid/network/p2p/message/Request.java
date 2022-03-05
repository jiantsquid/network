package org.jiantsquid.network.p2p.message;

import java.util.Map;

import org.jiantsquid.core.identity.NetworkIdentity;

public class Request extends Message {

	private static final String REQUEST_ACTION = "REQUEST_ACTION" ;
	
	public Request() {}
	
	public Request( NetworkIdentity from, String action, Map<String, String> params) {
		super(from, params);
		getParameters_().put( REQUEST_ACTION , action ) ;
	}

	public String getAction() {
		return getParameters_().get( REQUEST_ACTION ) ;
	}
	
}
