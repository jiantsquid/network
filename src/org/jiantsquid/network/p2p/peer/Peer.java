package org.jiantsquid.network.p2p.peer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jiantsquid.core.identity.NetworkIdentity;
import org.jiantsquid.network.p2p.message.Request;
import org.jiantsquid.network.p2p.message.Response;
import org.jiantsquid.network.p2p.service.Actions;
import org.jiantsquid.network.protocol.Connection;


public class Peer extends ConnectionEntity {

	private NetworkIdentity identity ;
	
	private boolean ready = false ;
	
	
	
	public Peer( Connection connection, NetworkIdentity identity  ){
		super( connection ) ;
		this.identity   = identity ;
	}
	
	public boolean connect() throws IOException {
		getConnection().connect();
		
		Map<String,String> parameters = new HashMap<>() ;
		parameters.put( Actions.IDENTITY, identity.getId() ) ;
		Request request = new Request( identity, Actions.PEER_REQUEST, null ) ;
		
		Response response = getConnection().sendRequest( request ) ;
		
		ready = response != null && 
				Actions.REQUEST_OK.equals( response.getParameter( Actions.REQUEST_STATUS ) ) ;
		return ready ;	
	}
	
	public boolean isReady() {
		return ready ;
	}
	
	public void stop() throws IOException {
		getConnection().close(); 
	}
	
	public Response sendRequest( Request request ) throws IOException {
		return getConnection().sendRequest( request ) ;
	}
}
