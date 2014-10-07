package com.sopovs.moradanen.zkoss;

import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

public class ComponentWindow extends Window implements AfterCompose {

	private Label fooLabel;

	public void init() {
		fooLabel.setValue("Hello World!");
	}

	@Override
	public void afterCompose() {
		fooLabel = (Label) getFellow("fooLabel");
	}

}
