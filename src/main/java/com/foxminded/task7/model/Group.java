package com.foxminded.task7.model;

import java.util.Objects;

public class Group implements Comparable<Group>{
    private int groupId;
    private String groupName;
    private int studentsCount;


    public Group(int group_id, String group_name) {
        this.groupId = group_id;
        this.groupName = group_name;
    }

    public Group(int group_id, String group_name, int studentsCount) {
        this.groupId = group_id;
        this.groupName = group_name;
        this.studentsCount = studentsCount;
    }

    public Group() {

    }

    public int getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(int studentsCount) {
        this.studentsCount = studentsCount;
    }

    public int getGroup_id() {
        return groupId;
    }

    public void setGroup_id(int group_id) {
        this.groupId = group_id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    @Override
    public String toString() {
        return "Group id = " + groupId + "; " +
                "Group Name = " + groupName + "; " +
                "Count of student = " + studentsCount + ";";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupId == group.groupId &&
                studentsCount == group.studentsCount &&
                Objects.equals(groupName, group.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName, studentsCount);
    }

    @Override
    public int compareTo(Group o) {
        return this.groupId - o.groupId;

    }
}
