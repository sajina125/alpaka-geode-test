package akka.stream.alpakka.geode.javadsl;

import akka.stream.alpakka.geode.GeodeSettings;

public class MyReactiveGeode  extends ReactiveGeodeWithPoolSubscription {
    public MyReactiveGeode(GeodeSettings settings) {
        super(settings);
    }
}
