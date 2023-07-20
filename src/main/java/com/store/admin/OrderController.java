package com.store.admin;

import com.store.dao.OrderDAO;
import com.store.dao.OrderDetailDAO;
import com.store.model.Order_Details;
import com.store.model.Orders;
import com.store.model.Products;
import com.store.model.Users;
import com.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @RequestMapping("")
    public String adminUser(Model model,@RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("dislabled", "pagination dislabled");
        model.addAttribute("active","active pagination");
        List<Orders> list = orderDAO.findAll();
        Page<Orders> ordersPage = orderService.findPaginatedOrder(PageRequest.of(currentPage - 1, pageSize), list);
        model.addAttribute("Orders", ordersPage);
        List<List> listOrderDetails = new ArrayList<>();
        ordersPage.forEach( item -> {
            System.out.println(item.getOrderID());
           List<Order_Details> ord = orderDetailDAO.findByOrder_OrderID(item.getOrderID());
            listOrderDetails.add(ord);
        });
        model.addAttribute("listOrderDetails", listOrderDetails);
        int totalPages = ordersPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "/admin/order/order";
    }
}
