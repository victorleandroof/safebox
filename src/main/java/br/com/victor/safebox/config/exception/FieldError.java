package br.com.victor.safebox.config.exception;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class FieldError implements Serializable{
	
	
	
	private static final long serialVersionUID = -4905923394151922805L;
	
	private String mensagem;
	private List<String> fields;

	public FieldError() {
		
	}
	
	public FieldError(String mensagem,List<String> fields) {
		this.mensagem = mensagem;
		this.fields = fields;
	}
	@Override
	public int hashCode() {
		return Objects.hash(mensagem,fields);
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass())return false;
		FieldError fieldError = (FieldError) obj;
		return Objects.equals(mensagem, fieldError.mensagem) &&
				Objects.equals(fields, fieldError.fields);
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	
	
}
