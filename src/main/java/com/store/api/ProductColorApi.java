    package com.store.api;
    import com.store.DTO.ProductColorDTO;
    import com.store.DTO.ProductColorUserDTO;
    import com.store.configs.CustomConfiguration;
    import com.store.model.Colors;
    import com.store.model.Product_Colors;
    import com.store.model.Product_Images;
    import com.store.model.Products;
    import com.store.service.ProductColorsService;
    import com.store.service.ProductService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

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
        @RequestMapping("/admin/product/listProductColorByProductId/{id}")
        public List<Product_Colors>  listProductColorByProductId(@PathVariable String id) {
            List<Product_Colors> prdColor = productColorsService.findbyProductID(id);
            return prdColor ;
        }
//        /admin/product/listProductColorByProductId
    }
