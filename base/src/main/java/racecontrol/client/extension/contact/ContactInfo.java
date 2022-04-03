/*
 * Copyright (c) 2021 Leonard Sch�ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.client.extension.contact;

import java.util.ArrayList;
import racecontrol.client.protocol.SessionId;
import racecontrol.client.protocol.CarInfo;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import racecontrol.client.model.Car;

/**
 *
 * @author Leonard
 */
public class ContactInfo {

    private static final Logger LOG = Logger.getLogger(ContactInfo.class.getName());

    /**
     * time of the earliest accident event.
     */
    private final int sessionEarliestTime;
    /**
     * time of the latest accident event.
     */
    private final int sessionLatestTime;
    /**
     * The rough replay time for this incident.
     */
    private final int replayTime;
    /**
     * List of cars involved by carID.
     */
    private final List<Car> cars;
    /**
     * The session index when it occured.
     */
    private final SessionId sessionID;
    /**
     * List of cars that are yellow flagged.
     */
    private final List<Integer> yellowFlaggedCars;
    /**
     * Indicates that this is a contact generated by the game.
     */
    private final boolean gameContact;

    public ContactInfo(int time, int replayTime, Car car, SessionId sessionId) {
        this(time,
                time,
                Arrays.asList(car),
                sessionId,
                replayTime,
                new ArrayList<>(),
                true
        );
    }

    public ContactInfo(int time, int replayTime, SessionId sessionId) {
        this(time,
                time,
                new LinkedList<Car>(),
                sessionId,
                replayTime,
                new ArrayList<>(),
                true
        );
    }

    private ContactInfo(int earliestTime,
            int latestTime,
            List<Car> cars,
            SessionId sessionID,
            int replayTime,
            List<Integer> yellowFlaggedCars,
            boolean gameContact) {
        this.sessionEarliestTime = earliestTime;
        this.sessionLatestTime = latestTime;
        this.cars = cars;
        this.sessionID = sessionID;
        this.replayTime = replayTime;
        this.yellowFlaggedCars = yellowFlaggedCars;
        this.gameContact = gameContact;
    }

    public ContactInfo withCar(int sessionTime, Car car) {
        List<Car> c = new LinkedList<>();
        c.addAll(cars);
        c.add(car);
        return new ContactInfo(sessionEarliestTime,
                sessionTime,
                c,
                sessionID,
                replayTime,
                yellowFlaggedCars,
                gameContact);
    }

    public ContactInfo withReplayTime(int replayTime) {
        return new ContactInfo(sessionEarliestTime,
                sessionLatestTime,
                cars,
                sessionID,
                replayTime,
                yellowFlaggedCars,
                gameContact);
    }

    public ContactInfo withYellowFlaggedCars(List<Integer> yellowFlaggedCars) {
        return new ContactInfo(sessionEarliestTime,
                sessionLatestTime,
                cars,
                sessionID,
                replayTime,
                yellowFlaggedCars,
                gameContact);
    }

    public ContactInfo withIsGameContact(boolean gameContact) {
        return new ContactInfo(sessionEarliestTime,
                sessionLatestTime,
                cars,
                sessionID,
                replayTime,
                yellowFlaggedCars,
                gameContact);
    }

    public int getSessionEarliestTime() {
        return sessionEarliestTime;
    }

    public int getSessionLatestTime() {
        return sessionLatestTime;
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }

    public SessionId getSessionID() {
        return sessionID;
    }

    public int getReplayTime() {
        return replayTime;
    }

    public boolean isGameContact() {
        return gameContact;
    }

    public List<Integer> getYellowFlaggedCars() {
        return Collections.unmodifiableList(yellowFlaggedCars);
    }

}
