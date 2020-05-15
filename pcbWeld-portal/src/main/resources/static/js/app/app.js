/**
 * 用户登录  登出
 */
function logout() {
    $.ajax({
        url: '/logout',
        type: 'get',
        success: function (message) {
            if (message.code == 0) {
                //跳转登录页面
                layer.msg(message.msg)
                setInterval("denglu()", 2000);//1000为1秒钟
            } else {
                layer.msg(message.msg)
                reload();
            }
        }
    })
}
function denglu() {
    window.location.href = '/denglu.html';
}

/**
 * 发票管理
 */
//保存  发票开票信息
function  saveKaiPiaoXinXi() {
    $.ajax({
        type: "POST",
        url: "/information/receipt/saveReceipt",
        data: {
            receiptCompany: $("#receiptCompany").val(),
            receiptBank: $("#receiptBank").val(),
            receiptPhone: $("#receiptPhone").val(),
            receiptNumber: $("#receiptNumber").val(),
            receiptAccount: $("#receiptAccount").val(),
            receiptAddress: $("#receiptAddress").val()
        },
        async: false,
        success: function (data) {
           if(data.code==0)
               window.location.href="/wode/"+1;
           else
               alert(data.msg);
        }
    });
}

//选择合适的订单开发票
var orderNos=[];
var totalAmount=0;
function chooseOderNoToReceipt(event){
    if($(event).next().text()=="全选" && event.checked){
        $(event).parents("li").siblings().find(":checkbox").prop("checked",true);
    }
    if($(event).next().text()=="全选" && !event.checked){
        $(event).parents("li").siblings().find(":checkbox").prop("checked",false);
    }
    totalAmount=0;
    $(":checked").each(function(index,item){
        if($(item).next().text()!="全选") {
            var orderNo = $(item).parent().siblings(".span1").text();
            var payAmount = $(item).parent().siblings(".span3").children("em").text().substr(1);
            payAmount = parseInt(payAmount);
            totalAmount += payAmount;
            orderNos.push(orderNo);
        }
    });
    $("#receiptAmount").text("￥"+totalAmount);
};

//开发票
function createReceipt(){

    if(totalAmount<1000){
        alert("累计金额到达1000元以上可申请开发票");
        return;
    }
   window.location.href="/information/receipt/createReceipt?orderNos="+orderNos;
};

//已开发票记录查看详情
function getOrderDetail(event){
    var orderarry = $(event).parent().siblings().eq(1).text();
    orderarry  =orderarry.split(" ");
    var tr="";
    for(var i=0;i<orderarry.length;i++){
        if(orderarry[i]!=""){
            tr+="<tr>";
            tr+="<td>"+orderarry[i]+"</td>";
            tr+="<td><span onclick='nowtogetorderdetail(this)'>查看详情</span></td>";
            tr+="</tr>";
        }
    }
    $("#ppt").find("tbody").html(tr);
    $("#ppt").show();
}

function nowtogetorderdetail(event){
    var orderNo   = $(event).parent().prev().text();
    console.info(orderNo);
    $("#ppt").hide();
    //跳转查看订单详情的方法
}

/**
 * 收货地址
 */
//新增、修改收货地址
function addUserAddress(){
    var id=$(".dizhi p:eq(4)").children("input").val(),url="";
    if(id=="")
        url="/information/address/saveAddress";
    else
        url="/information/address/editAddress";
    $.ajax({
        type: "POST",
        url: url,
        data: {
            consignee: $(".dizhi p:eq(0)").children("input").val(),
            mobile: $(".dizhi p:eq(1)").children("input").val(),
            dizhi: $(".dizhi p:eq(2)").children("input").val(),
            address: $(".dizhi p:eq(3)").children("input").val(),
            id:id
        },
        async: false,
        success: function (data) {
            if(data.code==0)
                window.location.href="/wode/"+1;
            else
                alert(data.msg);
        }
    });
}
//设为默认地址
function changeDefaultAddress(event){
    $(":button.on").removeClass("on").val("设为默认地址");
    $(event).addClass("on").val("默认地址");
   var id=$(event).parent().siblings().first().text();
    $.ajax({
        type : "POST",
        url : "/information/address/changeDefault",
        data:{id:id},
        async : false,
        success : function(data) {
            if(data.code==0){

            }else{
                alert(data.msg);
            }
        }
    });
};
//删除地址
function deleteAddress(event) {
    var id=$(event).parent().siblings().first().text();
    $.ajax({
        type : "POST",
        url : "/information/address/removeAddress",
        data:{id:id},
        async : false,
        success : function(data) {
            if(data.code==0){
                $(event).parents("tr").remove();
            }
            else{
                alert(data.msg);
            }
        }
    });
}
//修改地址
function editAddress(event) {
    $(".dizhi p:eq(0)").children("input").val($(event).parent().siblings().eq(1).text());
    $(".dizhi p:eq(1)").children("input").val($(event).parent().siblings().eq(3).text());
    $(".dizhi p:eq(2)").children("input").val($(event).parent().siblings().eq(4).text());
    $(".dizhi p:eq(3)").children("input").val($(event).parent().siblings().eq(2).text());
    $(".dizhi p:eq(4)").children("input").val($(event).parent().siblings().eq(0).text());
}

/**
 * 修改个人资料
 */
function editPerson(event){
    var name=$(event).siblings("div:eq(0)").children("p:eq(1)").children("input").val();
    var phone=$(event).siblings("div:eq(0)").children("p:eq(2)").children("input").val();
    var company=$(event).siblings("div:eq(1)").children("p:eq(1)").children("input").val();
    var qqNumber=$(event).siblings("div:eq(1)").children("p:eq(2)").children("input").val();
    $.ajax({
        type : "POST",
        url : "/user/editInfo",
        data:{name:name,phone:phone,company:company,qqNumber:qqNumber},
        async : false,
        success : function(data) {
            if(data.code==0){
                window.location.href="/wode/"+1;
            }
            else{
                alert(data.msg);
            }
        }
    });
}



