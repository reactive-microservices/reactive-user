package com.max.reactive.user;

import com.google.inject.Guice;
import com.max.reactive.user.core.UserDao;
import com.max.reactive.user.core.UserDto;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Optional;
import java.util.Random;

public class UserVerticle extends AbstractVerticle {

    private static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String VERTEX_NAME = UserVerticle.class.getCanonicalName();
    private static final String APPLICATION_JSON_TYPE = "application/json";

    private static final Random RAND = new Random();

    private static final int PORT = 7070;

    private UserDao userDao;

    @Override
    public void start() {

        userDao = Guice.createInjector(new UserModule()).getInstance(UserDao.class);

        Router router = Router.router(vertx);

        router.get("/health").handler(request -> {
            JsonObject data = new JsonObject();
            data.put("service_name", "reactive_user");
            data.put("status", "healthy");
            request.response().
                    putHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_TYPE).
                    end(data.encode());
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(PORT);

        vertx.eventBus().consumer("reactive-user/user", message -> {

            normalFlow(message);

            //TODO: change flow here
//            faultyFlow(message);

        });

        LOG.info("{} started at port {}", VERTEX_NAME, PORT);
    }

    private void faultyFlow(Message<Object> message) {
        int randomValue = RAND.nextInt(10);

        if (randomValue <= 3) {
            // success
            String userName = (String) message.body();

            Optional<UserDto> userDto = userDao.findUser(userName);

            if (userDto.isPresent()) {
                JsonObject data = new JsonObject();
                data.put("name", userDto.get().getName());
                data.put("nickname", userDto.get().getNickName());
                data.put("age", userDto.get().getAge());
                data.put("thread", Thread.currentThread().getName());
                data.put("hobbies", userDto.get().getHobbies());
                data.put("served-by", this.toString());
                message.reply(data);
            }
            else {
                message.fail(404, createErrorBody("Can't find user with name " + userName).encode());
            }
        }
        else if (randomValue <= 6) {
            // no reply
        }
        else {
            // failure
            message.fail(500, createErrorBody("Some random failure").encode());
        }
    }

    private void normalFlow(Message<Object> message) {
        String userName = (String) message.body();

        Optional<UserDto> userDto = userDao.findUser(userName);

        if (userDto.isPresent()) {
            JsonObject data = new JsonObject();
            data.put("name", userDto.get().getName());
            data.put("nickname", userDto.get().getNickName());
            data.put("age", userDto.get().getAge());
            data.put("thread", Thread.currentThread().getName());
            data.put("hobbies", userDto.get().getHobbies());
            data.put("served-by", this.toString());
            message.reply(data);
        }
        else {
            message.fail(404, createErrorBody("Can't find user with name " + userName).encode());
        }
    }

    private JsonObject createErrorBody(String msg) {
        JsonObject errorMessage = new JsonObject();
        errorMessage.put("errorMessage", msg);
        errorMessage.put("served-by", this.toString());
        return errorMessage;
    }

    @Override
    public void stop() {
        LOG.info("{} stopped", VERTEX_NAME);
    }
}
