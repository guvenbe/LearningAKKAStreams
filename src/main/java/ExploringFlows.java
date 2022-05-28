import akka.Done;
import akka.NotUsed;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import java.util.List;
import java.util.concurrent.CompletionStage;


public class ExploringFlows {
    public static void main(String[] args) {
        ActorSystem actorSystem =ActorSystem.create(Behaviors.empty(), "actorsystem");

        Source<Integer, NotUsed> number = Source.range(1,200);
        Flow<Integer, Integer, NotUsed> filterFlow = Flow.of(Integer.class).filter(value -> value %17==0);
        Flow<Integer, Integer, NotUsed> mapConcatFlow = Flow.of(Integer.class)
                .mapConcat(value -> {
                    List<Integer> result = List.of(value, value + 1, value +2);
                    return  result;
                });
        Flow<Integer, List<Integer>, NotUsed> groupFlow = Flow.of(Integer.class)
                .grouped(3);
        Sink<List<Integer>, CompletionStage<Done>> printsink =  Sink.foreach(System.out::println);

        number.via(filterFlow).via(mapConcatFlow).via(groupFlow).to(printsink).run(actorSystem);
    }
}
