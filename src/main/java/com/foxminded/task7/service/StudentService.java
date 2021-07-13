package com.foxminded.task7.service;

import com.foxminded.task7.dao.StudentDaoInterface;
import com.foxminded.task7.dao.impl.StudentDaoImpl;
import com.foxminded.task7.model.Student;
import com.foxminded.task7.service.sql.DataSource;

import java.util.List;

public class StudentService {

    private final StudentDaoInterface studentDao;


    public StudentService(DataSource dataSource) {
        studentDao = new StudentDaoImpl(dataSource);
    }


    public void doAddStudent(List<Student> students) {
        studentDao.addStudent(students);

    }

    public List<Student> doGetAllStudents() {
        return studentDao.getAllStudents();
    }


    public void doAddStudent(Student student) {
        studentDao.addStudent(student);
    }


    public void doDeleteStudent(int id) {
        studentDao.deleteStudent(id);
    }


    public List<Student> doGetStudentsFromCourse(int courseId) {
        return studentDao.getStudentsFromCourse(courseId);
    }


    public void doDeleteFromCourse(int studentId, int courseId) {
        studentDao.deleteFromCourse(studentId, courseId);
    }


    public void doInsertStudentToCourse(int studentId, int courseId) {
        studentDao.insertStudentToCourse(studentId, courseId);
    }

    public void doDeleteAllData () {
        studentDao.deleteAllData();
    }
}
