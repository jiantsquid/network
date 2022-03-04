package org.jiantsquid.network.p2p.peer;

import java.io.IOException;

import org.jiantsquid.network.p2p.message.Request;
import org.jiantsquid.network.p2p.message.Response;
import org.jiantsquid.network.p2p.service.AbstractServiceprovider;
import org.jiantsquid.network.protocol.Connection;

public class Client extends ConnectionEntity {

	private AbstractServiceprovider provider ;
	
	public Client( Connection connection, AbstractServiceprovider provider ) {
		super( connection ) ;
		this.provider = provider ;
		start() ;
	}
	
	public void start() {
		provider.getLogger().log( provider.getIdentity(), "listen to client " + getId() ) ;
		new Thread( () -> run() ).start() ;
		
	}

	private void run() {
		boolean run = true ;
		while ( run ) {
			try {
				Request request = getConnection().readRequest() ;
				provider.getLogger().log( provider.getIdentity(), "process request " + request.getAction() + " for client " + getId() ) ;
				respond( provider.process( request ) ) ;
			} catch (IOException e) {
				e.printStackTrace();
				provider.getLogger().log( provider.getIdentity(), "client disconnected " + getId() + " " + e.getMessage() ) ;
				run = false ;
				try {
					getConnection().close() ;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	private void respond( Response response ) throws IOException {
		provider.getLogger().log( provider.getIdentity(), "respond to client " + getId() ) ;
		getConnection().respond( response );
	}
	
	void stop() throws IOException {
		provider.getLogger().log( provider.getIdentity(), "stop listening to client " + getId() ) ;
		getConnection().close(); 
	}
}
