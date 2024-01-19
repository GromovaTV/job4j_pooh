package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        int status = 204;
        if ("GET".equals(req.httpRequestType())) {
            ConcurrentLinkedQueue<String> clq = queue.get(req.getSourceName());
            if (clq != null) {
                var tmp = clq.poll();
                if (tmp != null) {
                    text = tmp;
                    status = 200;
                }
            }
        }
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            ConcurrentLinkedQueue<String> clq = queue.get(req.getSourceName());
            if (clq.add(req.getParam())) {
                status = 200;
            }
        }
        return new Resp(text, status);
    }
}
