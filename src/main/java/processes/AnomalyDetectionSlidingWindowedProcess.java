package processes;

import domain.AggregateData;
import domain.AnomalyCount;
import domain.Reading;
import domain.ReadingWithAnomalyScore;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class AnomalyDetectionSlidingWindowedProcess extends ProcessAllWindowFunction<Reading, ReadingWithAnomalyScore, TimeWindow> {

    private AggregateData aggregateData = new AggregateData();
    private AnomalyCount anomalyCount = new AnomalyCount();

    @Override
    public void process(Context context, Iterable<Reading> elements, Collector<ReadingWithAnomalyScore> out) {
        aggregateData = new AggregateData();
        Reading lastReading = null;
        for (Reading r : elements){
            aggregateData.addReading(r);
            lastReading = r;
        }
        ReadingWithAnomalyScore readingWithAnomalyScore = new ReadingWithAnomalyScore(lastReading, aggregateData, anomalyCount);
        anomalyCount.trackAnomolies(readingWithAnomalyScore);
        out.collect(readingWithAnomalyScore);
    }
}
