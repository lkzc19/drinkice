package org.example.loafer.mapper;


import jakarta.annotation.Resource;
import org.example.loafer.model.Link;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class LinkMapperTest {

    @Resource
    private LinkMapper linkMapper;

    @Test
    public void test() {
        Link link = Link.builder()
                .url("https://ai.com")
                .code("zxc")
                .pv(0)
                .expireAt(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .build();
        linkMapper.insert(link);
    }
}