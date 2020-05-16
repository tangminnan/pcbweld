package com.pcbWeld.information.controller;

import java.util.List;
import java.util.Map;

import com.pcbWeld.common.utils.PageUtils;
import com.pcbWeld.common.utils.Query;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.information.domain.OrderDetailDO;
import com.pcbWeld.information.service.OrderDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 物料表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-16 13:38:45
 */
 
@Controller
@RequestMapping("/information/orderDetail")
public class OrderDetailController {
	@Autowired
	private OrderDetailService orderDetailService;

	

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:orderDetail:add")
	public R save( OrderDetailDO orderDetail){
		if(orderDetailService.save(orderDetail)>0){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:orderDetail:remove")
	public R remove( Long id){
		if(orderDetailService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}

}
