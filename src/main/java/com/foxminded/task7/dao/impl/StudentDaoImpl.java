package com.foxminded.task7.dao.impl;

import com.foxminded.task7.dao.StudentDaoInterface;
import com.foxminded.task7.model.Student;
import com.foxminded.task7.service.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDaoInterface {

    private final DataSource dataSource;

    public StudentDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void addStudent(List<Student> students) {
        if (students == null)
            throw new IllegalArgumentException("Null is not allowed");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)")) {
            for (Student student : students) {
                statement.setInt(1, student.getGroupId());
                statement.setString(2, student.getFirstName());
                statement.setString(3, student.getLastName());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addStudent(Student student) {
        if (student == null)
            throw new IllegalArgumentException("Null is not allowed");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)")) {

            statement.setInt(1, student.getGroupId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.executeUpdate();
            System.out.println("Student " + student.getFirstName() + " " + student.getLastName() + " add to group " + student.getGroupId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void deleteStudent(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM students where student_id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Student with id " + id + " removed from school");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Student> getStudentsFromCourse(int courseId) {
        List<Student> result = new ArrayList<>();
        if (courseId <= 0)
            throw new IllegalArgumentException("Invalid id");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT students.student_id, students.group_id, students.first_name, students.last_name " +
                     "  FROM students_courses " +
                     "       INNER JOIN students " +
                     "       ON students_courses.student_id = students.student_id " +

                     "       INNER  JOIN courses " +
                     "       ON students_courses.course_id = courses.course_id " +
                     " WHERE courses.course_id = ?")) {

            statement.setInt(1, courseId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    result.add(new Student(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4)));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result.size() < 1) {
            System.out.println("In this course 0 students");
        }
        return result;
    }

    @Override
    public void deleteFromCourse(int studentId, int courseId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE " +
                     "  FROM students_courses " +
                     " WHERE student_id = ? " +
                     "   AND course_id = ?")) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.executeUpdate();
            System.out.println("Student with id " + studentId + " deleted from course " + courseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertStudentToCourse(int studentId, int courseId) {

        if (studentId > 0 && courseId > 0) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO students_courses (student_id, course_id) " +
                         "     VALUES (?, ?)")) {
                statement.setInt(1, studentId);
                statement.setInt(2, courseId);
                statement.executeUpdate();
                System.out.println("Student with id " + studentId + " added to course " + courseId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error add student to course");

        }
    }


    @Override
    public List<Student> getAllStudents() {
        List<Student> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM students");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                result.add(new Student(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void deleteAllData() {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from STUDENTS;")) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

