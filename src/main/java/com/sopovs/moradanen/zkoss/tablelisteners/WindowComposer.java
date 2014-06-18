package com.sopovs.moradanen.zkoss.tablelisteners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.event.TreeDataEvent;

import com.sopovs.moradanen.zkoss.tablelisteners.model.Group;
import com.sopovs.moradanen.zkoss.tablelisteners.model.ModelService;
import com.sopovs.moradanen.zkoss.tablelisteners.model.Student;

public class WindowComposer extends GenericForwardComposer<Component> {
    private static final long serialVersionUID = 1L;
    private final ModelService service = ModelService.getInstance();
    @Wire
    private Tree groupTree;
    @Wire
    private Button addGroupButton;
    @Wire
    private Button addStudentButton;
    @Wire
    private Button removeButton;
    @Wire
    private Button toggle1CouncilButton;
    @Wire
    private Button toggle2CouncilButton;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        disableAllButtons();
        groupTree.setModel(getInitialTreeModel());
        groupTree.setItemRenderer(new GroupTreeItemRenderer());
        groupTree.addEventListener(Events.ON_SELECT, treeSelectionListener);

        addGroupButton.addEventListener(Events.ON_CLICK, addGroupListener);
        removeButton.addEventListener(Events.ON_CLICK, removeListener);
        addStudentButton.addEventListener(Events.ON_CLICK, addStudentListener);
        toggle1CouncilButton.addEventListener(Events.ON_CLICK, toggle1CouncilListener);
        toggle2CouncilButton.addEventListener(Events.ON_CLICK, toggle2CouncilListener);
    }

    private final EventListener<Event> addGroupListener = new EventListener<Event>() {
        @Override
        public void onEvent(Event event) throws Exception {
            @SuppressWarnings("unchecked")
            DefaultTreeNode<Object> root = (DefaultTreeNode<Object>) groupTree.getModel().getRoot();
            Group group = new Group("Математика", Collections.<String> emptyList(), null, null);
            service.addGroup(group);
            root.add(new DefaultTreeNode<Object>(group, new ArrayList<TreeNode<Object>>()));
        }
    };

    private final EventListener<Event> treeSelectionListener = new EventListener<Event>() {
        @Override
        public void onEvent(Event event) throws Exception {
            disableAllButtons();
            DefaultTreeModel model = (DefaultTreeModel) groupTree.getModel();
            Set selection = model.getSelection();
            if (!selection.isEmpty() && selection.size() == 1) {
                removeButton.setDisabled(false);
                DefaultTreeNode node = (DefaultTreeNode) selection.iterator().next();
                Object item = node.getData();
                if (item instanceof Group) {
                    addStudentButton.setDisabled(false);
                } else {
                    toggle1CouncilButton.setDisabled(false);
                    toggle2CouncilButton.setDisabled(false);
                }
            } else {
                throw new IllegalStateException();
            }
        }
    };

    private final EventListener<Event> removeListener = new EventListener<Event>() {
        @Override
        public void onEvent(Event event) throws Exception {
            DefaultTreeModel model = (DefaultTreeModel) groupTree.getModel();
            Set selection = model.getSelection();
            if (!selection.isEmpty() && selection.size() == 1) {
                DefaultTreeNode node = (DefaultTreeNode) selection.iterator().next();
                Object item = node.getData();
                if (item instanceof Group) {
                    service.removeGroup((Group) item);
                } else {
                    service.removeStudentFromGroup((Group) node.getParent().getData(), (Student) item);
                }
                node.getParent().remove(node);
                disableAllButtons();
            } else {
                throw new IllegalStateException();
            }
        }
    };

    private final EventListener<Event> toggle1CouncilListener = new FirstMemberToggleCouncilMemberListener();

    private final EventListener<Event> toggle2CouncilListener = new SecondMemberToggleCouncilMemberListener();

    private final EventListener<Event> addStudentListener = new EventListener<Event>() {
        @Override
        public void onEvent(Event event) throws Exception {
            DefaultTreeModel model = (DefaultTreeModel) groupTree.getModel();
            Set selection = model.getSelection();
            if (!selection.isEmpty() && selection.size() == 1) {
                DefaultTreeNode node = (DefaultTreeNode) selection.iterator().next();
                Object item = node.getData();
                if (item instanceof Group) {
                    Student student = new Student("Васечкин");
                    service.addStudentToGroup((Group) item, student);
                    node.add(new DefaultTreeNode(student));
                } else {
                    throw new IllegalStateException("Попытка добавить студента не в группу");
                }
            } else {
                throw new IllegalStateException();
            }
        }
    };

    private void disableAllButtons() {
        removeButton.setDisabled(true);
        addStudentButton.setDisabled(true);
        toggle1CouncilButton.setDisabled(true);
        toggle2CouncilButton.setDisabled(true);
    }

    private TreeModel getInitialTreeModel() {
        final List<Group> groups = service.getAllGroups();
        List<DefaultTreeNode> groupNodes = new ArrayList<DefaultTreeNode>(groups.size());
        for (Group group : groups) {
            List<Student> students = group.getStudents();
            List<DefaultTreeNode> studentNodes = new ArrayList<DefaultTreeNode>(students.size());
            for (Student student : students) {
                studentNodes.add(new DefaultTreeNode<Student>(student));
            }
            groupNodes.add(new DefaultTreeNode(group, studentNodes));
        }

        return new DefaultTreeModel(new DefaultTreeNode(null, groupNodes));
    }

    private static class GroupTreeItemRenderer implements TreeitemRenderer<DefaultTreeNode> {
        @Override
        public void render(Treeitem item, DefaultTreeNode node, int index) throws Exception {
            Object data = node.getData();
            if (data instanceof Group) {
                item.setLabel(((Group) data).getName());
            } else if (data instanceof Student) {
                Student student = (Student) data;
                StringBuilder value = new StringBuilder(student.getName());
                Group group = (Group) node.getParent().getData();
                if (student.equals(group.getCouncil().getMember1())) {
                    value.append(" (1 член совета)");
                }
                if (student.equals(group.getCouncil().getMember2())) {
                    value.append(" (2 член совета)");
                }
                item.setLabel(value.toString());
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private abstract class ToggleCouncilMemberListener implements EventListener<Event> {
        @Override
        public void onEvent(Event event) throws Exception {
            DefaultTreeModel model = (DefaultTreeModel) groupTree.getModel();
            Set selection = model.getSelection();
            if (!selection.isEmpty() && selection.size() == 1) {
                DefaultTreeNode node = (DefaultTreeNode) selection.iterator().next();
                Object student = node.getData();
                if (student instanceof Student) {
                    DefaultTreeNode groupNode = (DefaultTreeNode) node.getParent();
                    Group.StudentCouncil council = ((Group) groupNode.getData()).getCouncil();
                    if (student.equals(getCouncilMember(council))) {
                        removeCouncilMember(node, council);
                    } else {
                        changeCouncilMember(node, council);
                    }
                } else {
                    throw new IllegalStateException();
                }
            } else {
                throw new IllegalStateException();
            }
        }

        private void removeCouncilMember(DefaultTreeNode node, Group.StudentCouncil council) {
            setCouncilMember(council, null);

            DefaultTreeModel model = (DefaultTreeModel) groupTree.getModel();
            DefaultTreeNode groupNode = (DefaultTreeNode) node.getParent();
            // обновляем отображение конкретного студента в дереве - все
            // остальные точно не изменились
            int index = groupNode.getIndex(node);
            model.fireEvent(TreeDataEvent.CONTENTS_CHANGED, model.getPath(groupNode), index, index);
        }

        private void changeCouncilMember(DefaultTreeNode node, Group.StudentCouncil council) {
            setCouncilMember(council, (Student) node.getData());

            DefaultTreeNode groupNode = (DefaultTreeNode) node.getParent();
            DefaultTreeModel model = (DefaultTreeModel) groupTree.getModel();
            // Вот такое событие (и многие другие) в версии zkoss 6.5.2 вешают
            // UI, а браузер предложит убить зависшую вкладку - одинаково в
            // Chrome & Firefox
            model.fireEvent(TreeDataEvent.CONTENTS_CHANGED, model.getPath(groupNode), 0, groupNode.getChildCount() - 1);
        }

        protected abstract Student getCouncilMember(Group.StudentCouncil council);

        protected abstract void setCouncilMember(Group.StudentCouncil council, Student student);
    }

    private class FirstMemberToggleCouncilMemberListener extends ToggleCouncilMemberListener {

        @Override
        protected Student getCouncilMember(Group.StudentCouncil council) {
            return council.getMember1();
        }

        @Override
        protected void setCouncilMember(Group.StudentCouncil council, Student student) {
            council.setMember1(student);
        }
    }

    private class SecondMemberToggleCouncilMemberListener extends ToggleCouncilMemberListener {

        @Override
        protected Student getCouncilMember(Group.StudentCouncil council) {
            return council.getMember2();
        }

        @Override
        protected void setCouncilMember(Group.StudentCouncil council, Student student) {
            council.setMember2(student);
        }
    }
}
