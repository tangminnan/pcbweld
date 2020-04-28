
var prefix = "/information/order"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
					//	showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset,
								orderStatus:$('#orderStatus').val()
					           // name:$('#searchName').val(),
					           // username:$('#searchName').val()
							};
						},
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
						columns : [
								{
									checkbox : true
								},
										/*						{
									field : 'id', 
									title : '' 
								},
									*/							{
									field : 'orderNo', 
									title : '订单编号'
								},
																{
									field : 'username',
									title : '用户'
								},
																{
									field : 'payType', 
									title : '支付方式',
									formatter:function (value) {
										if(value==0){
											return "积分兑换"
										}else if(value == 1){
											return "在线支付"
										}else{
											return ""
										}
                                    }
								},
																{
									field : 'shipmentTime', 
									title : '配送时间',
									formatter:function (value) {
										if(value==1){
											return "不限送货时间"
										}else if(value == 2){
											return "工作日送货"
										}else if(value == 3){
											return "双休日、假日送货"
										}else{
											return ""
										}
									}
								},
																{
									field : 'shipmentType', 
									title : '配送方式',
									formatter:function (value) {
										if(value==0){
											return "免运费"
										}else if(value == 1){
											return "运费"
										}else{
											return ""
										}
									}
								},
																{
									field : 'shipmentAmount', 
									title : '快递费' 
								},
																{
									field : 'invoiceType', 
									title : '发票类型',
										formatter:function (value) {
											if(value==1){
												return "不开发票"
											}else if(value == 2){
												return "电子发票"
											}else if(value == 3){
                                                return "普通发票"
                                            }else{
												return ""
											}
										}
                                   },
																{
									field : 'invoiceStatus', 
									title : '发票状态',
									formatter:function (value) {
										if(value==1){
											return "待开"
										}else if(value == 2){
											return "已开"
										}else{
											return ""
										}
									}
								},
																{
									field : 'orderStatus', 
									title : '订单状态',
									formatter:function (value) {
										if(value==1){
											return "已提交"
										}else if(value == 2){
											return "资料待审核"
										}else if(value == 3){
											return "审核未通过"
										}else if(value == 4){
                                            return "待支付"
                                        }else if(value == 5){
                                            return "物料寄送"
                                        }else if(value == 6){
                                            return "物料待审核"
                                        }else if(value == 7){
                                            return "待发货"
                                        }else if(value == 8){
                                            return "待收货"
                                        }else if(value == 9){
                                            return "已完成"
                                        }else{
											return ""
										}
									}
								},
																{
									field : 'postid', 
									title : '运单号' 
								},
																{
									field : 'createTime', 
									title : '创建时间' 
								},
																{
									field : 'updateTime',
									title : '更新时间' 
								},
																{
									field : 'orderAmount', 
									title : '订单金额' 
								},
																{
									field : 'orderScore', 
									title : '订单积分' 
								},
																{
									field : 'payAmount', 
									title : '支付总金额'
								},
																{
									field : 'buyNumber', 
									title : '商品总数量' 
								},
																{
									field : 'address', 
									title : '收货人地址'
								},
																{
									field : 'consignee', 
									title : '收货人' 
								},
																{
									field : 'mobile', 
									title : '收货手机号' 
								},
																{
									field : 'invoiceNo', 
									title : '公司税号' 
								},
																{
									field : 'invoiceTitle', 
									title : '公司抬头' 
								},
																{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ row.id
												+ '\')"><i class="fa fa-edit"></i></a> ';
										var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
												+ row.id
												+ '\')"><i class="fa fa-remove"></i></a> ';
										var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
												+ row.id
												+ '\')"><i class="fa fa-key"></i></a> ';
										var checkData = '<a class="btn btn-warning btn-sm '+s_checkData_h+'" href="#" title="审核"  mce_href="#" onclick="checkData(\''
                                            + row.id
                                            + '\')"><i class="fa fa-remove">审核</i></a> ';

										return e + d + checkData;
									}
								} ],
                        onLoadSuccess: function () {
							//如果状态是资料待审核状态
                            if ($('#orderStatus').val() == 2) {
                                $('#exampleTable').bootstrapTable('hideColumn', 'payType');//隐藏支付方式
                                $('#exampleTable').bootstrapTable('hideColumn', 'shipmentTime');//隐藏配送时间
                                $('#exampleTable').bootstrapTable('hideColumn', 'shipmentType');//配送类型
                                $('#exampleTable').bootstrapTable('hideColumn', 'shipmentAmount');//快递费
                                $('#exampleTable').bootstrapTable('hideColumn', 'invoiceType');//发票类型
                                $('#exampleTable').bootstrapTable('hideColumn', 'invoiceStatus');//发票类型
                                $('#exampleTable').bootstrapTable('hideColumn', 'orderStatus');//发票类型
                                $('#exampleTable').bootstrapTable('hideColumn', 'postid');//发票类型
                                $('#exampleTable').bootstrapTable('hideColumn', 'orderAmount');//发票类型
                                $('#exampleTable').bootstrapTable('hideColumn', 'orderScore');//发票类型
                                $('#exampleTable').bootstrapTable('hideColumn', 'payAmount');//发票类型
                                $('#exampleTable').bootstrapTable('hideColumn', 'buyNumber');//发票类型
                                $('#exampleTable').bootstrapTable('hideColumn', 'updateTime');//发票类型

                            }
                        }
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}

function resetPwd(id) {
}
function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {

	});
}