package ebs.bolts;

import ebs.common.Pair;
import ebs.common.Utils;
import ebs.publications.Publication;
import ebs.subscriptions.Subscription;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TerminalBolt extends BaseRichBolt {

    private OutputCollector collector;
    private Map<UUID, String> mapSubscriptionsToClientId = new HashMap<>();
    private Socket socket;
    private ObjectOutputStream ous;

    public void prepare(Map<String, Object> stormConf, TopologyContext context, OutputCollector collector) {

        this.collector = collector;

        try {
            InetAddress host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostName(), Utils.TERMINAL_BOLT_PORT);
            ous = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void execute(Tuple input) {

        String type = input.getStringByField("type");
        if(type.compareTo("publication") == 0) {
            Publication publication = (Publication) input.getValueByField("data");

            handlePublication(publication);

            System.out.println("Terminal bolt handled publication");
        }
        else {
            Pair<String, Subscription> subscriptionPair = (Pair<String, Subscription>) input.getValueByField("data");
            handleSubscription(subscriptionPair);

            System.out.println("Terminal bolt handled subscription");
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("type", "data"));
    }

    public void handlePublication(Publication publication) {


            for (UUID key : mapSubscriptionsToClientId.keySet()) {

                boolean toSend = publication.getMatchingSubscriptionsStationId().contains(key) &&
                 publication.getMatchingSubscriptionsDirection().contains(key) &&
                 publication.getMatchingSubscriptionsWind().contains(key) &&
                 publication.getMatchingSubscriptionsCity().contains(key) &&
                publication.getMatchingSubscriptionsTemperature().contains(key);

                boolean toSendMeta = publication.isMetaPublication() && publication.getMatchingSubscriptions().contains(key);

                // send subscription to client
                try {

                    if(toSend || toSendMeta) {
                        System.out.println("[Terminal bolt] Is about to send " + (toSendMeta? "meta" : "") + " publication!");
                        ous.writeObject(new Pair<>(mapSubscriptionsToClientId.get(key), publication));
                        ous.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    public void handleSubscription(Pair<String, Subscription> subscriptionPair) {

        mapSubscriptionsToClientId.put(subscriptionPair.second.getUuid(), subscriptionPair.first);
    }
}