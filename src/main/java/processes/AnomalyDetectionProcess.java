package processes;

import domain.AggregateData;
import domain.Reading;
import domain.ReadingWithAnomalyScore;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

public class AnomalyDetectionProcess extends KeyedProcessFunction<Long, Reading, ReadingWithAnomalyScore> {

    private transient ValueState<AggregateData> aggregateReadingValueState;

    @Override
    public void open(Configuration parameters) {
        ValueStateDescriptor<AggregateData> aggregateReadingValueStateDescriptor = new ValueStateDescriptor<>(
                "aggregateData", AggregateData.class
        );
        aggregateReadingValueState = getRuntimeContext().getState(aggregateReadingValueStateDescriptor);
    }

    @Override
    public void processElement(Reading value, Context ctx, Collector<ReadingWithAnomalyScore> out) throws Exception {
        AggregateData aggregateData;
        if (aggregateReadingValueState.value() != null) {
            aggregateData = aggregateReadingValueState.value();
        } else {
            aggregateData = new AggregateData();
        }
        aggregateData.addReading(value);
        aggregateReadingValueState.update(aggregateData);
        out.collect(new ReadingWithAnomalyScore(value, aggregateReadingValueState.value()));
    }

}
