package com.max.reactive.user;

import com.google.inject.Guice;
import com.max.reactive.user.core.UserDao;
import com.max.reactive.user.core.UserDto;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;

import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

public class UserVerticle extends AbstractVerticle {

    private static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String VERTEX_NAME = UserVerticle.class.getCanonicalName();
    private static final String APPLICATION_JSON_TYPE = "application/json";

    private static final int PORT = 7070;

    private UserDao userDao;

    @Override
    public void start() {

        userDao = Guice.createInjector(new UserModule()).getInstance(UserDao.class);

        Router router = Router.router(vertx);

        router.get("/user/health").handler(request -> {
            JsonObject data = new JsonObject();
            data.put("status", "healthy");
            request.response().
                    putHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_TYPE).
                    end(data.encode());
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(PORT);

//        router.get("/user/:name").handler(request -> {
//
//            String userName = request.pathParam("name");
//
//            Optional<UserDto> userDto = userDao.findUser(userName);
//
//            if (userDto.isPresent()) {
//                JsonObject data = new JsonObject();
//                data.put("name", userDto.get().getName());
//                data.put("nickname", userDto.get().getNickName());
//                data.put("age", userDto.get().getAge());
//                data.put("thread", Thread.currentThread().getName());
//                data.put("hobbies", userDto.get().getHobbies());
//                request.response().
//                        setStatusCode(200).
//                        putHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_TYPE).
//                        end(data.encode());
//            }
//            else {
//                JsonObject errorMessage = new JsonObject();
//                errorMessage.put("message", "Can't find user with name " + userName);
//                request.response().
//                        setStatusCode(404).
//                        putHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_TYPE).
//                        end(errorMessage.encode());
//            }
//        });
//



        vertx.eventBus().consumer("reactive-user/user", message -> {

            String userName = (String)message.body();
            
            Optional<UserDto> userDto = userDao.findUser(userName);

            if (userDto.isPresent()) {
                JsonObject data = new JsonObject();
                data.put("name", userDto.get().getName());
                data.put("nickname", userDto.get().getNickName());
                data.put("age", userDto.get().getAge());
                data.put("thread", Thread.currentThread().getName());
                data.put("hobbies", userDto.get().getHobbies());
                message.reply(data);
            }
            else {
                JsonObject errorMessage = new JsonObject();
                errorMessage.put("errorMessage", "Can't find user with name " + userName);
                message.reply(errorMessage);
            }
        });

        LOG.info("{} started at port {}", VERTEX_NAME, PORT);
    }

    @Override
    public void stop() {
        LOG.info("{} stopped", VERTEX_NAME);
    }
}
