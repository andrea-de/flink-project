package processes;

import domain.Reading;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

public class FlatMapReading implements FlatMapFunction<String, Reading> {

    @Override
    public void flatMap(String line, Collector<Reading> collector) {
        try {
            Reading reading = new Reading(line.replace(" ", "").split(","));
            collector.collect(reading);
        } catch (Exception ignored) {
        }
    }

}
