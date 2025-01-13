package com.example.browserstack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
public class WebBrowserController {

    @GetMapping("/start")
    public void startBrowser(@RequestParam("browser") String browser,
                             @RequestParam("url") String url) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("open", "-a", browser, url);

        System.out.println("Opening browser " + browser + " with " + url);

        processBuilder.start();
    }

    @GetMapping("/stop")
    public void stopBrowser(@RequestParam("browser") String browser) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("pgrep", "-f", browser);

        Process process = processBuilder.start();

        InputStream inputStream = process.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String processId = bufferedReader.readLine();

        bufferedReader.close();

        System.out.println("Stopping browser " + browser + " with  PID " + processId);

        ProcessBuilder pb = new ProcessBuilder("kill", processId);
        pb.start();
    }

    @GetMapping("/getUrl")
    public String getActiveTabUrl(@RequestParam("browser") String browser) throws IOException {
        ProcessBuilder processBuilder =
                new ProcessBuilder("osascript", "-e", "tell application \"" + browser + "\" to get url of active tab of the front window");

        Process process = processBuilder.start();

        InputStream inputStream = process.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String activeTabUrl = bufferedReader.readLine();

        bufferedReader.close();

        System.out.println("Active browser " + activeTabUrl);

        return activeTabUrl;
    }
}
/*
* Commands for quick testing
* */

// http://localhost:8080/start?browser=Firefox&url=https://www.facebook.com/
// http://localhost:8080/start?browser=Safari&url=https://www.facebook.com/
// http://localhost:8080/start?browser=Google%20Chrome&url=https://www.facebook.com/

//http://localhost:8080/stop?browser=Firefox
//http://localhost:8080/stop?browser=Google%20Chrome
//http://localhost:8080/getUrl?browser=Google%20Chrome
//  osascript -e "tell application \"Google Chrome\" to get url of active tab of the front window"
//  osascript -e "tell application \"Mozilla Firefox\" to get url of active tab of the front window"