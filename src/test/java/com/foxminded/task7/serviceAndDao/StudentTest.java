package com.foxminded.task7.serviceAndDao;

import com.foxminded.task7.model.Course;
import com.foxminded.task7.model.Student;
import com.foxminded.task7.service.CourseService;
import com.foxminded.task7.service.StudentService;
import com.foxminded.task7.service.reader.ReaderProp;
import com.foxminded.task7.service.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class StudentTest {


    private static final String DATA_BASE_PROP = "test.application.properties";
    private StudentService studentService;
    private CourseService courseService;


    @BeforeEach
    void setUp() throws IOException {

        DataSource dataSource = ReaderProp.getProperty(DATA_BASE_PROP);

        studentService = new StudentService(dataSource);
        courseService = new CourseService(dataSource);
        studentService.doDeleteAllData();

    }

    @Test
    void shouldReturnIllegalArgumentExceptionWhenGiveNull() {
        Student student = null;
        assertThrows(IllegalArgumentException.class, () -> studentService.doAddStudent(student));
    }

    @Test
    void shouldReturnEmptyListWhenTableIsEmpty() {
        List<Student> expected = new ArrayList<>();
        List<Student> actual = studentService.doGetAllStudents();
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
        studentService.doAddStudent(expected);
        List<Student> actual = studentService.doGetAllStudents();
        assertThat(actual).usingElementComparatorIgnoringFields("studentId").isEqualTo(expected);
    }

    @Test
    void shouldAddToDatabaseWhenGiveStudent() {
        Student student = new Student(1, 1, "Jon", "Snow");
        studentService.doAddStudent(student);

        List<Student> expected = new ArrayList<>();
        expected.add(student);
        List<Student> actual = studentService.doGetAllStudents();
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
        studentService.doAddStudent(expected);
        expected.remove(new Student(5, 1, "Daenerys", "Targaryen"));

        List<Student> listStudents = studentService.doGetAllStudents();
        Student student = listStudents.stream().filter(st -> "Daenerys".equals(st.getFirstName())).findAny().orElse(null);

        studentService.doDeleteStudent(student.getStudentId());
        List<Student> actual = studentService.doGetAllStudents();

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
        studentService.doAddStudent(expected);
        studentService.doDeleteStudent(1000000);
        List<Student> actual = studentService.doGetAllStudents();
        assertThat(actual).usingElementComparatorIgnoringFields("studentId").isEqualTo(expected);
    }

    @Test
    void shouldReturnStudentsFromCourse() {
        List<Course> courses = Arrays.asList(
                new Course(1, "Read", "Reading"));

        List<Student> expected = Arrays.asList(
                new Student(1, 1, "Jon", "Snow"),
                new Student(2, 2, "Rob", "Stark"),
                new Student(3, 3, "Tyrion", "Lannister"));

        courseService.doAddCourse(courses);
        studentService.doAddStudent(expected);
        List<Student> students = studentService.doGetAllStudents();
        int studentIdFirst = students.get(0).getStudentId();
        int studentIdSecond = students.get(1).getStudentId();
        int studentIdThird = students.get(2).getStudentId();
        List<Course> allCourses = courseService.doGetAllCourses();
        int courseId = allCourses.get(0).getCourseId();
        studentService.doInsertStudentToCourse(studentIdFirst, courseId);
        studentService.doInsertStudentToCourse(studentIdSecond, courseId);
        studentService.doInsertStudentToCourse(studentIdThird, courseId);

        List<Student> actual = studentService.doGetStudentsFromCourse(courseId);
        assertThat(actual).usingElementComparatorIgnoringFields("studentId").isEqualTo(expected);
    }

    @Test
    void shouldReturnEmptyListStudentsFromCourse() {
        List<Course> courses = Arrays.asList(
                new Course(1, "Read", "Reading"));

        List<Student> expected = new ArrayList<>();

        courseService.doAddCourse(courses);

        List<Student> actual = studentService.doGetStudentsFromCourse(1);
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

        courseService.doAddCourse(courses);
        studentService.doAddStudent(expected);
        List<Student> students = studentService.doGetAllStudents();
        int studentIdFirst = students.get(0).getStudentId();
        int studentIdSecond = students.get(1).getStudentId();
        int studentIdThird = students.get(2).getStudentId();
        List<Course> allCourses = courseService.doGetAllCourses();
        int courseId = allCourses.get(0).getCourseId();
        studentService.doInsertStudentToCourse(studentIdFirst, courseId);
        studentService.doInsertStudentToCourse(studentIdSecond, courseId);
        studentService.doInsertStudentToCourse(studentIdThird, courseId);

        studentService.doDeleteFromCourse(studentIdThird, courseId);
        expected.remove(new Student(3, 3, "Tyrion", "Lannister"));

        List<Student> actual = studentService.doGetStudentsFromCourse(courseId);
        assertThat(actual).usingElementComparatorIgnoringFields("studentId").isEqualTo(expected);
    }

    @Test
    void shouldNotDeleteStudentFromCurseIfGiveNotExistStudentId() {
        List<Course> courses = Arrays.asList(
                new Course(1, "Read", "Reading"));

        List<Student> expected = new ArrayList(Arrays.asList(
                new Student(1, 1, "Jon", "Snow")));

        courseService.doAddCourse(courses);
        List<Course> allCourses = courseService.doGetAllCourses();
        int courseId = allCourses.get(0).getCourseId();

        studentService.doAddStudent(expected);
        List<Student> studentList = studentService.doGetAllStudents();
        Student student = studentList.get(0);
        int studentId = student.getStudentId();
        studentService.doInsertStudentToCourse(studentId, courseId);

        studentService.doDeleteFromCourse(Integer.MAX_VALUE, 1);

        List<Student> actual = studentService.doGetStudentsFromCourse(courseId);
        assertThat(actual).usingElementComparatorIgnoringFields("studentId").isEqualTo(expected);
    }
}
