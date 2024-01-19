package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        int status = 204;
        if ("GET".equals(req.httpRequestType())) {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            var users = topics.get(req.getSourceName());
            users.putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            ConcurrentLinkedQueue<String> user = users.get(req.getParam());
            text = user.poll();
            if (text == null) {
                text = "";
            }
            status = 200;
        }
        if ("POST".equals(req.httpRequestType())) {
            var users = topics.get(req.getSourceName());
            if (users != null) {
                for (String user : users.keySet()) {
                    ConcurrentLinkedQueue<String> clq = users.get(user);
                    clq.add(req.getParam());
                }
                status = 200;
            }
        }
        return new Resp(text, status);
    }
}
