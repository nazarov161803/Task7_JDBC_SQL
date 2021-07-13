package com.foxminded.task7.data;

import com.foxminded.task7.model.Course;
import com.foxminded.task7.model.Group;
import com.foxminded.task7.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {


    public List<Group> generateGroupData() {
        List<Group> groups = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Group group = new Group();
            group.setGroup_id(i);
            group.setGroupName(getGroupName());
            groups.add(group);
        }
        return groups;
    }


    public List<Student> generateStudentData() {
        List<Student> students = new ArrayList<>();
        for (int i = 1; i < 200; i++) {
            Student student = new Student();
            student.setStudentId(i);
            student.setFirstName(getFirstName());
            student.setLastName(getLastName());
            student.setGroupId(getRandomNumber(10));
            students.add(student);
        }
        return students;
    }

    public List<Course> generateCourseData() {
        List<Course> courses = new ArrayList<>();
        Course courseArt = new Course(1, "Art",
                "Art is a creative activity that expresses imaginative or technical skill.");

        Course courseCitizenship = new Course(2, "Citizenship",
                "Citizenship is a legal relationship between a person and a country.");

        Course courseGeography = new Course(3, "Geography",
                "Geography is the study of earth and its people. Its features are things like continents, seas, rivers and mountains.");

        Course courseHistory = new Course(4, "History",
                "History is the study of past events. People know what happened in the past by looking at things from the past including sources.");

        Course courseMusic = new Course(5, "Music",
                "Music is a form of art that uses sound organised in time.");

        Course courseScience = new Course(6, "Music",
                "Science is what we do to find out about the natural world.");

        Course courseArithmetic = new Course(7, "Arithmetic",
                "In mathematics, arithmetic is the basic study of numbers.");

        Course courseReading = new Course(8, "Reading",
                "More fully, it a cognitive process of understanding information represented by printed or written language.");

        Course courseWriting = new Course(9, "Writing",
                "Writing is the act of recording language on a visual medium using a set of symbols.");

        Course courseMathematics = new Course(10, "Mathematics",
                "Mathematics is the study of numbers, shapes and patterns.");

        courses.add(courseArt);
        courses.add(courseCitizenship);
        courses.add(courseGeography);
        courses.add(courseHistory);
        courses.add(courseMusic);
        courses.add(courseScience);
        courses.add(courseArithmetic);
        courses.add(courseReading);
        courses.add(courseWriting);
        courses.add(courseMathematics);

        return courses;
    }



    private int getRandomNumber(int maxNumber) {
        return (int) (Math.random() * maxNumber + 1);
    }


    private String getGroupName() {
        Random r = new Random();
        char firstChar = (char) (r.nextInt(26) + 'a');
        char secondChar = (char) (r.nextInt(26) + 'a');
        int firstNum = getRandomNumber(9);
        int secondNum = getRandomNumber(9);
        return String.valueOf(firstChar) + secondChar + "-" + firstNum + secondNum;
    }


    private String getFirstName() {
        String[] firstNameArr = new String[20];
        firstNameArr[0] = "Rory";
        firstNameArr[1] = "Derek";
        firstNameArr[2] = "Dwayne";
        firstNameArr[3] = "Edwin";
        firstNameArr[4] = "Pete";
        firstNameArr[5] = "Rigoberto";
        firstNameArr[6] = "Alvin";
        firstNameArr[7] = "Everett";
        firstNameArr[8] = "Gilberto";
        firstNameArr[9] = "Horace";
        firstNameArr[10] = "Federico";
        firstNameArr[11] = "Eloy";
        firstNameArr[12] = "Johnnie";
        firstNameArr[13] = "Dorian";
        firstNameArr[14] = "Lane";
        firstNameArr[15] = "Marcus";
        firstNameArr[16] = "Emmanuel";
        firstNameArr[17] = "Gil";
        firstNameArr[18] = "Alberto";
        firstNameArr[19] = "Benjamin";
        return firstNameArr[new Random().nextInt(firstNameArr.length)];
    }

    private String getLastName() {
        String[] lastNameArr = new String[20];
        lastNameArr[0] = "Swanson";
        lastNameArr[1] = "Alexander";
        lastNameArr[2] = "Richard";
        lastNameArr[3] = "Benton";
        lastNameArr[4] = "Maddox";
        lastNameArr[5] = "Heath";
        lastNameArr[6] = "Beck";
        lastNameArr[7] = "Villanueva";
        lastNameArr[8] = "Lucas";
        lastNameArr[9] = "Dickerson";
        lastNameArr[10] = "Maldonado";
        lastNameArr[11] = "Adams";
        lastNameArr[12] = "Foley";
        lastNameArr[13] = "Small";
        lastNameArr[14] = "Vasquez";
        lastNameArr[15] = "Rosales";
        lastNameArr[16] = "Hart";
        lastNameArr[17] = "Carlson";
        lastNameArr[18] = "Moore";
        lastNameArr[19] = "Decker";
        return lastNameArr[new Random().nextInt(lastNameArr.length)];
    }
}
