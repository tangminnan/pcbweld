package com.pcbWeld.information.controller;

import com.pcbWeld.common.annotation.Log;
import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.information.domain.*;
import com.pcbWeld.information.service.*;
import com.pcbWeld.owneruser.domain.OwnerUserDO;
import com.pcbWeld.owneruser.service.OwnerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class IndexController {
    @Autowired
    MaterialItemService materialItemService;
    @Autowired
    MaterialParameterService materialParameterService;
    @Autowired
    OwnerUserService userService;
    @Autowired
    MsgService msgService;
    @Autowired
    OrderService orderService;
    @Autowired
    private ReceiptService receiptService;
    @Autowired
    UserAddressService userAddressService;


    @Log("首页")
    @GetMapping("/")
    String index(Model model) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", 2);

        List<MaterialItemDO> materialItemDOS = materialItemService.list(params);
        for (MaterialItemDO materialItemDO : materialItemDOS) {
            params.put("itemId", materialItemDO.getId());

            materialItemDO.setMaterialParameterDO(materialParameterService.list(params));
        }
        System.out.println("===========materialItemDOS====================" + materialItemDOS);

        model.addAttribute("materialItemDOS", materialItemDOS);
        return "jijia";
    }

    @Log("首页")
    @GetMapping("/wode/{text}")
    String wode(@PathVariable("text") String text,Model model) {
        long  id= ShiroUtils.getUserId();
        Map<String, Object> params = new HashMap<>();

        OwnerUserDO user = userService.get(id);
        List<MsgDO> msgDOS = msgService.userMsgList(id);

        //资料审核数量
        params.put("orderStatus", 2);
        Integer dataCheckOrder = orderService.list(params).size();

        //资料审核未通过列表数量
        params.remove("orderStatus");
        params.put("orderStatus", 3);
        Integer dataCheckFailedOrder = orderService.list(params).size();

        //待支付列表数量
        params.remove("orderStatus");
        params.put("orderStatus", 4);
        Integer unpaidOrder = orderService.list(params).size();

        //物料寄送列表数量
        params.remove("orderStatus");
        params.put("orderStatus", 5);
        Integer materialDelivery = orderService.list(params).size();

        //物料待审核列表数量
        params.remove("orderStatus");
        params.put("orderStatus", 6);
        Integer materialCheck = orderService.list(params).size();

        //未读消息数量
        model.addAttribute("msgCount", msgDOS.size());
        //用户信息
        model.addAttribute("user", user);
        model.addAttribute("page", "../templates/wode");
        model.addAttribute("context","wode");
        if("开票信息".equals(text)) {
            model.addAttribute("page", "../templates/kaipiaoxinxi");
            model.addAttribute("context", "kpxx");
            OwnerUserDO ownerUserDO = receiptService.getReceipt(id);
            model.addAttribute("data",ownerUserDO);
        }else if("收货地址".equals(text)){
            model.addAttribute("page", "../templates/shouhuodizhi");
            model.addAttribute("context", "shdz");
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId", ShiroUtils.getUserId());
            List<UserAddressDO> address = userAddressService.list(paramsMap);
            model.addAttribute("address",address);
        }else if("待开票金额".equals(text)){
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId",id) ;
            List<OrderDO> orderList = orderService.list(paramsMap);
            System.out.println("------------------------"+orderList);
            orderList = orderList.stream().filter(a ->a.getOrderStatus()==10).filter(b->b.getInvoiceStatus()!=2).collect(Collectors.toList());
            System.out.println("------------------------"+orderList);
            model.addAttribute("orderList",orderList);
            model.addAttribute("page", "../templates/daikaijine");
            model.addAttribute("context", "dkje");
        }else if("已开发票记录".equals(text)){
            Map<String,Object> paramsMap = new HashMap<String,Object>();
            paramsMap.put("userId",id) ;
            List<ReceiptDO> recepitList = receiptService.list(paramsMap);
            model.addAttribute("recepitList",recepitList);
            model.addAttribute("page", "../templates/yikaifapiao");
            model.addAttribute("context", "ykjl");
        }else if("我的订单".equals(text)){
            model.addAttribute("page", "../templates/wodedingdan");
            model.addAttribute("context", "wddd");
        }else if("消息中心".equals(text)){
            model.addAttribute("page", "../templates/xiaoxizhongxin");
            model.addAttribute("context", "xxzx");
        }else if("个人资料".equals(text)){
            OwnerUserDO ownerUserDO = userService.get(id);
            model.addAttribute("user",ownerUserDO);
            model.addAttribute("page", "../templates/xiugaiziliao");
            model.addAttribute("context", "xgzl");
        }else if("个人中心".equals(text)){
            model.addAttribute("page", "../templates/wode");
            model.addAttribute("context", "wode");
        }


        return "main";
    }
}
