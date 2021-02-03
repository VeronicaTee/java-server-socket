package com.veronicatee.httpserver;

import com.veronicatee.httpserver.config.Configuration;
import com.veronicatee.httpserver.config.ConfigurationManager;
import com.veronicatee.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Driver Class for the Http Server
 */
public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws IOException {


        LOGGER.info("Server starting...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using Port: " + conf.getPort());
        LOGGER.info("Using Webroot: " + conf.getWebroot());

        try{
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch(IOException e) {
            e.printStackTrace();
            //TODO Handle Exception
        }

//        private static void sendResponse(Socket client, String status, String contentType, byte[] content) throws IOException {
//            String path = "";
//            OutputStream clientOutput = client.getOutputStream();
//            clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
//            clientOutput.write(("ContentType: text/html\r\n").getBytes());
//            clientOutput.write("\r\n".getBytes());
//            //       clientOutput.write("<b>It works!</b>".getBytes());
////        if ("/".equals(path)) {
//            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(getFilePath())));
//            String line;
//            while(!(line = br.readLine()).isBlank()) {
//                clientOutput.write(line.getBytes());
//            }
//            clientOutput.write("\r\n\r\n".getBytes());
//            clientOutput.flush();
//            client.close();

//            String html = "";
//
//            if (path.endsWith("/")) {
//                BufferedReader html = new BufferedReader(new FileReader(String.valueOf(getFilePath())));
//            }
//        }
//
//        public static Path getFilePath(){
//            if (path.endsWith("/")){
//                path = "src/main/resources/http.html";
//            }
//            if (path.endsWith("/json")){
//                path = ""src/main/resources/http.json"";
//            }
//            return Paths.get("/Users/a/Desktop/Concurency/Server/" +path);
//        }
//        public static String getContentType(Path filePath) throws IOException {
//            return Files.probeContentType(filePath);
//        }

    }
}
