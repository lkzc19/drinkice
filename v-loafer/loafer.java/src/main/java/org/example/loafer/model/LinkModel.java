package org.example.loafer.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("loafer_link")
public class Link {
    @TableId
    private Long id;
    private String link;
    private String code;
    private Integer visitNum;
    private LocalDateTime expireAt;
    private LocalDateTime createdAt;
    private String createdBy;
}
