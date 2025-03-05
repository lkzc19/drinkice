package org.example.loafer.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.example.loafer.model.common.BaseModel;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("loafer_link")
public class LinkModel extends BaseModel {
    private String link;
    private String code;
    private Integer visitNum = 0;
    private LocalDateTime expireAt;
}
