package com.blstream.ctf1.service;

/**
 * @author Adrian Swarcewicz
 */
public interface NetworkOperationService {
	public void abortNetworkOperation() throws UnsupportedOperationException;
	
	public boolean isNetworkOperationAborted();
}
