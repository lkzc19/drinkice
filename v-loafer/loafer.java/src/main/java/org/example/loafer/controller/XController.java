package org.example.loafer.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.ImmutableMap;
import jakarta.annotation.Resource;
import org.example.loafer.mapper.LinkMapper;
import org.example.loafer.model.LinkModel;
import org.example.loafer.utils.Base62Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/x")
public class XController {

    @Resource
    private LinkMapper linkMapper;

    @PostMapping
    public ResponseEntity<Map<String, Object>> x(String link) {
        // 进行简单的URL校验
        if (!link.startsWith("http://") && !link.startsWith("https://")) {
            ImmutableMap<String, Object> resp = ImmutableMap.<String, Object>builder()
                    .put("message", "请输入 http:// 或 https:// 开头的网址")
                    .build();
            return ResponseEntity.badRequest().body(resp);
        }

        // 查询是否已有短链映射
        LambdaQueryWrapper<LinkModel> q4link = new LambdaQueryWrapper<LinkModel>()
                .eq(LinkModel::getLink, link)
                .ge(LinkModel::getExpireAt, LocalDateTime.now());
        List<LinkModel> linkModels = linkMapper.selectList(q4link);
        if (!CollectionUtils.isEmpty(linkModels)) {
            LinkModel l = linkModels.get(0);
            ImmutableMap<String, Object> resp = ImmutableMap.<String, Object>builder()
                    .put("link", l.getLink())
                    .put("code", l.getCode())
                    .put("visitNum", l.getVisitNum())
                    .build();
            return ResponseEntity.badRequest().body(resp);
        }

        // 生成短链
        int codes;
        String tmpLink = link;
        String code;
        do {
            code = Base62Utils.encode(tmpLink);
            LambdaQueryWrapper<LinkModel> q4code = new LambdaQueryWrapper<LinkModel>()
                    .eq(LinkModel::getCode, code)
                    .ge(LinkModel::getExpireAt, LocalDateTime.now());
            codes = linkMapper.selectList(q4code).size();
            tmpLink += "drinkice"; // 碰撞处理
        } while (codes > 0);

        LinkModel l = LinkModel.builder()
                .link(link)
                .code(code)
                .expireAt(LocalDateTime.now().plusDays(1))
                .build();
        linkMapper.insert(l);

        ImmutableMap<String, Object> resp = ImmutableMap.<String, Object>builder()
                .put("link", l.getLink())
                .put("code", l.getCode())
                .build();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> code(@PathVariable String code) {
        LambdaQueryWrapper<LinkModel> q4code = new LambdaQueryWrapper<LinkModel>()
                .eq(LinkModel::getCode, code)
                .ge(LinkModel::getExpireAt, LocalDateTime.now());
        List<LinkModel> links = linkMapper.selectList(q4code);

        if (CollectionUtils.isEmpty(links)) {
            return ResponseEntity.notFound().build();
        }

        LinkModel link = links.get(0);
        // 更新短链统计信息
        LambdaUpdateWrapper<LinkModel> updateWrapper = new LambdaUpdateWrapper<LinkModel>()
                .eq(LinkModel::getId, link.getId())
                .setIncrBy(LinkModel::getVisitNum, 1);
        linkMapper.update(updateWrapper);

        // 进行重定向
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", link.getLink())
                .build();
    }
}
