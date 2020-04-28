package com.pcbWeld.information.controller;

import java.util.Date;
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

import com.pcbWeld.information.domain.MessageReplyDO;
import com.pcbWeld.information.service.MessageReplyService;
import com.pcbWeld.common.utils.PageUtils;
import com.pcbWeld.common.utils.Query;
import com.pcbWeld.common.utils.R;
import com.pcbWeld.common.utils.ShiroUtils;

/**
 * 留言回复表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-27 18:26:02
 */
 
@Controller
@RequestMapping("/information/messageReply")
public class MessageReplyController {
	@Autowired
	private MessageReplyService messageReplyService;
	
	@GetMapping()
	@RequiresPermissions("information:messageReply:messageReply")
	String MessageReply(){
	    return "information/messageReply/messageReply";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("information:messageReply:messageReply")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<MessageReplyDO> messageReplyList = messageReplyService.list(query);
		int total = messageReplyService.count(query);
		PageUtils pageUtils = new PageUtils(messageReplyList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("information:messageReply:add")
	String add(){
	    return "information/messageReply/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("information:messageReply:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		MessageReplyDO messageReply = messageReplyService.get(id);
		model.addAttribute("messageReply", messageReply);
	    return "information/messageReply/edit";
	}
	
	@GetMapping("/chakan/{id}")
	@RequiresPermissions("information:messageReply:edit")
	String chakan(@PathVariable("id") Integer id,Model model){
		MessageReplyDO messageReply = messageReplyService.get(id);
		model.addAttribute("messageReply", messageReply);
	    return "information/messageReply/chakan";
	}
	
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("information:messageReply:add")
	public R save( MessageReplyDO messageReply){
		if(messageReplyService.save(messageReply)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("information:messageReply:edit")
	public R update( MessageReplyDO messageReply){
		messageReply.setReplyTime(new Date());
		messageReply.setIfreply(2);
		messageReply.setReplyCreateId(ShiroUtils.getUserId().intValue());
		messageReplyService.update(messageReply);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("information:messageReply:remove")
	public R remove( Integer id){
		MessageReplyDO messageReply = new MessageReplyDO();
		messageReply.setId(id);
		messageReply.setDelFlag(2);
		messageReplyService.update(messageReply);
		//if(messageReplyService.remove(id)>0){
		return R.ok();
		//}
		//return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("information:messageReply:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		messageReplyService.batchRemove(ids);
		return R.ok();
	}
	
}
