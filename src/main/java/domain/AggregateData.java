package domain;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AggregateData implements Serializable {

    private Long time;
    private List<Double> sensor1 = new ArrayList<>();
    private List<Double> sensor2 = new ArrayList<>();
    private List<Double> sensor4 = new ArrayList<>();
    private List<Double> sensor3 = new ArrayList<>();
    private List<Double> sensor5 = new ArrayList<>();
    private List<Double> sensor6 = new ArrayList<>();
    private List<Double> sensor7 = new ArrayList<>();
    private List<Double> sensor8 = new ArrayList<>();
    private List<Double> sensor9 = new ArrayList<>();
    private List<Double> sensor10 = new ArrayList<>();

    private static void addSensorReading(double d, List<Double> list) {
        if (list.size() == 100) {
            list.remove(0);
        }
        list.add(d);
    }

    public void addReading(Reading reading) {
        time = reading.getTime();
        addSensorReading(reading.getSensor1(), sensor1);
    }

}