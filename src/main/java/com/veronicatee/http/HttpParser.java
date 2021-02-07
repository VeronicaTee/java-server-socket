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

    public HttpRequest parserHttpRequest(InputStream inputStream) throws IOException, HttpParsingException {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HttpRequest request = new HttpRequest();

        parserRequestLine(reader, request);
        parserHeader(reader, request);
        parserBody(reader, request);

        return request;

    }

    private void parserRequestLine(InputStreamReader reader, HttpRequest request) throws IOException, HttpParsingException {
        StringBuilder processingDataBuffer =new StringBuilder();


        Boolean methodParsed = false;
        Boolean requestTargetParsed = false;
        String path = "/";

        int _byte;
        while ((_byte = reader.read()) >= 0) {
            if (_byte == CR){
                _byte = reader.read();
                if (_byte == LF) {
                    LOGGER.debug("Request Line Version to Process: {}", processingDataBuffer.toString());
                    if (!methodParsed || !requestTargetParsed) {
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    return;
                }
            }

            if (_byte == SP) {
                if (!methodParsed){
                    LOGGER.debug("Request Line to Process: {}", processingDataBuffer.toString());
                    request.setMethod(processingDataBuffer.toString());
                    methodParsed = true;
                } else if (!requestTargetParsed) {
                    LOGGER.debug("Request Line Target to Process: {}", processingDataBuffer.toString());
                    requestTargetParsed = true;
                } else {
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
                processingDataBuffer.delete(0, processingDataBuffer.length());
            } else {
                processingDataBuffer.append((char)_byte);
                if (!methodParsed) {
                    if (processingDataBuffer.length() > HttpMethod.MAX_LENGTH) {
                        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                }
            }
        }
    }

    private void parserHeader(InputStreamReader reader, HttpRequest request) {
    }

    private void parserBody(InputStreamReader reader, HttpRequest request) {
    }

}
