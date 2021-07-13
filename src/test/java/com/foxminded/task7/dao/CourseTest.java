package com.foxminded.task7.dao;

import com.foxminded.task7.dao.impl.CourseDaoImpl;
import com.foxminded.task7.model.Course;
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




class CourseTest {

    private static final String DATA_BASE_PROP = "test.application.properties";
    private CourseDaoInterface courseDao;


    @BeforeEach
    public void setUp() throws IOException {

        DataSource dataSource = ReaderProp.getProperty(DATA_BASE_PROP);
        courseDao = new CourseDaoImpl(dataSource);
        courseDao.deleteAllData();
    }


    @Test
    public void shouldReturnIllegalArgumentExceptionWhenGiveNull() {
        assertThrows(IllegalArgumentException.class, () -> courseDao.addCourse(null));
    }

    @Test
    public void shouldReturnEmptyListWhenTableIsEmpty() {
        List<Course> expected = new ArrayList<>();
        List<Course> actual = courseDao.getAllCourses();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldAddToDatabaseWhenGiveCorrectData() {
        List<Course> expected = Arrays.asList(
                new Course(1, "Read", "Reading"),
                new Course(2, "Write", "Writing"),
                new Course(3, "Sing", "Singing"));
        courseDao.addCourse(expected);

        List<Course> actual = courseDao.getAllCourses();

        assertThat(actual).usingElementComparatorIgnoringFields("courseId").isEqualTo(expected);
    }
}