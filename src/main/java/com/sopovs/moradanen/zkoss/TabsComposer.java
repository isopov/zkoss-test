package com.sopovs.moradanen.zkoss;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabs;

public class TabsComposer extends GenericForwardComposer<Component> {
	private static final long serialVersionUID = 1L;

	@Wire
	private Tabbox mytabbox;
	@Wire
	private Label currentTab;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		Tabs tabs = new Tabs();
		tabs.setParent(mytabbox);

		Tab first = new Tab("first");
		first.setParent(tabs);
		Tab second = new Tab("second");
		second.setParent(tabs);
		
		Tab third = new Tab("third");
		third.setParent(tabs);
		EventListener<Event> listener = new EventListener<Event>() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				currentTab.setValue(((Tab)event.getTarget()).getLabel());
			}
		};
		first.addEventListener(Events.ON_SELECT, listener);
		second.addEventListener(Events.ON_SELECT, listener);
		third.addEventListener(Events.ON_SELECT, listener);

	}

}
