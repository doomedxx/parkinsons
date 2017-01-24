import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Simulator {

    // Fields voor de class Simulator.
	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	
	// Verschillende CarQueues worden hier aangemaakt.
	private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;

    // Fields voor de tijd.
    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;
    protected Timer tickTimer;

    // Het aantal "arrivals".
    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour

    int maxTicks = 10000; // sets the maximum amount of ticks until the system stops working
    int tickAmount = 0; // sets the amount of ticks executed by the simulator

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute

    /**
     * Constructor voor de class simulator.
     * De constructor maakt een nieuwe CarQueue voor de ingang, de pasjes, het betalen en voor de uitgang.
     * Er word ook een nieuwe SimulatorView aangemaakt, met de opgegeven hoeveelheid verdiepingen, rijen en plaatsen.
     */
    public Simulator() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(3, 6, 30);


        ///// TIMER START
        tickTimer = new Timer(200, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tickTimer.setDelay(200); // Bepaald de snelheid per tick in miliseconde
                    if (tickAmount <= maxTicks) {
                            tick();
                            System.out.println("Tick!"); // Debugging reasons
                        } }
                    });
        }
        /////   TIMER END

    /**
     * Timetick; zodat de simulatie werkt (mutator).
     */
        private void tick() {
    	advanceTime();
    	handleExit();
    	updateViews();
    	handleEntrance();
    }

    /**
     * Zorgt ervoor dat de "klok" goed loopt (eg. niet meer dan 60 minuten of meer dan 24 uur, na 24 uur day++)(mutator).
     */
    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

    }

    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);  	
    }
    
    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    /**
     * Doet een tick en update daarna de view. Verandert dus de situatie en daarna de weergave.
     */
    private void updateViews(){
    	simulatorView.tick();
        // Update the car park view.
        simulatorView.updateView();	
    }
    /**
     * De autos die aankomen.
     */
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);    	
    }

    /**
     * Haalt autos aan het begin van de rij weg en wijst ze een parkeerplaats toe.
     */
    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
    	while (queue.carsInQueue()>0 && 
    			simulatorView.getNumberOfOpenSpots()>0 && 
    			i<enterSpeed) {
            Car car = queue.removeCar();        // deze regel haalt de auto uit de queue.
            Location freeLocation = simulatorView.getFirstFreeLocation();       //deze haalt de eerste vrije plek op.
            simulatorView.setCarAt(freeLocation, car);      //deze zet de auto daar daadwerkelijk neer.
            i++;                                //increment, want op naar de volgende auto.
        }
    }

    /**
     * Zet autos die uit willen rijden in de betaalrij.
     */
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = simulatorView.getFirstLeavingCar();       //pakt auto vooraan de rij van vertrekkende autos.
        while (car!=null) {
        	if (car.getHasToPay()){                 //als de auto nog moet betalen
	            car.setIsPaying(true);              //gaat de auto nu betalen (want isPaying is nu true)
	            paymentCarQueue.addCar(car);        //auto word toegevoegd aan de betaalrij.
        	}
        	else {                                  //als de auto niet meer hoeft te betalen
        		carLeavesSpot(car);                 //rij uit.
        	}
            car = simulatorView.getFirstLeavingCar();
        }
    }

    /**
     * Laat autos betalen.
     */
    private void carsPaying(){
        // Let cars pay.
    	int i=0;
    	//wanneer er autos in de betaalrij staan, en de paymentSpeed groter is dan i, gaat er een auto uit de betaalrij.
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
    	}
    }

    /**
     * Laat autos uitrijden.
     */
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	//wanneer er autos in de uitgang rij staan, en de existspeed groeter is dan i zal de auto uitrijden.
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
    	}	
    }

    /**
     * Haalt het aantal autos op.
     * @param weekDay, weekend
     * @return int waarde voor het aantal autos.
     */
    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);	
    }
    
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
    	switch(type) {
    	case AD_HOC: 
            for (int i = 0; i < numberOfCars; i++) {
            	entranceCarQueue.addCar(new AdHocCar());
            }
            break;
    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ParkingPassCar());
            }
            break;	            
    	}
    }

    /**
     * Auto verlaat zijn plek.
     */
    private void carLeavesSpot(Car car){
    	simulatorView.removeCarAt(car.getLocation());       //verwijdert de auto van zijn locatie in de simulatie.
        exitCarQueue.addCar(car);                           //plaatst de auto in de uitgang rij.
    }

}
