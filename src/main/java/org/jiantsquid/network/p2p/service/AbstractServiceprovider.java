package org.jiantsquid.network.p2p.service;

import java.util.ArrayList;
import java.util.List;

import org.jiantsquid.core.identity.Identity;
import org.jiantsquid.core.identity.NetworkIdentity;
import org.jiantsquid.core.logger.LoggerI;
import org.jiantsquid.network.p2p.message.Request;
import org.jiantsquid.network.p2p.message.Response;

public abstract class AbstractServiceprovider {
	
	protected List<ServiceI> services = new ArrayList<>() ;
	

	protected AbstractServiceprovider() {
		registerServices() ;
	}
	
	protected abstract void registerServices() ;
	
	public Response process( Request request ) {
		
		Response response = new Response() ;
		for( ServiceI service : services ) {
			if( service.process( getLogger(), request, response) ) {
				break;
			}
		}
		
		return response ;
	}
	
	public abstract LoggerI getLogger() ;
	public abstract Identity getIdentity() ;
}
