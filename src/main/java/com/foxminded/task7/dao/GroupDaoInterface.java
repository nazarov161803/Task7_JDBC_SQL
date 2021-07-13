package com.foxminded.task7.dao;

import com.foxminded.task7.model.Course;
import com.foxminded.task7.model.Group;

import java.util.List;

public interface GroupDaoInterface {
    void addGroup (List<Group> groups);

    List<Group> getAllGroups ();

    List<Group> getGroupsByCount(int studentsCount);

    void deleteAllData();
}
