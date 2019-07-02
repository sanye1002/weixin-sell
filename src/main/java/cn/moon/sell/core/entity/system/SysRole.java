package cn.moon.sell.core.entity.system;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysRole implements Serializable  {

    private static final long serialVersionUID = 2098921535825596400L;
    @TableId
    private String id;
    
    private String name;

    @TableField(exist = false)
    private List<SysResource> resources;


}
