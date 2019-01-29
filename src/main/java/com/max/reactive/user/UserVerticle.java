package com.max.reactive.user;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;

public class UserVerticle extends AbstractVerticle {

    private static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String VERTEX_NAME = UserVerticle.class.getCanonicalName();

    private static final int PORT = 7070;

    @Override
    public void start() {
        Router router = Router.router(vertx);

        router.get("/user/health").handler(request -> {
            JsonObject data = new JsonObject();
            data.put("status", "healthy");
            request.response().
                    putHeader(HttpHeaders.CONTENT_TYPE, "application/json").
                    end(data.encode());
        });

        router.get("/user/:name").handler(request -> {

            String userName = request.pathParam("name");

            JsonObject data = new JsonObject();
            data.put("name", "Maksym Stepanenko");
            data.put("nickname", userName);
            data.put("age", 33);
            data.put("thread", Thread.currentThread().getName());
            data.put("hobbies", Arrays.asList("jujutsu", "programming", "reading books"));

            request.response().
                    putHeader(HttpHeaders.CONTENT_TYPE, "application/json").
                    end(data.encode());
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(PORT);

        LOG.info("{} started at port {}", VERTEX_NAME, PORT);
    }

    @Override
    public void stop() {
        LOG.info("{} stopped", VERTEX_NAME);
    }
}
