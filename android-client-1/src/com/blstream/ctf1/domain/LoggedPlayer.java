package com.blstream.ctf1.domain;

/**
 * Contain logged player data
 * 
 * @author Adrian Swarcewicz
 */

public class LoggedPlayer {

	private String login;

	private String accessToken;

	private String tokenType;

	private String scope;

	public LoggedPlayer() {
	}

	public LoggedPlayer(String mLogin, String mAccessToken, String mTokenType, String mScope) {
		this.login = mLogin;
		this.accessToken = mAccessToken;
		this.tokenType = mTokenType;
		this.scope = mScope;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "LoggedPlayer [mLogin=" + login + ", mAccessToken=" + accessToken + ", mTokenType=" + tokenType + ", mScope=" + scope + "]";
	}
}
