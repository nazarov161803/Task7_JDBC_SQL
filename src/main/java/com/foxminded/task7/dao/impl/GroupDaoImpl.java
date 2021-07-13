package com.foxminded.task7.dao.impl;

import com.foxminded.task7.dao.GroupDaoInterface;
import com.foxminded.task7.model.Group;
import com.foxminded.task7.service.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupDaoImpl implements GroupDaoInterface {

    private final DataSource dataSource;


    public GroupDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void addGroup(List<Group> groups) {
        if (groups == null)
            throw new IllegalArgumentException("Null is not allowed");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO groups (group_name) VALUES (?)")) {
            for (Group group : groups) {
                statement.setString(1, group.getGroupName());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Group> getAllGroups() {
        List<Group> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT group_id, group_name FROM groups");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                result.add(new Group(
                        resultSet.getInt(1),
                        resultSet.getString(2)));
            }
            Collections.sort(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Group> getGroupsByCount(int studentsCount) {
        List<Group> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT groups.group_id, groups.group_name, COUNT(students.student_id) " +
                             "  FROM groups " +
                             "       LEFT JOIN students " +
                             "       ON students.group_id = groups.group_id " +
                             " GROUP BY groups.group_id " +
                             "HAVING COUNT(*) <= ?")) {

            statement.setInt(1, studentsCount);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Group group = new Group(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3));
                    result.add(group);
                }
                if (result.size() < 1) {
                    System.out.println("Not exist groups with less or equals student count " + studentsCount);
                }
                Collections.sort(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void deleteAllData() {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from GROUPS;")) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

