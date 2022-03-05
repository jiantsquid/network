package org.jiantsquid.network.p2p.service;

public class Actions {

	private Actions() {}
	
	// request status
	public static final String REQUEST_STATUS = "REQUEST_STATUS" ;
	public static final String REQUEST_OK = "REQUEST_OK" ;
	public static final String REQUEST_FAILED = "REQUEST_FAILED" ;
	
	public static final String IDENTITY = "IDENTITY" ;
	
	// database 
	public static final String DATABASE_NAME = "DATABASE_NAME" ;
	public static final String INSERT_DATA = "INSERT_DATA" ;
	public static final String GET_DATA = "GET_DATA" ;
	public static final String DATA_KEY = "DATA_KEY" ;
	
	// code
	public static final String LOAD_SERVICE_ACTION = "LOAD_SERVICE_ACTION" ;
	public static final String LOAD_MAINGUI_PARAM = "LOAD_MAINGUI_PARAM" ;
	public static final String WHAT = "WHAT" ;
	
	public static final String PEER_REQUEST = "PEER_REQUEST" ;
}
