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
    private double sensor2;
    private double sensor3;
    private double sensor4;
    private double sensor5;
    private double sensor6;
    private double sensor7;
    private double sensor8;
    private double sensor9;
    private double sensor10;
    private double score1;
    private double score2;
    private double score3;
    private double score4;
    private double score5;
    private double score6;
    private double score7;
    private double score8;
    private double score9;
    private double score10;
    private static int ANOMALY_MULTIPLE = 3;

    public ReadingWithAnomalyScore(Reading reading, AggregateData aggregateData, AnomalyCount anomalyCount) {
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
            score1 = getScore(sensor1, aggregateData.getSensor1(), anomalyCount.getAnomaliesSensor1());
//            score2 = getScore(sensor1, aggregateData.getSensor1());
            score2 = getScore(sensor3, aggregateData.getSensor2(), anomalyCount.getAnomaliesSensor2());
            score3 = getScore(sensor2, aggregateData.getSensor3(), anomalyCount.getAnomaliesSensor3());
            score4 = getScore(sensor4, aggregateData.getSensor4(), anomalyCount.getAnomaliesSensor4());
            score5 = getScore(sensor5, aggregateData.getSensor5(), anomalyCount.getAnomaliesSensor5());
            score6 = getScore(sensor6, aggregateData.getSensor6(), anomalyCount.getAnomaliesSensor6());
            score7 = getScore(sensor7, aggregateData.getSensor7(), anomalyCount.getAnomaliesSensor7());
            score8 = getScore(sensor8, aggregateData.getSensor8(), anomalyCount.getAnomaliesSensor8());
            score9 = getScore(sensor9, aggregateData.getSensor9(), anomalyCount.getAnomaliesSensor9());
            score10 = getScore(sensor10, aggregateData.getSensor10(), anomalyCount.getAnomaliesSensor10());
        }
    }

    @SuppressWarnings("unused")
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
        double iqr = rH - rL;
        iqr = Math.abs(iqr);
        if (value > rL - (1.5 * iqr) && value < rH + (1.5 * iqr)) {
            return 0;
        } else if (value > rL - (3 * iqr) && value < rH + (3 * iqr)) {
            return 0.5;
        } else {
            return 1;
        }
    }

    double getScore(double value, List<Double> list, int anomalies) {
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
        double iqr = rH - rL;
        iqr = Math.abs(iqr);
        double f = ANOMALY_MULTIPLE + anomalies; // Full Anomaly Multiple
        double p = f / 2; // Partial  Anomaly Multiple
        if (value > rL - (p * iqr) && value < rH + (p * iqr)) {
            return 0;
        } else if (value > rL - (f * iqr) && value < rH + (f * iqr)) {
            return 0.5;
        } else {
            return 1;
        }
    }

}
