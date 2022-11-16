package cn.es.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="tb_hotel")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_hotel")
public class TbHotel implements Serializable {
    /**
     * 酒店id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="酒店id")
    private Long id;

    /**
     * 酒店名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="酒店名称")
    private String name;

    /**
     * 酒店地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value="酒店地址")
    private String address;

    /**
     * 酒店价格
     */
    @TableField(value = "price")
    @ApiModelProperty(value="酒店价格")
    private Integer price;

    /**
     * 酒店评分
     */
    @TableField(value = "score")
    @ApiModelProperty(value="酒店评分")
    private Integer score;

    /**
     * 酒店品牌
     */
    @TableField(value = "brand")
    @ApiModelProperty(value="酒店品牌")
    private String brand;

    /**
     * 所在城市
     */
    @TableField(value = "city")
    @ApiModelProperty(value="所在城市")
    private String city;

    /**
     * 酒店星级，1星到5星，1钻到5钻
     */
    @TableField(value = "star_name")
    @ApiModelProperty(value="酒店星级，1星到5星，1钻到5钻")
    private String starName;

    /**
     * 商圈
     */
    @TableField(value = "business")
    @ApiModelProperty(value="商圈")
    private String business;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    @ApiModelProperty(value="纬度")
    private String latitude;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    @ApiModelProperty(value="经度")
    private String longitude;

    /**
     * 酒店图片
     */
    @TableField(value = "pic")
    @ApiModelProperty(value="酒店图片")
    private String pic;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_ADDRESS = "address";

    public static final String COL_PRICE = "price";

    public static final String COL_SCORE = "score";

    public static final String COL_BRAND = "brand";

    public static final String COL_CITY = "city";

    public static final String COL_STAR_NAME = "star_name";

    public static final String COL_BUSINESS = "business";

    public static final String COL_LATITUDE = "latitude";

    public static final String COL_LONGITUDE = "longitude";

    public static final String COL_PIC = "pic";
}