(function(e){function t(t){for(var o,i,a=t[0],u=t[1],l=t[2],d=0,f=[];d<a.length;d++)i=a[d],r[i]&&f.push(r[i][0]),r[i]=0;for(o in u)Object.prototype.hasOwnProperty.call(u,o)&&(e[o]=u[o]);c&&c(t);while(f.length)f.shift()();return s.push.apply(s,l||[]),n()}function n(){for(var e,t=0;t<s.length;t++){for(var n=s[t],o=!0,a=1;a<n.length;a++){var u=n[a];0!==r[u]&&(o=!1)}o&&(s.splice(t--,1),e=i(i.s=n[0]))}return e}var o={},r={app:0},s=[];function i(t){if(o[t])return o[t].exports;var n=o[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,i),n.l=!0,n.exports}i.m=e,i.c=o,i.d=function(e,t,n){i.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},i.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},i.t=function(e,t){if(1&t&&(e=i(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(i.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var o in e)i.d(n,o,function(t){return e[t]}.bind(null,o));return n},i.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return i.d(t,"a",t),t},i.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},i.p="/";var a=window["webpackJsonp"]=window["webpackJsonp"]||[],u=a.push.bind(a);a.push=t,a=a.slice();for(var l=0;l<a.length;l++)t(a[l]);var c=u;s.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"034f":function(e,t,n){"use strict";var o=n("64a9"),r=n.n(o);r.a},"56d7":function(e,t,n){"use strict";n.r(t);n("cadf"),n("551c"),n("f751"),n("097d");var o=n("2b0e"),r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"app"}},[n("div",{attrs:{id:"nav"}}),n("router-view")],1)},s=[],i=(n("034f"),n("2877")),a={},u=Object(i["a"])(a,r,s,!1,null,null,null),l=u.exports,c=n("8c4f"),d=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("el-container",[n("el-header",[e.isOk?n("div",[e.isCon?n("div",{staticStyle:{float:"left"}},[n("el-button",{attrs:{type:"warning",round:""},on:{click:e.stopConn}},[e._v("停止同步")])],1):n("div",{staticStyle:{float:"left"}},[n("el-button",{attrs:{type:"success",round:""},on:{click:e.startConn}},[e._v("开始同步")])],1),n("span",[e._v("\n          "+e._s(e.userName)+",你好 \n          "),n("el-button",{attrs:{type:"danger",round:""},on:{click:e.loginOut}},[e._v("退出")])],1)]):n("div",[n("el-button",{attrs:{type:"success",round:""},on:{click:e.isLogin}},[e._v("登录")])],1)]),n("el-main",{attrs:{id:"main"}},[e.isOk?n("div",[e.isControl?n("div",{staticStyle:{"background-color":"#eaffe0"}},[n("img",{attrs:{id:"pics",draggable:"false"}})]):n("div",{staticStyle:{"background-color":"#f1b7b5"}},[n("img",{attrs:{id:"pics",draggable:"false"}})])]):n("div",[n("h2",[e._v("请登录")])])])],1)],1)},f=[],g=(n("6b54"),n("f499")),m=n.n(g),p={created:function(){this.reSize(),this.getServerHost(),this.isLogin()},data:function(){return{mainHeight:0,mainWidth:0,isOk:!1,userName:"",isControl:!1,isCon:!1}},mounted:function(){},methods:{key:function(){var e=this,t=new WebSocket("ws://"+sessionStorage.hostIp+":9527/keyService");t.onopen=function(){t.send(m()({errorCode:"4",errorMsg:"成功",data:m()({height:e.mainHeight.toString(),width:e.mainWidth.toString()})}))},t.onmessage=function(e){null!=e.data&&console.log(e.data)},t.onerror=function(){e.$message("服务器通讯失败")},t.onclose=function(){e.$message("连接关闭")},document.addEventListener("keydown",function(e){t.send(m()({errorCode:"3",errorMsg:"成功",data:m()({code:e.keyCode.toString()})}))});var n=function(e){e=e||window.event,e.wheelDelta?e.deltaY>0?t.send(m()({errorCode:"1",errorMsg:"成功",data:m()({scale:"1"})})):t.send(m()({errorCode:"1",errorMsg:"成功",data:m()({scale:"-1"})})):e.detail&&console.log("gun")};document.addEventListener&&document.addEventListener("DOMMouseScroll",n,!1),window.onmousewheel=document.onmousewheel=n,document.getElementById("main").oncontextmenu=function(e){e.preventDefault()},document.getElementById("main").onmouseup=function(e){e||(e=window.event),2==e.button?(t.send(m()({errorCode:"2",errorMsg:"成功",data:m()({x:e.offsetX.toString(),y:e.offsetY.toString()})})),console.log("右击"+e.offsetX+":"+e.offsetY)):0==e.button&&(t.send(m()({errorCode:"0",errorMsg:"成功",data:m()({x:e.offsetX.toString(),y:e.offsetY.toString()})})),console.log(e.offsetX+":"+e.offsetY))}},fullScreen:function(){var e=document.getElementById("pics");e.requestFullscreen?e.requestFullscreen():e.mozRequestFullScreen?e.mozRequestFullScreen():e.webkitRequestFullScreen?e.webkitRequestFullScreen():e.msRequestFullscreen&&e.msRequestFullscreen()},stopConn:function(){document.location.reload()},startConn:function(){var e=this;this.key(),this.isCon=!0;var t=new WebSocket("ws://"+sessionStorage.hostIp+":9527/mainService");t.onopen=function(){t.send(m()({errorCode:0,errodMsg:"成功",data:null}))},t.onmessage=function(e){document.getElementById("pics").src=window.URL.createObjectURL(e.data),t.send(m()({errorCode:0,errodMsg:"成功",data:null}))},t.onerror=function(){e.$message("服务器通讯失败")},t.onclose=function(){e.$message("连接关闭")}},keyDown:function(e){var t,n;t=window.event?e.keyCode:e.which,n=String.fromCharCode(t),"B"==n&&alert("返回上一个画面")},login:function(){var e=this;this.$alert('<input type="text" id="txtName" class="el-input__inner" placeholder="请输入用户名"><hr style="border:none;height: 1px;"><input type="password" class="el-input__inner" id="txtPwd" placeholder="请输入密码">',"登 录",{confirmButtonText:"登 录",dangerouslyUseHTMLString:!0,callback:function(t){if("confirm"==t){var n=document.getElementById("txtName").value,o=document.getElementById("txtPwd").value;n.length<3||o.length<3?e.$message("用户名或密码长度必须大于3"):(e.goLogin(n,o),console.log(t))}}})},getServerHost:function(){sessionStorage.hostIp=document.location.hostname},goLogin:function(e,t){var n=this,o=new WebSocket("ws://"+sessionStorage.hostIp+":9527/loginService");o.onopen=function(){console.log("login server"),o.send(m()({errorCode:0,errorMsg:"成功",data:m()({userName:e,userPwd:t})}))},o.onerror=function(e){null!=e&&n.$message("与服务器通讯失败！")},o.onmessage=function(e){var t=JSON.parse(e.data);0==t.errorCode?(console.log("登录成功"),n.setUserName(t.data)):n.$message("用户名或密码错误")},o.close=function(){n.$message("连接已关闭")}},reSize:function(){this.mainHeight=window.screen.availHeight-225,this.mainWidth=window.screen.availWidth-200},setUserName:function(e){sessionStorage.userName=e,this.userName=e,console.log(e),this.isOk=!0},loginOut:function(){sessionStorage.userName=null,this.isOk=!1},isLogin:function(){null!=sessionStorage.userName&&"null"!=sessionStorage.userName?this.isOk=!0:(this.isOk=!1,this.login())}}},h=p,v=(n("b851"),Object(i["a"])(h,d,f,!1,null,"2125faf5",null)),w=v.exports;o["default"].use(c["a"]);var S=new c["a"]({routes:[{path:"/",name:"home",component:w}]}),b=n("2f62");o["default"].use(b["a"]);var y=new b["a"].Store({state:{},mutations:{},actions:{}}),k=n("5c96"),C=n.n(k);n("0fae");o["default"].use(C.a),o["default"].config.productionTip=!1,new o["default"]({router:S,store:y,render:function(e){return e(l)}}).$mount("#app")},"64a9":function(e,t,n){},a4c1:function(e,t,n){},b851:function(e,t,n){"use strict";var o=n("a4c1"),r=n.n(o);r.a}});
//# sourceMappingURL=app.2b906717.js.map