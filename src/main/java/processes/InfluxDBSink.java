package processes;

import domain.ReadingWithAnomalyScore;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;

public class InfluxDBSink extends RichSinkFunction<ReadingWithAnomalyScore> {

    private static String dataBaseName = "anomalyDB";
    private transient InfluxDB influxDB = null;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        influxDB = InfluxDBFactory.connect("http://localhost:8086", "admin", "admin");
        influxDB.deleteDatabase(dataBaseName);
        influxDB.createDatabase(dataBaseName);
        influxDB.enableBatch(2000, 100, TimeUnit.MILLISECONDS);
    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    @Override
    public void invoke(ReadingWithAnomalyScore readingWithAnomalyScore, Context context) throws Exception {
        Point.Builder builder = Point.measurement("domain.Reading")
                .time(readingWithAnomalyScore.getTime(),TimeUnit.SECONDS)
                .addField("Sensor_1", readingWithAnomalyScore.getSensor1())
                .addField("Score_1", readingWithAnomalyScore.getScore1())
                .addField("Sensor_2", readingWithAnomalyScore.getSensor2())
                .addField("Score_2", readingWithAnomalyScore.getScore2())
                .addField("Sensor_3", readingWithAnomalyScore.getSensor3())
                .addField("Score_3", readingWithAnomalyScore.getScore3())
                .addField("Sensor_4", readingWithAnomalyScore.getSensor4())
                .addField("Score_4", readingWithAnomalyScore.getScore4())
                .addField("Sensor_5", readingWithAnomalyScore.getSensor5())
                .addField("Score_5", readingWithAnomalyScore.getScore5())
                .addField("Sensor_6", readingWithAnomalyScore.getSensor6())
                .addField("Score_6", readingWithAnomalyScore.getScore6())
                .addField("Sensor_7", readingWithAnomalyScore.getSensor7())
                .addField("Score_7", readingWithAnomalyScore.getScore7())
                .addField("Sensor_8", readingWithAnomalyScore.getSensor8())
                .addField("Score_8", readingWithAnomalyScore.getScore8())
                .addField("Sensor_9", readingWithAnomalyScore.getSensor9())
                .addField("Score_9", readingWithAnomalyScore.getScore9())
                .addField("Sensor_10", readingWithAnomalyScore.getSensor10())
                .addField("Score_10", readingWithAnomalyScore.getScore10());

        Point p = builder.build();

        influxDB.write(dataBaseName, "autogen", p);

    }
}