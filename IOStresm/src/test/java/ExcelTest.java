import com.IOApplication;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.entity.Product;
import com.service.poService.ProductService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Description : 测试Excel
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/7/17  00:57:57
 * @Version : 1.0
 */

@SpringBootTest(classes = IOApplication.class)
public class ExcelTest {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void getList() throws JSONException {
        QueryWrapper<Product> query = Wrappers.query();
        query.ge(Product.COL_ID, 900);
        List<Product> list = productService.list(query);

        JSONArray jsonArray = new JSONArray();
        for (Product product : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("产品id", product.getPid());
            jsonObject.put("产品名称", product.getPName());
            jsonObject.put("产品标题", product.getPSubTitle());
            jsonObject.put("产品原价", product.getPOriginalPrice());
            jsonObject.put("产品现价", product.getPPromotePrice());
            jsonObject.put("产品存货", product.getPStock());
            jsonObject.put("产品cid", product.getPCid());
            jsonObject.put("产品创建时间", product.getPCreateDate());
            jsonArray.put(jsonObject);
        }
        System.out.println(jsonArray);
    }
}
