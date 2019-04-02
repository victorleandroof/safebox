package br.com.victor.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.victor.safebox.config.security.jwt.AccountCredentials;


public class AccountCredentialsTemplate  implements TemplateLoader  {
	
	@Override
	public void load() {
		Fixture.of(AccountCredentials.class)
			.addTemplate("accountjwt informations valid", new Rule() {
				{
					add("username","admin");
					add("password","password");
					
				}
			}).addTemplate("accountjwt information not valid", new Rule() {
				{
				
				
				}
			});
		
	}
	
}
