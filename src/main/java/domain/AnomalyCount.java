package domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class AnomalyCount implements Serializable {

    int anomaliesSensor1 = 0;
    int anomaliesSensor2 = 0;
    int anomaliesSensor3 = 0;
    int anomaliesSensor4 = 0;
    int anomaliesSensor5 = 0;
    int anomaliesSensor6 = 0;
    int anomaliesSensor7 = 0;
    int anomaliesSensor8 = 0;
    int anomaliesSensor9 = 0;
    int anomaliesSensor10 = 0;

    public void trackAnomolies(ReadingWithAnomalyScore reading) {
        if (reading.getScore1() == 1) anomaliesSensor1 ++; else if (reading.getScore1() == 0) anomaliesSensor1 = 0;
        if (reading.getScore2() == 1) anomaliesSensor2 ++; else if (reading.getScore2() == 0) anomaliesSensor2 = 0;
        if (reading.getScore3() == 1) anomaliesSensor3 ++; else if (reading.getScore3() == 0) anomaliesSensor3 = 0;
        if (reading.getScore4() == 1) anomaliesSensor4 ++; else if (reading.getScore4() == 0) anomaliesSensor4 = 0;
        if (reading.getScore5() == 1) anomaliesSensor5 ++; else if (reading.getScore5() == 0) anomaliesSensor5 = 0;
        if (reading.getScore6() == 1) anomaliesSensor6 ++; else if (reading.getScore6() == 0) anomaliesSensor6 = 0;
        if (reading.getScore7() == 1) anomaliesSensor7 ++; else if (reading.getScore7() == 0) anomaliesSensor7 = 0;
        if (reading.getScore8() == 1) anomaliesSensor8 ++; else if (reading.getScore8() == 0) anomaliesSensor8 = 0;
        if (reading.getScore9() == 1) anomaliesSensor9 ++; else if (reading.getScore9() == 0) anomaliesSensor9 = 0;
        if (reading.getScore10() == 1) anomaliesSensor10 ++; else if (reading.getScore10() == 0) anomaliesSensor10 = 0;
    }

}
