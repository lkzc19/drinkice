package org.example.loafer.mapper;


import jakarta.annotation.Resource;
import org.example.loafer.model.LinkModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class LinkModelMapperTest {

    @Resource
    private LinkMapper linkMapper;

    @Test
    public void test() {
        LinkModel linkModel = LinkModel.builder()
                .link("https://ai.com")
                .code("zxc")
                .visitNum(0)
                .expireAt(LocalDateTime.now().plusDays(1))
                .build();
        linkMapper.insert(linkModel);
    }
}