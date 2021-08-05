package com.hoaxify.shared;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5016690378360420593L;
	private String message;

	public GenericResponse(String message) {
		this.message = message;
	}
}
