package com.max.reactive.user;

import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

final class UserMain {

    private static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private UserMain() {
        final Vertx vertex = Vertx.vertx();
        vertex.deployVerticle(new UserVerticle());
    }

    public static void main(String[] args) {
        try {
            new UserMain();
        }
        catch (Exception ex) {
            LOG.error("Error occurred", ex);
        }
    }
}
