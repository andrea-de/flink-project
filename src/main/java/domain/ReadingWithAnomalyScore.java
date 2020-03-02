package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Data
public class ReadingWithAnomalyScore {

    private Long time;
    private double sensor1;
    private double score1;
    private double sensor2;
    private double sensor3;
    private double sensor4;
    private double sensor5;
    private double sensor6;
    private double sensor7;
    private double sensor8;
    private double sensor9;
    private double sensor10;

    public ReadingWithAnomalyScore(Reading reading, AggregateData aggregateData) {
        time = reading.getTime();
        sensor1 = reading.getSensor1();
        sensor2 = reading.getSensor2();
        sensor3 = reading.getSensor3();
        sensor4 = reading.getSensor4();
        sensor5 = reading.getSensor5();
        sensor6 = reading.getSensor6();
        sensor7 = reading.getSensor7();
        sensor8 = reading.getSensor8();
        sensor9 = reading.getSensor9();
        sensor10 = reading.getSensor10();
        if (aggregateData != null && aggregateData.getSensor1().size() > 1) {
            score1 = getScore(sensor1, aggregateData.getSensor1());
        }
    }

    double getScore(double value, List<Double> list) {
        Collections.sort(list);
        int n = list.size();
        double rH;
        double rL;
        if (n % 4 > 1) {
            rL = list.get(n / 4);
            rH = list.get(3 * n / 4);
        } else if (n % 4 == 1) {
            rL = (list.get((n / 4) - 1) + list.get(n / 4)) / 2;
            rH = (list.get(3 * n / 4) + list.get((3 * n / 4) + 1)) / 2;
        } else {
            rL = (list.get((n / 4) - 1) + list.get(n / 4)) / 2;
            rH = (list.get((3 * n / 4) - 1) + list.get(3 * n / 4)) / 2;
        }
        if (value < 1.5 * rL || value > 1.5 * rH) {
            return 1;
        } else if (value < 3 * rL || value > 3 * rH) {
            return 0.5;
        } else {
            return 0;
        }
    }

}