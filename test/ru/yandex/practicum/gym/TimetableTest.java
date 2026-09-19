package ru.yandex.practicum.gym;

import java.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForDay = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        int traingsCounter = 0;
        if (trainingSessionsForDay != null) {
            for (List<TrainingSession> trainingsOnTime : trainingSessionsForDay.values()) {
                traingsCounter += trainingsOnTime.size();
            }
        }
        Assertions.assertEquals(1, traingsCounter, "За понедельник вернулось не одно занятие");

        //Проверить, что за вторник не вернулось занятий
        trainingSessionsForDay = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        traingsCounter = 0;
        if (trainingSessionsForDay != null) {
            for (List<TrainingSession> trainingsOnTime : trainingSessionsForDay.values()) {
                traingsCounter += trainingsOnTime.size();
            }
        }
        Assertions.assertEquals(0, traingsCounter, "За вторник вернулось не 0 занятий");
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForDay = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        int traingsCounter = 0;
        if (trainingSessionsForDay != null) {
            for (List<TrainingSession> trainingsOnTime : trainingSessionsForDay.values()) {
                traingsCounter += trainingsOnTime.size();
            }
        }
        Assertions.assertEquals(1, traingsCounter, "За понедельник вернулось не одно занятие");

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        trainingSessionsForDay = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        traingsCounter = 0;
        if (trainingSessionsForDay != null) {
            for (TimeOfDay timeOfDay : trainingSessionsForDay.navigableKeySet()) {
                List<TrainingSession> listTrainingSession = trainingSessionsForDay.get(timeOfDay);
                for (TrainingSession trainingSession : listTrainingSession) {
                    traingsCounter++;
                    if (traingsCounter == 1) {
                        Assertions.assertEquals(new TimeOfDay(13, 00), timeOfDay, "Первая тренировка в четверг не в 13 часов");
                    } else if (traingsCounter == 2) {
                        Assertions.assertEquals(new TimeOfDay(20, 00), timeOfDay, "Вторая тренировка в четверг не в 20 часов");
                    }
                }
            }
        }
        Assertions.assertEquals(2, traingsCounter, "За четверг вернулось не 2 занятия");

        // Проверить, что за вторник не вернулось занятий
        trainingSessionsForDay = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        traingsCounter = 0;
        if (trainingSessionsForDay != null) {
            for (List<TrainingSession> trainingsOnTime : trainingSessionsForDay.values()) {
                traingsCounter += trainingsOnTime.size();
            }
        }
        Assertions.assertEquals(0, traingsCounter, "За вторник вернулось не 0 занятий");
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForDay = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        int traingsCounterMonday13 = 0;
        int traingsCounterMonday14 = 0;
        if (trainingSessionsForDay != null) {
            List<TrainingSession> listTrainingSession = trainingSessionsForDay.get(new TimeOfDay(13, 00));
            if (listTrainingSession != null) traingsCounterMonday13 = listTrainingSession.size();
            listTrainingSession = trainingSessionsForDay.get(new TimeOfDay(14, 00));
            if (listTrainingSession != null) traingsCounterMonday14 = listTrainingSession.size();
        }
        Assertions.assertEquals(1, traingsCounterMonday13, "За понедельник в 13:00 вернулось не 1 занятие");
        Assertions.assertEquals(0, traingsCounterMonday14, "За понедельник в 14:00 вернулось не 0 занятий");
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultiple() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Coach coach2 = new Coach("Тютюнников", "Павел", "Сергеевич");
        TrainingSession singleTrainingSession2 = new TrainingSession(group2, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(singleTrainingSession2);

        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForDay = timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY);
        int traingsCounter = 0;
        if (trainingSessionsForDay != null) {
            List<TrainingSession> listTrainingSession = trainingSessionsForDay.get(new TimeOfDay(12, 00));
            if (listTrainingSession != null) traingsCounter = listTrainingSession.size();
        }
        Assertions.assertEquals(2, traingsCounter, "За среду в 12:00 вернулось не 2 занятия");
    }

    @Test
    void testgetCountByCoachesForOne() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        TreeSet<CounterOfTrainings> countByCoaches = timetable.getCountByCoaches();
        CounterOfTrainings counterOfTrainings = countByCoaches.getFirst();
        Assertions.assertEquals(4, counterOfTrainings.getNumberOfTrainings(), "Неверное кол-во занятий у тренера");
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeNonExistentDay() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> result = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.TUESDAY, new TimeOfDay(13, 0));

        Assertions.assertNull(result, "Для несуществующего дня должен был вернуться null");
    }

    @Test
    void testGetTrainingSessionsForDayNonExistentDay() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        TreeMap<TimeOfDay, List<TrainingSession>> result = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        Assertions.assertNull(result, "Для несуществующего дня должен был вернуться null");
    }

    @Test
    void testgetCountByCoachesCountForTwo() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Новичков", "Михаил", "Станиславович");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        TreeSet<CounterOfTrainings> countByCoaches = timetable.getCountByCoaches();
        for (CounterOfTrainings counterOfTrainings : countByCoaches) {
            if (counterOfTrainings.getCoach().equals(coach1)) {
                Assertions.assertEquals(3, counterOfTrainings.getNumberOfTrainings(), "Неверное кол-во занятий у тренера 1");
            } else if (counterOfTrainings.getCoach().equals(coach2)) {
                Assertions.assertEquals(1, counterOfTrainings.getNumberOfTrainings(), "Неверное кол-во занятий у тренера 2");
            }
        }
    }

    @Test
    void testgetCountByCoachesSortOfThree() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Новичков", "Михаил", "Станиславович");
        Coach coach3 = new Coach("Аносов", "Станислав", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupAdult2 = new Group("Пауэрлифтинг для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession2 = new TrainingSession(groupAdult2, coach3,
                DayOfWeek.SUNDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession2);

        Group groupAdult3 = new Group("Аэробика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession3 = new TrainingSession(groupAdult3, coach3,
                DayOfWeek.SUNDAY, new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession3);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        TreeSet<CounterOfTrainings> countByCoaches = timetable.getCountByCoaches();
        int coachCounter = 0;
        for (CounterOfTrainings counterOfTrainings : countByCoaches) {
            coachCounter++;
            if (coachCounter == 1) {
                Assertions.assertEquals(coach1, counterOfTrainings.getCoach(), "1-й по порядку не Тренер1");
            } else if (coachCounter == 2) {
                Assertions.assertEquals(coach3, counterOfTrainings.getCoach(), "2-й по порядку не Тренер3");
            } else if (coachCounter == 3) {
                Assertions.assertEquals(coach2, counterOfTrainings.getCoach(), "3-й по порядку не Тренер2");
            }
        }
        Assertions.assertEquals(3, coachCounter, "Кол-во тренеров в сортированном списке не равно 3");
    }

    @Test
    void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();

        TreeSet<CounterOfTrainings> countByCoaches = timetable.getCountByCoaches();

        Assertions.assertNotNull(countByCoaches, "Должен был вернуться пустой TreeSet, а не null");
        Assertions.assertTrue(countByCoaches.isEmpty(), "Для пустого расписания должен был вернуться пустой TreeSet");
    }
}
