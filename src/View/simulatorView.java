package View;

import javax.swing.*;
import java.awt.*;

public class SimulatorView extends JFrame {
    private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private JButton pauseButton;
    private JButton resumeButton;

    private Controller.Location location;
    private Controller.Car car;
    private Controller.SimulatorController simulatorController;
    private Model.SimulatorModel simulatorModel;

    private Controller.Car[][][] cars;
    /**
     * Constructor van de class SimulatorView.
     *
     * @param numberOfFloors het aantal verdiepingen.
     * @param numberOfRows   het aantal rijen.
     * @param numberOfPlaces het aantal plekken.
     */
    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces;
        cars = new Controller.Car[numberOfFloors][numberOfRows][numberOfPlaces];

        carParkView = new CarParkView();
        Container contentPane = getContentPane();
        contentPane.add(carParkView, BorderLayout.CENTER);
        pack();
        setVisible(true);
        setSize(600, 800);
        setTitle("Parkin'Sons Parkeer Simulator v.00002");
        setLayout(null);
        initComponents();
        updateView();
    }

    /**
     * Deze method update de view.
     */
    public void updateView() {
        carParkView.updateView();
    }


    public boolean setCarAt(Controller.Location location, Controller.Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Controller.Car oldCar = simulatorModel.getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    /**
     * Verwijder een auto van een bepaalde locatie.
     */
    public Controller.Car removeCarAt(Controller.Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Controller.Car car = simulatorModel.getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }

    /**
     * Pakt de dichtstbijzijnde lege parkeerplek.
     */
    public Controller.Location getFirstFreeLocation() {
        for (int floor = 0; floor < simulatorModel.getNumberOfFloors(); floor++) {
            for (int row = 0; row < simulatorModel.getNumberOfRows(); row++) {
                for (int place = 0; place < simulatorModel.getNumberOfPlaces(); place++) {
                    Controller.Location location = new Controller.Location(floor, row, place);
                    if (simulatorModel.getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Pakt de auto vooraan de uitgang rij.
     */

    public void tick() {
        for (int floor = 0; floor < simulatorModel.getNumberOfFloors(); floor++) {
            for (int row = 0; row < simulatorModel.getNumberOfRows(); row++) {
                for (int place = 0; place < simulatorModel.getNumberOfPlaces(); place++) {
                    Controller.Location location = new Controller.Location(floor, row, place);
                    Controller.Car car = simulatorModel.getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    /**
     * Checkt of een locatie valid is (heeft de parkeergarage zoveel plekken?)
     */
    private boolean locationIsValid(Controller.Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }

    private void initComponents() {  // Vult de Frame met alle toeters en bellen
        this.setLayout(null);
        pauseButton = new JButton("Pause");
        add(pauseButton);
        pauseButton.setBounds(170, 70, 130, 10);

        resumeButton = new JButton("Resume");
        add(resumeButton);
        resumeButton.setBounds(70, 70, 130, 10);

        // PAUZE BUTTON
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simulatorController.stopTimer();
            }
        });

        // RESUME BUTTON
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simulatorController.startTimer();
            }
        });
    }
}