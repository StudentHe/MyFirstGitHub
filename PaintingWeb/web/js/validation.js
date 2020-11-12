/**
 *实现隐藏或显示错误信息的功能
 * @param onOff 输入框值是否不为空，值为true验证成功，隐藏错误信息，为false时，验证失败，显示错误信息
 * @param input 表单域选择器
 * @param errSelector 错误提示选择器
 * @param message  错误信息
 * @returns {boolean}
 */
function switchValid(onOff,input,errSelector,message)//js中函数的参数不用全传递，可以传递任意个数
{
    //第一个参数传递是输入框值是否存在，第二个参数是输入框标签的选择器，第三个是错误提示标签的选择器，第四个是错误提示信息字符串
    if(onOff==false)//当输入框值为空时
    {
        //为错误提示标签添加提示信息，为错误标签和输入框标签添加css类进行设置css样式
        $(errSelector).text(message);
        $(errSelector).addClass("error_message");
        $(input).addClass("error_input");
        return false;
    }else//当输入框值不为空时
    {
        $(errSelector).text("");//当输入项的值符合要求时，向提示标签添加空字符串覆盖之前的信息
        $(errSelector).removeClass("error_message");
        $(input).removeClass("error_input");
        return true;
    }
}

/**
 * 检查输入框值是否为空
 * @param input 表单域选择器
 * @param errSelector 错误提示域选择器
 * @returns {boolean} ture-校验成功 false-校验失败
 */
function  checkEmpty(input,errSelector)
{
     var val=$(input).val();//获得输入框的值
    if($.trim(val)=="")//如果输入框内容为空，会显示提示信息
    {
        switchValid(false,input,errSelector,"内容为空，请输入内容");
        return  false;
    }
    else{//如果不为空，会移除提示信息
        switchValid(true,input,errSelector);//当隐藏错误信息时，不用再switchValid()方法内添加错误信息参数
        return true;
    }

}

/**
 * 下拉列表必选功能，当没选择选项时会显示提示信息
 * @param input 表单域选择器
 * @param errSelector 错误提示域选择器
 * @returns {boolean} ture-校验成功 false-校验失败
 */
function checkCategory(input,errSelector)
{
    var val=$(input).val();
    if(val==-1)//jsp文件中设置的下拉列表的默认option的value值是-1，具体情况具体判断
    {
        switchValid(false,input,errSelector,"请选择油画类型");
        return false;
    }
    else
    {
        switchValid(true,input,errSelector);
        return true;
    }
}

/**
 * 对文件提交按钮进行设置，这里只考虑不了图片文件
 * 图片文件提交错误大概有三种情况，对这三种情况一次先后判断，实现图片提交校验功能
 * 1.未选择文件
 * 2.选择的文件的名称长度不符合规范
 * 3.文件的后缀名不符合格式
 * @param input 表单域选择器
 * @param errSelector 错误提示域选择器
 * @returns {boolean} ture-校验成功 false-校验失败
 */
function checkFile(input,errSelector)
{
    var val=$(input).val();

if(checkEmpty(input,errSelector)==false)//1.当文件提交项为空时，会显示提示信息，并返回false
{//显示错误信息在if括号是进行判断时里就已经实现了
    return  false;
}
 if(val.length<4)//当上传的文件名小于4位时，会显示提示信息，因为图片文件都是格式例如x.jpg,x.png,x.gif等等，其中x代表至少一个字符
{
    switchValid(false,input,errSelector,"请选择有效的图片");
    return false;
}
 var suffix=val.substring(val.length-3).toLowerCase();//获得图片的后缀名,并格式化为小写,因为有时候会存在大写，不方便判断
 if(suffix=="jpg"||suffix=="png"||suffix=="gif")//前两项if判断都不符合，然后这里后缀格式也对，就隐藏提示信息
{
    switchValid(true,input,errSelector);
    return true;
}
 else
 {
     switchValid(false,input,errSelector,"请选择有效的图片");
     return false;
 }

}

/**
 * 对通过正则表达式对价格格式进行校验
 * @param input 表单域选择器
 * @param errSelector 错误提示域选择器
 * @returns {boolean} ture-校验成功 false-校验失败
 */
function checkPrice(input,errSelector)
{
    var val=$(input).val();
    var p=/^[1-9][0-9]*$/;//设置一个价格格式的正则表达式
  if(!p.test(val))//当价格不符合正则表达式格式时，显示错误信息，当输入框为空时，也是此类情况
    {
        switchValid(false,input,errSelector,"请设置有效的价格");
        return false;
    }
    else//价格格式正确时，隐藏错误信息
    {
        switchValid(true,input,errSelector);
        return true;
    }
}

