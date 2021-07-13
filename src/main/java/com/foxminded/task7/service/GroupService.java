package com.foxminded.task7.service;

import com.foxminded.task7.dao.impl.GroupDaoImpl;
import com.foxminded.task7.dao.GroupDaoInterface;
import com.foxminded.task7.model.Group;
import com.foxminded.task7.service.sql.DataSource;

import java.util.List;

public class GroupService {

    private final GroupDaoInterface groupDao;


    public GroupService(DataSource dataSource) {
        groupDao = new GroupDaoImpl(dataSource);
    }


    public void doAddGroup(List<Group> groups) {
        groupDao.addGroup(groups);
    }

    public List<Group> doGetGroupsByCount(int studentsCount) {
        return groupDao.getGroupsByCount(studentsCount);
    }

    public List<Group> doGetAllGroups() {
        return groupDao.getAllGroups();
    }

    public void doDeleteAllData () {
        groupDao.deleteAllData();
    }

}
