package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private TreeMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new TreeMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) { //сохраняем занятие в расписании
        DayOfWeek trainingDay = trainingSession.getDayOfWeek();
        TimeOfDay trainingTime = trainingSession.getTimeOfDay();
        TreeMap<TimeOfDay, List<TrainingSession>> dayTimetable = timetable.get(trainingDay);
        if (dayTimetable==null) {
            dayTimetable = new TreeMap<>();
            timetable.put(trainingDay, dayTimetable);
        }
        List<TrainingSession> allTrainingsInTime = dayTimetable.get(trainingTime);
        if (allTrainingsInTime == null) {
            allTrainingsInTime = new ArrayList<>();
            dayTimetable.put(trainingTime, allTrainingsInTime);
        }
        allTrainingsInTime.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> trainingsInDay = timetable.get(dayOfWeek);
        if (trainingsInDay == null) return null;
        return trainingsInDay.get(timeOfDay);
    }

    public TreeSet<CounterOfTrainings> getCountByCoaches() {
        HashMap<Coach, Integer> countedCoaches = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> dayTrainings : timetable.values()) {
            for (List<TrainingSession> timeTrainings : dayTrainings.values()) {
                for (TrainingSession trainingSession : timeTrainings) {
                    Coach currentCoach = trainingSession.getCoach();
                    countedCoaches.put(currentCoach, countedCoaches.getOrDefault(currentCoach, 0) + 1);
                }
            }
        }
        TreeSet<CounterOfTrainings> counterOfTrainings = new TreeSet<>();
        for (Coach coach : countedCoaches.keySet()) {
            counterOfTrainings.add(new CounterOfTrainings(countedCoaches.get(coach), coach));
        }
        return counterOfTrainings;
    }
}
