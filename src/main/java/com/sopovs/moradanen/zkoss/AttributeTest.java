package com.sopovs.moradanen.zkoss;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

public class AttributeTest extends Window implements AfterCompose {

    @Override
    public void afterCompose() {
        Button b = (Button) getFellow("testButton");
        b.setWidgetListener(Events.ON_CLICK,
                "$('.myTestButton').attr('foo', 'bar');");
    }

}
