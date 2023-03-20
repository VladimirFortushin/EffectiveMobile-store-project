package ru.fortushin.EffectiveMobilestore.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.fortushin.EffectiveMobilestore.dto.GoodsDTO;
import ru.fortushin.EffectiveMobilestore.dto.NotificationDTO;
import ru.fortushin.EffectiveMobilestore.dto.UserDTO;
import ru.fortushin.EffectiveMobilestore.model.*;
import ru.fortushin.EffectiveMobilestore.repository.*;
import ru.fortushin.EffectiveMobilestore.service.*;
import ru.fortushin.EffectiveMobilestore.util.FeedBackAndRatingForbidden;
import ru.fortushin.EffectiveMobilestore.util.InsufficientFundsException;
import ru.fortushin.EffectiveMobilestore.util.RefundDateExpired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final PurchaseHistoryService purchaseHistoryService;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final FeedBackService feedBackService;
    private final GoodsService goodsService;
    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;
    private final NotificationRepository notificationRepository;
    private final CompanyRegistrationApplicationService companyRegistrationApplicationService;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final GoodsRegistrationApplicationService goodsRegistrationApplicationService;
    private final GoodsRegistrationApplicationRepository goodsRegistrationApplicationRepository;

    @Autowired
    public UserController(PurchaseHistoryService purchaseHistoryService, PurchaseHistoryRepository purchaseHistoryRepository,
                          UserService userService, ModelMapper modelMapper, FeedBackService feedBackService, GoodsService goodsService,
                          UserRepository userRepository,
                          GoodsRepository goodsRepository,
                          NotificationRepository notificationRepository,
                          CompanyRegistrationApplicationService companyRegistrationApplicationService, CompanyService companyService,
                          CompanyRepository companyRepository, GoodsRegistrationApplicationService goodsRegistrationApplicationService,
                          GoodsRegistrationApplicationRepository goodsRegistrationApplicationRepository) {
        this.purchaseHistoryService = purchaseHistoryService;
        this.purchaseHistoryRepository = purchaseHistoryRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.feedBackService = feedBackService;
        this.goodsService = goodsService;
        this.userRepository = userRepository;
        this.goodsRepository = goodsRepository;
        this.notificationRepository = notificationRepository;
        this.companyRegistrationApplicationService = companyRegistrationApplicationService;
        this.companyService = companyService;
        this.companyRepository = companyRepository;
        this.goodsRegistrationApplicationService = goodsRegistrationApplicationService;
        this.goodsRegistrationApplicationRepository = goodsRegistrationApplicationRepository;
    }

    @GetMapping("/goods-list")
    public ResponseEntity<List<GoodsDTO>> getGoodsList(){
        List<GoodsDTO> goodsListForShow = new ArrayList<>();
        List<Company> companyList = companyRepository.getAllByEnabled(true);
        for(Company company : companyList){
            for(Goods goods : company.getGoodsList()){
                if(goods.getGoodsRegistrationApplication().isApproved()){
                    goodsListForShow.add(modelMapper.map(goods, GoodsDTO.class));
                }
            }
        }
        return ResponseEntity.ok(goodsListForShow);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") int userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()){
            User user = userService.get(userId);
            return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/{userId}/notification")
    public ResponseEntity<List<NotificationDTO>> getNotificationList(@PathVariable("userId") int userId) {
        ResponseEntity<List<NotificationDTO>> responseEntity = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userRepository.findUserByUserName(currentUserName).orElse(null);
        if (currentUser != null) {
            if (currentUser.getId() != userId) {
                responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);

            } else {
                List<NotificationDTO> list = new ArrayList<>();
                for(Notification notification : notificationRepository.findAllByUserId(userId)){
                    list.add(modelMapper.map(notification, NotificationDTO.class));
                }
                responseEntity = ResponseEntity.ok(list);
            }
        }
        return responseEntity;
    }


    @GetMapping("/{userId}/{goodsId}/buy")
    public ResponseEntity<String> purchase(@PathVariable("userId") int userId,
                                           @PathVariable("goodsId") int goodsId){
        PurchaseHistory purchaseHistory = purchaseHistoryRepository.getPurchaseHistoryByUserId(userId);
        User user = userService.get(userId);
        Goods purchasedGoods = goodsService.get(goodsId);
        User beneficiary = userService.get(purchasedGoods.getUser().getId());

        if(user.getBalance() - purchasedGoods.getPrice() < 0.0){
            return handleException(new InsufficientFundsException("Insufficient funds"));
        }else{
            purchaseHistory.setPurchaseTime(LocalDateTime.now());
            purchaseHistory.getGoodsList().add(purchasedGoods);
            user.updateBalance(purchasedGoods.getPrice() * (-1.0));
            beneficiary.updateBalance(purchasedGoods.getPrice());
            userService.update(user, userId);
            userService.update(beneficiary, beneficiary.getId());
            purchaseHistoryService.update(purchaseHistory, purchaseHistory.getId());
            return ResponseEntity.status(HttpStatus.OK).body("Purchase success");
        }
    }


    @GetMapping("/{userId}/purchase-history")
    public ResponseEntity<PurchaseHistory> getPurchaseHistory(@PathVariable("userId") int userId) {
        ResponseEntity<PurchaseHistory> responseEntity = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userRepository.findUserByUserName(currentUserName).orElse(null);
        if (currentUser != null) {
            if (currentUser.getId() != userId) {
                responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);

            } else {
                responseEntity = ResponseEntity.ok(purchaseHistoryRepository.getPurchaseHistoryByUserId(userId));
            }
        }
        return responseEntity;
    }
    @GetMapping("/{userId}/purchase-history/{goodsId}/rate")
    public ResponseEntity<String> rate(@PathVariable("userId") int userId,
                                       @PathVariable("goodsId") int goodsId,
                                       @RequestParam("rate") double rate){
        PurchaseHistory purchaseHistory = purchaseHistoryRepository.getPurchaseHistoryByUserId(userId);
        if(purchaseHistory.getGoodsList().contains(goodsService.get(goodsId))) {
            Goods goods = goodsService.get(goodsId);
            goods.setRate(rate);
            goodsService.update(goods, goodsId);
            return ResponseEntity.status(HttpStatus.OK).body("Rate success");
        }else{
            return handleException(new FeedBackAndRatingForbidden("User can't rate or leave feedback for not purchased goods"));
        }
    }
    @GetMapping("/{userId}/purchase-history/{goodsId}/feedback")
    public ResponseEntity<String> leaveFeedback(@PathVariable("userId") int userId,
                                                @PathVariable("goodsId") int goodsId,
                                                @RequestParam("feedBack")FeedBack feedBack){
        PurchaseHistory purchaseHistory = purchaseHistoryRepository.getPurchaseHistoryByUserId(userId);
        if(purchaseHistory.getGoodsList().contains(goodsService.get(goodsId))){
            feedBack.setGoods(goodsService.get(goodsId));
            feedBack.setUser(userService.get(userId));
            feedBackService.create(feedBack);
            return ResponseEntity.status(HttpStatus.OK).body("FeedBack success");
        }else{
            return handleException(new FeedBackAndRatingForbidden("User can't rate or leave feedback for goods not purchased"));
        }

    }


    @GetMapping("/{userId}/purchase-history/{goodsId}/refund")
    public ResponseEntity<String> makeRefund(@PathVariable("userId") int userId,
                                             @PathVariable("goodsId") int goodsId) {
        PurchaseHistory purchaseHistory = purchaseHistoryRepository.getPurchaseHistoryByUserId(userId);
        Goods goodsToBeRefund = goodsService.get(goodsId);
        User user = userService.get(userId);
        User seller = goodsToBeRefund.getUser();
        if(purchaseHistory.getPurchaseTime().plusHours(24).isBefore(LocalDateTime.now())){
            purchaseHistory.getGoodsList().remove(goodsToBeRefund);
            user.updateBalance(goodsToBeRefund.getPrice());
            seller.updateBalance(goodsToBeRefund.getPrice() * (-0.1));
            userService.update(user, userId);
            userService.update(seller, seller.getId());
            return ResponseEntity.status(HttpStatus.OK).body("Refund success");
        }else{
            return handleException(new RefundDateExpired("Refund date is expired"));
        }

    }

    @GetMapping("/{userId}/company-register-application")
    public ResponseEntity<String> applyForCompanyRegistration(@PathVariable("userId") int userId,
                                                  @RequestBody CompanyRegistrationApplication companyRegistrationApplication,
                                                              @RequestBody Company company) {
        companyRegistrationApplicationService.create(companyRegistrationApplication);
        companyService.create(company);
        companyRegistrationApplication.setUser(userService.get(userId));
        companyRegistrationApplication.setApproved(false);
        companyRegistrationApplication.setCompany(company);
        company.setUser(userService.get(userId));
        company.setCompanyRegistrationApplication(companyRegistrationApplication);
        company.setEnabled(false);
        companyService.update(company, company.getId());
        companyRegistrationApplicationService.update(companyRegistrationApplication, companyRegistrationApplication.getId());


        return ResponseEntity.status(HttpStatus.OK).body("Application success");
    }

    @GetMapping("/{userId}/{companyId}/goods-register-application")
    public ResponseEntity<String> applyForGoodsRegistration(@PathVariable("userId") int userId,
                                                              @PathVariable("companyId") int companyId,
                                                              @RequestBody GoodsRegistrationApplication goodsRegistrationApplication) {
        goodsRegistrationApplication.setUser(userService.get(userId));
        goodsRegistrationApplication.setApproved(false);
        goodsRegistrationApplication.setCompany(companyService.get(companyId));
        goodsRegistrationApplicationService.create(goodsRegistrationApplication);
        List<Goods> goodsList = goodsRepository.getAllByCompanyId(companyId);
        for(Goods goods : goodsList){
            goods.setUser(userService.get(userId));
            goods.setGoodsRegistrationApplication(goodsRegistrationApplication);
            goodsService.create(goods);
        }


        return ResponseEntity.status(HttpStatus.OK).body("Application success");
    }

    @GetMapping("/{userId}/place-goods")
    public ResponseEntity<String> placeGoods(@PathVariable("userId") int userId) {
        List<Company> companyList = companyRepository.getCompanyListByUserId(userId);

        for(Company company : companyList){
            GoodsRegistrationApplication goodsRegistrationApplication = goodsRegistrationApplicationRepository.findByCompany(company);
            List<Goods> goodsList = company.getGoodsList();
            if(goodsRegistrationApplication.isApproved()){
                for(Goods goods : goodsList){
                    goods.setUser(userService.get(userId));
                    goods.setCompany(company);
                    goodsService.update(goods, goods.getId());
                }
            }

        }
        return ResponseEntity.status(HttpStatus.OK).body("Application success");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred: " + e.getMessage());
    }



}
