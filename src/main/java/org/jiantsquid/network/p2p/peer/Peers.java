package org.jiantsquid.network.p2p.peer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Peers {

	private final List<Peer> peers = new CopyOnWriteArrayList<>() ;
	
	public final void addPeer( final Peer peer ) {
		peers.add( peer ) ;
		peers.sort( (o1,o2) -> o1.getId().compareTo( o2.getId() ) );
	}
	
	public final List<Peer> getPeers() {
		return new ArrayList<>( peers ) ;
	}
	
	public List<String> getPeerIds() {
		List<String> peerIds = new ArrayList<>() ;
		
		for( Peer peer : peers ) {
			peerIds.add( peer.getId() ) ;
		}
		return peerIds ;
	}
}
