package edu.sru.cpsc.webshopping.domain.widgets.electronics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.lang.NonNull;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.User;

/**
 * Inherits from the electronics class and adds attributes for video games.
 * @author NICK
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Electronics_VideoGames extends Widget_Electronics{

	@NonNull
	private String developer;
	
	@NonNull
	private String title;
	
	@NonNull
	private String console;
	
	@NonNull
	private String itemCondition;

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public String getCondition() {
		return itemCondition;
	}

	public void setCondition(String condition) {
		this.itemCondition = condition;
	}

	public String getItemCondition() {
		return itemCondition;
	}

	public void setItemCondition(String itemCondition) {
		this.itemCondition = itemCondition;
	}
	
	public static HashMap<String, HashSet<String>> getAttributes(Iterable<Electronics_VideoGames> videoGames) {
		HashMap<String, HashSet<String>> videoGame_items = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		HashSet<String> videoGame_developer = new HashSet<String>();
		HashSet<String> videoGame_title = new HashSet<String>();
		HashSet<String> videoGame_console = new HashSet<String>();
		HashSet<String> videoGame_condition = new HashSet<String>();
		for (Electronics_VideoGames currVideoGame : videoGames) {
			videoGame_developer.add(currVideoGame.getDeveloper());
			videoGame_title.add(currVideoGame.getTitle());
			videoGame_console.add(currVideoGame.getConsole());
			videoGame_condition.add(currVideoGame.getCondition());
		}
		// Put unique lists into HashMap
		videoGame_items.put("videoGame_developer", videoGame_developer);
		videoGame_items.put("videoGame_title", videoGame_title);
		videoGame_items.put("videoGame_console", videoGame_console);
		videoGame_items.put("videoGame_condition", videoGame_condition);

		
		return videoGame_items;
	}
}
