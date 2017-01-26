package Controller;

import java.util.LinkedList;
import java.util.Queue;

public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();      //nieuwe LinkedList variabele, zie java.util.LinkedList voor info.


    /**
     * Deze methode voegt een auto toe aan de CarQueue list (mutator).
     * @param car als Car.
     */
    public boolean addCar(Car car) {
        return queue.add(car);
    }

    /**
     * Deze methode verwijdert een auto uit de carQueue list (mutator).
     */
    public Car removeCar() {
        return queue.poll();
    }

    /**
     * Deze methode vraagt op hoeveel autos er in de CarQueue zijn (accessor).
     * @return integer value die de grootte van de lijst uitdrukt.
     */
    public int carsInQueue(){
    	return queue.size();
    }
}
