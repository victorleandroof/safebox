package br.com.victor;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SpringBootRestApplicationTests {

	@Test
	public void contextLoads() {
		FixtureFactoryLoader.loadTemplates("br.com.victor.templates");
		this.setupTest();
	
	}
	
	public void setupTest() {
		
	}
	
}

