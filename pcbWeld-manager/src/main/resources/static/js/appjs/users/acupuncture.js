var prefix = "/pcbWeld/acupunctureResult"
var id = $('#userId').val();

$(function() {
	load();
	
});

function load() {
	
	$('#exampleTable1')
	.bootstrapTable(
			{
				method : 'post', // 服务器数据的请求方式 get or post
				url : "/pcbWeld/acupunctureResult/getDetail/"+id, // 服务器数据的加载地址
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
						offset:params.offset
						/*checkDate : $("#checkDate").find("option:selected").text()*/
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
					/*	{
							checkbox : true
						},
														{
							field : 'tOptometryId', 
							title : 'id' 
						},*/
														{
							field : 'userId', 
							title : '用户ID' 
						},
														{
							field : 'firstCheckDate', 
							title : '第一次检查时间',
						},
														{
							field : 'firstCheckCompany', 
							title : '第一次检查单位' 
						},
														{
							field : 'firstCheckDoctor', 
							title : '第一次检查医生' 
						},
														{
							field : 'secondCheckDate', 
							title : '第二次检查时间' 
						},
														{
							field : 'secondCheckCompany', 
							title : '第二次检查单位' 
						},
														{
							field : 'secondCheckDoctor', 
							title : '第二次检查医生' 
						},
													{
							title : '操作',
							field : 'id',
							align : 'center',
							formatter : function(value, row, index) {
								var e = '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="编辑" onclick="edit(\''
										+ row.tAcupunctureId
										+ '\')"><i class="fa fa-edit"></i></a> ';
								return e ;
							}
						}
						]
			});
}

function add() {
	var addPage = layer.open({
		type : 2,
		title : '录入检查数据',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add/' + id // iframe的url
	});
	layer.full(addPage);
}

function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        
        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        
        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
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

function reLoad() {
	$('#exampleTable1').bootstrapTable('refresh');

}