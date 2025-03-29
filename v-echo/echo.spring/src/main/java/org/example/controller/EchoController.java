package org.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.util.*;

@RestController
public class EchoController {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    @RequestMapping("/**")
    public ResponseEntity<Map<String, Object>> echo(HttpServletRequest request) {
        Map<String, Object> vo = new LinkedHashMap<>();

        vo.put("echo", "spring");

        vo.put("method", request.getMethod());

        vo.put("url", request.getRequestURL());

        Map<String, Object> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        vo.put("headers", headers);

        Map<String, Object> args = new HashMap<>();
        request.getParameterMap().forEach((k, v) -> {
            if (v.length == 1) {
                args.put(k, v[0]);
            } else {
                args.put(k, Arrays.toString(v));
            }
        });
        vo.put("args", args);

        Map<String, Object> data = new HashMap<>();
        String contentType = String.valueOf(headers.get("content-type"));
        if ("application/json".equals(contentType)) {
            BufferedReader reader = request.getReader();
            StringBuilder dataStr = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                dataStr.append(line);
            }

            data = MAPPER.readValue(dataStr.toString(), new TypeReference<>() {});
        }
        vo.put("data", data);


        return ResponseEntity.ok(vo);
    }
}
