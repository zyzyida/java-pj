var fail_time = 0       //定义全局的是否需要显示图文验证码 1:需要，其他：不需要
var tempToken = sessionStorage.getItem('tempToken')           //获取存在sessionStorage里的token
var redirect_uri = getQueryString('redirect_uri')
var agentid = getQueryString('agentid')
var appid = getQueryString('appid')
var state = getQueryString('state')
var uuidData
$().ready(function () {
  // 获取临时token
  getToken()

  // 如果redirect_uri不在同域名则修改为同域名
  if (redirect_uri && !trustList(hex_md5(redirect_uri.split('/')[2]))) {
    var temp_url = redirect_uri.split('?')[1]
    redirect_uri = location.origin + '/_tif_wwlocal_login_/?' + temp_url
  }
  //console.log(hex_md5("xtbgelk.digitalgd.com.cn"));
  // 校验数字证书认证后的access_token
  checkDigitalToken()

  //if(window.location.host === "xtbg.gdzwfw.gov.cn"){
  //  $('#captcha').val('')
  //}else{
  //  $('#captcha').val('aa123')
  //}
  //加载二维码
  var redirect_uri1
  if (redirect_uri && redirect_uri.indexOf('?') != -1) {
    redirect_uri1 = redirect_uri + '%26yhzxtype%3d1'
  } else {
    redirect_uri1 = redirect_uri + '%3fyhzxtype%3d1'
  }
  redirect_uri1 = redirect_uri1.replace('%23', '%2523')
 // window.WwLogin({
//   id: 'qrcodeContainer',
 //   appid: appid,
  // agentid: agentid,
   // redirect_uri: redirect_uri1,
  //  state: state,
  //  href: baseUrl + '/rz/css/WeChat.css'
 // })
 $("#scanLogin").click(function (){
    var QRCodeHasRender  = $("#qrcodeContainer iframe").length >= 1
    if (!QRCodeHasRender){
      window.WwLogin({
        id: "qrcodeContainer",
        appid: appid,
        agentid: agentid,
        redirect_uri: redirect_uri1,
        state: state,
        href: baseUrl + "/rz/css/WeChat.css",
      });
    }
  })
})

function getQueryString(name) {
  var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i')
  var r = window.location.search.substr(1).match(reg)
  if (r != null) return unescape(r[2])
  return null
}
//键盘ENTER键
document.onkeydown = function (event) {
  e = event ? event : window.event ? window.event : null
  if (e.keyCode == 13) {
    //执行的方法 alert('回车检测到了'); search();return false;
    $('#loginBtn').click()
  }
}
//通知消息
//$(".notic_header").show();

//输入框提示语
$('#username').focus(function () {
  $(this).removeClass('has-error').siblings('.form-explain').remove()
  $('#username').attr('placeholder', '请输入账号/手机号码/证件号码')
})
$('#password').focus(function () {
  $(this).removeClass('has-error').siblings('.form-explain').remove()
  $('#password').attr('placeholder', '请输入密码')
})
$('#captcha').focus(function () {
  $(this).removeClass('has-error').siblings('.form-explain').remove()
  $('#captcha').attr('placeholder', '请输入验证码')
})
/**
 * 获取uuid
 */
function getUUID() {
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    return (c === 'x' ? (Math.random() * 16) | 0 : 'r&0x3' | '0x8').toString(
      16
    )
  })
}
// 获取图形验证码
function getCaptcha() {
  uuidData = getUUID()
  var params = uuidData
  // // return baseUrl + '/rz/userLogin/dsfcaptcha.jpg?uuid=' + params //（生产保留）
  return baseUrl + '/pwd/authIdentity/userLogin/dsfcaptcha.jpg?uuid=' + params // (本地保留1)
  // return baseUrl + '/authIdentity/userLogin/dsfcaptcha.jpg?uuid=' + params // (本地保留)
}

function setIntervalFun() {
  setInterval(function () {
    setCodePicImg()
  }, 60000)
}
// setIntervalFun()
// 给img赋值url
function setCodePicImg() {

  $('#codePic_img').attr('src', getCaptcha())
}
// 手机号码二次认证
function gotoCheck(response) {
  // todo 此处的uid（用于checkphone页面的）与url上面的uid不是同一个，此处的uid是由接口返回的
  var uid =
    response &&
      response.uid
      ? response.uid
      : ''
  var account =
    response &&
      response.account
      ? response.account
      : ''
  var mobile =
    response &&
      response.mobile
      ? response.mobile
      : ''
  var source = 1
  var str = Base64.encode(
    'uid=' +
    uid +
    '&account=' +
    account +
    '&mobile=' +
    mobile +
    '&source=' +
    source
  )
  var strMobile = response.mobile
  window.location.href =
    // baseUrl + '/rz/checkphone?strMobile=' + strMobile + '&str=' + str //（生产保留原先）
    './checkPhone.html?strMobile=' + strMobile + '&str=' + str + '&redirect_uri=' + redirect_uri + '&state=' + state //(迁移后)
}
// setCodePicImg()
// 点击验证码图片更换新验证码
$('#codePic_img').click(function () {
  setCodePicImg()
  $('#captcha').attr('placeholder', '请输入验证码')
})
// 截取某个字符后所有字符串
function getCaption(obj) {
  var index = obj.lastIndexOf('\?')
  obj = obj.substring(index, obj.length)
  return obj
}
// 获取url某个值
function getParam(paramName) {
  ; (paramValue = ''), (isFound = !1)
  if (
    redirect_uri &&
    getCaption(redirect_uri).indexOf('?') == 0 &&
    getCaption(redirect_uri).indexOf('=') > 1
  ) {
    ; (arrSource = unescape(getCaption(redirect_uri))
      .substring(1, getCaption(redirect_uri).length)
      .split('&')),
      (i = 0)
    while (i < arrSource.length && !isFound)
      arrSource[i].indexOf('=') > 0 &&
        arrSource[i].split('=')[0].toLowerCase() == paramName.toLowerCase() &&
        ((paramValue = arrSource[i].split('=')[1]), (isFound = !0)),
        i++
  }
  return paramValue == '' && (paramValue = null), paramValue
}
if (getQueryString('code_auth')) {
  identificationApi.verifyCodeAuth(
    {
      code_auth: getQueryString('code_auth')
    },
    function (res) {
      if (res.status === 0) {
        var code = res.data
        identifyInfo(code)
      }
    }
  )
}
//登录操作
$('#loginBtn').click(function () {
  $('.has-error').removeClass('has-error')
  $('.form-explain').remove()
  var userName = $('#username').val()
  if (userName == null || userName == '') {
    $('#username').addClass('has-error').parent().append($('<div class="form-explain">账号不能为空</div>'))
    //alert("账号为空，请输入账号！");
    //$("#username").focus();
    return false
  }
  var passWord
  if ($('#password').val() == null || $('#password').val() == '') {
    $('#password').addClass('has-error').parent().append($('<div class="form-explain">密码不能为空</div>'))
    //$("#password").focus();
    return false
  } else {
    passWord = hex_md5($('#password').val())
  }
  if (fail_time == 1) {
    var captchaParams = $('#captcha').val()
    if ($('#captcha').val() == null || $('#captcha').val() == '') {
      $('#captcha').addClass('has-error').parent().append($('<div class="form-explain">验证码不能为空</div>'))
      return false
    }
    loginData(userName, passWord, uuidData, captchaParams)

  }
  else {
    requireFailTimes(function (res) {
      loginData(userName, passWord, uuidData)
    })
  }
})

// 姓名文本框失去焦点
$('#username').on('blur', function () {
  // 请求接口
  requireFailTimes()
});

function getToken(successFn) {
  // todo 从后端获取临时token
  if (!tempToken) {
    // tempToken为空，获取临时token
    $.ajax({
      type: 'post',
      // url: baseUrl + '/rz/userLogin/getTempToken', //（生产保留）
      url: baseUrl + '/pwd/authIdentity/userLogin/getTempToken', // (本地保留?生产是否保留pwd？)
      // url: baseUrl + '/authIdentity/userLogin/getTempToken', // (本地保留1)
      dataType: 'json',
      success: function (msg) {
        // 返回token，保存到sessionStorage  sessionStorage.setItem('tempToken',...)
        if (msg.status == 0) {
          sessionStorage.setItem('tempToken', msg.tempToken)
          tempToken = sessionStorage.getItem('tempToken')
        }
        typeof successFn === 'function' && successFn()
      },
      fail: function (e) {
        console.log('接口请求错误', e)
      }
    })
  }
}


function loginData(userName, passWord, uuidData, captchaParams) {
  var params = {
    userName: userName,
    passWord: passWord,
    uuid: uuidData,
    captcha: captchaParams
  }
  var value = window.encrypt(JSON.stringify(params))
  $.ajax({
    type: 'post',
    // url: baseUrl + '/rz/userLogin/login?ng=1', //（生产保留）
    url: baseUrl + '/pwd/authIdentity/userLogin/login?ng=1', // （本地保留?生产是否保留pwd？）
    // url: baseUrl + '/authIdentity/userLogin/login?ng=1', // （本地保留）
    headers: {
      tempToken: tempToken
    },
    contentType: "application/json;charset=utf-8",
    dataType: 'json',
    data: value,
    error: function (err) {

      console.error(err)
    },
    success: function (msg) {
      if (msg.status == 0) {
        if (redirect_uri == null) {
          tempToken = ''
          sessionStorage.removeItem('tempToken')
          getToken(function () {
            // window.location.href = './checkPhone.html?userName=' + userName + '&redirect_uri=' + redirect_uri + '&state=' + state
            window.location.href = baseUrl + '/'
          })
          // alert('登录成功！')
        } else {
          //alert("登录成功！")
          tempToken = ''
          sessionStorage.removeItem('tempToken')
          if (getParam('url')) {
            var getThisUrl = getParam('url').split('/')[2]
            if (trustList(hex_md5(getThisUrl))) {
              if (redirect_uri.indexOf('?') != -1) {
                //alert(redirect_uri); 
                window.location.href = redirect_uri + '&code=' + msg.code + '&state=' + state
              } else {
                //alert(redirect_uri);)
                window.location.href = redirect_uri + '?code=' + msg.code + '&state=' + state
              }
            }
          } else {
            window.location.href = baseUrl + '/login'
          }
        }
      } else if (msg.status == -1001) {
        //用户不存在
        $('#username').val('')
        $('#username').addClass('has-error').parent().append($('<div class="form-explain">' + msg.errmsg + '</div>'))
      }
      else if (msg.status == -1002) {
        //密码错误
        //alert(msg.errmsg);
        $('#password').val('')
        $('#password').addClass('has-error').parent().append($('<div class="form-explain">' + msg.errmsg + '</div>'))
      } else if (msg.status == 1005) {
        //登录成功，密码没重置 跳转到修改密码页面
        tempToken = ''
        sessionStorage.removeItem('tempToken')
        var currentUrl = window.location.href
        var account = $('#username').val()
        var index = currentUrl.indexOf('?')
        if (index != -1) {
          window.location.href =
            baseUrl +
            // redirectBaseUrl +
            '/pwd/#/changePassword?' +
            // '/#/changePassword?' +
            currentUrl.substring(index + 1, currentUrl.length) +
            '&account=' +
            account
        } else {
          window.location.href =
            // redirectBaseUrl + '/#/changePassword?account=' + account
            baseUrl + '/pwd/#/changePassword?account=' + account
        }
      }
      else if (msg.status == 1006) {
        // 需要进行二次认证
        gotoCheck(msg)
      } else if (msg.status == -1003) {
        // 验证码错误
        $('#captcha').val('')
        $('#captcha').addClass('has-error').parent().append($('<div class="form-explain">验证码错误或失效</div>'))
        setCodePicImg()
      }
      // 密码错误提示 或 冻结密码提示
      else if (msg.status == -1006 || msg.status == -1007) {
        $('#password').addClass('has-error').parent().append($('<div class="form-explain">' + msg.errmsg + '</div>'))
      }
      else {
        //其他错误
        alert(msg.errmsg)
      }
    }
  })
}

function requireFailTimes(callback) {
  // 请求登陆失败的次数
  // var userName = $('#username').val()
  $.ajax({
    // type: 'get',
    url: baseUrl + '/rz/userLogin/needValidate', // (生产保留)
    // url: baseUrl + '/authIdentity/userLogin/needValidate', // (本地保留)
    dataType: 'json',
    // data: {
    //   userName: userName
    // },
    headers: {
      tempToken: tempToken
    },
    success: function (msg) {
      if (msg.status == 0) {
        // 不需要校验码，不提示
        callback && callback(msg)
      }
      else if (msg.status == 1) {
        // 需要校验码的情况，出现图文验证码并且提示'验证码不能为空'
        fail_time = msg.status
        // $('#captcha').val('')
        setIntervalFun()
        setCodePicImg()
        $('#captcha_contain').css('display', 'block')
      }
      else {
        getToken()
        // alert(msg.errmsg)
      }
    },
    error: function (e) {
      console.log('接口请求错误', e)
    }
  })
}

function identifyInfo(code) {
  // 实名认证-人脸识别
  identificationApi.getMiniProgrameQrCode(
    {
      paasid: 'dzqz',
      scope: 'userinfo',
      from: 'aio'
    },
    function (qRes) {
      var stateIndex = 0
      if (qRes.errcode === 0) {
        var uuid = qRes.data.uuid
        var png = 'data:image/png;base64,' + qRes.data.pngbase64
        var IdentifyModal = $('body').identifyModal({
          image: png,
          retry: function () {
            identifyInfo(code)
          }
        })
        // 开始轮询状态
        var timer = setInterval(function () {
          identificationApi.queryQrCodeStatus(
            {
              uuid: uuid
            },
            function (res) {
              if (res.errcode === 0) {
                var state = res.data.state
                if (stateIndex !== state) {
                  stateIndex = state
                  IdentifyModal.setStatus(state)
                }
                if (state === 2) {
                  clearInterval(timer)
                  var qrcode = res.data.code
                  identificationApi.setUserInfo(
                    {
                      qrcode: qrcode,
                      code: code
                    },
                    function (uRes) {
                      if (uRes.status !== -1005) {
                        console.log('人脸识别成功')
                        if (redirect_uri == null) {
                          // alert('登录成功！')
                        } else {
                          //alert("登录成功！")
                          if (getParam('url')) {
                            var getThisUrl = getParam('url').split('/')[2]
                            if (trustList(hex_md5(getThisUrl))) {
                              if (redirect_uri.indexOf('?') != -1) {
                                //alert(redirect_uri);
                                window.location.href =
                                  redirect_uri + '&code=' + code
                              } else {
                                //alert(redirect_uri);)
                                window.location.href =
                                  redirect_uri + '?code=' + code
                              }
                            }
                          } else {
                            window.location.href = baseUrl + '/login'
                          }
                        }
                      } else {
                        console.log('人脸识别失败')
                        IdentifyModal.setStatus(0)
                      }
                    }
                  )
                }
              }
            }
          )
        }, 2000)
      }
    }
  )
}

// 跳转数字证书页面获取token
function getDigitalToken() {
  // 数字证书IP及对应ID
  var digitalIp;
  // 判断是否生产环境
  if (window.location.host === "xtbg.gdzwfw.gov.cn") {
    digitalIp = 'http://19.15.0.100/ca-rzpt/caunion/sggzyjypt/user/login'
  } else {
    digitalIp = 'http://210.76.71.77/caunion/sggzyjypt/user/login'
  }
  var digitalClientId = 'xtcs'
  // 数字证书页面回调地址，认证成功后会在地址后面带上token
  var digitalService = baseUrl + '/login'

  window.location.href =
    digitalIp + '?client_id=' + digitalClientId + '&service=' + digitalService
}

// 校验数字证书认证后的access_token，存在则使用其登录用户中心
function checkDigitalToken() {
  var winLocationHref = window.location.href
  var digitalTokenKey = 'access_token%253D'
  if (winLocationHref.indexOf(digitalTokenKey) > 1) {
    var digitalTokenValue = winLocationHref.substring(
      winLocationHref.indexOf(digitalTokenKey) + digitalTokenKey.length,
      winLocationHref.indexOf(digitalTokenKey) + digitalTokenKey.length + 36
    )
    $.ajax({
      type: 'post',

      // /rz/userLogin/loginByDigital
      url: baseUrl + '/rz/userLogin/loginByDigital',
      dataType: 'json',
      data: {
        accessToken: digitalTokenValue
      },
      success: function (msg) {
        if (msg.status == 0) {
          if (redirect_uri == null) {
            tempToken = ''
            sessionStorage.removeItem('tempToken')
            getToken(function () {
              window.location.href = baseUrl + '/'
            })
            // alert('登录成功！')
          } else {
            //alert("登录成功！")
            tempToken = ''
            sessionStorage.removeItem('tempToken')
            if (getParam('url')) {
              var getThisUrl = getParam('url').split('/')[2]
              if (trustList(hex_md5(getThisUrl))) {
                // 截取该access_token前面的url，防止跳转后暴露在地址栏
                redirect_uri = redirect_uri.substring(
                  0,
                  redirect_uri.indexOf('%3Faccess_token')
                )
                if (redirect_uri.indexOf('?') != -1) {
                  //alert(redirect_uri);
                  window.location.href = redirect_uri + '&code=' + msg.code
                } else {
                  //alert(redirect_uri);)
                  window.location.href = redirect_uri + '?code=' + msg.code
                }
              }
            } else {
              window.location.href = baseUrl + '/login'
            }
          }
        } else {
          //其他错误
          alert(msg.errmsg)
        }
      }
    })
  }
}

function forgotPassword() {
  var currentUrl = window.location.href
  var index = currentUrl.indexOf('?')
  if (index != -1) {
    window.location.href =
      baseUrl +
      '/pwd/#/forgotPassword?' +
      currentUrl.substring(index + 1, currentUrl.length)
  } else {
    window.location.href = baseUrl + '/pwd/#/forgotPassword'
  }
}

function changePassword() {
  var currentUrl = window.location.href
  var index = currentUrl.indexOf('?')
  if (index != -1) {
    window.location.href =
      baseUrl +
      '/pwd/#/changePassword?' +
      currentUrl.substring(index + 1, currentUrl.length)
  } else {
    window.location.href = baseUrl + '/pwd/#/changePassword'
  }
}

//切换tab页
$('.tabs-item').click(function () {
  $(this)
    .addClass('active')
    .siblings()
    .removeClass('active')
  $('.tab-pane')
    .eq($(this).index())
    .show()
    .siblings()
    .hide()
})
