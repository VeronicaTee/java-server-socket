package com.veronicatee.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread{

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;
    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page was served using my simple Java HTTP server</h1></body</html>";


            final String CRLF = "\n\r"; // 13, 10

            String response =
                    "HTTP/1.1 200 OK" + CRLF + //Status Line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF + //HEADER
                            CRLF +
                            html +
                            CRLF + CRLF;
            outputStream.write(response.getBytes());

            LOGGER.info("Connection Processing Finished");


//            fs.readFile("index.html", function(err, data){
//                if(err){
//                    response.writeHead(404);
//                    response.write("Not Found!");
//                }
//                else{
//                    response.writeHead(200, {'Content-Type': contentType});
//                    response.write(data);
//                }
//                response.end();
//            }


        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
            e.printStackTrace();

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {

                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {

                }
            }

        }
    }

}
