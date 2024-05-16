package se.craftsmanship.unit.thermometer;

public class Thermometer {

    private Sensor sensor;
    private Display display;

    public void update() {
        final String temperature = sensor.read() + "°C";
        display.output(temperature);
    }
}
