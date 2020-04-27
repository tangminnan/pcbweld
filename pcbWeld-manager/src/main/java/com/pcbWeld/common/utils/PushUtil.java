package com.pcbWeld.common.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;

public class PushUtil {
	/**
	 * 阿里推送工具类
	 * */
	public static void mobliePush(String accessKeyId,			//阿里账号key
								  String accessKeySecret,		//密钥
							      Long appKey,					//appkey
							      String target,				//推送目标
							      String targetValue,			//推送值，根据目标变化
							      String title,					//发送标题
							      String body,
							      Integer msgid) 					//发送内容
	{
		
		IClientProfile profile = DefaultProfile.getProfile("cn-beijing", accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        PushRequest pushRequest = new PushRequest();
        
     // 推送目标
        pushRequest.setAppKey(appKey);
        pushRequest.setTarget(target); //推送目标: DEVICE:按设备推送 ALIAS : 按别名推送 ACCOUNT:按帐号推送  TAG:按标签推送; ALL: 广播推送
        pushRequest.setTargetValue(targetValue); //根据Target来设定，如Target=DEVICE, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
   
        pushRequest.setPushType("NOTICE"); // 消息类型 MESSAGE NOTICE
        pushRequest.setDeviceType("ANDROID"); // 设备类型 ANDROID iOS ALL.
        pushRequest.setAndroidOpenType("ACTIVITY"); 	//点开通知后的活动
        pushRequest.setAndroidActivity("com.newsnon.teas.ui.activity.XiaoXiInfoActivity");
        pushRequest.setAndroidExtParameters("{msgid:"+msgid+"}");
        
        pushRequest.setAndroidNotificationChannel("1");
        
        // 推送配置
        pushRequest.setTitle(title); // 消息的标题
        pushRequest.setBody(body); // 消息的内容
        pushRequest.setAndroidNotifyType("SOUND");
        
        
        // 推送控制
        pushRequest.setStoreOffline(true); 
        PushResponse pushResponse;
		try {
			pushResponse = client.getAcsResponse(pushRequest);
			System.out.printf("RequestId: %s, MessageID: %s\n",
	        pushResponse.getRequestId(), pushResponse.getMessageId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
