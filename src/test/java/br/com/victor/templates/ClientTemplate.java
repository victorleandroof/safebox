package br.com.victor.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.victor.safebox.domain.Client;

public class ClientTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Client.class)
			.addTemplate("client informations valid", new Rule() {
				{
					add("name","victor");
					add("lastname","samuel");
					add("username","samuellv10@hotmail.com");
					add("password","adasdasdas");
					add("cpf","0123456789abcd");
					
				}
			}).addTemplate("client information not valid", new Rule() {
				{
				
					add("username","samuellv10");
					add("cpf","0123456789abcdasdas");
					
				}
			});
		
	}

}
