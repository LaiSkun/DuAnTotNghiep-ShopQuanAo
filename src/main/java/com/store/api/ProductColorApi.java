    package com.store.api;
    import com.store.model.Colors;
    import com.store.model.Product_Colors;
    import com.store.service.ProductColorsService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestMethod;
    import org.springframework.web.bind.annotation.ResponseBody;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.List;

    @RestController
    public class ProductColorApi {
        @Autowired
        ProductColorsService productColorsService;
        @RequestMapping(value = "/admin/product/list", method = RequestMethod.GET)
        public List<Product_Colors> productColors() {
            List<Product_Colors> list = productColorsService.findAll();
            return list;
        }
    }
