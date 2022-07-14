// 本地环境
var fundBaseUrl = "http://10.86.130.106:8082";
//测试环境

// UAT环境
//var fundBaseUrl = "http://10.86.227.146:8080";

// 获取基金经理信息
function getManagerByFundCode(fundCode,func) {
    var param = {"header": {"requestSerialNo": "20200616180546123456"},
        "data": {
            "page": {"num": "1", "size": "1"},
            "obj": {"baseType": 1 ,"fundCodes": fundCode, "custodianFullName": "", "oaId": "", "manageStatus": "0"}
        }
    };
    // 查询基金经理信息
    $.ajax({
        type: "GET",
        url: fundBaseUrl + "/plms/info/get_fund_manager_info_list/v1",
        data: {param: JSON.stringify(param)},
        success: function (res) {
            func(res.data);
        }
    });
}

// 获取基金经理信息
function getManagerList (func) {
    var param = {"header": {"requestSerialNo": "20200616180546123456"},
        "data": {
            "page": {"num": "1", "size": "1"},
            "obj": {"fundCodes": "",baseType: "1", "custodianFullName": "", "oaId": "", "managerType": "1", "manageStatus": "0"}
        }
    };
    // 查询基金经理信息
    $.ajax({
        type: "GET",
        url: fundBaseUrl + "/plms/info/get_manager_info_list/v1",
        data: {param: JSON.stringify(param)},
        success: function (res) {
            func(res.data);
        }
    });
}