package com.clt.expenses;

import com.clt.domain.commons.UseCase;
import com.clt.event.Listener;
import com.clt.event.Notifier;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationStatusReporter {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationStatusReporter.class);

    private final List<UseCase> useCases;
    private final List<Notifier<?>> notifiers;
    private final List<Listener<?>> listeners;

    public ApplicationStatusReporter(List<UseCase> useCases, List<Notifier<?>> notifiers, List<Listener<?>> listeners) {
        this.useCases = useCases;
        this.notifiers = notifiers;
        this.listeners = listeners;
    }

    @PostConstruct
    public void printConfig() {
        logger.info("Enabled Use Cases:");
        this.useCases.forEach(uc -> logger.info(" - {}", uc.getName()));
        logger.info("Enabled Listeners: ");
        this.listeners.forEach(l -> logger.info(" - {}", l.getClass().getName()));
        logger.info("Enabled Notifiers: ");
        this.notifiers.forEach(n -> logger.info(" - {}", n.getClass().getName()));
    }

}
