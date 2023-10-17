package edu.sru.cpsc.webshopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import edu.sru.cpsc.webshopping.service.TaxExcelToDatabaseService;
import edu.sru.cpsc.webshopping.util.PreLoad;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SellingWidgets extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
	private final PreLoad preLoad;

	@Autowired
    private Environment env;

    @Autowired
    private TaxExcelToDatabaseService taxExcelToDatabaseService;

	@Autowired
    public SellingWidgets(PreLoad preLoad, Environment env) {
        this.preLoad = preLoad;
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(SellingWidgets.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        Resource excelResource = new ClassPathResource("StateTaxes.xlsx");
        try {
            taxExcelToDatabaseService.loadFromExcelFile(excelResource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SellingWidgets.class);
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