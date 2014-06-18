package com.sopovs.moradanen.zkoss.tablelisteners.model;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelService {

    private final List<Group> groups = createModel();

    public List<Group> getAllGroups() {
        return Collections.unmodifiableList(groups);
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public void addStudentToGroup(Group group, Student student) {
        group.getStudents().add(student);
    }

    public void removeStudentFromGroup(Group group, Student student) {
        group.getStudents().remove(student);
    }

    private static final ModelService INSTANCE = new ModelService();

    public static ModelService getInstance() {
        return INSTANCE;
    }

    private static List<Group> createModel() {
        List<Group> grp = new ArrayList<Group>();
        grp.add(new Group("Математика", asList("Иванов", "Петров", "Сидоров"), "Сидоров", null));
        grp.add(new Group("Информатика", asList("Петров", "Петрова", "Абырвалг"), "Петров", "Петрова"));
        grp.add(new Group("Философия", asList("Степанов", "Еромолаев", "Антонов"), "Еромолаев", "Еромолаев"));
        return grp;
    }

    private ModelService() {
        // no code
    }
}
