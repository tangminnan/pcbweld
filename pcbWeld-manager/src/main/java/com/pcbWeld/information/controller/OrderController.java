package com.pcbWeld.information.controller;

import com.pcbWeld.common.utils.PageUtils;
import com.pcbWeld.common.utils.Query;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.information.domain.OrderDO;
import com.pcbWeld.information.service.OrderService;
import com.pcbWeld.users.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 订单表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-28 09:17:26
 */
 
@Controller
@RequestMapping("/information/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	
	@GetMapping()
	@RequiresPermissions("information:order:order")
	String Order(){
	    return "information/order/order";
	}


	//资料审核列表
	@GetMapping("/dataCheckOrder")
	@RequiresPermissions("information:order:dataCheckOrder")
	String dataCheckOrder(Model model){
		model.addAttribute("orderStatus",2);
		return "information/order/order";
	}

	//资料审核未通过列表
	@GetMapping("/dataCheckFailedOrder")
	@RequiresPermissions("information:order:dataCheckFailedOrder")
	String dataCheckFailedOrder(Model model){
		model.addAttribute("orderStatus",3);
		return "information/order/order";
	}

	//待支付列表
	@GetMapping("/unpaidOrder")
	@RequiresPermissions("information:order:unpaidOrder")
	String unpaidOrder(Model model){
		model.addAttribute("orderStatus",4);
		return "information/order/order";
	}

	//物料寄送列表
	@GetMapping("/materialDelivery")
	@RequiresPermissions("information:order:materialDelivery")
	String materialDelivery(Model model){
		model.addAttribute("orderStatus",5);
		return "information/order/order";
	}

	//物料待审核列表
	@GetMapping("/materialCheck")
	@RequiresPermissions("information:order:materialCheck")
	String materialCheck(Model model){
		model.addAttribute("orderStatus",6);
		return "information/order/order";
	}

	//物料审核失败
	@GetMapping("/materialCheckFailed")
	@RequiresPermissions("information:order:materialCheckFailed")
	String materialCheckFailed(Model model){
		model.addAttribute("orderStatus",7);
		return "information/order/order";
	}

	//待发货
	@GetMapping("/waitFaHuo")
	@RequiresPermissions("information:order:waitFaHuo")
	String waitFaHuo(Model model){
		model.addAttribute("orderStatus",8);
		return "information/order/order";
	}

	//待收货
	@GetMapping("/waitShouHuo")
	@RequiresPermissions("information:order:waitShouHuo")
	String waitShouHuo(Model model){
		model.addAttribute("orderStatus",9);
		return "information/order/order";
	}

	//已完成
	@GetMapping("/achieveOrder")
	@RequiresPermissions("information:order:achieveOrder")
	String achieveOrder(Model model){
		model.addAttribute("orderStatus",10);
		return "information/order/order";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:order:order")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<OrderDO> orderList = orderService.list(query);
		for (OrderDO orderDO : orderList) {
			if(orderDO.getUserId() != null){
				orderDO.setUsername(userService.get(orderDO.getUserId().intValue()).getName());
			}
		}
		int total = orderService.count(query);
		PageUtils pageUtils = new PageUtils(orderList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:order:add")
	String add(){
	    return "information/order/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:order:edit")
	String edit(@PathVariable("id") Long id,Model model){
		OrderDO order = orderService.get(id);
		model.addAttribute("order", order);
	    return "information/order/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:order:add")
	public R save(OrderDO order){
		if(orderService.save(order)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:order:edit")
	public R update( OrderDO order){
		orderService.update(order);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:order:remove")
	public R remove( Long id){
		if(orderService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:order:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		orderService.batchRemove(ids);
		return R.ok();
	}
	
}
