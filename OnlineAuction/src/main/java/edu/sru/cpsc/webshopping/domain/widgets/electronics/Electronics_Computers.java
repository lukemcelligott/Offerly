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
 * Inherits from the electronics class and adds attributes for computers.
 * @author NICK
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Electronics_Computers extends Widget_Electronics{

	@NonNull
	private String memory;
	
	@NonNull
	private String storage;
	
	@NonNull
	private String processor;
	
	@NonNull
	private String gpu;
	
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public String getGpu() {
		return gpu;
	}
	public void setGpu(String gpu) {
		this.gpu = gpu;
	}
	
	public static HashMap<String, HashSet<String>> getAttributes(Iterable<Electronics_Computers> computers) {
		HashMap<String, HashSet<String>> computer_items = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		HashSet<String> computer_memory = new HashSet<String>();
		HashSet<String> computer_storage = new HashSet<String>();
		HashSet<String> computer_processor = new HashSet<String>();
		HashSet<String> computer_gpu = new HashSet<String>();
		for (Electronics_Computers currComputer : computers) {
			computer_memory.add(currComputer.getMemory());
			computer_storage.add(currComputer.getStorage());
			computer_processor.add(currComputer.getProcessor());
			computer_gpu.add(currComputer.getGpu());
		}
		// Put unique lists into HashMap
		computer_items.put("computer_memory", computer_memory);
		computer_items.put("computer_storage", computer_storage);
		computer_items.put("computer_processor", computer_processor);
		computer_items.put("computer_gpu", computer_gpu);

		
		return computer_items;
	}
}
