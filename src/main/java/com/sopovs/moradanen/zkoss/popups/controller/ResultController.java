package com.sopovs.moradanen.zkoss.popups.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

public class ResultController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	@Wire
	private Window resultWin;

	@Listen("onClick = #closeButton")
	public void close() {
		resultWin.detach();
	}
}
