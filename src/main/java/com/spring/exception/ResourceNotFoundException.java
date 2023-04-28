package com.spring.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	String resourcename;
	String fieldname;
	long fieldvalue;
	public ResourceNotFoundException(String resourcename, String fieldname, long fieldvalue) {
		super(String.format("%s not found with %s : %s",resourcename,fieldname,fieldvalue));
		this.resourcename = resourcename;
		this.fieldname = fieldname;
		this.fieldvalue = fieldvalue;
	}
	
}
