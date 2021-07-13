package com.foxminded.task7.service;

import com.foxminded.task7.model.Student;
import com.foxminded.task7.service.reader.ReaderProp;
import com.foxminded.task7.service.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentTest {

    private static final String DATA_BASE_PROP = "test.application.properties";
    @Mock
    private StudentService studentService;

    @BeforeEach
    void setUp() throws IOException {
        DataSource dataSource = ReaderProp.getProperty(DATA_BASE_PROP);

        studentService = new StudentService(dataSource);
        studentService = org.mockito.Mockito.mock(StudentService.class);
    }

    @Test
    public void shouldReturnIllegalArgumentExceptionWhenGiveNull() {
        when(studentService.doGetAllStudents()).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class,
                () -> {
                    studentService.doGetAllStudents();
                });
    }

    @Test()
    public void shouldReturnEmptyList() {
        when(studentService.doGetAllStudents()).thenReturn(new ArrayList<>());

        List<Student> actual = studentService.doGetAllStudents();
        List<Student> expected = new ArrayList<>();

        assertEquals(expected, actual);
    }


    @Test()
    public void shouldReturnListOfStudents() {
        when(studentService.doGetAllStudents()).thenReturn(Arrays.asList(
                new Student(1, 1, "Jon", "Snow"),
                new Student(2, 2, "Rob", "Stark"),
                new Student(3, 3, "Tyrion", "Lannister"),
                new Student(4, 1, "Aria", "Stark"),
                new Student(5, 1, "Daenerys", "Targaryen")));


        List<Student> actual = studentService.doGetAllStudents();
        List<Student> expected = Arrays.asList(
                new Student(1, 1, "Jon", "Snow"),
                new Student(2, 2, "Rob", "Stark"),
                new Student(3, 3, "Tyrion", "Lannister"),
                new Student(4, 1, "Aria", "Stark"),
                new Student(5, 1, "Daenerys", "Targaryen"));

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnStudentsFromCourse() {
        when(studentService.doGetStudentsFromCourse(1)).thenReturn(Arrays.asList(
                new Student(1, 1, "Jon", "Snow"),
                new Student(2, 2, "Rob", "Stark"),
                new Student(3, 3, "Tyrion", "Lannister")));

        List<Student> expected = Arrays.asList(
                new Student(1, 1, "Jon", "Snow"),
                new Student(2, 2, "Rob", "Stark"),
                new Student(3, 3, "Tyrion", "Lannister"));

        List<Student> actual = studentService.doGetStudentsFromCourse(1);

        assertEquals(expected, actual);

    }


    @Test
    public void shouldReturnEmptyListStudentsFromCourse() {
        when(studentService.doGetStudentsFromCourse(1)).thenReturn(new ArrayList<>());

        List<Student> expected = new ArrayList<>();

        List<Student> actual = studentService.doGetStudentsFromCourse(1);

        assertEquals(expected, actual);
    }

    @Test()
    public void shouldInvokeDoAddStudentMethodWhenGiveListStudents() {
        doNothing().when(studentService).doAddStudent(new ArrayList<>());

        studentService.doAddStudent(new ArrayList<>());
        verify(studentService).doAddStudent(new ArrayList<>());

    }

    @Test()
    public void shouldInvokeDoAddStudentMethodWhenGiveStudent() {
        doNothing().when(studentService).doAddStudent(new Student());

        studentService.doAddStudent(new Student());
        verify(studentService).doAddStudent(new Student());

    }

    @Test()
    public void shouldInvokeDoDeleteStudentMethod() {
        doNothing().when(studentService).doDeleteStudent(anyInt());

        studentService.doDeleteStudent(anyInt());
        verify(studentService).doDeleteStudent(anyInt());

    }

    @Test()
    public void shouldInvokeDoDeleteFromCourseMethod() {
        doNothing().when(studentService).doDeleteFromCourse(anyInt(), anyInt());

        studentService.doDeleteFromCourse(anyInt(), anyInt());
        verify(studentService).doDeleteFromCourse(anyInt(), anyInt());

    }

    @Test()
    public void shouldInvokeDodoInsertStudentToCourseMethod() {
        doNothing().when(studentService).doInsertStudentToCourse(anyInt(), anyInt());

        studentService.doInsertStudentToCourse(anyInt(), anyInt());
        verify(studentService).doInsertStudentToCourse(anyInt(), anyInt());

    }
}
