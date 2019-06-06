<template>
  <div>
    <el-container>
      <el-header>
        <div v-if="!isOk">
          <el-button type="success" @click="isLogin" round>登录</el-button>
        </div>
        <div v-else>
          <div v-if="!isCon" style="float:left;">
            <el-button type="success" @click="startConn" round>开始同步</el-button>
          </div>
          <div v-else style="float:left;">
            <el-button type="warning" @click="stopConn" round>停止同步</el-button>
            <!-- <el-button type="success" @click="fullScreen" round>全屏</el-button> -->
          </div>
          <!-- <el-switch
            style="display: block float:right;"
            v-model="isControl"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="控制模式"
            inactive-text="观看模式"
          ></el-switch> -->
          <span>
            {{userName}},你好&nbsp;
            <el-button type="danger" @click="loginOut" round>退出</el-button>
          </span>
        </div>
      </el-header>
      <!-- :style="'height:'+(44+mainHeight)+'px;'" -->
      <el-main id="main">
        <div v-if="!isOk">
          <h2>请登录</h2>
        </div>
        <div v-else>
          <div v-if="isControl" style=" background-color: #eaffe0;">
            <!-- <button @click="test">测试</button> -->
            <!-- :style="'height:'+(mainHeight)+'px;width:'+(mainWidth)+'px;'" -->
            <img id="pics" draggable="false">
          </div>
          <div v-else style="background-color: #f1b7b5;">
            <img id="pics" draggable="false">
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>
<script>
export default {
  created() {
    //页面大小
    this.reSize();
    //获取服务器地址
    this.getServerHost();
    this.isLogin();
  },
  data() {
    return {
      mainHeight: 0,
      mainWidth: 0,
      isOk: false,
      userName: "",
      isControl: false,
      isCon: false
    };
  },
  mounted() {},
  methods: {
    //监听键盘
    key() {
      //判断是否连接
      var keyService = new WebSocket(
        "ws://" + sessionStorage.hostIp + ":9527/keyService"
      );
      //初始化事件绑定
      keyService.onopen = () => {
        keyService.send(
          JSON.stringify({
            errorCode: "4",
            errorMsg: "成功",
            data: JSON.stringify({
              height: this.mainHeight.toString(),
              width: this.mainWidth.toString()
            })
          })
        );
      };
      keyService.onmessage = msg => {
        if (msg.data != null) {
          console.log(msg.data);
        }
      };
      keyService.onerror = () => {
        this.$message("服务器通讯失败");
      };
      keyService.onclose = () => {
        this.$message("连接关闭");
      };
      document.addEventListener("keydown", e => {
        keyService.send(
          JSON.stringify({
            errorCode: "3",
            errorMsg: "成功",
            data: JSON.stringify({
              code: e.keyCode.toString()
            })
          })
        );
      });
      var scrollFunc = function(e) {
        e = e || window.event;
        if (e.wheelDelta) {
          //IE/Opera/Chrome
          //自定义事件：编写具体的实现逻辑
          if(e.deltaY>0){
            keyService.send(
          JSON.stringify({
            errorCode: "1",
            errorMsg: "成功",
            data: JSON.stringify({
             scale: "1"
            })
          })
        );
          }else{
            //下滚
            keyService.send(
          JSON.stringify({
            errorCode: "1",
            errorMsg: "成功",
            data: JSON.stringify({
              scale: "-1"
            })
          })
        );
          }
        } else if (e.detail) {
          //Firefox
          //自定义事件：编写具体的实现逻辑
          console.log("gun");
        }
      };
      if (document.addEventListener) {
        //firefox
        document.addEventListener("DOMMouseScroll", scrollFunc, false);
      }
      //滚动滑轮触发scrollFunc方法  //ie 谷歌
      window.onmousewheel = document.onmousewheel = scrollFunc;
      //这一步是为了阻止右击时系统默认的弹出框
      document.getElementById("main").oncontextmenu = function(e) {
        e.preventDefault();
      };
      //在这里定义事件的函数
      document.getElementById("main").onmouseup = function(oEvent) {
        if (!oEvent) oEvent = window.event;
        if (oEvent.button == 2) {
          //右击
          keyService.send(
            JSON.stringify({
              errorCode: "2",
              errorMsg: "成功",
              data: JSON.stringify({
                x: oEvent.offsetX.toString(),
                y: oEvent.offsetY.toString()
              })
            })
          );
          console.log("右击" + oEvent.offsetX + ":" + oEvent.offsetY);
        } else if (oEvent.button == 0) {
          //左击
          keyService.send(
            JSON.stringify({
              errorCode: "0",
              errorMsg: "成功",
              data: JSON.stringify({
                x: oEvent.offsetX.toString(),
                y: oEvent.offsetY.toString()
              })
            })
          );
          console.log(oEvent.offsetX + ":" + oEvent.offsetY);
        }
      };
    },
    //全屏显示
    fullScreen() {
      var docElm = document.getElementById("pics");
      if (docElm.requestFullscreen) {
        docElm.requestFullscreen();
      } else if (docElm.mozRequestFullScreen) {
        docElm.mozRequestFullScreen();
      } else if (docElm.webkitRequestFullScreen) {
        docElm.webkitRequestFullScreen();
      } else if (docElm.msRequestFullscreen) {
        docElm.msRequestFullscreen();
      }
    },
    //停止连接
    stopConn() {
      document.location.reload();
    },
    //开始连接
    startConn() {
      // //全屏
      // this.fullScreen();
      //按键
      this.key();
      //绑定右击事件
      // this.you();
      //绑定鼠标
      this.isCon = true;

      var mainService = new WebSocket(
        "ws://" + sessionStorage.hostIp + ":9527/mainService"
      );
      //初始化事件绑定
      mainService.onopen = () => {
        mainService.send(
          JSON.stringify({
            errorCode: 0,
            errodMsg: "成功",
            data: null
          })
        );
      };
      mainService.onmessage = msg => {
        document.getElementById("pics").src = window.URL.createObjectURL(
          msg.data
        );
        // if(msg.data!=null){

        // }
        mainService.send(
          JSON.stringify({
            errorCode: 0,
            errodMsg: "成功",
            data: null
          })
        );
      };
      mainService.onerror = () => {
        this.$message("服务器通讯失败");
      };
      mainService.onclose = () => {
        this.$message("连接关闭");
      };
    },
    keyDown: function(e) {
      var keynum;
      // <!-- 把键值转化为‘键码’ -->
      var keychar;
      if (window.event) {
        //IE浏览器下的事件
        keynum = e.keyCode;
      } else {
        //非IE浏览器下的事件
        keynum = e.which;
      }
      keychar = String.fromCharCode(keynum);
      if (keychar == "B") {
        alert("返回上一个画面");
      }
    },
    login() {
      this.$alert(
        '<input type="text" id="txtName" class="el-input__inner" placeholder="请输入用户名"><hr style="border:none;height: 1px;"><input type="password" class="el-input__inner" id="txtPwd" placeholder="请输入密码">',
        "登 录",
        {
          confirmButtonText: "登 录",
          dangerouslyUseHTMLString: true,
          callback: action => {
            //点击确定后
            if (action != "confirm") {
              return;
            }
            var userName = document.getElementById("txtName").value;
            var pwd = document.getElementById("txtPwd").value;
            if (userName.length < 3 || pwd.length < 3) {
              this.$message("用户名或密码长度必须大于3");
              return;
            }
            this.goLogin(userName, pwd);
            console.log(action);
          }
        }
      );
    },
    getServerHost: function() {
      sessionStorage.hostIp = document.location.hostname;
    },
    goLogin: function(userName, pwd) {
      var loginService = new WebSocket(
        "ws://" + sessionStorage.hostIp + ":9527/loginService"
      );
      //websocket开启时
      loginService.onopen = () => {
        console.log("login server");
        loginService.send(
          JSON.stringify({
            errorCode: 0,
            errorMsg: "成功",
            data: JSON.stringify({
              userName: userName,
              userPwd: pwd
            })
          })
        );
      };
      //出错误时
      (loginService.onerror = error => {
        if (error != null) this.$message("与服务器通讯失败！");
      }),
        //收到信息时
        (loginService.onmessage = msg => {
          var result = JSON.parse(msg.data);
          if (result.errorCode == 0) {
            console.log("登录成功");
            this.setUserName(result.data);
            //登陆后挂载右键监听
            // this.you();
          } else {
            this.$message("用户名或密码错误");
            return;
          }
        });
      loginService.close = () => {
        this.$message("连接已关闭");
        return;
      };
    },
    reSize: function() {
      this.mainHeight = window.screen.availHeight - 225;
      this.mainWidth = window.screen.availWidth - 200;
    },
    setUserName: function(userName) {
      sessionStorage.userName = userName;
      this.userName = userName;
      console.log(userName);
      this.isOk = true;
      // document.location.reload();
    },
    loginOut: function() {
      sessionStorage.userName = null;
      this.isOk = false;
    },
    isLogin: function() {
      if (
        sessionStorage.userName != null &&
        sessionStorage.userName != "null"
      ) {
        this.isOk = true;
      } else {
        this.isOk = false;
        this.login();
      }
    }
  }
};
</script>
<style scoped>
.el-header,
.el-footer {
  background-color: #fff9e0;

  color: #333;
  text-align: center;
  height: 60px;
  padding-top: 10px;
  text-align: right;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
}

.el-main {
  background-color: #c2defa;
  color: #333;
  text-align: center;
}

body > .el-container {
  margin-bottom: 10px;
}
</style>
