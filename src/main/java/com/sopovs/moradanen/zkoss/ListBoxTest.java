package com.sopovs.moradanen.zkoss;

import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Window;

public class ListBoxTest extends Window implements AfterCompose {

    @Override
    public void afterCompose() {
        Listbox lb = (Listbox) getFellow("lbDict");
        lb.setModel(new SimpleListModel<String>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26" }));

    }

}
