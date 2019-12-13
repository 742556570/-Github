var plat = window.navigator.platform.toLowerCase();
var json = {"action":"fromJs", "jsMethod":"where are u"};
var userInfoKey = 'userInfoKey';

function postMessage(msg){
	if(plat == "iphone"){
		window.webkit.messageHandlers.iphone.postMessage(msg);
	}else{
		android.postMessage(msg);
	}
}

//获取超链的携带参数
function getQueryStr(LocString, str) { 
	var rs = new RegExp("(^|)" + str + "=([^&]*)(&|$)", "gi").exec(LocString), tmp; 
	if (tmp = rs) { 
		return tmp[2]; 
	} 
	return ""; 
} 

//钱转大写人命币
function upDigit(n)   
{  
    var fraction = ['角', '分'];  
    var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];  
    var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ];  
    var head = n < 0? '欠': '';  
    n = Math.abs(n);  

    var s = '';  

    for (var i = 0; i < fraction.length; i++)   
    {  
        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');  
    }  
    s = s || '整';  
    n = Math.floor(n);  

    for (var i = 0; i < unit[0].length && n > 0; i++)   
    {  
        var p = '';  
        for (var j = 0; j < unit[1].length && n > 0; j++)   
        {  
            p = digit[n % 10] + unit[1][j] + p;  
            n = Math.floor(n / 10);  
        }  
        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s;  
    }  
    return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');  
} 

//获取年月日
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + '年' + month + '月' + strDate + '日';
    return currentdate;
}
//获取年
function getYear(){
	var date = new Date();
	return date.getFullYear();
}
//获取月
function getMonth(){
	var date = new Date();
	return date.getMonth() + 1;
}
//获取日
function getDay(){
	var date = new Date();
	return date.getDate();
}
//判断字符是否为空
function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == ""){
        return true;
    }else{
        return false;
    }
}