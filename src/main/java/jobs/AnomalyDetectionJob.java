package jobs;

import processes.WatermarkAssigner;
import domain.Reading;
import domain.ReadingWithAnomalyScore;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import processes.AnomalyDetectionSlidingWindowedProcess;
import processes.FlatMapReading;
import processes.ReadingSink;

import java.io.File;

public class AnomalyDetectionJob {
    public static void main(String[] args) throws Exception {


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);

        String resources = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
        DataStream<String> text = env.readTextFile(resources + "TestFile.csv");
//        DataStream<String> text = env.readTextFile(resources + "Small.csv");

        DataStream<Reading> readings = text
                .flatMap(new FlatMapReading())
                .assignTimestampsAndWatermarks(new WatermarkAssigner())
                .name("readings");

        DataStream<ReadingWithAnomalyScore> readingPlusDataStream = readings
                .windowAll(SlidingProcessingTimeWindows.of(Time.seconds(120), Time.seconds(1)))
                .process(new AnomalyDetectionSlidingWindowedProcess())
                .name("detection");

        readingPlusDataStream
                .addSink(new ReadingSink())
//                .addSink(new processes.InfluxDBSink())
                .name("sink");

        env.execute("Anomaly Detection");

    }

}