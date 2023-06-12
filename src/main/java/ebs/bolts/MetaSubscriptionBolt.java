package ebs.bolts;

import ebs.bolts.checker.MatchChecker;
import ebs.bolts.checker.MatchCheckerFactory;
import ebs.common.Operator;
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
import org.apache.storm.tuple.Values;

import java.util.*;

public class MetaSubscriptionBolt extends BaseRichBolt {

    private OutputCollector collector;
    private MatchCheckerFactory factory = MatchCheckerFactory.getInstance();
    private List<Subscription> subscriptionList = new ArrayList<>();
    private List<Publication> publicationList = new ArrayList<>();
    private volatile int count = 0;

    public void prepare(Map<String, Object> stormConf, TopologyContext context, OutputCollector collector) {

        this.collector = collector;
    }

    public void execute(Tuple input) {

        String type = input.getStringByField("type");
        if(type.compareTo("publication") == 0) {
            Publication publication = (Publication) input.getValueByField("data");

            handlePublication(publication);

            this.collector.emit(new Values("publication", publication));

            System.out.println("City bolt handled publication");
        }
        else {
            Pair<String, Subscription> subscriptionPair = (Pair<String, Subscription>) input.getValueByField("data");
            handleSubscription(subscriptionPair.second);

            this.collector.emit(new Values("subscription", subscriptionPair));

            System.out.println("City bolt handled subscription");
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("type", "data"));
    }

    public void handlePublication(Publication publication) {

        if(count < Utils.WINDOW_SIZE) {
            publicationList.add(publication);
            count ++;
        } else {
            double temperatureAVG = 0;
            double windAVG = 0;
            for (int i = 0; i < Utils.WINDOW_SIZE; i++) {
                temperatureAVG += publicationList.get(i).getTemperature();
                windAVG += publicationList.get(i).getWind();
            }
            temperatureAVG = temperatureAVG / Utils.WINDOW_SIZE;
            windAVG = windAVG / Utils.WINDOW_SIZE;

            for (int i = 0; i < subscriptionList.size(); i++) {
                boolean passedTemp = false;
                boolean isSimpleTemp = true;

                if(subscriptionList.get(i).getTempVal() != null && !subscriptionList.get(i).getTempVal().first.isSimple()) {
                    isSimpleTemp = false;
                    Operator operator = subscriptionList.get(i).getTempVal().first;
                    MatchChecker checker = factory.getMatcher(operator);
                    if (checker.checkOperation(temperatureAVG, subscriptionList.get(i).getTempVal().second)) {

                        passedTemp = true;
                    }
                }

                boolean passedWind = false;
                boolean isSimpleWind = true;

                if(subscriptionList.get(i).getWindVal() != null && !subscriptionList.get(i).getWindVal().first.isSimple()) {
                    isSimpleWind = false;
                    Operator operator = subscriptionList.get(i).getWindVal().first;
                    MatchChecker checker = factory.getMatcher(operator);
                    if (checker.checkOperation(windAVG, subscriptionList.get(i).getWindVal().second)) {

                        passedWind = true;
                    }
                }

                boolean isCityOkay = subscriptionList.get(i).getCityVal() == null;
                if(subscriptionList.get(i).getCityVal() != null) {
                    Operator operator = subscriptionList.get(i).getCityVal().first;
                    MatchChecker checker = factory.getMatcher(operator);
                    if (checker.checkOperation(publication.getCity(), subscriptionList.get(i).getCityVal().second)) {

                        isCityOkay = true;
                    }
                }

                boolean isStationOkay = subscriptionList.get(i).getStationIdVal() == null;
                if(subscriptionList.get(i).getStationIdVal() != null) {
                    Operator operator = subscriptionList.get(i).getStationIdVal().first;
                    MatchChecker checker = factory.getMatcher(operator);
                    if (checker.checkOperation(publication.getStationId(), subscriptionList.get(i).getStationIdVal().second)) {

                        isStationOkay = true;
                    }
                }

                boolean isDirectionOkay = subscriptionList.get(i).getDirectionVal() == null;
                if(subscriptionList.get(i).getDirectionVal() != null) {
                    Operator operator = subscriptionList.get(i).getDirectionVal().first;
                    MatchChecker checker = factory.getMatcher(operator);
                    if (checker.checkOperation(publication.getDirection(), subscriptionList.get(i).getDirectionVal().second)) {

                        isDirectionOkay = true;
                    }
                }

                if(isCityOkay && isDirectionOkay && isStationOkay && (!isSimpleTemp && !isSimpleWind && passedTemp && passedWind)||
                        (!isSimpleTemp && isSimpleWind && passedTemp) ||
                        (isSimpleTemp && !isSimpleWind && passedWind)) {

                    publication.setMetaPublication(true);
                    publication.addMatchingSubscription(subscriptionList.get(i).getUuid());
                }
            }
            publicationList = new ArrayList<>();
            count = 0;
        }
    }

    public void handleSubscription(Subscription subscription) {

        boolean isNotSimple = subscription.getWindVal() != null && !subscription.getWindVal().first.isSimple();
        isNotSimple = isNotSimple || (subscription.getTempVal() != null && !subscription.getTempVal().first.isSimple());

        if(isNotSimple) {
            subscriptionList.add(subscription);
        }
    }
}