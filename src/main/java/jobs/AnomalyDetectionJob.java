package jobs;

import domain.Reading;
import domain.ReadingWithAnomalyScore;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import processes.AnomalyDetectionWindowedProcess;
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
                .name("readings");

        DataStream<ReadingWithAnomalyScore> readingPlusDataStream = readings
                .keyBy(Reading::getTime)
//                .process(new AnomalyDetectionProcess())
                .timeWindow(Time.seconds(30))
                .process(new AnomalyDetectionWindowedProcess())
                .name("detection");

        readingPlusDataStream
                .addSink(new ReadingSink())
//                .addSink(new processes.InfluxDBSink())
                .name("sink");

        env.execute("Anomaly Detection");

    }

}