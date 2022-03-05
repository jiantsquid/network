package org.jiantsquid.network.p2p.service;

import org.jiantsquid.core.logger.LoggerI;
import org.jiantsquid.network.p2p.message.Request;
import org.jiantsquid.network.p2p.message.Response;

public interface ServiceI {
	boolean process( LoggerI logger, Request request, Response response ) ;
}
