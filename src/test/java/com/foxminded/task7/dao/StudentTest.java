package com.foxminded.task7.dao;

import com.foxminded.task7.dao.impl.CourseDaoImpl;
import com.foxminded.task7.dao.impl.StudentDaoImpl;
import com.foxminded.task7.model.Course;
import com.foxminded.task7.model.Student;
import com.foxminded.task7.service.sql.DataSource;
import com.foxminded.task7.service.reader.ReaderFiles;
import com.foxminded.task7.service.reader.ReaderProp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class StudentTest {

    private static final String CREATE_TABLES_SCRIPT = "test_initDB.sql";
    private static final String DATA_BASE_PROP = "test.application.properties";
    private StudentDaoInterface studentDao;
    private CourseDaoInterface courseDao;


    @BeforeEach
    void setUp() throws IOException {
        DataSource dataSource = ReaderProp.getProperty(DATA_BASE_PROP);
        studentDao = new StudentDaoImpl(dataSource);
        courseDao = new CourseDaoImpl(dataSource);
        studentDao.deleteAllData();

    }

    @Test
    void shouldReturnIllegalArgumentExceptionWhenGiveNull() {
        Student student = null;
        assertThrows(IllegalArgumentException.class, () -> studentDao.addStudent(student));
    }

    @Test
    void shouldReturnEmptyListWhenTableIsEmpty() {
        List<Student> expected = new ArrayList<>();
        List<Student> actual = studentDao.getAllStudents();
        assertEquals(expected, actual);
    }

    @Test
    void shouldAddToDatabaseWhenGiveListStudents() {
        List<Student> expected = Arrays.asList(
                new Student(1, 1, "Jon", "Snow"),
                new Student(2, 2, "Rob", "Stark"),
                new Student(3, 3, "Tyrion", "Lannister"),
                new Student(4, 1, "Aria", "Stark"),
                new Student(5, 1, "Daenerys", "Targaryen"));
        studentDao.addStudent(expected);
        List<Student> actual = studentDao.getAllStudents();
        assertThat(actual).usingElementComparatorIgnoringFields("studentId").isEqualTo(expected);
    }

    @Test
    void shouldAddToDatabaseWhenGiveStudent() {
        Student student = new Student(1, 1, "Jon", "Snow");
        studentDao.addStudent(student);

        List<Student> expected = new ArrayList<>();
        expected.add(student);
        List<Student> actual = studentDao.getAllStudents();
        assertThat(actual).usingElementComparatorIgnoringFields("studentId").isEqualTo(expected);
    }

    @Test
    void shouldDeleteStudentWhenGiveExistId() {
        List<Student> expected = new ArrayList(Arrays.asList(
                new Student(1, 1, "Jon", "Snow"),
                new Student(2, 2, "Rob", "Stark"),
                new Student(3, 3, "Tyrion", "Lannister"),
                new Student(4, 1, "Aria", "Stark"),
                new Student(5, 1, "Daenerys", "Targaryen")));
        studentDao.addStudent(expected);
        expected.remove(new Student(5, 1, "Daenerys", "Targaryen"));

        List<Student> listStudents = studentDao.getAllStudents();
        Student student = listStudents.stream().filter(st -> "Daenerys".equals(st.getFirstName())).findAny().orElse(null);

        studentDao.deleteStudent(student.getStudentId());
        List<Student> actual = studentDao.getAllStudents();

        assertThat(actual).usingElementComparatorIgnoringFields("studentId").isEqualTo(expected);
    }


    @Test
    void shouldNotDeleteStudentWhenGiveNotExistId() {
        List<Student> expected = Arrays.asList(
                new Student(1, 1, "Jon", "Snow"),
                new Student(2, 2, "Rob", "Stark"),
                new Student(3, 3, "Tyrion", "Lannister"),
                new Student(4, 1, "Aria", "Stark"),
                new Student(5, 1, "Daenerys", "Targaryen"));
        studentDao.addStudent(expected);
        studentDao.deleteStudent(1000000);
        List<Student> actual = studentDao.getAllStudents();
        assertThat(actual).usingElementComparatorIgnoringFields("studentId").isEqualTo(expected);
    }

    @Test
    void  shouldReturnStudentsFromCourse() {
        List<Course> courses = Arrays.asList(
                new Course(1, "Read", "Reading"));

        List<Student> expected = Arrays.asList(
                new Student(1, 1, "Jon", "Snow"),
                new Student(2, 2, "Rob", "Stark"),
                new Student(3, 3, "Tyrion", "Lannister"));

        courseDao.addCourse(courses);
        studentDao.addStudent(expected);
        List<Student>students = studentDao.getAllStudents();
        int studentIdFirst = students.get(0).getStudentId();
        int studentIdSecond = students.get(1).getStudentId();
        int studentIdThird = students.get(2).getStudentId();
        List<Course> allCourses = courseDao.getAllCourses();
        int courseId = allCourses.get(0).getCourseId();
        studentDao.insertStudentToCourse(studentIdFirst, courseId);
        studentDao.insertStudentToCourse(studentIdSecond, courseId);
        studentDao.insertStudentToCourse(studentIdThird, courseId);

        List<Student> actual = studentDao.getStudentsFromCourse(courseId);
        assertThat(actual).usingElementComparatorIgnoringFields("studentId").isEqualTo(expected);
    }

    @Test
    void shouldReturnEmptyListStudentsFromCourse() {
        List<Course> courses = Arrays.asList(
                new Course(1, "Read", "Reading"));

        List<Student> expected = new ArrayList<>();

        courseDao.addCourse(courses);

        List<Student> actual = studentDao.getStudentsFromCourse(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnListStudentsAfterDeleteStudentFromCourse() {
        List<Course> courses = Arrays.asList(
                new Course(1, "Read", "Reading"));

        List<Student> expected = new ArrayList(Arrays.asList(
                new Student(1, 1, "Jon", "Snow"),
                new Student(2, 2, "Rob", "Stark"),
                new Student(3, 3, "Tyrion", "Lannister")));

        courseDao.addCourse(courses);
        studentDao.addStudent(expected);
        List<Student>students = studentDao.getAllStudents();
        int studentIdFirst = students.get(0).getStudentId();
        int studentIdSecond = students.get(1).getStudentId();
        int studentIdThird = students.get(2).getStudentId();
        List<Course> allCourses = courseDao.getAllCourses();
        int courseId = allCourses.get(0).getCourseId();
        studentDao.insertStudentToCourse(studentIdFirst, courseId);
        studentDao.insertStudentToCourse(studentIdSecond, courseId);
        studentDao.insertStudentToCourse(studentIdThird, courseId);

        studentDao.deleteFromCourse(studentIdThird, courseId);
        expected.remove(new Student(3, 3, "Tyrion", "Lannister"));

        List<Student> actual = studentDao.getStudentsFromCourse(courseId);
        assertThat(actual).usingElementComparatorIgnoringFields("studentId").isEqualTo(expected);
    }

    @Test
    void shouldNotDeleteStudentFromCurseIfGiveNotExistStudentId() {
        List<Course> courses = Arrays.asList(
                new Course(1, "Read", "Reading"));

        List<Student> expected = new ArrayList(Arrays.asList(
                new Student(1, 1, "Jon", "Snow")));

        courseDao.addCourse(courses);
        List<Course> allCourses = courseDao.getAllCourses();
        int courseId = allCourses.get(0).getCourseId();

        studentDao.addStudent(expected);
        List<Student> studentList = studentDao.getAllStudents();
        Student student = studentList.get(0);
        int studentId = student.getStudentId();
        studentDao.insertStudentToCourse(studentId, courseId);

        studentDao.deleteFromCourse(Integer.MAX_VALUE, 1);

        List<Student> actual = studentDao.getStudentsFromCourse(courseId);
        assertThat(actual).usingElementComparatorIgnoringFields("studentId").isEqualTo(expected);
    }
}
