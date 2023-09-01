package edu.sru.cpsc.webshopping.repository.widgets;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.widgets.Widget_General;

public interface WidgetGeneralRepository<T extends Widget_General> extends CrudRepository<T, Long> {

}