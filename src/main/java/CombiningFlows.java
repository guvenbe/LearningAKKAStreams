import akka.Done;
import akka.NotUsed;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

import java.util.List;
import java.util.concurrent.CompletionStage;


public class CombiningFlows {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create(Behaviors.empty(), "actorsystem");
        Source<String, NotUsed> sentencesSource = Source.from(List.of(
                "The sky is blue",
                "The moon is only at night",
                "Planets only orbit around the sun"
        ));
        Flow<String, Integer, NotUsed> howManyWordFlow = Flow.of(String.class).map(sentence -> sentence.split(" ").length);

        Source<Integer, NotUsed> howManyWordsSource = sentencesSource.via(howManyWordFlow);

        //or just chain them
        Source<Integer, NotUsed> sentencesSource2 = Source.from(List.of(
                "The sky is blue",
                "The moon is only at night",
                "Planets only orbit around the sun"
        )).map(sentence -> sentence.split(" ").length);

        Sink<Integer, CompletionStage<Done>> sink = Sink.ignore();
        Sink<String, NotUsed> combinedSink = howManyWordFlow.to(sink);


    }

}
