package org.jiantsquid.network.impl;


import java.io.IOException ;

import org.jiantsquid.network.p2p.node.AbstractNode;
import org.jiantsquid.network.p2p.service.AbstractServiceprovider;

public class Node extends AbstractNode {
	
	
	public Node( int port ) throws IOException {
		super( "localhost", port ) ;
	}
	
	@Override
	protected AbstractServiceprovider createServiceProvider() {
		return new ServiceProvider( getIdentity(), getPeers() ) ;
	}
}
