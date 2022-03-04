package org.jiantsquid.network.p2p.service;

import java.util.ArrayList;
import java.util.List;

import org.jiantsquid.core.identity.NetworkIdentity;
import org.jiantsquid.core.logger.LoggerI;
import org.jiantsquid.network.p2p.message.Request;
import org.jiantsquid.network.p2p.message.Response;
import org.jiantsquid.network.p2p.peer.Peers;

public abstract class AbstractServiceprovider {
	
	protected List<ServiceI> services = new ArrayList<>() ;
	
	private NetworkIdentity identity ;

	protected AbstractServiceprovider( NetworkIdentity identity, Peers peers ) {
		this.identity = identity ;
		registerServices( peers ) ;
	}
	
	protected abstract void registerServices( Peers peers ) ;
	
	public Response process( Request request ) {
		
		Response response = new Response() ;
		for( ServiceI service : services ) {
			if( service.process( getLogger(), request, response) ) {
				break;
			}
		}
		
		return response ;
	}
	
	public NetworkIdentity getIdentity() {
		return identity ;
	}
	
	public abstract LoggerI getLogger() ;

}
