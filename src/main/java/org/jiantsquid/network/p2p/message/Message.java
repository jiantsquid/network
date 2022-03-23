package org.jiantsquid.network.p2p.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jiantsquid.core.data.Data;
import org.jiantsquid.core.identity.Identity;
import org.jiantsquid.core.identity.NetworkIdentity;


public class Message {

	private Map<String,String> parameters = new HashMap<>() ;
	protected Map<String,Data> userData = new HashMap<>() ;
	private Identity from ;
	
	private List<NetworkIdentity> route = new ArrayList<>() ;
	
	public Message() {}
	
	public Message( Identity from, Map<String,String> parameters ) {
		if( parameters != null ) {
			this.parameters.putAll( parameters ) ;
		}
		this.from = from ;
	}
	
	public void addRouteEntity( NetworkIdentity entity ) {
		route.add( entity ) ;
	}
	
	public boolean allreadyProcessedBy( NetworkIdentity entity ) {
		return route.contains( entity ) ;
	}
	
	public void addUserData( String key, Data userData ) {
		this.userData.put( key, userData ) ;
	}
	
	public void setUserData( Map<String,Data> userData ) {
		this.userData.putAll( userData ) ;
	}
	
	public Map<String,Data> getUserData() {
		return new HashMap<>( userData ) ;
	}
	
	public Map<String,String> getParameters() {
		return new HashMap<>( parameters ) ;
	}
	
	public String getParameter( String parameterName ) {
		return parameters.get( parameterName ) ;
	}

	public Identity getFrom() {
		return from;
	}
	
	protected Map<String,String> getParameters_() {
		return parameters ;
	}
	
	protected void setFrom( NetworkIdentity from ) {
		this.from = from ;
	}
	@Override
	public boolean equals( Object o ) {
		if( !( o instanceof Message) ) {
			return false ;
		}
		
		Message m = (Message) o ;
		return from.equals( m.getFrom() ) && parameters.equals(m.parameters ) ;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash( from, parameters ) ;
	}
}
