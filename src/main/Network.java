package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jiantsquid.network.impl.Node;
import org.jiantsquid.network.p2p.node.AbstractNode;

public class Network {
	
	public static final int PORT = 32000 ;
	
	public static final int NB_NODES = 5 ;
	
	List<AbstractNode> nodes = new ArrayList<>(); 
	
	int nodeCount = 0 ;
	class NodeRunnable implements Runnable {
		
		AbstractNode node ;
		
		NodeRunnable( AbstractNode node ) {
			
			this.node = node ;
		}
		public void run() {
			try {
				System.out.println( "start node " + node .getPort() ) ;
				node.start();
				System.out.println( "started node " + node .getPort() ) ;
			} catch (IOException e) {
				e.printStackTrace();
			}
			nodeCount ++ ;
		}
	}
	public Network() throws ClassNotFoundException, IOException {
		long t0 = System.currentTimeMillis() ;
		System.out.println( "start network" ) ;
			for( int i = 0 ; i < NB_NODES ; i ++ ) {
				try {
					nodes.add( new Node( PORT + i ) ) ;
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
			for( final AbstractNode node : nodes ) {
				new Thread( new NodeRunnable( node ) ).start() ;
			}
			
			while( nodeCount < NB_NODES ) {
				try {
					Thread.sleep( 100 ) ;
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			long t1 = System.currentTimeMillis() ;
			System.out.println( "network started " + ( ( t1- t0 ) / 1000 ) + " s") ;
			print() ;
	}
	
	public void print() {
		boolean print = true ;
		//int count = 0 ;
		for( AbstractNode node : nodes ) {
			node.print( print /*count == NB_NODES - 1*/ ) ;
			//print = false ;
			//count ++ ;
		}
	}
}
