package org.jiantsquid.network.p2p.peer;

import org.jiantsquid.core.identity.NetworkIdentity;
import org.jiantsquid.network.protocol.Connection;

public class ConnectionEntity {

	private Connection connection ;
	protected NetworkIdentity connectionIdentity ;
	
	ConnectionEntity( Connection connection ) {
		this.connection = connection ;
		connectionIdentity   = new NetworkIdentity( connection.getHost(), connection.getPort() ) ;
	}
	
	protected Connection getConnection() { 
		return connection ;
	}
	
	public String getId() {
		return connectionIdentity.getId() ;
	}
	
	public boolean is( ConnectionEntity entity ) {
		return entity.getId().equals( getId() ) ;
	}
}
