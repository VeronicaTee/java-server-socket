package com.veronicatee.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20; // 32
    private static final int CR = 0x0D; // 13
    private static final int LF = 0x0A; // 10

    public HttpRequest parserHttpRequest(InputStream inputStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HttpRequest request = new HttpRequest();
        parserRequestLine(reader, request);
        parserHeader(reader, request);
        parserBody(reader, request);

        return request;

    }

    private void parserRequestLine(InputStreamReader reader, HttpRequest request) throws IOException {
        StringBuilder processingDataBuffer =new StringBuilder();


        Boolean methodParsed = false;
        String path = "/";
        Boolean requestTargetParsed = false;

        int _byte;
        while ((_byte = reader.read()) >= 0) {
            if (_byte == CR){
                _byte = reader.read();
                if (_byte == LF) {
                    LOGGER.debug("Request Line Version to Process: {}", processingDataBuffer.toString());
                    return;
                }
            }

            if (_byte == SP) {
                //
                if (!methodParsed){
                    LOGGER.debug("Request Line to Process: {}", processingDataBuffer.toString());
                    request.setMethod(processingDataBuffer.toString());
                    methodParsed = true;
                } else if (!requestTargetParsed) {
                    LOGGER.debug("Request Line Target to Process: {}", processingDataBuffer.toString());
                    requestTargetParsed = true;
                }
                processingDataBuffer.delete(0, processingDataBuffer.length());
            } else {
                processingDataBuffer.append((char)_byte);
            }
        }
    }

    private void parserHeader(InputStreamReader reader, HttpRequest request) {
    }

    private void parserBody(InputStreamReader reader, HttpRequest request) {
    }


//    if (path.endsWith("/")){
//        path = "src/main/resources/http.html";
//    } else if (path.endsWith("/json")) {
//        path = "src/main/resources/http.json";
//    } else {
////                    throw IOException;
//    }

}
