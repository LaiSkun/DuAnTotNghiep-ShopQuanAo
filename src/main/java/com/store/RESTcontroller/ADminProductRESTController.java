package com.store.RESTcontroller;

import com.store.dao.ProductDAO;
import com.store.model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class ADminProductRESTController {
//    @Autowired
//    ProductDAO productDAO;
//    @GetMapping("")
//    public String dogetIndex(Model model) {
//        List<Products> products = productDAO.findAll();
//        model.addAttribute("products", products);
//        model.addAttribute("productsRequest", new Products());
//        return "/admin/product";
//    }

}
