package com.foxminded.task7.service;

import com.foxminded.task7.dao.impl.CourseDaoImpl;
import com.foxminded.task7.dao.CourseDaoInterface;
import com.foxminded.task7.model.Course;
import com.foxminded.task7.service.sql.DataSource;

import java.util.List;

public class CourseService {

    private final CourseDaoInterface courseDao;

    public CourseService(DataSource dataSource) {
        courseDao = new CourseDaoImpl(dataSource);
    }

    public void doAddCourse(List<Course> courses) {
        courseDao.addCourse(courses);
    }

    public List<Course> doGetAllCourses() {
        return courseDao.getAllCourses();
    }

    public void doDeleteAllData () {
        courseDao.deleteAllData();
    }
}
