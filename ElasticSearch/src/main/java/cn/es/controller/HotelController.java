package cn.es.controller;

import cn.es.dto.HotelDoc;
import cn.es.dto.PageResult;
import cn.es.param.HotelParam;
import cn.es.service.TbHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/21 17:07:07
 * @Version : 1.0
 */
@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    TbHotelService hotelService;


    @PostMapping("/list")
    public PageResult search(@RequestBody HotelParam hotelParam) {
        return hotelService.search(hotelParam);
    }


    @PostMapping("/filters")
    public Map<String, List<String>> getFilters(@RequestBody HotelParam param) {
        return hotelService.filter(param);
    }

    @GetMapping("/suggestion")
    public List<String> suggestion(@RequestParam("key") String prefix) {
        return hotelService.getSuggestions(prefix);

    }
}
