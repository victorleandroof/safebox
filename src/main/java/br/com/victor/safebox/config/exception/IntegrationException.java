package br.com.victor.safebox.config.exception;

public class IntegrationException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8732901822314968449L;
	private String name;
	private String description;
	
	public IntegrationException(String name, String description,String mensagem) {
		super(mensagem);
		this.setName(name);
		this.setDescription(description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
