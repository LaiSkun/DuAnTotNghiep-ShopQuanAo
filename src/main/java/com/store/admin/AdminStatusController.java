package com.store.admin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.store.dao.*;
import com.store.model.staff;
import com.store.constant.SessionConstant;
import com.store.service.OrderService;
import com.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.store.model.staff;
import com.store.model.Authorities;
import com.store.model.Roles;
import com.store.model.Status;
import com.store.model.Users;
import com.store.service.StatusService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.store.model.descriptionStatus;

@CrossOrigin("*")
@Controller
public class AdminStatusController {
    @Autowired
    StatusService statusService;
    @Autowired
    staffDAO staffDAO;
    @Autowired
    descriptionStatusDAO descriptionStatusDAO;
    @Autowired
    StatusNameDAO statusNameDAO;
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    StatusDAO statusDAO;
    @Autowired
    UserService userService;
    @GetMapping("/admin/status")
    public String adminStatus(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page, String type,
                              @RequestParam(defaultValue = "5") int size) {
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute(SessionConstant.CURRENT_USER);
        staff st = staffDAO.findByStaffID(currentUser);
        List<Authorities>  lsAuthor = userService.findById(currentUser.getUserID()).getAuthorities();
        List<String>  ls = new ArrayList<>();
        lsAuthor.forEach(item -> {
            if (item.getRole().getRoleID().equals("admin")){
             ls.add("1");
            }
        });
        if (type == null) {
            type = "";
        }
        List<Status> status = new ArrayList<>();
       // đơn chờ xử lý
        int orderProcessing ;
        //đơn đã xong;
        int StatusDone ;
        //đơn đang làm
        int AllStatusByStaff ;
        // đơn đã bị hủy
        int sttFalse;
        if (!ls.isEmpty()){
            switch (type.trim()) {
                case "T1":
                    //chowf xac nhan
                   status = statusService.findAllDesc(1);
                    break;
                case "T4":
                    // bi huy
                    status = statusService.findAllDesc( 4);
                    break;
                case "T5":
                    //da xong
                    status = statusService.findAllDesc( 5);
                    break;
                case "T23":
                    //dang xu ly
                    status = statusService.findAllDesc2(2,3);
                    break;
                default:
                    status = statusService.findAll();
                    break;

            }
             orderProcessing = statusDAO.selectCountStatusNameAll();
             StatusDone = statusDAO.findStatusByStaffDoneAll();
            AllStatusByStaff = statusDAO.findAllStatusByAll();
            sttFalse = statusDAO.findStatusByStaffFalseAll();
        } else {
            switch (type.trim()) {
                case "T1":
                    //chowf xac nhan
                    status = statusService.findByStatusAndDesc(st,1);
                    break;
                case "T4":
                    // bi huy
                    status = statusService.findByStatusAndDesc(st, 4);
                    break;
                case "T5":
                    //da xong
                    status = statusService.findByStatusAndDesc(st, 5);
                    break;
                case "T23":
                    //dang xu ly
                    status = statusService.findByStatusAndDesc1(st,2,3);

                    break;

                default:
                    status = statusService.findByCurrentStaff(st);
                    break;

            }
             orderProcessing = statusDAO.selectCountStatusName(st);
             StatusDone = statusDAO.findStatusByStaffDone(st);
            AllStatusByStaff = statusDAO.findAllStatusByAll();
            sttFalse = statusDAO.findStatusByStaffFalse(st);
            if (orderProcessing > 0) {
                model.addAttribute("orderProcessing", "Có " + orderProcessing + " đơn hàng đang chờ bạn xử lý");
                model.addAttribute("messStatus", "done_delete");
            }
        }
        int totalItems = status.size();
        int totalPages = (int) Math.ceil(totalItems / (double) size);
        int startItem = page * size + 1;
        int endItem = Math.min((page + 1) * size, totalItems);

        model.addAttribute("desc", descriptionStatusDAO.findAll());
        model.addAttribute("statusName", statusNameDAO.findAll());
        List<Status> statusList = status.subList(page * size, endItem);
        model.addAttribute("statuss", statusList);
        model.addAttribute("status", new Status());
        model.addAttribute("currentPage", page + 1);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("type", type.trim());
        model.addAttribute("startItem", startItem);
        model.addAttribute("endItem", endItem);
        model.addAttribute("AllStatusByStaff", AllStatusByStaff);
        model.addAttribute("OrderFalse", sttFalse + " Đơn hàng");
        model.addAttribute("StatusDone", StatusDone + " Đơn hàng");
        model.addAttribute("statusOrderProcessing", AllStatusByStaff - StatusDone - orderProcessing - sttFalse + " Đơn hàng");
        model.addAttribute("ordPro", orderProcessing + " Đơn hàng");
        model.addAttribute("mss",orderProcessing);

        // tổng đơn hàng--- đơn đang xử lý --- đang xử lý xong--- ca trực

        return "/admin/status/status";

    }

    @PostMapping("/admin/status/update")
    public String updateStatus(@ModelAttribute("status") Status status) {
        java.util.Date date = new java.util.Date();
        Status currentStatus = new Status();

        Status PrevStatus = statusService.findByOrderID(orderDAO.findByOrderID(status.getStatusID()));
        if (!status.getNotes().isEmpty()) {
            PrevStatus.setNotes(status.getNotes());
        }
        if (!status.getReason().isEmpty()) {
            PrevStatus.setReason(status.getReason());
        }
        PrevStatus.setDescription(status.getDescription());
        statusService.updateStatus(PrevStatus);
        if (status.getDescription().getDescriptionID() == 5 || status.getDescription().getDescriptionID() == 4) {
            staff staff = staffDAO.findByStaffID(PrevStatus.getStaffID().getStaffID());
            staff.setOrderProcessing(statusDAO.findAllStatusByStaff(staff) - statusDAO.findStatusByStaffDone(staff) + statusDAO.findStatusByStaffFalse(staff));
            // trangj thái hoàn thành gồm 2 th là đơn đã xong hoặc đơn đã bị hủy
            staff.setDoneProcessing(statusDAO.findStatusByStaffDone(staff) + statusDAO.findStatusByStaffFalse(staff));
            staffDAO.save(staff);
        }

        //
        //createDate lấy ngày hiện tại. update description.
        //transportfee fecollected
        return "redirect:/admin/status";
    }
}
