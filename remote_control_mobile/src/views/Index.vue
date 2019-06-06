<template>
  <div>
    <!-- Header -->
    <div>
      <mt-header :title="page" class="heard" fixed>
        <!-- <mt-button icon="more" slot="right"></mt-button> -->
      </mt-header>
      <!-- <mt-spinner type="double-bounce" color="blue">
      </mt-spinner>-->
    </div>
    <!-- Header -->
    <div class="page-wrap">
      <!-- tabcontainer -->
      <mt-tab-container class="page-tabbar-container" v-model="page">
        <mt-tab-container-item id="首页">
          <div v-if="isOk">
            <!-- 首页 -->
            <mt-cell>
              <img bv slot="icon" src="../assets/pc.png" width="128" style="margin-top: 5px;" >
              <mt-button style="margin: 10px;" type="danger" @click="goto('/watch')">观&nbsp;看</mt-button>
            </mt-cell>
            <hr>
            <!-- <mt-button style="margin: 10px;" type="primary" @click="goto('/control')">控&nbsp;制</mt-button> -->

            <!-- 首页 -->
          </div>
          <div v-else>
            <br>
            <mt-button @click="isLogin" type="primary" size="large">请登录</mt-button>
          </div>
        </mt-tab-container-item>
        <mt-tab-container-item id="其他">
          <!-- 其他 -->
          <div class="functiones" v-if="isOk">
            <div class="cell">
              <!-- <img src="../assets/logo.png"> -->
              <p>远程关机</p>
            </div>
            <div class="cell">
              <!-- <img src="../assets/logo.png"> -->
              <p>远程下载</p>
            </div>
            <div class="cell">
              <!-- <img src="../assets/logo.png"> -->
              <p>剪贴板</p>
            </div>
            <div class="cell">
              <!-- <img src="../assets/logo.png"> -->
              <p>文件传输</p>
            </div>
          </div>
          <div v-else>
            <br>
            <mt-button @click="isLogin" type="primary" size="large">请登录</mt-button>
          </div>
          <!-- 其他 -->
        </mt-tab-container-item>

        <mt-tab-container-item id="我的">
          <div v-if="isOk">
            <h2>{{username}},你好</h2>
            <!-- 我的 -->
            <mt-button @click="loginOut" type="danger" size="large">退出</mt-button>
            <!-- 我的 -->
          </div>
          <div v-else>
            <br>
            <mt-button @click="isLogin" type="primary" size="large">请登录</mt-button>
          </div>
        </mt-tab-container-item>
      </mt-tab-container>
    </div>
    <mt-tabbar v-model="page" fixed>
      <mt-tab-item @click.native="isLogin()" id="首页">
        <img slot="icon" src="../assets/home.png">
        首页
      </mt-tab-item>
      <!-- <mt-tab-item @click.native="isLogin()" id="其他">
        <img slot="icon" src="../assets/logo.png">
        其他
      </mt-tab-item>-->
      <mt-tab-item @click.native="isLogin()" id="我的">
        <img slot="icon" src="../assets/user.png">
        我的
      </mt-tab-item>
    </mt-tabbar>
  </div>
</template>

<script>
// @ is an alias to /src
import { MessageBox } from "mint-ui";
export default {
  name: "index",
  data() {
    return {
      isOk: false,
      page: "首页",
      username: "",
      popupVisible: true
    };
  },
  created() {
    // sessionStorage.userName = "hello";
    this.isLogin();
    this.getServerHost();
    this.username = sessionStorage.userName;
  },
  methods: {
    login: function() {
      const html =
        '<input type="text" id="txtName" class="mint-searchbar-core" placeholder="请输入用户名"><hr style="border:none;background:skyblue;height: 1px;"><input type="password" class="mint-searchbar-core" id="txtPwd" placeholder="请输入密码"><hr style="border:none;background:skyblue;height: 1px;">';
      MessageBox({
        $type: "prompt",
        title: "登 录",
        showCancelButton: true,
        confirmButtonText: "登 录",
        message: html
      })
        .then(({ value, action }) => {
          var userName = document.getElementById("txtName").value;
          var pwd = document.getElementById("txtPwd").value;
          if (userName.length < 3 || pwd.length < 3) {
            MessageBox("提示", "用户名或密码长度必须大于3");
            return;
          }
          this.goLogin(userName, pwd);
          console.log(value, action);
        })
        .catch(err => {
          console.log(err);
        });
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
        console.log("error" + error);
      }),
        //收到信息时
        (loginService.onmessage = msg => {
          var result = JSON.parse(msg.data);
          if (result.errorCode == 0) {
            console.log("登录成功");
            this.setUserName(result.data);
          } else {
            MessageBox("提示", "用户名或密码错误");
            return;
          }
        });
      loginService.close = () => {
        MessageBox("提示", "连接已关闭");
        return;
      };
    },
    getServerHost: function() {
      sessionStorage.hostIp = document.location.hostname;
    },
    setUserName: function(userName) {
      sessionStorage.userName = userName;
      console.log(userName);
      this.username = userName;
      this.isOk = true;
      // document.location.reload();
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
    },
    loginOut: function() {
      sessionStorage.userName = null;
      this.isOk = false;
      console.log(this.isOk);
    },
    goto(path) {
      console.log("跳转");
      this.$router.push(path);
    }
  },
  components: {}
};
</script>
<style scoped>
.page-tabbar {
  overflow: hidden;
  height: 100vh;
}
.heard {
  font-size: 13px;
  height: 50px;
  
}
.page-wrap {
  padding-bottom: 60px;
}
.functiones {
  padding: 10px;
  display: flex;
  flex-wrap: wrap;
  background: white;
}
.functiones.cell {
  width: 26vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
}
.cell > img {
  padding: 20px;
  width: 50px;
}
hr {
  border: none;
  background: yellowgreen;
  height: 2px;
}
</style>

