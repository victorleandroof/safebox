package br.com.victor.safebox.config.exception;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseError implements Serializable{
	
	private static final long serialVersionUID = -1526974410234879738L;

	public static final String  ERR_FORBIDDEN = "error.forbidden";
	
	private final String menssage;
	private final String description;
	
	private List<FieldError> fieldsError;
	
	public ResponseError(String menssage,String description) {
		this.menssage = menssage;
		this.description = description;
		
	}

	public List<FieldError> getFieldsError() {
		return fieldsError;
	}

	public String getMenssage() {
		return menssage;
	}

	public String getDescription() {
		return description;
	}

	
	
}
