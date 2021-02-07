package com.veronicatee.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread{

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private static Socket socket;

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

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String inp = br.readLine();
            System.out.println(this.getName() + inp);

//            String html = "";

//            html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page was served using my simple Java HTTP server</h1></body</html>";

            final String CRLF = "\n\r"; // 13, 10

            String response =
                    "HTTP/1.1 200 OK" + CRLF + //Status Line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length : " + clientResponse(inp).getBytes().length + CRLF +
                            CRLF +
                            clientResponse(inp) +
                            CRLF + CRLF;
//                outputStream.write(response.getBytes());

            LOGGER.info("Connection Processing Finished");
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

    public String clientResponse(String route) throws IOException {
        String result = "";
        String content;
        if (route.contains("/json")) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("text.json"));
            while ((content = bufferedReader.readLine()) != null) {
                result += content;
            }
        } else {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("text.html"));
            while ((content = bufferedReader.readLine()) != null) {
                result += content;
            }
        }
        return result;
    }

}
