package ru.job4j.pooh;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        /* TODO parse a content */
        System.out.println("---------");
        System.out.println("PARSE REQ");
        System.out.println("---------");
        String httpRequestType = "";
        String poohMode = "";
        String sourceName = "";
        String param = "";
        String ls = System.lineSeparator();
        String[] lines = content.split(ls);
        String firstLine = lines[0];
        String lastLine = lines[lines.length - 1];
        String[] firstLineSplitted = firstLine.split(" ");
        httpRequestType = firstLineSplitted[0];
        System.out.println("RequestType: " + httpRequestType);
        String[] modeSourseParam = firstLineSplitted[1].split("/");
        poohMode = modeSourseParam[1];
        System.out.println("Pooh Mode: " + poohMode);
        sourceName = modeSourseParam[2];
        System.out.println("Source Name: " + sourceName);
        if (httpRequestType.equals("POST")) {
            param = lastLine;
        }
        if (httpRequestType.equals("GET") && modeSourseParam.length > 3) {
            param = modeSourseParam[3];
        }
        System.out.println("Param: " + param);
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}