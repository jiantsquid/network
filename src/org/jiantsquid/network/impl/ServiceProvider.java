package org.jiantsquid.network.impl;

import org.jiantsquid.core.identity.NetworkIdentity;
import org.jiantsquid.core.logger.LoggerI;
import org.jiantsquid.network.p2p.peer.Peers;
import org.jiantsquid.network.p2p.service.AbstractServiceprovider;

import com.jiantsquid.database.DatabaseService;
import com.jiantsquid.database.SmartCodeDataBase;
import com.jiantsquid.database.SystemDatabase;
i

public final class ServiceProvider extends AbstractServiceprovider {
	
	private LoggerI logger ;
	
	ServiceProvider(NetworkIdentity nodeIdentity, Peers peers ) {
		super( nodeIdentity, peers );
	}
	
	@Override
	protected void registerServices( Peers peers ) {
		SmartCodeDataBase smartCodeDataBase = new SmartCodeDataBase( getIdentity(), peers ) ;
		SystemDatabase systemDataBase = new SystemDatabase( getIdentity(), peers )  ;
		logger = new Logger( systemDataBase ) ;
		
		services.add( new LoadCodeService( getIdentity(), smartCodeDataBase ) ) ;
		services.add( new PeersService( getIdentity() ) ) ;
		services.add( new DatabaseService( getIdentity(), systemDataBase, smartCodeDataBase ) ) ;
		//TO DO : lot of problems !
		//services.add( new DelegateService( peers ) ) ;
	}

	@Override
	public LoggerI getLogger() {
		return logger ;
	}
}
