package com.pcbWeld.common.controller;

import org.springframework.stereotype.Controller;

import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.system.domain.UserDO;
import com.pcbWeld.system.domain.UserToken;

@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}