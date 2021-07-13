package com.foxminded.task7.dao;

import com.foxminded.task7.model.Student;

import java.util.List;

public interface StudentDaoInterface {

    void addStudent(List<Student>students);

    void addStudent(Student student);

    void deleteStudent(int id);

    List<Student> getStudentsFromCourse(int courseId);

    void deleteFromCourse(int studentId, int courseId);

    void insertStudentToCourse(int studentId, int courseId);

    List<Student> getAllStudents();

    void deleteAllData();
}
