package ru.fortushin.EffectiveMobilestore.controller;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fortushin.EffectiveMobilestore.dto.GoodsDTO;
import ru.fortushin.EffectiveMobilestore.dto.UserDTO;
import ru.fortushin.EffectiveMobilestore.model.*;
import ru.fortushin.EffectiveMobilestore.repository.*;
import ru.fortushin.EffectiveMobilestore.service.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final GoodsService goodsService;
    private final DiscountService discountService;
    private final UserService userService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final CompanyRegistrationApplicationRepository companyRegistrationApplicationRepository;
    private final CompanyRegistrationApplicationService companyRegistrationApplicationService;
    private final GoodsRegistrationApplicationService goodsRegistrationApplicationService;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(GoodsService goodsService, DiscountService discountService, UserService userService,
                           NotificationService notificationService,
                           UserRepository userRepository,
                           GoodsRepository goodsRepository,
                           PurchaseHistoryRepository purchaseHistoryRepository,
                           CompanyRegistrationApplicationRepository companyRegistrationApplicationRepository,
                           CompanyRegistrationApplicationService companyRegistrationApplicationService,
                           GoodsRegistrationApplicationService goodsRegistrationApplicationService, CompanyService companyService,
                           CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.goodsService = goodsService;
        this.discountService = discountService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
        this.goodsRepository = goodsRepository;
        this.purchaseHistoryRepository = purchaseHistoryRepository;
        this.companyRegistrationApplicationRepository = companyRegistrationApplicationRepository;
        this.companyRegistrationApplicationService = companyRegistrationApplicationService;
        this.goodsRegistrationApplicationService = goodsRegistrationApplicationService;
        this.companyService = companyService;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;

    }

    @GetMapping("/goods")
    public ResponseEntity<List<GoodsDTO>> getGoodsList(){
        List<GoodsDTO> goodsDTOList = new ArrayList<>();
        for(Goods goods : goodsRepository.findAll()){
            goodsDTOList.add(modelMapper.map(goods, GoodsDTO.class));
        }
        return ResponseEntity.ok(goodsDTOList);
    }

    @GetMapping("/goods/{goodsId}")
    public ResponseEntity<GoodsDTO> getGoods(@PathVariable("goodsId") int goodsId){
        try{
            Goods goods = goodsService.get(goodsId);
            return ResponseEntity.ok(modelMapper.map(goods, GoodsDTO.class));
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("goods/{goodsId}/goods-update")
    public ResponseEntity<String> updateGoods(@PathVariable("goodsId") int goodsId,
                                              @RequestBody Goods goods){
        goodsService.update(goods, goodsId);
        return ResponseEntity.status(HttpStatus.OK).body("Update goods success");
    }

    @GetMapping("discount/{discountId}/discount-update")
    public ResponseEntity<String> updateDiscount(@PathVariable("discountId") int discountId,
                                                 @RequestBody Discount discount,
                                                 @RequestBody List<Goods> goodsList){
        for(Goods goods : goodsList){
            discount.getGoodsList().add(goods);
            goods.getDiscountList().add(discount);
            goodsService.update(goods, goods.getId());
        }

        discountService.update(discount, discountId);
        return ResponseEntity.status(HttpStatus.OK).body("Update discount success");
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUserList(){
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user : userRepository.findAll()){
            userDTOList.add(modelMapper.map(user, UserDTO.class));
        }
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") int userId){
        try{
            User user = userService.get(userId);
            return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/{userId}/notification-send")
    public ResponseEntity<String> sendNotification(@PathVariable("userId") int userId,
                                                   @RequestBody Notification notification){
        try{
            notification.getUserList().add(userService.get(userId));
            notificationService.create(notification);
            return ResponseEntity.status(HttpStatus.OK).body("Notification send success");
        }catch (EntityNotFoundException e){
            return handleException(e);
        }

    }

    @GetMapping("/users/notification-send")
    public ResponseEntity<String> sendNotificationToListOfUsers(@RequestBody List<Notification> notificationList){
        try{
            for(User user : userRepository.findAllByNotificationListIn(notificationList)){
                user.getNotificationList().addAll(notificationList);
            }
            notificationList.forEach(notificationService::create);

            return ResponseEntity.status(HttpStatus.OK).body("Notification send success");
        }catch (EntityNotFoundException e){
            return handleException(e);
        }

    }

    @GetMapping("/users/{userId}/balance-update")
    public ResponseEntity<String> updateUserBalance(@PathVariable("userId") int userId, @RequestParam("balance") double balance){
        try{
            User user = userService.get(userId);
            user.updateBalance(balance);
            userService.update(user, userId);
            return ResponseEntity.status(HttpStatus.OK).body("Balance update success");
        }catch (EntityNotFoundException e){
            return handleException(e);
        }

    }
    @GetMapping("/users/{userId}/account-delete")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") int userId){
        try{
            User user = userService.get(userId);
            userService.delete(user);
            return ResponseEntity.status(HttpStatus.OK).body("User delete success");
        }catch (EntityNotFoundException e){
            return handleException(e);
        }
    }

    @GetMapping("/users/{userId}/account-disable")
    public ResponseEntity<String> disableUserAccount(@PathVariable("userId") int userId){
        try {
            User user = userService.get(userId);
            user.setAccountEnabled(false);
            userService.update(user, userId);
            return ResponseEntity.status(HttpStatus.OK).body("Account disable success");
        }catch (EntityNotFoundException e){
                return handleException(e);
        }
    }

    @GetMapping("/users/{userId}/account-enable")
    public ResponseEntity<String> enableUserAccount(@PathVariable("userId") int userId){
        try {
            User user = userService.get(userId);
            user.setAccountEnabled(true);
            userService.update(user, userId);
            return ResponseEntity.status(HttpStatus.OK).body("Account enable success");
        }catch (EntityNotFoundException e){
            return handleException(e);
        }
    }

    @GetMapping("/users/{userId}/purchase-history")
    public ResponseEntity<PurchaseHistory> getPurchaseHistory(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(purchaseHistoryRepository.getPurchaseHistoryByUserId(userId));
    }

    @GetMapping("/company-register-application")
    public ResponseEntity<List<CompanyRegistrationApplication>> getCompanyRegistrationApplications() {
        return ResponseEntity.ok(companyRegistrationApplicationRepository.findAll());
    }

    @GetMapping("/company-register-application/{applicationId}/approve")
    public ResponseEntity<String> approveCompanyRegistrationApplication(@PathVariable("applicationId") int applicationId) {
        CompanyRegistrationApplication companyRegistrationApplication =
                companyRegistrationApplicationService.get(applicationId);
        companyRegistrationApplication.setApproved(true);
        companyRegistrationApplicationService.update(companyRegistrationApplication, companyRegistrationApplication.getId());
        Company company = companyRepository.getCompanyByCompanyRegistrationApplicationId(applicationId);
        company.setEnabled(true);
        companyService.update(company, company.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Registration application approval success");
    }

    @GetMapping("/company-register-application/{applicationId}/deny")
    public ResponseEntity<String> denyCompanyRegistrationApplication(@PathVariable("applicationId") int applicationId) {
        CompanyRegistrationApplication companyRegistrationApplication =
                companyRegistrationApplicationService.get(applicationId);
        companyRegistrationApplication.setApproved(false);
        companyRegistrationApplicationService.update(companyRegistrationApplication, companyRegistrationApplication.getId());
        Company company = companyRepository.getCompanyByCompanyRegistrationApplicationId(applicationId);
        company.setEnabled(false);
        companyService.update(company, company.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Registration application denial success");
    }

    @GetMapping("/{companyId}/disable")
    public ResponseEntity<String> disableCompany(@PathVariable("companyId") int companyId){
        Company company = companyService.get(companyId);
        company.setEnabled(false);
        companyService.update(company, company.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Company disable success");
    }

    @GetMapping("/{companyId}/enable")
    public ResponseEntity<String> enableCompany(@PathVariable("companyId") int companyId){
        Company company = companyService.get(companyId);
        company.setEnabled(true);
        companyService.update(company, company.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Company enable success");
    }

    @GetMapping("/{companyId}/delete")
    public ResponseEntity<String> deleteCompany(@PathVariable("companyId") int companyId){
        Company company = companyService.get(companyId);
        companyService.delete(company);
        return ResponseEntity.status(HttpStatus.OK).body("Company removal success");
    }

    @GetMapping("/goods-register-application/{applicationId}/approve")
    public ResponseEntity<String> approveGoodsRegistrationApplication(@PathVariable("applicationId") int applicationId) {
        GoodsRegistrationApplication goodsRegistrationApplication =
                goodsRegistrationApplicationService.get(applicationId);
        goodsRegistrationApplication.setApproved(true);

        return ResponseEntity.status(HttpStatus.OK).body("Registration application approval success");
    }

    @GetMapping("/goods-register-application/{applicationId}/deny")
    public ResponseEntity<String> denyGoodsRegistrationApplication(@PathVariable("applicationId") int applicationId) {
        GoodsRegistrationApplication goodsRegistrationApplication =
                goodsRegistrationApplicationService.get(applicationId);
        goodsRegistrationApplication.setApproved(false);

        return ResponseEntity.status(HttpStatus.OK).body("Registration application denial success");
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred: " + e.getMessage());
    }



}
