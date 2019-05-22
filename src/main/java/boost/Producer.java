package boost;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.ejb.Stateless;
import javax.ejb.Schedule;
import javax.ejb.LocalBean;
import javax.annotation.Resource;

@Stateless
@LocalBean
public class Producer
{
    @Resource(name = "java:/ConnectionFactory" )
    private ConnectionFactory connectionFactory;

    @Resource(name = "java:/queue/test" )
    private Destination destination;

    @Schedule(hour = "*", minute ="*", second = "*/5", persistent = false)
    public void produceMessage(){
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage("Hello MDB");

            messageProducer.send(textMessage);
            System.out.println("========================================================");
            connection.close();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
