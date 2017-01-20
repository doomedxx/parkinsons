
import java.util.Random;
import java.awt.*;

public class ParkingPassCar extends Car {
	private static final Color COLOR=Color.blue;

    /**
     * Constructor voor class ParkingPassCar.
     */
    public ParkingPassCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }

    /**
     * Vraag op welke kleur het object heeft.
     */
    public Color getColor(){
    	return COLOR;
    }
}
