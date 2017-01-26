package Model;

/**
 * Created by wesle on 26-1-2017.
 */
public class SimulatorModel {

    private View.CarParkView carParkView;
    private Controller.Car Car;
    private Controller.Location locationController;
    private Controller.Car[][][] cars;

    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;


    public boolean setCarAt(Controller.Location location, Controller.Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Controller.Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    public void updateView() {
        carParkView.updateView();
    }

    /**
     * Vraag op hoeveel verdiepingen de parkeergarage heeft.
     * @return numberOfFloors (int)
     */
    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * Vraag op hoeveel rijen de parkeergarage heeft.
     * @return numberOfRows (int)
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * Vraag op hoeveel plaatsen de parkeergarage heeft.
     * @return numberOfPlaces (int)
     */
    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    /**
     * Vraag op hoeveel beschikbare plaatsen de parkeergarage heeft.
     * @return numberOfOpenSpots (int)
     */
    public int getNumberOfOpenSpots(){
        return numberOfOpenSpots;
    }

    /**
     * Vraag een auto op een bepaalde plek op.
     * @return cars.
     */
    public Controller.Car getCarAt(Controller.Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[locationController.getFloor()][locationController.getRow()][locationController.getPlace()];
    }

    public Controller.Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Controller.Location location = new Controller.Location(floor, row, place);
                    Controller.Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    public Controller.Location getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Controller.Location location = new Controller.Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    private boolean locationIsValid(Controller.Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }

}
