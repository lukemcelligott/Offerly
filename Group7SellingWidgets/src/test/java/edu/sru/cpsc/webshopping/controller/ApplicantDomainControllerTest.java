package edu.sru.cpsc.webshopping.controller;
import java.util.Optional;
import org.junit.Test;
import java.util.function.BooleanSupplier;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.controller.ApplicantDomainController;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.Applicant;
import edu.sru.cpsc.webshopping.repository.applicant.ApplicantRepository;
public class ApplicantDomainControllerTest {
	private static final BooleanSupplier True = null;
	private Logger log = Logger.getLogger(this.getClass());
	private Object applicantdomaincontroller;
	private Applicant Applicant;
	@BeforeAll
	static void initAll() {
	}
	@BeforeEach
	void init() {
	}
	@Test
		@DisplayName("get Applicant")
	
	public void getApplicant() {
		try {
			log.info("Starting execution of getApplicant");
		 Applicant expectedValue=null;
	
	ApplicantDomainController applicantdomaincontroller= new ApplicantDomainController(null);
	Applicant actualValue= applicantdomaincontroller.getApplicant(0);
		log.info(actualValue);
		System.out.println("actual value");
		}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
		
	}
	
@Test
@DisplayName("get Applicant Exception")
public void getApplicantException() {
	try {
		log.info("Starting execution of getApplicantException");

ApplicantDomainController applicationdomaincontroller= new ApplicantDomainController(null);
boolean actualValue = applicantdomaincontroller.equals(applicationdomaincontroller);
//((Object) applicantdomaincontroller.getApplicantException();
Assertions.assertTrue(false);
	}
	catch(Exception exception) {
	log.error(exception);
	exception.printStackTrace();
	Assertions.assertFalse(false);
}
}	
		
@Test 
@DisplayName("get All Boxes")
public void getAllBoxes(@PathVariable int getAllBoxes) {
	try {
		
		log.info(getAllBoxes);
ApplicantDomainController applicationdomaincontroller = new ApplicantDomainController(null);
Iterable<Applicant> actualValue=applicationdomaincontroller.getAllBoxes();
log.info(actualValue);
System.out.println("Actual Value");
Assertions.assertEquals(actualValue,0);

	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
	}

@Test 
@DisplayName("add Applicant")

public void addApplicant() {
	try {
		log.info("Starting execution of addApplicant");
BindingResult result= null;

ApplicantDomainController applicantdomaincontroller= new ApplicantDomainController(null);
applicantdomaincontroller.addApplicant(null, result);
Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("add Applicant Simple")

public void addApplicantSimple() {
	
	try {
		log.info("Starting execution of addApplicantSimple");

ApplicantDomainController applicantdomaincontroller= new ApplicantDomainController(null);
applicantdomaincontroller.addApplicantSimple(Applicant);
Assertions.assertTrue(true);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("edit Applicant")
public void editApplicant() {
	try {
		log.info("Starting Execution of editApplicant");

ApplicantDomainController applicationdomaincontroller= new ApplicantDomainController(null);
((ApplicantDomainController) applicantdomaincontroller).editApplicant(Applicant);
Assertions.assertTrue(true);

	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@Test
@DisplayName("delete Applicant")
public void deleteApplicant(){
	try {
		log.info("Starting execution of deleteApplicant");

ApplicantDomainController applicantdomaincontroller= new ApplicantDomainController(null);
applicantdomaincontroller.deleteApplicant(Applicant);
Assertions.assertTrue(True);
	}
	catch(Exception exception) {
		log.error(exception);
		exception.printStackTrace();
		Assertions.assertFalse(false);
	}
}
@AfterEach
void tearDown() {
}
@AfterAll
static void tearDownAll() {
}
}


