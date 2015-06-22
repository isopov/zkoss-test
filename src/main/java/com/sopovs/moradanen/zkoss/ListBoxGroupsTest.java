package com.sopovs.moradanen.zkoss;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.GroupComparator;
import org.zkoss.zul.GroupsModelArray;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

//Ну работает же на http://zkfiddle.org/sample/jhg0s4/3-ListBoxGroupsTest
public class ListBoxGroupsTest extends GenericForwardComposer {

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        Listbox lb = (Listbox) comp.getFellow("lbDict");
        lb.setItemRenderer(new ItemRenderer());
        lb.setModel(getModel());
    }

    private static class ItemRenderer implements ListitemRenderer<Item> {

        @Override
        public void render(Listitem item, Item data, int index) throws Exception {
            item.appendChild(new Listcell(data.group));
            item.appendChild(new Listcell(data.item));
        }

    }

    private static ItemGroupModel getModel() {
        return new ItemGroupModel(new Item[] {
                new Item("foo1", "bar11"),
                new Item("foo2", "bar21"),
                new Item("foo2", "bar22"),
                new Item("foo3", "bar31"),
                new Item("foo4", "bar41"),
                new Item("foo4", "bar42"),
                new Item("foo5", "bar51"),
        });
    }

    private static class ItemGroupModel extends GroupsModelArray<Item, Item, Void, Object> {

        public ItemGroupModel(Item[] data) {
            super(data, ITEM_GROUP_COMPARATOR);
        }
    }

    private static final ItemGroupComparator ITEM_GROUP_COMPARATOR = new ItemGroupComparator();

    private static class ItemGroupComparator implements GroupComparator<Item> {

        @Override
        public int compare(Item o1, Item o2) {
            return o1.item.compareTo(o2.item);
        }

        @Override
        public int compareGroup(Item o1, Item o2) {
            return o1.group.compareTo(o2.group);
        }

    }

    private static class Item {
        private final String group;
        private final String item;

        public Item(String group, String item) {
            this.group = group;
            this.item = item;
        }
    }

}
