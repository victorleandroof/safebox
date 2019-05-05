package br.com.victor.safebox.config.exception;

public class FeatureException extends RuntimeException {
	
	private static final long serialVersionUID = -1684603617658082415L;
	
	private final String name;
	private final transient Object[] msgParameters;
	
	public FeatureException(String name,String mesagem,Object... msgParameters ) {
			super(mesagem);
			this.name = name;
			this.msgParameters = msgParameters;
	}

	public String getName() {
		return name;
	}

	public Object[] getMsgParameters() {
		return msgParameters;
	}
	
	
}
