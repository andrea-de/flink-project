package processes;

import domain.AggregateData;
import domain.Reading;
import domain.ReadingWithAnomalyScore;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class AnomalyDetectionWindowedProcess extends ProcessWindowFunction<Reading, ReadingWithAnomalyScore, Long, TimeWindow> {

    private AggregateData aggregateData = new AggregateData();

    @Override
    public void process(Long time, Context context, Iterable<Reading> elements, Collector<ReadingWithAnomalyScore> out) throws Exception {
        elements.forEach(reading -> {
            aggregateData.addReading(reading);
            out.collect(new ReadingWithAnomalyScore(reading, aggregateData));
        });
    }
}
