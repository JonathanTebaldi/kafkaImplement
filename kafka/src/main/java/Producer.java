import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Producer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        var producer = new KafkaProducer<String, String >(properties());

        //mandar mensagem
    var record = new ProducerRecord<>("compras.do.cliente", "cliente-1", "compras: 90reais");

    //verificar mensagem
        Callback callback = (data, error) ->{
            if(error != null){
                error.printStackTrace();
                return;
            }
            System.out.println("Mensagem publicada com sucesso: ");
            System.out.println(data.partition());
            System.out.println(data.offset());
        };
        //ENVIAR O CALLBACK PARA VERIFICAR SE DEU PROBLEMA
        producer.send(record, callback).get();
    }

    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "197.0.0.1:9092");
        //SERIALIZA QUANDO POSTA E DESERIALIZA QUANDO CONSOME
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
