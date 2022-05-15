import akka.Done;
import akka.NotUsed;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import java.util.concurrent.CompletionStage;

public class SimpleStream {
    public static void main(String[] args) {
        Source<Integer, NotUsed> range = Source.range(1, 10);
        Sink<String, CompletionStage<Done>> sink = Sink.foreach(value ->{
            System.out.println(value);
        });
    }
}
