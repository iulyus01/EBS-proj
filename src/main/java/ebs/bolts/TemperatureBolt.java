package ebs.bolts;

import ebs.bolts.checker.MatchChecker;
import ebs.bolts.checker.MatchCheckerFactory;
import ebs.common.Operator;
import ebs.common.Pair;
import ebs.subscriptions.Subscription;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import ebs.publications.Publication;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TemperatureBolt extends BaseRichBolt {

    private OutputCollector collector;
    private MatchCheckerFactory factory;
    private Map<UUID, Pair<Operator, Integer>> subscriptionsMap = new HashMap<>();

    public void prepare(Map<String, Object> stormConf, TopologyContext context, OutputCollector collector) {

        this.collector = collector;
        factory = MatchCheckerFactory.getInstance();
    }

    @SuppressWarnings("unchecked")
    public void execute(Tuple input) {

        String type = input.getStringByField("type");
        if(type.compareTo("publication") == 0) {
            Publication publication = (Publication) input.getValueByField("data");

            handlePublication(publication);

            if (publication.existsMatchingSubscriptions()) {
                this.collector.emit(new Values("publication", publication));
            }

            System.out.println("Direction bolt handled publication");
        }
        else {
            Pair<String, Subscription> subscriptionPair = (Pair<String, Subscription>) input.getValueByField("data");
            handleSubscription(subscriptionPair.second);

            this.collector.emit(new Values("subscription", subscriptionPair));

            System.out.println("Direction bolt handled subscription");
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("type", "data"));
    }

    public void handlePublication(Publication publication) {

        for(UUID key: publication.getMatchingSubscriptions()) {

            Pair<Operator, Integer> subscription = subscriptionsMap.getOrDefault(key, null);
            if(subscription != null) {

                MatchChecker checker = factory.getMatcher(subscription.first);
                if(checker.checkOperation(publication.getDirection(), subscription.second)) {

                    publication.addMatchingSubscription(key);
                }
                else {
                    publication.removeMatchingSubscription(key);
                }
            } else {

                publication.addMatchingSubscription(key);
            }
        }
    }

    public void handleSubscription(Subscription subscription) {

        subscriptionsMap.put(subscription.getUuid(), subscription.getTempVal());
    }
}