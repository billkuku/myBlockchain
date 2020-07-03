package StreamForBC;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

/** this class is only used  for test a multithread-producer.
*   function not impemented yet.
*   @ beier
*/

public class ProducerValidate extends Thread{
    
	public static final String TOPIC1 = "source-topic";
	public static final String TOPIC2 = "block-validator-topic";	
	public static final String TOPIC3 = "block-verifier-topic";	
	public static final String TOPIC4 = "source-validator-topic";


    public static void main(String[] args) {
        boolean isAsync = false;
        /*ProducerValidate producerThread1 = new ProducerValidate(TOPIC1, isAsync);
        // start the producer
        producerThread1.start();
        ProducerValidate producerThread2 = new ProducerValidate(TOPIC2, isAsync);
        // start the producer
        //producerThread2.start();        
        ProducerValidate producerThread3 = new ProducerValidate(TOPIC3, isAsync);
        // start the producer
        //producerThread3.start();   */  
        ProducerValidate producerThread4 = new ProducerValidate(TOPIC4, isAsync);
        // start the producer
        producerThread4.start();         
    }
    
    private final KafkaProducer<Integer, String> producer;
    private final String topic;
    private final Boolean isAsync;
 
    public static final String KAFKA_SERVER_URL = "localhost";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final String CLIENT_ID = "SampleProducer";
 
    public ProducerValidate(String topic, Boolean isAsync) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
        properties.put("client.id", CLIENT_ID);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        producer = new KafkaProducer<>(properties);
        this.topic = topic;
        this.isAsync = isAsync;
    }
 
    public void run() {
        int messageNo = 1;
        while (true) {
            String messageStr = "Message ist _" + messageNo;
            //messageStr = GetData.getSourceDatabyValidator();
			//messageStr = "21345";     		
            long startTime = System.currentTimeMillis();
            if (isAsync) { // Send asynchronously
                producer.send(new ProducerRecord<>(topic,
                        messageNo,
                        messageStr), new DemoCallBack(startTime, messageNo, messageStr));
                System.out.println("Sent message: (" + messageNo + ", " + messageStr + ")");
            } else { // Send synchronously
                try {
                    producer.send(new ProducerRecord<>(topic,
                            messageNo,
                            messageStr)).get();
                    System.out.println("Sent message: (" + messageNo + ", " + messageStr + ")");
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    // handle the exception
                }
            }
            ++messageNo;
        }
    }
}
 
class DemoCallBack implements Callback {
 
    private final long startTime;
    private final int key;
    private final String message;
 
    public DemoCallBack(long startTime, int key, String message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }
 
    /**
     * onCompletion method will be called when the record sent to the Kafka Server has been acknowledged.
     *
     * @param metadata  The metadata contains the partition and offset of the record. Null if an error occurred.
     * @param exception The exception thrown during processing of this record. Null if no error occurred.
     */
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (metadata != null) {
            System.out.println(
                    "message(" + key + ", " + message + ") sent to partition(" + metadata.partition() +
                    "), " +
                    "offset(" + metadata.offset() + ") in " + elapsedTime + " ms");
        } else {
            exception.printStackTrace();
        }
    }
}

