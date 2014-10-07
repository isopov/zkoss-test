package com.sopovs.moradanen.zkoss;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;

public class ComponentComposer extends GenericForwardComposer<Component> {

	@Wire
	private Button fooButton;

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);

		fooButton.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				ComponentWindow window = (ComponentWindow) Executions.createComponents("/componentTest2.zul", null,
						null);
				window.init();
				window.doModal();

			}
		});
	}

}
