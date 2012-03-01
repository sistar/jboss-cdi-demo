package question;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(Arquillian.class)
public class CrossModuleInjectTest {

    @Deployment(testable = false, name = "producer", order = 0)
    public static Archive<?> deployProducer() {
        return ShrinkWrap.create(JavaArchive.class, "producer.jar")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addClass(Producer.class);
    }


    @Deployment(testable = false, name = "consumer", order = 1)
    public static Archive<?> deployConsumers() {
        return ShrinkWrap.create(WebArchive.class, "consumer.war")
                .addAsManifestResource("META-INF/MANIFEST.MF", "MANIFEST.MF")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml").addClass(Consumer.class);
    }

    @Deployment(testable = true, name = "tester", order = 2)
    public static Archive<?> deployTester() {
        return ShrinkWrap.create(WebArchive.class, "tester.war");
    }

    @Inject
    Consumer consumer;

    @Test
    @OperateOnDeployment("consumer")
    public void testThatProducerFromOtherModuleIsUsed() {
        consumer.doLog();
    }


}
