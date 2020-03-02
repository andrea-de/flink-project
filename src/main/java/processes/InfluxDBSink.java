package processes;

import domain.ReadingWithAnomalyScore;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;

public class InfluxDBSink extends RichSinkFunction<ReadingWithAnomalyScore> {

    private static String dataBaseName = "mydb";
    private transient InfluxDB influxDB = null;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        influxDB = InfluxDBFactory.connect("http://localhost:8086", "admin", "admin");
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
//                .time(readingWithAnomalyScore.getTime())
//                .addField("Time", readingWithAnomalyScore.getTime())
                .addField("Sensor_1", readingWithAnomalyScore.getSensor1());

        Point p = builder.build();

        influxDB.write(dataBaseName, "autogen", p);

    }
}