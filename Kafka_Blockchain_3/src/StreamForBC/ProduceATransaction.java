package StreamForBC;

/** create first transaction to transaction-topic. 
 * can also create other data to other topics by modify the topic and data.
 * 
 * @author beier
 */

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProduceATransaction {
	public static final String TOPIC = "transaction-topic";
	public static final String TOPIC2 = "block-validator-topic";	
	public static final String TOPIC3 = "block-verifier-topic";	
	public static final String TOPIC4 = "source-validator-topic";
	public static final String TOPIC5 = "prehash-topic";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";
    private static Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }
    
    static void runProducer(final int sendMessageCount) throws InterruptedException {
        final Producer<String, String> producer = createProducer();
        long time = System.currentTimeMillis();
        final CountDownLatch countDownLatch = new CountDownLatch(sendMessageCount);

        try {
            for (long index = time; index < time + sendMessageCount; index++) {
                final ProducerRecord<String, String> record =
                        new ProducerRecord<>(TOPIC, "Record is: ", "I'm-record001");
                producer.send(record, (metadata, exception) -> {
                    if (metadata != null) {
                        System.out.println(record.key()+"  "+record.value()+"  "+ metadata.offset()+"writeto: "+ TOPIC);
                    } else {
                        exception.printStackTrace();
                    }
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await(25, TimeUnit.SECONDS);
        }finally {
            producer.flush();
            producer.close();
        }
    }
    
    public static void main(String... args) throws Exception {
        if (args.length == 0) {
            runProducer(1);
        } else {
            runProducer(Integer.parseInt(args[0]));
        }
    }
}
