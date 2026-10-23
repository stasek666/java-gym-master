package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private int numberOfTrainings;
    private Coach coach;

    public CounterOfTrainings(int numberOfTrainings, Coach coach) {
        this.numberOfTrainings = numberOfTrainings;
        this.coach = coach;
    }

    public int getNumberOfTrainings() {
        return numberOfTrainings;
    }

    public void setNumberOfTrainings(int numberOfTrainings) {
        this.numberOfTrainings = numberOfTrainings;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    @Override
    public int compareTo(CounterOfTrainings counterOfTrainings) {
        return counterOfTrainings.getNumberOfTrainings() - numberOfTrainings;
    }
}
