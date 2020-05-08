package com.pcbWeld.common.controller;

import org.springframework.stereotype.Controller;

import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.owneruser.domain.OwnerUserDO;
import com.pcbWeld.system.domain.UserToken;

@Controller
public class BaseController {
	public OwnerUserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
	/*public Long getforIds() {
		return getUser().getUserId();
	}*/
}