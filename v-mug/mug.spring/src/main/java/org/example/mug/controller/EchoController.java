package org.example.mug.controller;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.SneakyThrows;
import org.example.mug.util.MdUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class EchoController {

    @SneakyThrows
    @GetMapping()
    public String echo(String signature, String timestamp, String nonce, String echostr) {
        if (!StringUtils.hasText(signature)) {
            return null;
        }

        ArrayList<String> strings = new ArrayList<>();
        strings.add(timestamp);
        strings.add(nonce);
        Dotenv dotenv = Dotenv.load(); // 加载 .env 文件
        String token = dotenv.get("VX_MP_TOKEN");
        strings.add(token);

        strings.sort(String::compareTo);
        String join = String.join("", strings);

        if (signature.equals(MdUtils.sha1(join))) {
            return echostr;
        } else {
            return null;
        }
    }
}
