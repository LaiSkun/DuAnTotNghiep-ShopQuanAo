package com.store.admin;

import com.store.DTO.ProductDTO;
import com.store.DTO.ProductImgDTO;
import com.store.configs.CustomConfiguration;
import com.store.dao.*;
import com.store.model.*;
import com.store.service.ProductColorsService;
import com.store.service.ProductImgService;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    ColorsDAO colorsDAO;
    @Autowired
    ProductImgDAO productImgDAO;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryDAO categoryDAO;
    @Autowired
    CustomConfiguration customConfiguration;
    @Autowired
    ProductDAO productDAO;
    @Autowired
    ProductImgService productImgService;
    @Autowired
    ProductColorsService productColorsService;
    @RequestMapping("")
    public String adminProduct(Model model, String type, ProductDTO productRequest, @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,@RequestParam("page2") Optional<Integer> page2,
                               @RequestParam("size2") Optional<Integer> size2){
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(5);
        int currentPage2 = page2.orElse(1);
        int pageSize2 = size2.orElse(5);
            if (productRequest == null) {
                productRequest = new ProductDTO();
            }
            List<Products> products = productService.findDeprecatedTrue();
            List<Products> productsDepFalse = productService.findDeprecatedFalse();
            List<Categories> categories = categoryDAO.findAll();
            List<Product_Colors> productColors = productColorsService.findAll();
            List<Product_Images> productImg = productImgService.findAll();
            List<Products> productAll = productDAO.findAll();
            List<Colors> colorList = colorsDAO.findAll();
            model.addAttribute("disabled", "disabled");
            model.addAttribute("productColor", productColors);
            model.addAttribute("currentPage", currentPage);
        model.addAttribute("currentPage2", currentPage2);
            model.addAttribute("categories", categories);
            model.addAttribute("productRequest", productRequest);
            model.addAttribute("productImgRequest", new ProductImgDTO());
            model.addAttribute("type", type);
            model.addAttribute("colorList", colorList);
            model.addAttribute("productAll", productAll);
        Page<Products> productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize), productAll);
        Page<Product_Images> productImgPage = productImgService.findPaginated(PageRequest.of(currentPage2 - 1, pageSize2),productImg);
        model.addAttribute("productImg", productImgPage);
        if (type == null || type.isEmpty()) {
                model.addAttribute("url", "/admin/product");
                productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize), productAll);
                model.addAttribute("productPage", productPage);
            } else {
                if (type.equals("db")) {
                    productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize), products);
                    model.addAttribute("productPage", productPage);
                    model.addAttribute("url", "/admin/product?type=db");

                } else {
                    if (type.equals("nb")) {
                         productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize), productsDepFalse);
                        model.addAttribute("productPage", productPage);
                        model.addAttribute("url", "/admin/product?type=nb");

                    } else {
                        if (type.equals("category")) {
                            List<String> cate = productRequest.listProductByCategory;
                            List<Categories> categoryIds = cate
                                    .parallelStream().map(item -> {
                                        Categories cat = new Categories();
                                        cat.setCategoryID(item);
                                        return cat;
                                    }).collect(Collectors.toList());
                            List<Products> productscategory = productDAO.findByCategoryIn(categoryIds);
                            model.addAttribute("showFormFilter", "showFormFilter");
                            model.addAttribute("formProductByCategory", "formProductByCategory col-6");
                            if (productscategory == null || productscategory.isEmpty()) {
                                productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize), productAll);
                                model.addAttribute("productPage", productPage);
                                model.addAttribute("url", "/admin/product");

                            } else {
                                productPage = productService.findPaginated(PageRequest.of(currentPage - 1, pageSize), productscategory);
                                model.addAttribute("productPage", productPage);
                                model.addAttribute("url", "/admin/product?type=category");
                            }
                        }
                    }
                }
            }
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        int totalPagesImg = productImgPage.getTotalPages();
        if (totalPagesImg > 0) {
            List<Integer> pageNumbers2 = IntStream.rangeClosed(1, totalPagesImg)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers2", pageNumbers2);
        }
            return "/admin/product/index";
        }

        @GetMapping("/updateStatusProduct")
        public String Updatequery (@RequestParam("productID") String productID, RedirectAttributes redirectAttributes){
            Optional<Products> product = productService.findByID(productID);
            if (product.get().isDeprecated()) {
                try {
                    productService.deleteProduct(productID);
                    redirectAttributes.addFlashAttribute("message", "Xóa thành công " + productID);

                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("message", "Xóa thất bại " + productID);
                    e.printStackTrace();
                }
            } else {
                try {
                    productService.deleteLogical(productID);
                    redirectAttributes.addFlashAttribute("message", "Chuyển thành công sản phẩm " + productID + " sang trạng thái ngưng bán");
                } catch (Exception e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("message", "Chuyển thất bại sản phẩm " + productID + " sang trạng thái ngưng bán");
                }
            }
            redirectAttributes.addFlashAttribute("status", "done_delete");
            return "redirect:/admin/product";
        }

        @GetMapping("/deleteProductImgAndColor")
        public String Updatequery ( @RequestParam("productImgId") long imgId, RedirectAttributes redirectAttributes){
            Optional<Product_Images> productImg = productImgService.findById(imgId);
            Optional<Product_Colors> productColor = productColorsService.findByID(productImg.get().getProductcolor().getColorID());

            try {
                productImgService.deleteImg(imgId);
                productColorsService.deleteColor(productColor.get().getColorID());
                Optional<Products> product = productService.findByID(productColor.get().getProduct().getProductID());
                redirectAttributes.addFlashAttribute("message", "Xóa thành công ảnh " + productImg.get().getImage() + " màu " + productColor.get().getColor_name() + " sản phẩm" + product.get().getName());

            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", "Xóa thất bại ");
                e.printStackTrace();
            }

            redirectAttributes.addFlashAttribute("status", "done_delete");
            return "redirect:/admin/product";
        }

        @GetMapping("/updateStatusTrue")
        public String UpdateStatusTrue (@RequestParam("productID") String productID, RedirectAttributes
        redirectAttributes){
            try {
                productService.updateStatusTrue(productID);
                redirectAttributes.addFlashAttribute("message", "Chuyển thành công sản phẩm " + productID + " sang trạng thái đang bán");
            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("message", "Chuyển thất bại sản phẩm " + productID + " sang trạng thái đang bán");
            }
            redirectAttributes.addFlashAttribute("status", "done_delete");
            return "redirect:/admin/product";
        }

        @PostMapping("/create")
        public String doPostCreateProduct (@ModelAttribute("productRequest") ProductDTO productReq, RedirectAttributes
        redirectAttributes){
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
            redirectAttributes.addFlashAttribute("status", "done_delete");
            return "redirect:/admin/product";
        }

        @PostMapping("/createImgProduct")
        public String doPostCreateImgProduct (@ModelAttribute("productImgRequest") ProductImgDTO
        productImgDTO, RedirectAttributes redirectAttributes, Model model){
            Product_Colors productColors;
            Product_Images productImages;
            String img;
            Path path = Paths.get("product/");
            Optional<Product_Colors> productColorsServiceByproductIDAndColorHex = productColorsService.findByproductIDAndColorHex(productImgDTO.getProduct(), productImgDTO.getColorHex());
            if (productColorsServiceByproductIDAndColorHex.isPresent()) {

                redirectAttributes.addFlashAttribute("message", "Sản phẩm có mã màu này đã tồn tại. ");
            } else {
                try {
                    InputStream inputStream = productImgDTO.getImg().getInputStream();
                    img = productImgDTO.getImg().getOriginalFilename();
                    if (img == null) {
                        img = "logo.png";
                    }
                    Files.copy(inputStream, path.resolve(img), StandardCopyOption.REPLACE_EXISTING);
                    Colors color = colorsDAO.findByRGBColor(productImgDTO.getColorHex());
                    productColors = new Product_Colors();
                    productColors.setColorhex(color.getRGBColor());
                    productColors.setColor_name(color.getNameColor());
                    productColors.setProduct(productImgDTO.getProduct());
                    productColorsService.save(productColors);
                    productImages = new Product_Images(img, productColors);
                    productImgService.save(productImages);
                    redirectAttributes.addFlashAttribute("message", "Thêm mới thành công sản phẩm ");
                } catch (Exception e) {
                    System.out.println(e);
                    redirectAttributes.addFlashAttribute("message", "Thêm mới thất bại sản phẩm ");
                }
            }
            model.addAttribute("table2", "table2");
            model.addAttribute("table1", "handletable records table-responsive table1");
            redirectAttributes.addFlashAttribute("status", "done_delete");
            return "redirect:/admin/product";
        }

        @PostMapping("/update")
        public String doPostUpdateProduct (@ModelAttribute("productRequest") ProductDTO productReq, RedirectAttributes
        redirectAttributes){
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
            redirectAttributes.addFlashAttribute("status", "done_delete");
            return "redirect:/admin/product";
        }

        @PostMapping("/updateProductImgAndColor")
        public String updateProductImgAndColor (@ModelAttribute("productImgRequest") ProductImgDTO
        productReq, RedirectAttributes redirectAttributes){
            Optional<Product_Colors> productColor = productColorsService.findByID(productReq.getId());
            Optional<Product_Images> productImages = productImgService.findById(productReq.getImgid());
            Product_Images productimage = new Product_Images();
            Product_Colors productColors = new Product_Colors();
            Colors color = colorsDAO.findByRGBColor(productReq.getColorHex());
            String img;
            Path path = Paths.get("product/");
            if (productReq.getImg().isEmpty()) {
                try {
                    img = productImages.get().getImage();
                    productimage.setImgID(productImages.get().getImgID());
                    productimage.setImage(img);
                    productimage.setProductcolor(productColor.get());
                    productColors.setColorID(productImages.get().getProductcolor().getColorID());
                    productColors.setProduct(productReq.getProduct());
                    productColors.setColor_name(color.getNameColor());
                    productColors.setColorhex(productReq.getColorHex());
                    productImgService.save(productimage);
                    productColorsService.save(productColors);
                    redirectAttributes.addFlashAttribute("message", "Cập nhật thành công " + productReq.getProduct().getProductID());
                } catch (Exception e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("message", "Cập nhật thất bại " + productReq.getProduct().getProductID());
                }
            } else {
                img = productReq.getImg().getOriginalFilename();
                try {
                    if (img == null) {
                        img = "logo.png";
                    }
                    InputStream inputStream = productReq.getImg().getInputStream();
                    Files.copy(inputStream, path.resolve(img), StandardCopyOption.REPLACE_EXISTING);
                    productimage.setImgID(productImages.get().getImgID());
                    productimage.setImage(img);
                    productimage.setProductcolor(productColor.get());
                    productColors.setColorID(productImages.get().getProductcolor().getColorID());
                    productColors.setProduct(productReq.getProduct());
                    productColors.setColor_name(color.getNameColor());
                    productColors.setColorhex(productReq.getColorHex());
                    productImgService.save(productimage);
                    productColorsService.save(productColors);
                    redirectAttributes.addFlashAttribute("message", "Cập nhật thành công " + productReq.getProduct().getProductID());

                } catch (Exception e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("message", "Cập nhật thất bại " + productReq.getProduct().getProductID());
                }

            }
            redirectAttributes.addFlashAttribute("status", "done_delete");
            return "redirect:/admin/product";
        }

        @RequestMapping("/edit/{id}")
        public String UpdateProduct (@PathVariable("id") String id, ModelMap model){
            Optional<Products> prd = productService.findByID(id);
            List<Products> products1 = productDAO.findAll();
            List<Categories> categories = categoryDAO.findAll();
            List<Products> productsDepFalse = productService.findDeprecatedFalse();
            List<Colors> colorList = colorsDAO.findAll();
            model.addAttribute("productsDepFalse", products1);
            model.addAttribute("productImgRequest", new ProductImgDTO());
            model.addAttribute("handleUpdate", "display : block");
            model.addAttribute("colorList", colorList);
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
            model.addAttribute("handle", "handle");
            if (dto.isDeprecated()) {

            } else {
                model.addAttribute("productRequest", dto);
            }
            return "/admin/product/index";
        }

        @RequestMapping("/productImgEdit/{id}")
        public String UpdateProductImgAndColor (@PathVariable("id") Long id, ModelMap model){
            // productImg {namne img, idcolor, imgid }
            model.addAttribute("imgName", "ok.jpg");
            model.addAttribute("productRequest", new ProductDTO());
            //productColor {colorID, colorhex., color_name, productID}
            Optional<Product_Colors> productColor = productColorsService.findByID(id);
            List<Product_Images> productImgAll = productImgService.findAll();
            Optional<Product_Images> productImg = productImgService.findByProductcolor(productColor.get());
//            model.addAttribute("productImg", productImgAll);
            List<Products> products1 = productDAO.findAll();
            List<Categories> categories = categoryDAO.findAll();
//        List<Products> productsDepFalse = productService.findDeprecatedFalse();
            List<Colors> colorList = colorsDAO.findAll();
            model.addAttribute("productsDepFalse", products1);
            List<Products> productAll = productDAO.findAll();
            model.addAttribute("productAll", productAll);
            model.addAttribute("table2", "table2");
            model.addAttribute("updateImgProduct", "btn updateImgProduct");
            model.addAttribute("table1", "handletable records table-responsive table1");
            model.addAttribute("handle2", "handle2");
            model.addAttribute("productImgRequest", new ProductImgDTO());
            model.addAttribute("handleUpdate", "display : block");
            model.addAttribute("colorList", colorList);
            ProductImgDTO dto = new ProductImgDTO();
            if (productImg.isPresent()) {
                Product_Images products = productImg.get();
                model.addAttribute("imgName2", products.getImage());
                dto.setColorHex(productColor.get().getColorhex());
                dto.setId(productColor.get().getColorID());
                dto.setColor(productColor.get().getColor_name());
                dto.setProduct(productColor.get().getProduct());
                dto.setImgid(products.getImgID());
            } else {
                model.addAttribute("productImgRequest", new ProductImgDTO());
            }
            //fill dữ liệu lên combobox trong table
            model.addAttribute("categories", categories);
            //fill dữ liệu lên table
            model.addAttribute("product", products1);

            model.addAttribute("productImgRequest", dto);

            return "/admin/product/index";
        }
    }
