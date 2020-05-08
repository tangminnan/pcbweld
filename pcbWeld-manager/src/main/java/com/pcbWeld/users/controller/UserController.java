package com.pcbWeld.users.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.pcbWeld.common.utils.PageUtils;
import com.pcbWeld.common.utils.Query;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.information.domain.OrderDO;
import com.pcbWeld.information.domain.UserAddressDO;
import com.pcbWeld.information.service.OrderService;
import com.pcbWeld.information.service.UserAddressService;
import com.pcbWeld.users.domain.UserDO;
import com.pcbWeld.users.service.UserService;



/**
 * 用户信息表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2018-09-27 10:18:38
 */
 

@Controller("UserController")
@RequestMapping("/information/users")
public class UserController{
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserAddressService userAddressService;
	
	@GetMapping()
	@RequiresPermissions("information:user:user")
	String User(){
	    return "users/user";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:user:user")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserDO> userList = userService.list(query);
		System.out.println("==========userList==============="+userList);
		int total = userService.count(query);
		PageUtils pageUtils = new PageUtils(userList, total);
		return pageUtils;
	}
	
	
	@GetMapping("/add")
	@RequiresPermissions("information:user:add")
	String add(){
	    return "users/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:user:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		UserDO user = userService.get(id);
		model.addAttribute("user", user);
	    return "users/edit";
	}
	
	@GetMapping("/xiangqing/{id}")
	@RequiresPermissions("information:user:edit")
	String xiangqing(@PathVariable("id") Integer id,Model model){
		UserDO user = userService.get(id);
		model.addAttribute("user", user);
		Map<String, Object> map = new HashMap<>();
		map.put("userId", id);
		List<OrderDO> order = orderService.list(map);
		model.addAttribute("order", order);
		List<UserAddressDO> userAddress = userAddressService.list(map);
		model.addAttribute("userAddress", userAddress);
	    return "users/xiangqing";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:user:add")
	public R save( UserDO user){	
		if(userService.save(user)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:user:edit")
	public R update( UserDO user){
		userService.update(user);
		return R.ok();
	}
		

	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:user:remove")
	public R remove( Long id){
		
		UserDO user = new UserDO();
        user.setId(id);
        user.setDeleteFlag(1);
		userService.update(user);
		
//		if(userService.remove(id)>0){
//			return R.ok();
//		}	        	
//		return R.error();
		return R.ok();
		
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:user:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		userService.batchRemove(ids);
		return R.ok();
	}



}
