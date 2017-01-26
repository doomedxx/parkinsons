package Controller;

import java.awt.*;

public abstract class Car {

    private Location location;              // Een auto heeft een locatie in de parkeergarage,
    private int minutesLeft;                // blijft een bepaalde tijd staan,
    private boolean isPaying;               // kan aan het betalen zijn
    private boolean hasToPay;               // of moet nog betalen.

    /**
     * Constructor for objects of class Car
     */
    public Car() {

    }
    /**
     * Deze method haalt de locatie op (accessor).
     * @return een object van de class Location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set de locatie (mutator).
     * @param location van de class Location.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Vraagt het aantal minuten dat de auto nog geparkeerd zal staan op (accessor).
     * @return een integer waarde als aantal minuten.
     */
    public int getMinutesLeft() {
        return minutesLeft;
    }

    /**
     * Veranderd het aantal minuten dat de auto nog geparkeerd zal staan (mutator).
     * @param minutesLeft, een integer waarde als aantal minuten.
     */
    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    /**
     * Vraag op of de gebruiker aan het betalen is (accessor).
     * @return isPaying yes/no.
     */
    public boolean getIsPaying() {
        return isPaying;
    }

    /**
     * Verander de isPaying waarde (mutator).
     * @param isPaying, boolean waarde om aan te geven dat de gebruiker aan het betalen is.
     */
    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    /**
     * Vraag op of de gebruiker nog moet betalen (accessor).
     * @return hasToPay yes/no.
     */
    public boolean getHasToPay() {
        return hasToPay;
    }

    /**
     * Verander de hasToPay waarde (mutator).
     * @param hasToPay, boolean waarden om aan te geven dat de gebruiker nog moet betalen.
     */
    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    /**
     * Timetick: decrement de minutesLeft (waarschijnlijk elke minuut)(mutator).
     */
    public void tick() {
        minutesLeft--;
    }
    
    public abstract Color getColor();
}