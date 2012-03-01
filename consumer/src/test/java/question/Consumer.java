package question;


import javax.inject.Inject;
import java.util.logging.Logger;

public class Consumer {
    @Inject
    Logger logger;

    public void doLog() {
        logger.info("Hello CDI with JBoss Modules");
    }
}
