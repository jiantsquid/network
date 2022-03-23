package org.jiantsquid.network.impl;


import java.io.IOException ;
import java.net.InetAddress;

import org.jiantsquid.network.p2p.node.AbstractNode;
import org.jiantsquid.network.p2p.service.AbstractServiceprovider;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class Node extends AbstractNode {
	
	
	public Node( final int port, final int databasePort, final AbstractServiceprovider serviceProvider ) throws IOException {
		super( "localhost", port, serviceProvider ) ;
		Config config = new Config();
		 config.setClusterName( "JIANTSQUID" ) ;

		 NetworkConfig network = config.getNetworkConfig();
	     network.setPort( databasePort ) ;
	     network.setPortAutoIncrement(true);
	     
	     JoinConfig join = network.getJoin();
	     join.getMulticastConfig().setEnabled(false);
	     join.getTcpIpConfig().setEnabled( true ) ;
	     String IPAdress = InetAddress.getLocalHost().getHostAddress() ;
	     join.getTcpIpConfig().addMember(IPAdress).setRequiredMember("localhost:33000").setEnabled(true);
	     network.setJoin( join ) ;
	     
	     //config.getCPSubsystemConfig().setCPMemberCount(3);
		 /// vertex : cluster management
		 ClusterManager mgr = new HazelcastClusterManager(config);
		 VertxOptions options = new VertxOptions().setClusterManager(mgr);

		 // ?????
		 Vertx.clusteredVertx(options, res -> {
		   if (res.succeeded()) {
			   Vertx o = res.result() ;
		      System.out.println( "RESULT=" + o.toString() ) ;
		   } else {
		     System.err.println( "PROBLEM with Hazelcast culster" ) ;
		   }
		 });
	}
}
