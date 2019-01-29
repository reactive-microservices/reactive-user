package com.max.reactive.user;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.atomic.LongAdder;

public class UserVerticle extends AbstractVerticle {

    private static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String VERTEX_NAME = UserVerticle.class.getCanonicalName();

    private static final LongAdder REQUESTS_COUNTER = new LongAdder();

    private static final int PORT = 7070;

    @Override
    public void start() {
        vertx.createHttpServer().requestHandler(request -> {

            REQUESTS_COUNTER.increment();


            request.response().end("User-" + REQUESTS_COUNTER + ", " + Thread.currentThread().getName() + "\n");
        }).listen(PORT);

        LOG.info("{} started at port {}", VERTEX_NAME, PORT);
    }

    @Override
    public void stop() {
        LOG.info("{} stopped", VERTEX_NAME);
    }
}
