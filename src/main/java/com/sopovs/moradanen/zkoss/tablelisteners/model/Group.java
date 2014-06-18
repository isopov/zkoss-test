package com.sopovs.moradanen.zkoss.tablelisteners.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private StudentCouncil council;
    private List<Student> students;

    public Group(String groupName, List<String> studentNames, String councilMember1, String councilMember2) {
        this.name = groupName;
        if (studentNames != null) {
            students = new ArrayList<Student>(studentNames.size());
            for (String student : studentNames) {
                students.add(new Student(student));
            }
        }
        if (council == null) {
            council = new StudentCouncil();
        }
        if (councilMember1 != null && studentNames.contains(councilMember1)) {
            council.setMember1(students.get(studentNames.indexOf(councilMember1)));
        }
        if (councilMember2 != null && studentNames.contains(councilMember2)) {
            council.setMember2(students.get(studentNames.indexOf(councilMember2)));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public StudentCouncil getCouncil() {
        return council;
    }

    @Override
    public String toString() {
        return name;
    }

    public class StudentCouncil {
        private Student member1;
        private Student member2;

        public Student getMember1() {
            return member1;
        }

        public void setMember1(Student member1) {
            this.member1 = member1;
        }

        public Student getMember2() {
            return member2;
        }

        public void setMember2(Student member2) {
            this.member2 = member2;
        }
    }
}
