package domain;

import lombok.Data;

import java.time.Instant;

@Data
public class Reading {

    private final Long time;
    private final double sensor1;
    private final double sensor2;
    private final double sensor3;
    private final double sensor4;
    private final double sensor5;
    private final double sensor6;
    private final double sensor7;
    private final double sensor8;
    private final double sensor9;
    private final double sensor10;

    public Reading(String[] strings) {
        time = Instant.parse(strings[0]).getEpochSecond();
        sensor1 = Double.parseDouble(strings[1]);
        sensor2 = Double.parseDouble(strings[2]);
        sensor3 = Double.parseDouble(strings[3]);
        sensor4 = Double.parseDouble(strings[4]);
        sensor5 = Double.parseDouble(strings[5]);
        sensor6 = Double.parseDouble(strings[6]);
        sensor7 = Double.parseDouble(strings[7]);
        sensor8 = Double.parseDouble(strings[8]);
        sensor9 = Double.parseDouble(strings[9]);
        sensor10 = Double.parseDouble(strings[10]);
    }
}


