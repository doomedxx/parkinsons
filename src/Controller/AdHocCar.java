package Controller;

import java.awt.*;
import java.util.Random;

/**
 * Uit het woordenboek: created or done for a particular purpose as necessary.
 */
public class AdHocCar extends Car {
private static final Color COLOR=Color.red;
/**
 * Constructor voor objecten van de class AdHocCar.
 */
public AdHocCar() {
    Random random = new Random();
    int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
    this.setMinutesLeft(stayMinutes);
    this.setHasToPay(true);
}
/**
 * Vraag op welke kleur het object heeft.
 */
public Color getColor(){
    return COLOR;
}
}
