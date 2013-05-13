package com.blstream.ctf1.domain;

//TODO remove empty lines

/**
 * Contain logged player data
 * 
 * @author Adrian Swarcewicz
 */

public class LoggedPlayer {
	
	private String mLogin;
	
	private String mAccessToken;
	
	private String mTokenType;
	
	private String mScope;
	
	
	
	public LoggedPlayer() {
	}

	public LoggedPlayer(String mLogin, String mAccessToken, String mTokenType,
			String mScope) {
		this.mLogin = mLogin;
		this.mAccessToken = mAccessToken;
		this.mTokenType = mTokenType;
		this.mScope = mScope;
	}

	public String getLogin() {
		return mLogin;
	}

	public void setLogin(String login) {
		this.mLogin = login;
	}

	public String getAccessToken() {
		return mAccessToken;
	}

	public void setAccessToken(String accessToken) {
		this.mAccessToken = accessToken;
	}

	public String getTokenType() {
		return mTokenType;
	}

	public void setTokenType(String tokenType) {
		this.mTokenType = tokenType;
	}

	public String getScope() {
		return mScope;
	}

	public void setScope(String scope) {
		this.mScope = scope;
	}

	@Override
	public String toString() {
		return "LoggedPlayer [mLogin=" + mLogin + ", mAccessToken="
				+ mAccessToken + ", mTokenType=" + mTokenType + ", mScope="
				+ mScope + "]";
	}
	
}
