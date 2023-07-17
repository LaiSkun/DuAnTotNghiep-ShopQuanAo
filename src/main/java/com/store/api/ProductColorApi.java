    package com.store.api;
    import com.store.DTO.ProductColorDTO;
    import com.store.configs.CustomConfiguration;
    import com.store.model.Colors;
    import com.store.model.Product_Colors;
    import com.store.model.Products;
    import com.store.service.ProductColorsService;
    import com.store.service.ProductService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestMethod;
    import org.springframework.web.bind.annotation.ResponseBody;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.ArrayList;
    import java.util.List;

    @RestController
    public class ProductColorApi {
        @Autowired
        ProductColorsService productColorsService;
        @Autowired
        ProductService productService;
        @Autowired
        CustomConfiguration customConfiguration;
        @RequestMapping(value = "/admin/product/list", method = RequestMethod.GET)
        public List<ProductColorDTO> productColors() {
            List<Product_Colors> list = productColorsService.findAll();
            List<ProductColorDTO> listprDTO = new ArrayList<>();
            list.forEach( item -> {
            ProductColorDTO prdDTO = new ProductColorDTO();
                prdDTO.setColorID(item.getColorID());
                prdDTO.setColor_name(item.getColor_name());
                prdDTO.setAvailable(item.getAvailable());
                prdDTO.setColorhex(item.getColorhex());
                prdDTO.setProductID(item.getProduct().getProductID());
                listprDTO.add(prdDTO);
            });
            return listprDTO;
        }
    }
