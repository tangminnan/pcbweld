package com.pcbWeld.information.controller;

import java.util.Date;
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

import com.pcbWeld.information.domain.MsgDO;
import com.pcbWeld.information.domain.MsgUserDO;
import com.pcbWeld.information.service.MsgService;
import com.pcbWeld.information.service.MsgUserService;
import com.pcbWeld.users.domain.UserDO;
import com.pcbWeld.users.service.UserService;
import com.pcbWeld.common.utils.PageUtils;
import com.pcbWeld.common.utils.Query;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.common.utils.ShiroUtils;

/**
 * 消息表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-27 15:31:12
 */
 
@Controller
@RequestMapping("/information/msg")
public class MsgController {
	@Autowired
	private MsgService msgService;	
	@Autowired
	private MsgUserService msgUserService;	
	@Autowired
	private UserService userService;
	
	
	@GetMapping()
	@RequiresPermissions("information:msg:msg")
	String Msg(){
	    return "information/msg/msg";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:msg:msg")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<MsgDO> msgList = msgService.list(query);
		int total = msgService.count(query);
		PageUtils pageUtils = new PageUtils(msgList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:msg:add")
	String add(){
	    return "information/msg/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:msg:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		MsgDO msg = msgService.get(id);
		model.addAttribute("msg", msg);
	    return "information/msg/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:msg:add")
	public R save( MsgDO msg){
		msg.setAddTime(new Date());
		msg.setDeleteFlag(0);
		msg.setCreateBy(ShiroUtils.getUserId());
		String forIds = msg.getForIds();
		if(forIds.length()==0 || forIds.equals("")){
			msg.setForIds("0");
			msg.setForNames("所有用户");
		}else{
			UserDO userDO = userService.get(Integer.valueOf(forIds));
			if(userDO == null){
				return R.error("用户不存在！！！");
			}
			msg.setForNames(userDO.getNickname());
		}
		if(msgService.save(msg)>0){
			if(msg.getForIds().equals("0")){
				List<UserDO> list = userService.list(new HashMap<>());
				for (UserDO userDO : list) {
					MsgUserDO mu = new MsgUserDO();
					mu.setMsgId(msg.getId().longValue());
					mu.setUserId(userDO.getId());
					mu.setStatue(1);
					mu.setAddTime(new Date());
					msgUserService.save(mu);
				}
			}else{
				MsgUserDO mu = new MsgUserDO();
				mu.setMsgId(msg.getId().longValue());
				mu.setUserId(Long.valueOf(msg.getForIds()));
				mu.setStatue(1);
				mu.setAddTime(new Date());
				msgUserService.save(mu);
			}
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:msg:edit")
	public R update( MsgDO msg){
		String forIds = msg.getForIds();
		String forIds2 = msgService.get(msg.getId()).getForIds();
		if(!forIds.equals(forIds2)){
			if(forIds.length()==0 || forIds.equals("")){
				msg.setForIds("0");
				msg.setForNames("所有用户");
			}else{
				UserDO userDO = userService.get(Integer.valueOf(forIds));
				if(userDO == null){
					return R.error("用户不存在！！！");
				}
				msg.setForNames(userDO.getNickname());
			}
		}
		msgService.update(msg);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:msg:remove")
	public R remove( Integer id){
		if(msgService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:msg:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		msgService.batchRemove(ids);
		return R.ok();
	}
	
}
