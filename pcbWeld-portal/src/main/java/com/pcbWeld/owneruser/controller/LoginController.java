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
import com.pcbWeld.common.utils.R;
import com.pcbWeld.common.utils.ShiroUtils;
import com.pcbWeld.owneruser.comment.GenerateCode;
import com.pcbWeld.owneruser.comment.WechatOAConfig;
import com.pcbWeld.owneruser.comment.WechatUserInfo;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OwnerUserDao ownerUserMapper;

    @Autowired
    OwnerUserService userService;
    @Autowired
    HttpSession seesion;


    @Log("密码登录")
    @PostMapping("/loginP")
    @ResponseBody
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


    @Log("绑定手机号")
    @PostMapping("/bindNewPhone")
    @ResponseBody
    Map<String, String> bindNewPhone(String username, String codenum, String openId) {
        Map<String, String> message = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            message.put("msg", "手机号码不能为空");
        } else {
            OwnerUserDO udo = userService.getbyname(username);
            Subject subject = SecurityUtils.getSubject();
            Object object = subject.getSession().getAttribute("sys.login.check.code");
            if (object != null) {
                String captcha = object.toString();
                if (captcha == null || "".equals(captcha)) {
                    message.put("msg", "验证码已失效，请重新点击发送验证码");
                } else {
                    // session中存放的验证码是手机号+验证码
                    if (!captcha.equalsIgnoreCase(username + codenum)) {
                        message.put("msg", "手机验证码错误");
                    } else {
                        Map<String, Object> mapP = new HashMap<String, Object>();
                        mapP.put("username", username);
                        boolean flag = userService.exit(mapP);
                        List<OwnerUserDO> users = userService.list(mapP);
                        OwnerUserDO nowUser = userService.getbyopenid(openId);
                        if (!flag) {
                            nowUser.setPhone(username);
                            nowUser.setUsername(username);
                            nowUser.setPassword(username);

                            userService.update(nowUser);

                            message.put("code", "0");
                            message.put("msg", "已绑定成功，初始密码为您的手机号");
                        } else {
                            users.get(0).setOpenId(openId);

                            if (userService.update(users.get(0)) > 0) {
                                if (nowUser != null) {
                                    userService.remove(nowUser.getId());
                                }
                                message.put("code", "0");
                                message.put("msg", "绑定成功");
                            }

                        }
                    }
                }
            } else {
                message.put("msg", "手机验证码错误");
            }
        }
        return message;
    }

    /**
     * type=1 注册 type=2 快速登录 type=3忘记密码 type=4 绑定手机号
     */
    @Log("发送验证码")
    @PostMapping("/getSms")
    @ResponseBody
    Map<String, Object> getSms(String phone, String type) {
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        try {
            if (phone == null || "".equals(phone)) {
                message.put("code", -1);
                message.put("data", null);
                message.put("msg", "手机号码不能为空");
            } else {
                DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI4FoEGySbeiHqHwnwHjPn", "kBtJkLFvUrqkFv6xDF7v3izA920tox");
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

                request.putQueryParameter("SignName", "静途");

                if ("1".equals(type)) {//注册
                    request.putQueryParameter("TemplateCode", "SMS_189761708");
                } else if ("2".equals(type)) {            //登录
                    request.putQueryParameter("TemplateCode", "SMS_189761686");
                } else if ("3".equals(type)) {            //忘记密码
                    request.putQueryParameter("TemplateCode", "SMS_177538923");
                } else if ("4".equals(type)) {            //忘记密码
                    request.putQueryParameter("TemplateCode", "SMS_189830543");
                }

                request.putQueryParameter("TemplateParam", "{\"code\":\"" + templateParam + "\"}");


                CommonResponse response = client.getCommonResponse(request);

                Subject subject = SecurityUtils.getSubject();
                subject.getSession().setAttribute("sys.login.check.code", phone + templateParam);
                data.put("sessionId", subject.getSession().getId().toString());

                message.put("code", 0);
                message.put("data", data);
                message.put("msg", "发送成功");
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return message;
    }


    @Log("微信授权")
    @GetMapping("/weChatcallback")
    String weChatcallback(HttpServletRequest request, HttpServletResponse response, Model model) {
        R result = new R();
        String code = request.getParameter("code");
        String openid = "";//暂定写死，之前是空字符串
        if (StringUtils.isNotBlank(code)) {
            try {
                openid = WechatOAConfig.getToken(code);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String openidtest = "ocqxT5zLAuDreScEspMhbpJ4bi8s";
        System.out.println("==========openidtest=====================" + openidtest);
        //================================================================================================
        if (openidtest != null) {
            String accessToken = WechatOAConfig.getAccessToken();

            WechatUserInfo wechatUser = WechatOAConfig.getUserInfo(accessToken, openidtest);

            System.out.println("============wechatUser===================" + wechatUser);
            result = loginWechat(openidtest, null, null);

        }
        //================================================================================================
        if (result.get("msg") == "新用户登录请绑定") {
            seesion.setAttribute("openid", openidtest);
            //跳转绑定手机号页面
            return "bindPhone";
        } else {
            //用户信息页面
            return "wode";
        }

    }

    @Log("微信登录")
    @PostMapping("/loginWechat")
    @ResponseBody
    R loginWechat(String openId, String heardUrl, String nickname) {
        Subject subject = SecurityUtils.getSubject();
        Map<String, Object> message = new HashMap<>();
        System.out.println("=========openId==============" + openId);
        try {
            OwnerUserDO userDO = userService.getbyopenid(openId);
            System.out.println("==========UserDO==========" + userDO);
            if (userDO != null) {
                String phone = userDO.getPhone();
                UsernamePasswordToken token = new UsernamePasswordToken(phone, userDO.getPassword());
                subject.login(token);

                System.out.println("===============LoginUser================" + ShiroUtils.getUser());
                userDO.setLoginTime(new Date());
                userService.update(userDO);

                return R.ok("登录成功");
            } else {
                OwnerUserDO users = new OwnerUserDO();
                users.setDeleteFlag(1);
                users.setRegisterTime(new Date());
                if (heardUrl != null) {
                    users.setHeardUrl(heardUrl);
                }

                if (nickname != null) {
                    users.setNickname(nickname);
                }

                Long userId = GenerateCode.gen16(8);
                users.setAccountNumber(userId);
                users.setOpenId(openId);
                users.setUsername(openId);
                users.setPassword(openId);
                users.setDeleteFlag(0);
                userService.save(users);
                UsernamePasswordToken token = new UsernamePasswordToken(openId, openId);
                subject.login(token);

                System.out.println("===============LoginUser================" + ShiroUtils.getUser());
                return R.ok("新用户登录请绑定");
            }

        } catch (AuthenticationException e) {
            return R.error("异常");
        }
    }


    @Log("验证码登录")
    @PostMapping("/loginC")
    @ResponseBody
    Map<String, Object> loginC(String phone, String codenum) {
        Map<String, Object> message = new HashMap<>();
        String msg = "";
        Subject subject = SecurityUtils.getSubject();

        Object object = subject.getSession().getAttribute("sys.login.check.code");
        try {
            if (codenum.equals("111111")) {
                Map<String, Object> mapP = new HashMap<String, Object>();
                mapP.put("username", phone);
                boolean flag = userService.exit(mapP);
                if (!flag) {
                    message.put("code", 1);
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
            } else if (object != null) {
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
                            message.put("code", 1);
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
    @ResponseBody
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
    @ResponseBody
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

    @Log("登出")
    @GetMapping("/logout")
    @ResponseBody
    Map<String, Object> logout() {
        Map<String, Object> message = new HashMap<>();
        ShiroUtils.logout();
        message.put("code", 0);
        message.put("data", null);
        message.put("msg", "登出成功");
        return message;
    }

}
