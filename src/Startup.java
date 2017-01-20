/**
 * Created by wesle on 20-1-2017.
 */
public class Startup {
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        SimulatorView view = new SimulatorView(8, 2, 1);
        view.setVisible(true);
    }
}