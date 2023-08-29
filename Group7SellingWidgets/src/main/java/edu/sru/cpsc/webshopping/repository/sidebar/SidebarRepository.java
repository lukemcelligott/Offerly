package edu.sru.cpsc.webshopping.repository.sidebar;

import edu.sru.cpsc.webshopping.domain.sidebar.Sidebar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SidebarRepository extends CrudRepository<Sidebar, Long> {
	//Iterable<Sidebar> getAllTabs();
}