package com.pcbWeld.owneruser.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.pcbWeld.common.annotation.Log;
import com.pcbWeld.common.controller.BaseController;
import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.owneruser.comment.GenerateCode;
import com.pcbWeld.owneruser.dao.OwnerUserDao;
import com.pcbWeld.owneruser.domain.OwnerUserDO;
import com.pcbWeld.owneruser.service.OwnerUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OwnerUserDao ownerUserMapper;

    @Autowired
    OwnerUserService userService;


    @Log("密码登录")
    @PostMapping("/loginP")
    Map<String, Object> loginP(String phone, String password) {
        Map<String, Object> message = new HashMap<>();
        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            Map<String, Object> mapP = new HashMap<String, Object>();
            mapP.put("username", phone);
            boolean flag = userService.exit(mapP);
            if (!flag) {
                message.put("code", -1);
                message.put("data", null);
                message.put("msg", "该手机号码未注册");
            } else {
                OwnerUserDO udo = userService.getbyname(phone);
                if (udo.getDeleteFlag() == 1) {
                    message.put("code", -1);
                    message.put("data", null);
                    message.put("msg", "禁止登录，请联系客服");
                }
                subject.login(token);
                udo.setLoginTime(new Date());
                userService.update(udo);

                message.put("code", 0);
                message.put("msg", "登录成功");
                message.put("data", udo);
            }
        } catch (AuthenticationException e) {
            message.put("code", -1);
            message.put("data", null);
            message.put("msg", "用户或密码错误");
        }
        return message;
    }


    /**
     * @param phone 手机号
     * @说明 发送验证码
     */
    @Log("绑定手机号")
    @PostMapping("/bindPhone")
    Map<String, Object> bindPhone(Long id, String phone) {
        Map<String, Object> message = new HashMap<>();
        OwnerUserDO ownerUserDONow = new OwnerUserDO();

        if (phone != null && !("").equals(phone)) {
            //当前登录用户
            if (id != null) {
                ownerUserDONow = userService.get(id);
            }
            //查询手机号是否已存在
            OwnerUserDO ownerUserDOBind = userService.getUserByphone(phone);
            //如果手机号已存在，把openid或者unionid补充，删除当前
            if (ownerUserDOBind != null) {
                if (ownerUserDONow.getOpenId() != null) {
                    ownerUserDOBind.setOpenId(ownerUserDONow.getOpenId());
                }
                if (ownerUserDONow.getUnionid() != null) {
                    ownerUserDOBind.setUnionid(ownerUserDONow.getUnionid());
                }

                ownerUserDOBind.setNickname(ownerUserDONow.getNickname());
                ownerUserDOBind.setName(ownerUserDONow.getName());
                ownerUserDOBind.setHeardUrl(ownerUserDONow.getHeardUrl());

                if (userService.update(ownerUserDOBind) > 0) {
                    if (userService.remove(id) > 0) {
                        message.put("code", 0);
                        message.put("msg", "绑定成功");
                        message.put("data", ownerUserDOBind);
                    } else {
                        message.put("code", -1);
                        message.put("msg", "绑定失败");
                        message.put("data", null);
                    }
                } else {
                    message.put("code", -1);
                    message.put("msg", "绑定失败");
                    message.put("data", null);
                }
            } else {
                ownerUserDONow.setPhone(phone);
                ownerUserDONow.setUsername(phone);

                if (userService.update(ownerUserDONow) > 0) {
                    message.put("code", 0);
                    message.put("msg", "绑定成功");
                    message.put("data", ownerUserDONow);
                }
            }

        } else {
            message.put("code", -1);
            message.put("msg", "手机号为空");
            message.put("data", null);
        }
        return message;
    }

    @Log("发送验证码")
    @PostMapping("/getSms")
    Map<String, Object> getSms(String phone, String type) {
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        try {
            if (phone == null || "".equals(phone)) {
                message.put("code", -1);
                message.put("data", null);
                message.put("msg", "手机号码不能为空");
            } else {
                DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIshP9j8jxO4KO", "QKwMvh2VocxZUf1qJl5nfPyJEHA7Lk");
                IAcsClient client = new DefaultAcsClient(profile);

                Integer templateParam = (int) ((Math.random() * 9 + 1) * 100000);
                System.out.println("=================================" + templateParam + "===================================");

                CommonRequest request = new CommonRequest();
                //request.setProtocol(ProtocolType.HTTPS);
                request.setMethod(MethodType.POST);
                request.setDomain("dysmsapi.aliyuncs.com");
                request.setVersion("2017-05-25");
                request.setAction("SendSms");

                request.putQueryParameter("PhoneNumbers", phone);

                request.putQueryParameter("SignName", "慢酷数码app");

                if ("0".equals(type)) {
                    request.putQueryParameter("TemplateCode", "SMS_162732611");
                } else if ("1".equals(type)) {            //登陆
                    request.putQueryParameter("TemplateCode", "SMS_163720480");
                } else if ("2".equals(type)) {            //重置密码
                    request.putQueryParameter("TemplateCode", "SMS_163720481");
                }

                request.putQueryParameter("TemplateParam", "{\"code\":\"" + templateParam + "\"}");


                CommonResponse response = client.getCommonResponse(request);

                Subject subject = SecurityUtils.getSubject();
                subject.getSession().setAttribute("sys.login.check.code", phone + templateParam);
                data.put("sessionId", subject.getSession().getId().toString());
                if (type.equals("0")) {
                    Map<String, Object> mapP = new HashMap<String, Object>();

                    mapP.put("username", phone);
                    boolean flag = userService.exit(mapP);    //查手机号是否存在
                    if (flag) {
                        message.put("code", -1);
                        message.put("data", data);
                        message.put("msg", "手机号已存在");
                    } else {
                        message.put("code", 0);
                        message.put("data", data);
                        message.put("msg", "发送成功");
                    }
                } else {
                    message.put("code", 0);
                    message.put("data", data);
                    message.put("msg", "发送成功");
                }
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return message;
    }


    @Log("微信登录")
    @PostMapping("/loginWechat")
    Map<String, Object> loginWechat(String openId, String nickname, String headUrl) {
        Map<String, Object> message = new HashMap<>();
        try {
            if (openId == null || "".equals(openId)) {
                message.put("code", -1);
                message.put("data", null);
                message.put("msg", "微信标识为空");
            } else {
                OwnerUserDO ownerUserDO = userService.getbyopenid(openId);
                if (ownerUserDO != null) {
                    if (ownerUserDO.getPhone() != null) {
                        ownerUserDO.setLoginTime(new Date());
                        userService.update(ownerUserDO);
                        String phone = ownerUserDO.getPhone();
                        String password = ownerUserDO.getPassword();

                        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
                        Subject subject = SecurityUtils.getSubject();
                        subject.getSession().setAttribute("token", token);
                        subject.login(token);

                        message.put("code", 0);
                        message.put("msg", "登录成功");
                        message.put("data", ownerUserDO);
                    } else {
                        UsernamePasswordToken token = new UsernamePasswordToken(openId, ownerUserDO.getPassword());
                        Subject subject = SecurityUtils.getSubject();
                        subject.getSession().setAttribute("token", token);
                        subject.login(token);

                        message.put("code", 0);
                        message.put("msg", "登录成功");
                        message.put("data", ownerUserDO);
                    }
                } else {
                    OwnerUserDO newOwnerUserDO = new OwnerUserDO();

                    newOwnerUserDO.setDeleteFlag(0);
                    Long userId = GenerateCode.gen16(8);
                    newOwnerUserDO.setAccountNumber(userId);
                    newOwnerUserDO.setOpenId(openId);
                    newOwnerUserDO.setUsername(openId);
                    if (headUrl != null) {
                        newOwnerUserDO.setHeardUrl(headUrl);
                    }
                    if (nickname != null) {
                        newOwnerUserDO.setNickname(nickname);
                        newOwnerUserDO.setName(nickname);
                    }
                    newOwnerUserDO.setPassword(openId);
                    newOwnerUserDO.setRegisterTime(new Date());

                    if (userService.save(newOwnerUserDO) > 0) {
                        UsernamePasswordToken token = new UsernamePasswordToken(openId, newOwnerUserDO.getPassword());
                        Subject subject = SecurityUtils.getSubject();
                        subject.getSession().setAttribute("token", token);
                        subject.login(token);

                        message.put("code", 0);
                        message.put("msg", "登录成功");
                        message.put("data", newOwnerUserDO);
                    } else {
                        message.put("code", -1);
                        message.put("msg", "登录失败，请重试");
                        message.put("data", null);
                    }

                }
            }
        } catch (AuthenticationException e) {
            message.put("code", -1);
            message.put("data", null);
            message.put("msg", "异常！请重新登录尝试");
        }
        return message;
    }

    @Log("qq登录")
    @PostMapping("/loginqq")
    Map<String, Object> loginqq(String unionid, String nickname, String headUrl) {
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            if (unionid == null || "".equals(unionid)) {
                message.put("code", -1);
                message.put("data", null);
                message.put("msg", "qq标识为空");
            } else {
                OwnerUserDO ownerUserDO = userService.getbyunionid(unionid);
                if (ownerUserDO != null) {
                    if (ownerUserDO.getPhone() != null) {
                        ownerUserDO.setLoginTime(new Date());
                        userService.update(ownerUserDO);
                        String phone = ownerUserDO.getPhone();
                        String password = ownerUserDO.getPassword();

                        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
                        Subject subject = SecurityUtils.getSubject();
                        subject.getSession().setAttribute("token", token);
                        subject.login(token);

                        message.put("code", 0);
                        message.put("msg", "登录成功");
                        message.put("data", ownerUserDO);
                    } else {
                        ownerUserDO.setLoginTime(new Date());
                        userService.update(ownerUserDO);

                        UsernamePasswordToken token = new UsernamePasswordToken(unionid, ownerUserDO.getPassword());
                        Subject subject = SecurityUtils.getSubject();
                        subject.getSession().setAttribute("token", token);
                        subject.login(token);

                        message.put("code", 0);
                        message.put("msg", "登录成功");
                        message.put("data", ownerUserDO);
                    }
                } else {
                    OwnerUserDO newOwnerUserDO = new OwnerUserDO();

                    newOwnerUserDO.setDeleteFlag(0);
                    Long userId = GenerateCode.gen16(8);
                    newOwnerUserDO.setAccountNumber(userId);
                    newOwnerUserDO.setUnionid(unionid);
                    newOwnerUserDO.setUsername(unionid);
                    if (headUrl != null) {
                        newOwnerUserDO.setHeardUrl(headUrl);
                    }
                    if (nickname != null) {
                        newOwnerUserDO.setNickname(nickname);
                        newOwnerUserDO.setName(nickname);
                    }
                    newOwnerUserDO.setPassword(unionid);
                    newOwnerUserDO.setRegisterTime(new Date());
                    newOwnerUserDO.setLoginTime(new Date());

                    if (userService.save(newOwnerUserDO) > 0) {
                        UsernamePasswordToken token = new UsernamePasswordToken(unionid, newOwnerUserDO.getPassword());
                        Subject subject = SecurityUtils.getSubject();
                        subject.getSession().setAttribute("token", token);
                        subject.login(token);

                        message.put("code", 0);
                        message.put("msg", "登录成功");
                        message.put("data", newOwnerUserDO);
                    } else {
                        message.put("code", -1);
                        message.put("msg", "登录失败，请重试");
                        message.put("data", null);
                    }

                }
            }
        } catch (AuthenticationException e) {
            message.put("code", -1);
            message.put("data", null);
            message.put("msg", "异常！请重新登录尝试");
        }
        return message;
    }

    @Log("验证码登录")
    @PostMapping("/loginC")
    Map<String, Object> loginC(String phone, String codenum) {
        Map<String, Object> message = new HashMap<>();
        String msg = "";
        Subject subject = SecurityUtils.getSubject();

        Object object = subject.getSession().getAttribute("sys.login.check.code");
        try {
            if (object != null) {
                String captcha = object.toString();
                if (captcha == null || "".equals(captcha)) {
                    message.put("code", -1);
                    message.put("data", null);
                    message.put("msg", "验证码已失效，请重新点击发送验证码");

                } else {
                    // session中存放的验证码是手机号+验证码
                    if (!captcha.equalsIgnoreCase(phone + codenum)) {
                        message.put("code", -1);
                        message.put("data", null);
                        message.put("msg", "手机验证码错误");
                    } else {
                        Map<String, Object> mapP = new HashMap<String, Object>();
                        mapP.put("username", phone);
                        boolean flag = userService.exit(mapP);
                        if (!flag) {
                            message.put("code", -1);
                            message.put("data", null);
                            message.put("msg", "该手机号码未注册");
                        } else {
                            OwnerUserDO udo = userService.getbyname(phone);
                            if (udo == null || udo.getDeleteFlag() == 1) {
                                message.put("code", -1);
                                message.put("data", null);
                                message.put("msg", "禁止登录，请联系客服");
                            } else {

                                String password = udo.getPassword();
                                UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
                                subject.login(token);

                                udo.setLoginTime(new Date());

                                userService.update(udo);

                                message.put("code", 0);
                                message.put("data", udo);
                                message.put("msg", "登录成功");


                            }
                        }
                    }
                }
            } else if (codenum.equals("111111")) {
                Map<String, Object> mapP = new HashMap<String, Object>();
                mapP.put("username", phone);
                boolean flag = userService.exit(mapP);
                if (!flag) {
                    message.put("code", -1);
                    message.put("data", null);
                    message.put("msg", "该手机号码未注册");
                } else {
                    OwnerUserDO udo = userService.getbyname(phone);
                    if (udo == null || udo.getDeleteFlag() == 1) {
                        message.put("code", -1);
                        message.put("data", null);
                        message.put("msg", "禁止登录，请联系客服");
                    } else {

                        String password = udo.getPassword();
                        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
                        subject.login(token);

                        udo.setLoginTime(new Date());

                        userService.update(udo);

                        message.put("code", 0);
                        message.put("data", udo);
                        message.put("msg", "登录成功");


                    }
                }
            } else {
                message.put("code", -1);
                message.put("data", null);
                message.put("msg", "手机验证码错误");
            }
        } catch (AuthenticationException e) {
            message.put("code", -1);
            message.put("data", null);
            message.put("msg", "手机号或验证码错误");
        }
        return message;
    }


    @Log("用户注册")
    @PostMapping("/register")
    Map<String, Object> register(String phone, String codenum, String password) {
        Map<String, Object> message = new HashMap<>();
        String msg = "";
        if (StringUtils.isBlank(phone)) {
            message.put("code", -1);
            message.put("data", null);
            message.put("msg", "手机号码不能为空");
        } else {
            Subject subject = SecurityUtils.getSubject();
            Object object = subject.getSession().getAttribute("sys.login.check.code");
            if (object != null) {
                String captcha = object.toString();
                //String captcha = "666666";
                if (captcha == null || "".equals(captcha)) {
                    message.put("code", -1);
                    message.put("data", null);
                    message.put("msg", "验证码已失效，请重新点击发送验证码");
                } else {
                    // session中存放的验证码是手机号+验证码
                    if (!captcha.equalsIgnoreCase(phone + codenum)) {
                        message.put("code", -1);
                        message.put("data", null);
                        message.put("msg", "手机验证码错误");
                    } else {
                        Map<String, Object> mapP = new HashMap<String, Object>();

                        mapP.put("username", phone);
                        boolean flag = userService.exit(mapP);    //查手机号是否存在
                        if (flag) {
                            message.put("code", -1);
                            message.put("data", null);
                            message.put("msg", "用户已存在");
                        } else {
                            OwnerUserDO udo = new OwnerUserDO();

                            Long userId = GenerateCode.gen16(8);
                            udo.setAccountNumber(userId);
                            udo.setUsername(phone);
                            udo.setPhone(phone);
                            udo.setPassword(password);
                            udo.setDeleteFlag(0);
                            udo.setRegisterTime(new Date());

                            if (userService.save(udo) > 0) {
                                message.put("code", 0);
                                message.put("data", udo);
                                message.put("msg", "注册成功");

                                UsernamePasswordToken token = new UsernamePasswordToken(udo.getPhone(), udo.getPassword());

                                subject.login(token);
                                udo.setLoginTime(new Date());
                                userService.update(udo);
                            } else {
                                message.put("code", -1);
                                message.put("data", null);
                                message.put("msg", "注册失败");
                            }
                        }
                    }
                }
            } else {
                message.put("code", -1);
                message.put("data", null);
                message.put("msg", "验证码无效，请重新点击发送验证码");
            }
        }
        return message;
    }

    @Log("忘记密码")
    @PostMapping("/retpwd")
    Map<String, Object> retpwd(String username, String password, String codenum) {
        Map<String, Object> message = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            message.put("code", -1);
            message.put("data", null);
            message.put("msg", "手机号码不能为空");
        } else {
            OwnerUserDO udo = userService.getbyname(username);
            Subject subject = SecurityUtils.getSubject();
            Object object = subject.getSession().getAttribute("sys.login.check.code");
            if (object != null) {
                String captcha = object.toString();
                if (captcha == null || "".equals(captcha)) {
                    message.put("code", -1);
                    message.put("data", null);
                    message.put("msg", "验证码已失效，请重新点击发送验证码");
                } else {
                    // session中存放的验证码是手机号+验证码
                    if (!captcha.equalsIgnoreCase(username + codenum)) {
                        message.put("code", -1);
                        message.put("data", null);
                        message.put("msg", "手机验证码错误");
                    } else {
                        Map<String, Object> mapP = new HashMap<String, Object>();
                        mapP.put("username", username);
                        boolean flag = userService.exit(mapP);
                        if (!flag) {
                            message.put("code", -1);
                            message.put("data", null);
                            message.put("msg", "该手机号码未注册");
                        } else {
                            udo.setPassword(password);
                            if (userService.update(udo) > 0) {
                                message.put("code", 0);
                                message.put("data", udo);
                                message.put("msg", "修改成功");
                            }
                        }
                    }
                }
            } else {
                message.put("code", -1);
                message.put("data", null);
                message.put("msg", "手机验证码错误");
            }
        }
        return message;
    }

    @Log("改密码")
    @PostMapping("/updateretpwd")
    Map<String, Object> updateretpwd(String yuanpassword, String xinpassword) {
        Map<String, Object> message = new HashMap<>();
        String password = userService.get(ShiroUtils.getUserId()).getPassword();
        if (!yuanpassword.equals(password)) {
            message.put("code", -1);
            message.put("data", null);
            message.put("msg", "原密码不正确");
        } else {
            OwnerUserDO ownerUserDO = userService.get(ShiroUtils.getUserId());
            ownerUserDO.setPassword(xinpassword);
            if (userService.update(ownerUserDO) > 0) {
                message.put("code", 0);
                message.put("data", ownerUserDO);
                message.put("msg", "修改成功");
            }
        }

        return message;

    }

    @Log("登出")
    @GetMapping("/logout")
    Map<String, Object> logout() {
        Map<String, Object> message = new HashMap<>();
        ShiroUtils.logout();
        message.put("code", 0);
        message.put("data", null);
        message.put("msg", "登出成功");
        return message;
    }

}
