package com.foxminded.task7.service;

import com.foxminded.task7.model.Group;
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
public class GroupTest {

    private static final String DATA_BASE_PROP = "test.application.properties";
    @Mock
    private GroupService groupService;


    @BeforeEach
    void setUp() throws IOException {

        DataSource dataSource = ReaderProp.getProperty(DATA_BASE_PROP);

        groupService = new GroupService(dataSource);
        groupService = org.mockito.Mockito.mock(GroupService.class);

    }

    @Test
    public void shouldReturnIllegalArgumentExceptionWhenGiveNull() {
        when(groupService.doGetAllGroups()).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class,
                () -> {
                    groupService.doGetAllGroups();
                });
    }

    @Test()
    public void shouldReturnListOfGroups() {

        when(groupService.doGetAllGroups()).thenReturn(Arrays.asList(
                new Group(1, "1group"),
                new Group(2, "2group"),
                new Group(3, "3group")));

        List<Group> actual = groupService.doGetAllGroups();
        List<Group> expected = Arrays.asList(
                new Group(1, "1group"),
                new Group(2, "2group"),
                new Group(3, "3group"));

        assertEquals(expected, actual);
    }

    @Test()
    public void shouldReturnEmptyList() {

        when(groupService.doGetAllGroups()).thenReturn(new ArrayList<>());

        List<Group> actual = groupService.doGetAllGroups();
        List<Group> expected = new ArrayList<>();

        assertEquals(expected, actual);

    }

    @Test()
    public void shouldInvokeDoAddGroupMethod() {
        doNothing().when(groupService).doAddGroup(new ArrayList<>());

        groupService.doAddGroup(new ArrayList<>());
        verify(groupService).doAddGroup(new ArrayList<>());

    }
}
