package com.store.admin;

import com.store.DTO.ProductDTO;
import com.store.configs.CustomConfiguration;
import com.store.dao.CategoryDAO;
import com.store.dao.ProductColorDAO;
import com.store.dao.ProductImgDAO;
import com.store.model.*;
import com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryDAO categoryDAO;
    @Autowired
    ProductColorDAO productColorDAO;
    @Autowired
    CustomConfiguration customConfiguration;
    @Autowired
    ProductImgDAO productImgDAO;
    @GetMapping("")
    public String adminProduct(Model model, String type) {
        List<Products> products = productService.findDeprecatedTrue();
        List<Products> productsDepFalse = productService.findDeprecatedFalse();
        List<Categories> categories = categoryDAO.findAll();
        List<Product_Colors> productColors = productColorDAO.findAll();
        model.addAttribute("productColor", productColors);
        List<Product_Images> productImg = productImgDAO.findAll();
        model.addAttribute("productImg", productImg);
        model.addAttribute("categories", categories);
        model.addAttribute("productRequest", new ProductDTO());
        model.addAttribute("type", type);
        if (type == null || type.isEmpty()) {
            model.addAttribute("product", products);
        } else {
            if (type.equals("db")) {
                model.addAttribute("product", products);
            } else {
                if (type.equals("nb")) {
                    model.addAttribute("product", productsDepFalse);
                }
            }

        }
        return "/admin/product";
    }

    @RequestMapping(value = "/listBooks", method = RequestMethod.GET)
    public String listBooks(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Products> productpage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("productpage", productpage);

        int totalPages = productpage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "/admin/product";
    }
    @GetMapping("/updateStatusProduct")
    public String Updatequery(@RequestParam("productID") String productID, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteLogical(productID);
            redirectAttributes.addFlashAttribute("message", "Chuyển thành công sản phẩm " + productID + "Sang trạng thái ngưng bán");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Chuyển thất bại sản phẩm " + productID + "Sang trạng thái ngưng bán");
        }
        redirectAttributes.addFlashAttribute("status", "done_delete");

        return "redirect:/admin/product";
    }

    @GetMapping("/delete")
    public String Delete(@RequestParam("productID") String ProductID, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(ProductID);
            redirectAttributes.addFlashAttribute("message", "Xóa thành công " + ProductID);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Xóa thất bại " + ProductID);
            e.printStackTrace();
        }
        return "redirect:/admin/product";
    }

    @PostMapping("/create")
    public String doPostCreateProduct(@ModelAttribute("productRequest") ProductDTO productReq, RedirectAttributes redirectAttributes) {
        Products products;
        String img;
        Path path = Paths.get("product/");
        try {

            InputStream inputStream = productReq.getImg().getInputStream();
            img = productReq.getImg().getOriginalFilename();
            if (img == null) {
                img = "logo.png";
            }
            Files.copy(inputStream, path.resolve(img), StandardCopyOption.REPLACE_EXISTING);
            products = customConfiguration.modelMapper().map(productReq, Products.class);
            products.setImg(img);
            productService.save(products);
            redirectAttributes.addFlashAttribute("message", "Thêm mới thành công sản phẩm " + productReq.getName());
        } catch (Exception e) {
            System.out.println(e);
            redirectAttributes.addFlashAttribute("error", "Thêm mới thất bại sản phẩm " + productReq.getName());
        }
        return "redirect:/admin/product";
    }

    @PostMapping("/update")
    public String doPostUpdateProduct(@ModelAttribute("productRequest") ProductDTO productReq, RedirectAttributes redirectAttributes) {
        Optional<Products> product = productService.findByID(productReq.getProductID());
        Products products = null;
        String img;
        Path path = Paths.get("product/");
        if (productReq.getImg().isEmpty()) {
            try {
                img = product.get().getImg();
                products = customConfiguration.modelMapper().map(productReq, Products.class);
                products.setImg(img);
                productService.save(products);
                redirectAttributes.addFlashAttribute("message", "Cập nhật thành công " + productReq.getName());
            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("message", "Cập nhật thất bại " + productReq.getName());
            }
        } else {
            img = productReq.getImg().getOriginalFilename();
            try {
                if (img == null) {
                    img = "logo.png";
                }
                InputStream inputStream = productReq.getImg().getInputStream();
                Files.copy(inputStream, path.resolve(img), StandardCopyOption.REPLACE_EXISTING);
                products = customConfiguration.modelMapper().map(productReq, Products.class);
                products.setImg(img);
                productService.save(products);
                redirectAttributes.addFlashAttribute("Thành công", "Cập nhật thành công " + productReq.getName());

            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("Lỗi", "Cập nhật thất bại " + productReq.getName());
            }

        }

        return "redirect:/admin/product";
    }

    @GetMapping("/edit/{id}")
    public String doPostUpdateProduct(@PathVariable("id") String id, ModelMap model) {
        Optional<Products> prd = productService.findByID(id);
        List<Products> products1 = productService.findDeprecatedTrue();
        List<Categories> categories = categoryDAO.findAll();
        List<Products> productsDepFalse = productService.findDeprecatedFalse();
        model.addAttribute("productsDepFalse", productsDepFalse);
        ProductDTO dto = null;
        if (prd.isPresent()) {
            Products products = prd.get();
            model.addAttribute("imgName", products.getImg());
            dto = customConfiguration.modelMapper().map(products, ProductDTO.class);
        } else {
            model.addAttribute("productRequest", new ProductDTO());
        }
        //fill dữ liệu lên combobox trong table
        model.addAttribute("categories", categories);
        //fill dữ liệu lên table
        model.addAttribute("product", products1);
        model.addAttribute("productRequest", new ProductDTO());
        model.addAttribute("handle", "handle");
        model.addAttribute("productRequest", dto);
        return "/admin/product";
    }
}
