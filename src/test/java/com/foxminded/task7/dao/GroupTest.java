package com.foxminded.task7.dao;

import com.foxminded.task7.dao.impl.GroupDaoImpl;
import com.foxminded.task7.model.Group;
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


class GroupTest {

    private static final String DATA_BASE_PROP = "test.application.properties";
    private GroupDaoInterface groupDao;


    @BeforeEach
    void setUp() throws IOException {

        DataSource dataSource = ReaderProp.getProperty(DATA_BASE_PROP);
        groupDao = new GroupDaoImpl(dataSource);
        groupDao.deleteAllData();

    }


    @Test
    void shouldReturnIllegalArgumentExceptionWhenGiveNull() {
        assertThrows(IllegalArgumentException.class, () -> groupDao.addGroup(null));
    }

    @Test
    void shouldReturnEmptyListWhenTableIsEmpty() {
        List<Group> expected = new ArrayList<>();
        List<Group> actual = groupDao.getAllGroups();
        assertEquals(expected, actual);
    }

    @Test
    void shouldAddToDatabaseWhenGiveCorrectData() {
        List<Group> expected = Arrays.asList(
                new Group(1, "1group"),
                new Group(2, "2group"),
                new Group(3, "3group")
        );
        groupDao.addGroup(expected);
        List<Group> actual = groupDao.getAllGroups();
        assertThat(actual).usingElementComparatorIgnoringFields("groupId").isEqualTo(expected);
    }
}
