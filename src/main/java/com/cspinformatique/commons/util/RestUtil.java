package com.cspinformatique.commons.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

public abstract class RestUtil {
	public static HttpHeaders createBasicAuthHeader(final String username, final String password){
		return new HttpHeaders(){
			private static final long serialVersionUID = 1766341693637204893L;
			
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64( auth.getBytes() );
				String authHeader = "Basic " + new String( encodedAuth );
				this.set( "Authorization", authHeader );
		   }
	   };
	}
}
