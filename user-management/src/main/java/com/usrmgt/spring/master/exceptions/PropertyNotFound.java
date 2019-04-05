package com.usrmgt.spring.master.exceptions;

/**
 * Signals that a property(key) was not found in the hash table.
 */
@SuppressWarnings("serial")
public class PropertyNotFound extends Exception {
	  public PropertyNotFound(String message) {
	        super(message);
	    }
}
