package com.sopovs.moradanen.zkoss.paths;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 * @author isopov
 * @since 18.03.2014
 */
public class PathsWindow extends Window implements AfterCompose {

	@Override
	public void afterCompose() {
		Button fooButton = (Button) getFellow("fooButton");
		fooButton.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				Messagebox.show("Hello!");
			}
		});
	}
}
