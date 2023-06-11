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

import java.util.Map;
import java.util.UUID;

public class CityBolt extends BaseRichBolt {

    private OutputCollector collector;
    private MatchCheckerFactory factory;
    private Map<UUID, Pair<Operator, String>> subscriptionsMap;

    public void prepare(Map<String, Object> stormConf, TopologyContext context, OutputCollector collector) {

        this.collector = collector;
    }

    public void execute(Tuple input) {

        try {
            Publication publication = (Publication) input.getValueByField("publication");
            handlePublication(publication);

            if (publication.existsMatchingSubscriptions()) {
                this.collector.emit(new Values("publication", publication));
            }
        } catch (Exception exception) {
            Subscription subscription = (Subscription) input.getValueByField("subscription");
            handleSubscription(subscription);

            this.collector.emit(new Values("subscription", subscription));
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("type", "data"));
    }

    public void handlePublication(Publication publication) {

        for(UUID key: subscriptionsMap.keySet()) {

            Pair<Operator, String> subscription = subscriptionsMap.get(key);
            MatchChecker checker = factory.getMatcher(subscription.first);
            if(checker.checkOperation(publication.getCity(), subscription.second)) {

                publication.addMatchingSubscription(key);
            }
        }
    }

    public void handleSubscription(Subscription subscription) {

        subscriptionsMap.put(subscription.getUuid(), subscription.getCityVal());
    }
}