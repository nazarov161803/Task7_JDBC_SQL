package com.foxminded.task7.serviceAndDao;

import com.foxminded.task7.model.Group;
import com.foxminded.task7.service.GroupService;
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


class GroupTest {

    private static final String DATA_BASE_PROP = "test.application.properties";
    private GroupService groupService;


    @BeforeEach
    void setUp() throws IOException {

        DataSource dataSource = ReaderProp.getProperty(DATA_BASE_PROP);
        groupService = new GroupService(dataSource);
        groupService.doDeleteAllData();

    }


    @Test
    void shouldReturnIllegalArgumentExceptionWhenGiveNull() {
        assertThrows(IllegalArgumentException.class, () -> groupService.doAddGroup(null));
    }

    @Test
    void shouldReturnEmptyListWhenTableIsEmpty() {
        List<Group> expected = new ArrayList<>();
        List<Group> actual = groupService.doGetAllGroups();
        assertEquals(expected, actual);
    }

    @Test
    void shouldAddToDatabaseWhenGiveCorrectData() {
        List<Group> expected = Arrays.asList(
                new Group(1, "1group"),
                new Group(2, "2group"),
                new Group(3, "3group")
        );
        groupService.doAddGroup(expected);
        List<Group> actual = groupService.doGetAllGroups();
        assertThat(actual).usingElementComparatorIgnoringFields("groupId").isEqualTo(expected);
    }
}
