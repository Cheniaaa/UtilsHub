package common.service.poService;

import common.entity.Product;
import common.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class ProductService extends ServiceImpl<ProductMapper, Product> {

}
