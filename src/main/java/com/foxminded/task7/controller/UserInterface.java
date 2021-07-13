package com.foxminded.task7.controller;

import com.foxminded.task7.model.Course;
import com.foxminded.task7.model.Group;
import com.foxminded.task7.model.Student;
import com.foxminded.task7.service.CourseService;
import com.foxminded.task7.service.GroupService;
import com.foxminded.task7.service.sql.DataSource;
import com.foxminded.task7.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class UserInterface {


    private final Scanner scanner;

    private final GroupService groupService;
    private final CourseService courseService;
    private final StudentService studentService;

    private final String MAIN_MENU = "This is main menu Task 7 - SQL!" + '\n' +
            "If you want \"Find all groups with less or equals student count\" press - 1." + '\n' +
            "If you want \"Find all students related to course with given name\" press - 2." + '\n' +
            "If you want \"Add new student\" press - 3." + '\n' +
            "If you want \"Delete student by STUDENT_ID\" press - 4." + '\n' +
            "If you want \"Add a student to the course (from a list)\" press - 5." + '\n' +
            "If you want \"Remove the student from one of his or her courses\" press - 6." + '\n' +
            "If you want exit press - 0";

    public UserInterface(DataSource dataSource) {
        groupService = new GroupService(dataSource);
        courseService = new CourseService(dataSource);
        studentService = new StudentService(dataSource);

        scanner = new Scanner(System.in);

    }

    public void runConsole() {
        boolean exit = false;
        while (!exit) {
            System.out.println(MAIN_MENU);
            String input = scanner.next();
            if (input.equals("1"))
                findGroups();
            if (input.equals("2"))
                findStudentsFromCourse();
            if (input.equals("3"))
                addNewStudent();
            if (input.equals("4"))
                deleteStudentById();
            if (input.equals("5"))
                addStudentToCourse();
            if (input.equals("6"))
                removeStudentFromCourse();

            else if (input.equals("0")) {
                System.out.println("Exit");
                exit = true;
            }
        }
        scanner.close();
    }

    private void findGroups() {
        System.out.println("Write count students");
        int studentsCount = scanner.nextInt();
        List<Group> result = groupService.doGetGroupsByCount(studentsCount);
        printList(result);
    }

    private void findStudentsFromCourse() {
        List<Course> courses = courseService.doGetAllCourses();
        printList(courses);

        System.out.println("Write course id");
        int courseId = scanner.nextInt();

        List<Student> students = studentService.doGetStudentsFromCourse(courseId);
        printList(students);
    }


    private void addNewStudent() {
        System.out.println("Write group from 1 to 10");
        int groupId = scanner.nextInt();

        System.out.println("Write first name");
        String firstName = scanner.next();

        System.out.println("Write last name");
        String lastName = scanner.next();

        Student student = new Student();
        student.setGroupId(groupId);
        student.setFirstName(firstName);
        student.setLastName(lastName);

        studentService.doAddStudent(student);

    }

    private void deleteStudentById() {
        System.out.println("Write id student to delete");
        int id = scanner.nextInt();
        studentService.doDeleteStudent(id);

    }


    private void addStudentToCourse() {
        System.out.println("Write student id");
        int studentId = scanner.nextInt();

        System.out.println("Write course id");
        int courseId = scanner.nextInt();

        studentService.doInsertStudentToCourse(studentId, courseId);
    }


    private void removeStudentFromCourse() {

        System.out.println("Write student id");
        int studentId = scanner.nextInt();

        System.out.println("Write course id");
        int courseId = scanner.nextInt();

        if (studentId > 0 && courseId > 0) {
            studentService.doDeleteFromCourse(studentId, courseId);
        } else {
            System.out.println("Error delete student from course");
        }
    }

    private void printList(List<?> list) {
        for (Object object : list) {
            System.out.println(object);
        }
    }
}
