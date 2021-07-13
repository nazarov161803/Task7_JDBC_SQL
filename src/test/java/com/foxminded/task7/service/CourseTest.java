package com.foxminded.task7.service;

import com.foxminded.task7.model.Course;
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
public class CourseTest {

    private static final String DATA_BASE_PROP = "test.application.properties";


    @Mock
    private CourseService courseService;

    @BeforeEach
    void setUp() throws IOException {
        DataSource dataSource = ReaderProp.getProperty(DATA_BASE_PROP);

        courseService = new CourseService(dataSource);
        courseService = org.mockito.Mockito.mock(CourseService.class);

    }


    @Test
    public void shouldThrowNullPointerException() {
        when(courseService.doGetAllCourses()).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class,
                () -> {
                    courseService.doGetAllCourses();
                });
    }

    @Test()
    public void shouldReturnListOfCourses() {

        when(courseService.doGetAllCourses()).thenReturn(Arrays.asList(
                new Course(1, "Read", "Reading"),
                new Course(2, "Write", "Writing"),
                new Course(3, "Sing", "Singing")));

        List<Course> actual = courseService.doGetAllCourses();
        List<Course> expected = Arrays.asList(
                new Course(1, "Read", "Reading"),
                new Course(2, "Write", "Writing"),
                new Course(3, "Sing", "Singing"));

        assertEquals(expected, actual);
    }

    @Test()
    public void shouldReturnEmptyList() {

        when(courseService.doGetAllCourses()).thenReturn(new ArrayList<>());

        List<Course> actual = courseService.doGetAllCourses();
        List<Course> expected = new ArrayList<>();

        assertEquals(expected, actual);
    }

    @Test()
    public void shouldInvokeDoAddCourseMethod() {
        doNothing().when(courseService).doAddCourse(new ArrayList<>());

        courseService.doAddCourse(new ArrayList<>());
        verify(courseService).doAddCourse(new ArrayList<>());


    }
}
