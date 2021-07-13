package com.foxminded.task7.dao.impl;

import com.foxminded.task7.dao.CourseDaoInterface;
import com.foxminded.task7.model.Course;
import com.foxminded.task7.service.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDaoInterface {

    private final DataSource dataSource;

    public CourseDaoImpl(DataSource config) {
        this.dataSource = config;
    }




    @Override
    public void addCourse(List<Course> courses) {
        if (courses == null)
            throw new IllegalArgumentException("Null is not allowed");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO COURSES (course_name, course_description) VALUES (?,?)")) {
            for (Course course : courses) {
                statement.setString(1, course.getCourseName());
                statement.setString(2, course.getCourseDescription());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Course> getAllCourses() {
        List<Course> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT course_id, course_name, course_description FROM COURSES");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                result.add(new Course(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void deleteAllData() {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from COURSES;")) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
