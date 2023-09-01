package edu.sru.cpsc.webshopping;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import edu.sru.cpsc.webshopping.controller.AddWidgetControllerTest;
import edu.sru.cpsc.webshopping.controller.ApplicantDomainControllerTest;
import edu.sru.cpsc.webshopping.controller.EmailControllerTest;
import edu.sru.cpsc.webshopping.controller.EmployeeControllerTest;
import edu.sru.cpsc.webshopping.controller.FriendRequestControllerTest;
import edu.sru.cpsc.webshopping.controller.IndexControllerTest;
import edu.sru.cpsc.webshopping.controller.LandingPageControllerTest;
import edu.sru.cpsc.webshopping.controller.MarketListingControllerTest;
import edu.sru.cpsc.webshopping.controller.MarketListingDomainControllerTest;
import edu.sru.cpsc.webshopping.controller.MessageControllerTest;
import edu.sru.cpsc.webshopping.controller.ShippingDomainControllerTest;
import edu.sru.cpsc.webshopping.controller.StatisticsDomainControllerTest;
import edu.sru.cpsc.webshopping.controller.TestPageControllerTest;
import edu.sru.cpsc.webshopping.controller.TicketPageControllerTest;
import edu.sru.cpsc.webshopping.controller.TransactionControllerSpringTest;
import edu.sru.cpsc.webshopping.controller.TransactionControllerTest;
import edu.sru.cpsc.webshopping.controller.UserDetailsControllerTest;
import edu.sru.cpsc.webshopping.controller.UserListDomainControllerTest;
import edu.sru.cpsc.webshopping.controller.UtilityControllerTest;
import edu.sru.cpsc.webshopping.controller.WebshoppingControllerSpringTest;
import edu.sru.cpsc.webshopping.controller.billing.ControllerBillingSpringTest;
import edu.sru.cpsc.webshopping.controller.misc.CustomErrorControllerSpringTest;
import edu.sru.cpsc.webshopping.controller.purchase.ConfirmPurchasePageControllerTest;
import edu.sru.cpsc.webshopping.controller.purchase.MarketListingPageControllerTest;
import edu.sru.cpsc.webshopping.controller.purchase.TransactionDetailsPageControllerTest;
import edu.sru.cpsc.webshopping.domain.billing.DomainBillingSpringTest;
import edu.sru.cpsc.webshopping.domain.market.DomainMarketSpringTest;
import edu.sru.cpsc.webshopping.domain.user.DomainUserSpringTest;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetSpringTest;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.DomainWidgetsAppliancesSpringTest;
import edu.sru.cpsc.webshopping.domain.widgets.electronics.DomainWidgetsElectronicsSpringTest;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.DomainWidgetsLawncareSpringTest;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.DomainWidgetsVehiclesSpringTest;
import edu.sru.cpsc.webshopping.secure.WebshoppingSecureSpringTest;
import edu.sru.cpsc.webshopping.service.TicketServiceSpringTest;
import edu.sru.cpsc.webshopping.util.constants.TimeConstantsSpringTest;
import edu.sru.cpsc.webshopping.util.enums.WebshoppingUtilEnumsSpringTest;



/*
 * Tests all of the tests in the project
 * Currently not working because the database is set to null 
 */

@RunWith(Suite.class)
@SuiteClasses({SellingWidgetsTests.class, AddWidgetControllerTest.class, ApplicantDomainControllerTest.class, EmailControllerTest.class,
	EmployeeControllerTest.class, FriendRequestControllerTest.class, IndexControllerTest.class, LandingPageControllerTest.class, MarketListingControllerTest.class, MarketListingDomainControllerTest.class,
	MessageControllerTest.class, ShippingDomainControllerTest.class, StatisticsDomainControllerTest.class, TestPageControllerTest.class, TicketPageControllerTest.class, TransactionControllerSpringTest.class, 
	TransactionControllerTest.class, UserDetailsControllerTest.class, UserListDomainControllerTest.class, UtilityControllerTest.class, WebshoppingControllerSpringTest.class, 
	ControllerBillingSpringTest.class, CustomErrorControllerSpringTest.class, ConfirmPurchasePageControllerTest.class, MarketListingPageControllerTest.class, TransactionDetailsPageControllerTest.class,
	DomainBillingSpringTest.class, DomainMarketSpringTest.class, DomainUserSpringTest.class, WidgetSpringTest.class, DomainWidgetsAppliancesSpringTest.class, DomainWidgetsElectronicsSpringTest.class, 
	DomainWidgetsLawncareSpringTest.class, DomainWidgetsVehiclesSpringTest.class, WebshoppingSecureSpringTest.class, TicketServiceSpringTest.class, TimeConstantsSpringTest.class, WebshoppingUtilEnumsSpringTest.class
})


public class AllTestFile {
	
	public static void main(String [] args) {
		Result result = JUnitCore.runClasses(AllTestFile.class);
		for(Failure failure : result.getFailures()){
			System.out.println(failure.toString());
		}
			System.out.println(result.wasSuccessful());
	}


}
	
	
	
