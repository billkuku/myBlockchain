package StreamForBC;

/** this class is used for verifying block from distributed-block-topic by verifier-nodes.
 * this is a stream-api. related methode see "VerifyBlock" class.
 * @author beier
 */

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import myBlockchain.VerifyBlock;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.ValueMapper;

import Utilities.CreateRandomData;
import Utilities.SetGet;

public class AppVerify {
    public static void main(String[] args) throws Exception {
    	Properties props = new Properties();
		CreateRandomData cverifid = new CreateRandomData();
    	String verifierId = "verifier-" + cverifid.getRandomString(5);
    	System.out.println(verifierId + "started to verify the block!");
    	props.put(StreamsConfig.APPLICATION_ID_CONFIG, verifierId);
    	props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    	props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    	props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    	
    	final StreamsBuilder builder = new StreamsBuilder();
 		SetGet sgPrehash = new SetGet();
 		SetGet sgBlock = new SetGet();

    	KStream<String, String> block = builder.stream("distributed-block-topic");
    	KStream<String, String> hash = block.mapValues(new ValueMapper<String, String>() {
            @Override
            public String apply(String sourceBlock) {
        		System.out.print("Block is coming: " + sourceBlock);
		 		String result;
		 		String preHashValue = sgPrehash.getPreHash();
				try {
					result = VerifyBlock.blockVerify(sourceBlock, preHashValue);
					sgPrehash.setPreHash(result);
					sgBlock.setBlockToVerify(sourceBlock);
					//System.out.println(result.substring(0,64));
					//System.out.println(result);
				}catch (NoSuchAlgorithmException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return sgBlock.getPreHash();
          }});
    	//hash.to("save-hash-topic");
    	
        final Topology topology = builder.build();
        final KafkaStreams streams = new KafkaStreams(topology, props);
        final CountDownLatch latch = new CountDownLatch(1);
 
        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });
 
        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
    	
}
