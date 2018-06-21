/** 
    * 检查字符串是否为合法手机号码 Email地址 身份证号码
    */  
$.extend($.fn.validatebox.defaults.rules, {
	phone: {    
        validator: function isPhone(value) {
			value == $(this).val(); 
            var bValidate = RegExp(/^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$/).test(value);  
            if (bValidate) {  
                return true;  
            } else {
            	return false;  
            } 
        },
        message: '您输入的手机号码不正确'
    },
   /* email: {
		validator:   function isEmail(value) {
	    	value == $(this).val(); 
	        var bValidate = RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(value);  
	        if (bValidate) {  
	            return true;  
	        } else {
	        	return false;  
	        } 
	   	},
	   	message: '您输入的电子邮箱不正确'
	},*/
	idcard: {
		validator:   function checkIdcard(num){
			num = $(this).val();
	        num = num.toUpperCase();
	        //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
	        if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num)))
	        {
	            return false;
	        }
	        //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
	        //下面分别分析出生日期和校验位
	        var len, re;
	        len = num.length;
	        if (len == 15)
	        {
	            re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
	            var arrSplit = num.match(re);
	     
	            //检查生日日期是否正确
	            var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
	            var bGoodDay;
	            bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
	            if (!bGoodDay)
	            {
	                return false;
	            }
	            else
	            {
	                    //将15位身份证转成18位
	                    //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
	                    var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
	                    var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
	                    var nTemp = 0, i;
	                    num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
	                    for(i = 0; i < 17; i ++)
	                    {
	                        nTemp += num.substr(i, 1) * arrInt[i];
	                    }
	                    num += arrCh[nTemp % 11];
	                    return true;
	            }
	        }
	        if (len == 18)
	        {
	            re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
	            var arrSplit = num.match(re);
	     
	            //检查生日日期是否正确
	            var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
	            var bGoodDay;
	            bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
	            if (!bGoodDay)
	            {
	                return false;
	            }
	        else
	        {
	            //检验18位身份证的校验码是否正确。
	            //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
	            var valnum;
	            var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
	            var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
	            var nTemp = 0, i;
	            for(i = 0; i < 17; i ++)
	            {
	                nTemp += num.substr(i, 1) * arrInt[i];
	            }
	            valnum = arrCh[nTemp % 11];
	            if (valnum != num.substr(17, 1))
	            {
	                return false;
	            }
	            return true;
	        }
	        }
	        return false;
	    },
	   	message: '您输入的身份证号不正确'
	},
	url: {
		validator: function(value){
			return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value);
		},
		message: '您输入的不合URL格式要求'
	},
	zipcode:{
		validator:function(value){
			return /[0-9]\d{5}(?!\d)/.test(value); //修改/[1-9]\d{5}(?!\d)/.test(value);
		},
		message: '您输入的邮编不正确'
	},
	ip:{
		validator:function(value){
			return /^((?:(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))))$/.test(value);
		},
		message: '您输入的IP地址格式不正确'
	},
	integer:{
		validator:function(value){
			return /^-?[1-9]\d*$/.test(value);
		},
		message: '只能输入整数'
	},
	chinese:{
		validator:function(value){
		  return /^[\u4e00-\u9fa5]+$/.test(value);
		},
		message: '只能输入中文'
	},
	number:{
		validator:function(value,param){
			try{
				parseInt(value);
				parseFloat(value);
			}catch(e){//非数字
				return false;
			}
			if(!jQuery.isArray(param))return true;
			if(param[0]==='' && param[1]!==''){
				this.message = "输入的数值必须小于或等于{1}";
				return Number(value) <= Number(param[1]);
			}
			else if(param[0] !== '' && param[1]===''){
				this.message = "输入的数值必须大于或等于{0}";
				return Number(value) >= Number(param[0]);
			}
			else{
				this.message = "输入的数值必须在{0}到{1}之间";
				return(Number(value) >= Number(param[0])) && (value <= Number(param[1]));
			}
			return true;
		},
		message: '输入的数值必须在{0}到{1}之间'
	}
});   