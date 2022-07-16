package com.service.poService;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Product;
import com.mapper.ProductMapper;
@Service
public class ProductService extends ServiceImpl<ProductMapper, Product> {

}
