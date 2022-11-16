package cn.es.dto;

import cn.es.entity.TbHotel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/20 18:28:28
 * @Version : 1.0
 */
@Data
@NoArgsConstructor
public class HotelDoc {

    private Long id;
    private String name;
    private String address;
    private Integer price;
    private Integer score;
    private String brand;
    private String city;
    private String starName;
    private String business;
    private String location;
    private String pic;

    private Object distance;
    private Boolean isAD;

    private List<String> suggestion;

    public HotelDoc(TbHotel hotel) {
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.price = hotel.getPrice();
        this.address = hotel.getAddress();
        this.score = hotel.getScore();
        this.brand = hotel.getBrand();
        this.city = hotel.getCity();
        this.starName = hotel.getStarName();
        this.business = hotel.getBusiness();
        this.location = hotel.getLatitude() + "," + hotel.getLongitude();
        this.pic = hotel.getPic();
        this.suggestion = Arrays.asList(this.brand, this.business);
    }
}
