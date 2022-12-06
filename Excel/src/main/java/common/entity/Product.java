package common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "product")
public class Product implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private Integer pid;

    @TableField(value = "`name`")
    @ApiModelProperty(value="")
    private String pName;

    @TableField(value = "subTitle")
    @ApiModelProperty(value="")
    private String pSubTitle;

    @TableField(value = "originalPrice")
    @ApiModelProperty(value="")
    private Double pOriginalPrice;

    @TableField(value = "promotePrice")
    @ApiModelProperty(value="")
    private Double pPromotePrice;

    @TableField(value = "stock")
    @ApiModelProperty(value="")
    private Integer pStock;

    @TableField(value = "cid")
    @ApiModelProperty(value="")
    private Integer pCid;

    @TableField(value = "createDate")
    @ApiModelProperty(value="")
    private LocalDateTime pCreateDate;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_SUBTITLE = "subTitle";

    public static final String COL_ORIGINALPRICE = "originalPrice";

    public static final String COL_PROMOTEPRICE = "promotePrice";

    public static final String COL_STOCK = "stock";

    public static final String COL_CID = "cid";

    public static final String COL_CREATEDATE = "createDate";
}