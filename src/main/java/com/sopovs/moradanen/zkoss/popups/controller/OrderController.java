package com.sopovs.moradanen.zkoss.popups.controller;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;
import com.sopovs.moradanen.zkoss.popups.Car;
import com.sopovs.moradanen.zkoss.popups.CarService;
import com.sopovs.moradanen.zkoss.popups.CarServiceImpl;
import com.sopovs.moradanen.zkoss.popups.OrderItem;

public class OrderController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	private CarService carService = new CarServiceImpl();
	private ListModel<OrderItem> orderItemsModel = new ListModelList<OrderItem>();

	@Wire
	private Checkbox tosCheckbox;

	public OrderController() {
		List<Car> cars = carService.findAll();
		for(int i = 0; i < 3; ++i) {
			Car car = cars.get(i);
			((ListModelList<OrderItem>)orderItemsModel).add(new OrderItem("XYZ-00" + i, 2, car.getCost() + 3000.0, car));
		}
	}

	public ListModel<OrderItem> getOrderItemsModel() {
		return orderItemsModel;
	}

	public String getTotalPrice() {
		double totalPrice = 0.0;
		for(OrderItem orderItem : ((ListModelList<OrderItem>)orderItemsModel).getInnerList())
			totalPrice += orderItem.getSubtotalPrice();
		return MessageFormat.format("{0,number,#.0}", totalPrice);
	}
	
	@Listen("onCreate = #win")
	public void init() {
		Clients.showNotification("Accept terms of service before submit the order","info",tosCheckbox,"end_center",3000);
	}

	@Listen("onClick = #submitButton")
	public void submit() {
		// TOS should be checked before accepting order
		if(tosCheckbox.isChecked()) {
			carService.order(((ListModelList<OrderItem>)orderItemsModel));
			// show result
			Map<String, Object> arguments = new HashMap<String, Object>();
			arguments.put("orderItems", orderItemsModel);
			arguments.put("totalPrice", getTotalPrice());
			String template = "/widgets/getting_started/dialog_popup/order_result.zul";
			Window window = (Window)Executions.createComponents(template, null, arguments);
			window.doModal();
		} else {
			Messagebox.show("Please read the terms of service and accept it before you submit the order.");
		}
	}

	@Listen("onClick = #cancelButton")
	public void cancel() {
		// ask confirmation before canceling order
		EventListener<ClickEvent> clickListener = new EventListener<ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if(Messagebox.Button.YES.equals(event.getButton())) {
					// cancel order
					// ...
					// notify user
					Messagebox.show("The order has been cancelled.");
				}
			}
		};
		Messagebox.show("Are you sure you want to cancel?", "Cancel Order", new Messagebox.Button[]{
				Messagebox.Button.YES, Messagebox.Button.NO }, Messagebox.QUESTION, clickListener);
	}
	
}
