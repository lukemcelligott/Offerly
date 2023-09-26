package edu.sru.cpsc.webshopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.core.env.Environment;

import edu.sru.cpsc.webshopping.util.PreLoad;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SellingWidgets implements CommandLineRunner {

    @Autowired
	private final PreLoad preLoad;

	@Autowired
    private Environment env;

	@Autowired
    public SellingWidgets(PreLoad preLoad, Environment env) {
        this.preLoad = preLoad;
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(SellingWidgets.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String ddlAuto = env.getProperty("spring.jpa.hibernate.ddl-auto");
        if (!"update".equals(ddlAuto)) {
            //preLoad.importCategoriesFromCSV();
        }
        System.out.println("Running");
    }
}