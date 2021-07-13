package com.foxminded.task7.dao;

import com.foxminded.task7.model.Course;

import java.util.List;

public interface CourseDaoInterface {

    void addCourse (List <Course> courses);

    List<Course> getAllCourses ();

    void deleteAllData();
}
