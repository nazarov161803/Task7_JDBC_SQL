package com.foxminded.task7.model;

import java.util.Objects;

public class Course implements Comparable<Course> {

    private int courseId;
    private String courseName;
    private String courseDescription;

    public Course(int courseId, String courseName, String course_description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = course_description;
    }

    public Course() {

    }



    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    @Override
    public String toString() {
        return "Course id = " + courseId + "; " +
                "Course name = " + courseName + "; " +
                "Course description = " + courseDescription + ";";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId == course.courseId &&
                Objects.equals(courseName, course.courseName) &&
                Objects.equals(courseDescription, course.courseDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, courseDescription);
    }

    @Override
    public int compareTo(Course o) {
        return this.courseId - o.courseId;
    }
}
