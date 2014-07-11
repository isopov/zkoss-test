package com.sopovs.moradanen.zkoss;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import com.sopovs.moradanen.zkoss.tablelisteners.model.Group;

public class DragAndDropTreeComposer extends GenericForwardComposer<Component> {
	private static final long serialVersionUID = 1L;
	@Wire
	private Tree dragAndDropTree;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		final TreeModel<TreeNode<String>> treeModel = getInitialTreeModel();

		dragAndDropTree.setModel(treeModel);
		dragAndDropTree.setItemRenderer(new TreeitemRenderer<TreeNode<String>>() {
			@Override
			public void render(Treeitem item, TreeNode<String> data, int index) {
				item.setLabel(data.getData());

				if (data instanceof LeafTreeNode) {
					final LeafTreeNode leafNode = (LeafTreeNode) data;

					item.setDraggable(leafNode.getParent().getData());
					item.setDroppable(leafNode.getParent().getData());

					item.addEventListener(Events.ON_DROP, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) {
							Treeitem draggedItem = (Treeitem) ((DropEvent) event).getDragged();
							Component parent = draggedItem.getParent();
							Treeitem droppedItem = (Treeitem) event.getTarget();
							List<String> leaves = ((GroupTreeNode) leafNode.getParent()).getLeaves();
							int targetIndex = leaves.indexOf(leafNode.getData());
							int dragIndexBeforeDrop = leaves.indexOf(draggedItem.getLabel());

							leaves.remove(dragIndexBeforeDrop);
							leaves.add(targetIndex, draggedItem.getLabel());

							if (targetIndex < dragIndexBeforeDrop) {
								// двигаем элемент снизу вверх
								parent.removeChild(draggedItem);
								parent.insertBefore(draggedItem, droppedItem);
							} else if (targetIndex == leaves.size() - 1) {
								// перемещаем элемент сверху вниз на последнее
								// место
								parent.removeChild(draggedItem);
								draggedItem.setParent(parent);
							} else {
								// перемещаем элемент сверху вниз не на
								// последнее место
								Component target = parent.getChildren().get(targetIndex + 1);
								parent.removeChild(draggedItem);
								parent.insertBefore(draggedItem, target);
							}

						}
					});
				}
			}
		});
	}

	private TreeModel<TreeNode<String>> getInitialTreeModel() {
		List<GroupTreeNode> folderNodes = new ArrayList<>();
		for (Group folder : Model.INSTANCE.getGroups()) {
			GroupTreeNode folderNode = new GroupTreeNode(folder.getName(), folder.getLeaves());
			for (String item : folder.getLeaves()) {
				folderNode.add(new LeafTreeNode(item));
			}
			folderNodes.add(folderNode);
		}

		return new DefaultTreeModel<String>(new DefaultTreeNode<String>(null, folderNodes));
	}

	private static class GroupTreeNode extends DefaultTreeNode<String> {
		private static final long serialVersionUID = 1L;
		private final List<String> leaves;

		public GroupTreeNode(String data, List<String> leaves) {
			super(data, Collections.<TreeNode<String>> emptyList());
			this.leaves = leaves;
		}

		public List<String> getLeaves() {
			return leaves;
		}

	}

	private static class LeafTreeNode extends DefaultTreeNode<String> {
		private static final long serialVersionUID = 1L;

		public LeafTreeNode(String data) {
			super(data);
		}

	}

	private static class Model {
		public static final Model INSTANCE = new Model();

		private final List<Group> groups;

		private Model() {
			this.groups = new ArrayList<>();
			int i = 1;
			for (String folder : asList("Folder 1", "Folder 2", "Folder 3")) {
				groups.add(new Group(folder, new ArrayList<>(asList("Node " + i++, "Node " + i++, "Node " + i++))));
			}
		}

		public List<Group> getGroups() {
			return groups;
		}

	}

	private static class Group {
		private final String name;
		private final List<String> leaves;

		public Group(String name, List<String> leaves) {
			this.name = name;
			this.leaves = leaves;
		}

		public String getName() {
			return name;
		}

		public List<String> getLeaves() {
			return leaves;
		}

	}

}
