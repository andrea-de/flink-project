package processes;

import domain.AggregateData;
import domain.Reading;
import domain.ReadingWithAnomalyScore;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class AnomalyDetectionSlidingWindowedProcess extends ProcessAllWindowFunction<Reading, ReadingWithAnomalyScore, TimeWindow> {

    private AggregateData aggregateData = new AggregateData();

    @Override
    public void process(Context context, Iterable<Reading> elements, Collector<ReadingWithAnomalyScore> out) {
        elements.forEach(reading -> {
            aggregateData.addReading(reading);
            out.collect(new ReadingWithAnomalyScore(reading, aggregateData));
        });
    }
}
