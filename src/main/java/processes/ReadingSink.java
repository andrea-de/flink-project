package processes;

import domain.ReadingWithAnomalyScore;
import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

@PublicEvolving
public class ReadingSink implements SinkFunction<ReadingWithAnomalyScore> {
    @Override
    public void invoke(ReadingWithAnomalyScore value, Context context) throws Exception {
        System.out.println("Sensor 1: " + value.getSensor1() + " | Score 1: " + value.getScore1());
    }
}