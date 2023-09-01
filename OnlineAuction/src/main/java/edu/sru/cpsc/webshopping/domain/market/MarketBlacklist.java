package edu.sru.cpsc.webshopping.domain.market;

import java.io.IOException;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.*;

import edu.sru.cpsc.webshopping.domain.widgets.Widget;
//import edu.sru.cpsc.webshopping.controller.AddWidgetController;
import edu.sru.cpsc.webshopping.controller.LandingPageController;


//Takes input from a new product and checks if it contains blacklisted terms | Josh Malone
public class MarketBlacklist extends Widget {
		{
			
			String comparison = getName().toLowerCase();
			String[] inputList = comparison.split(" ", 9999);
			
			List<String> blackList = new ArrayList<String>();
			try {
				Scanner scnr = new Scanner(new FileReader("SellingWidgets\\Documents\\Text_files\\bad_words.txt"));
				String str;
				while (scnr.hasNext()) {
					str = scnr.next();
					blackList.add(str);
				}
				//String[] array = blackList.toArray(new String[0]);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int i = 0; i < inputList.length; i++)
							if (blackList.contains(inputList[i])) {
								System.out.println("The item you are trying to list is against our listing policy. Please list a different item");
								break;
							}
			
							else
							{
								//System.out.println("Acceptable term");
							}
						
						}

						
					}
	
	
	
	
	


