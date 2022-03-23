package org.jiantsquid.network.p2p.node;


import java.io.IOException ;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import javax.net.ServerSocketFactory;

import org.jiantsquid.core.identity.NetworkEntityI;
import org.jiantsquid.core.identity.NetworkIdentity;
import org.jiantsquid.core.logger.LoggerI;
import org.jiantsquid.network.p2p.peer.Client;
import org.jiantsquid.network.p2p.peer.Peer;
import org.jiantsquid.network.p2p.peer.Peers;
import org.jiantsquid.network.p2p.service.AbstractServiceprovider;
import org.jiantsquid.network.protocol.Connection;

public abstract class AbstractNode implements NetworkEntityI {
	
	private final int port ;
	private final String host ;
	
	protected ServerSocket server ;
	

	private final AbstractServiceprovider serviceProvider ;
	
	class Connections {
		
		private Peers peers = new Peers() ;
		private List<Client> clients = new ArrayList<>() ;
	
		
		Peers getPeers() {
			return peers ;
		}
		
		List<Client> getClients() {
			return new ArrayList<>( clients ) ;
		}
		
		void addClient( Client client ) {
			clients.add( client ) ;
		}
		
		void addPeer( Peer peer ) {
			peers.addPeer( peer ) ;
		}
		
		void clean() {
			List<Peer> peersToRemove = new ArrayList<>() ;
			for( Peer peer : peers.getPeers() ) {
				for( Client client : clients ) {
					if( client.is( peer ) ) {
						peersToRemove.add( peer ) ;
					}
				}
			}
			peers.getPeers().removeAll( peersToRemove ) ;
		}
	}
	
	Connections connections = new Connections() ;
	
	protected AbstractNode( String host, int port, AbstractServiceprovider serviceProvider ) {
		this.port = port ;
		this.host = host ;
		this.serviceProvider = serviceProvider ;
		
		serviceProvider.getLogger().log( getIdentity(), "Start node" ) ;
	}
	
	protected Peers getPeers() {
		return connections.getPeers() ;
	}
	
	public NetworkIdentity getIdentity() {
		return new NetworkIdentity( getHost(), getPort() ) ;
	}
	
	public String getHost() {
		return host ;
	}
	
	public int getPort() {
		return port ;
	}
	
	public void start() throws IOException {
		// start server to process requests
		startServer() ;
	}
	
	private void startServer() throws IOException {
		server = ServerSocketFactory.getDefault().createServerSocket( port ) ;
		new Thread( () -> {
			try {
				while( true ) {
					accept() ;
				}
			} catch ( IOException e) {
				e.printStackTrace();
			}
		} ).start() ;
		
	}
	
	private void accept() throws IOException {
		Client client = new Client( new Connection( server.accept() ), serviceProvider ) ;
		connections.addClient( client )  ;
		// NEED TO CKECK CLIENT LIST CONSISTENCY
	}
	
	public void connectPeer( String host, int port ) throws IOException {
		Peer peer = new Peer( new Connection( host, port ), getIdentity() ) ;
		serviceProvider.getLogger().log( getIdentity(), "try to connect peer " + peer.getId() ) ;
		
		boolean addPeer = true ;
		// NEED TO CKECK CLIENT LIST CONSISTENCY WITH PEER LIST
//		for( Client client : connections.getClients() ) {
//			if( client.is( peer ) ) {
//				addPeer = false ;
//				serviceProvider.getLogger().log( getIdentity(), "peer already a client " + peer.getId() ) ;
//				break ;
//			}
//		}
		if( addPeer ) {
			try {
				if( peer.connect() ) {
					connections.addPeer( peer ) ;
					serviceProvider.getLogger().log( getIdentity(), "peer connection ok " + peer.getId() ) ;
				} else {
					serviceProvider.getLogger().log( getIdentity(), "peer connection rejected " + peer.getId() ) ;
					peer.stop();
				}
			} catch( IOException e ) {
				serviceProvider.getLogger().log( getIdentity(), "peer connection failed : " + peer.getId() + " message=" + e.getMessage() ) ;
			}
		}
		connections.clean(); 
		
	}
	
	public void print( boolean printLog ) {
//		if( printLog ) {
//			((Logger)serviceProvider.getLogger()).print() ;
//		}
//		System.out.println( " *** Host " + this.getIdentity().getId()+ " PEER LIST=" ) ;
//		for( Peer peer : connections.getPeers().getPeers() ) {
//			System.out.println( peer.getId() ) ;
//		}
	}

	public LoggerI getLogger() {
		return serviceProvider.getLogger() ;
	}
}
