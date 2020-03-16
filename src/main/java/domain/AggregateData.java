package domain;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
public class AggregateData implements Serializable {

    private Long time;
    private List<Double> sensor1 = new LinkedList<>();
    private List<Double> sensor2 = new LinkedList<>();
    private List<Double> sensor4 = new LinkedList<>();
    private List<Double> sensor3 = new LinkedList<>();
    private List<Double> sensor5 = new LinkedList<>();
    private List<Double> sensor6 = new LinkedList<>();
    private List<Double> sensor7 = new LinkedList<>();
    private List<Double> sensor8 = new LinkedList<>();
    private List<Double> sensor9 = new LinkedList<>();
    private List<Double> sensor10 = new LinkedList<>();

    private static void addSensorReading(double d, List<Double> list) {
        /*if (list.size() == 100) {
            list.remove(0);
        }*/
        list.add(d);
    }

    public void addReading(Reading reading) {
        time = reading.getTime();
        addSensorReading(reading.getSensor1(), sensor1);
        addSensorReading(reading.getSensor2(), sensor2);
        addSensorReading(reading.getSensor3(), sensor3);
        addSensorReading(reading.getSensor4(), sensor4);
        addSensorReading(reading.getSensor5(), sensor5);
        addSensorReading(reading.getSensor6(), sensor6);
        addSensorReading(reading.getSensor7(), sensor7);
        addSensorReading(reading.getSensor8(), sensor8);
        addSensorReading(reading.getSensor9(), sensor9);
        addSensorReading(reading.getSensor10(), sensor10);
    }

}