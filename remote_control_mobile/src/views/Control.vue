<template >
  <div>
    <div class="content">
      <img id="pics" :width="imgWidth-20" :height="imgHeight-20" >
    </div>
    <mt-tabbar fixed>
      <mt-tab-item @click.native="click_l">单击</mt-tab-item>
      <mt-tab-item @click.native="click_d">双击</mt-tab-item>
      <mt-tab-item @click.native="click_r">右击</mt-tab-item>
      <mt-tab-item @click.native="pic">截图</mt-tab-item>
      <mt-tab-item @click.native="key">键盘</mt-tab-item>
    </mt-tabbar>
  </div>
</template>
<script>
import { MessageBox } from "mint-ui";
export default {
  name: "control",
  data() {
    return {
      imgHeight: 0,
      imgWidth: 0
    };
  },
  created() {
    this.checkSize();
    this.startConn();
    
    
  },
  mounted() {
    // this.fullScreen();
  },
  methods: {
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
    startConn() {
      //全屏
      //按键
      // this.key();
      // this.isCon = true;
      //绑定右击事件
      // this.you();
      var mainService = new WebSocket(
        "ws://" + sessionStorage.hostIp + ":8084/mainService"
      );
      console.log("conn"+sessionStorage.hostIp);
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
        // console.log(msg);
        // document.getElementById("pics").src = msg.data;
        document.getElementById("pics").src =window.URL.createObjectURL( msg.data);
        mainService.send(
          JSON.stringify({
            errorCode: 0,
            errodMsg: "成功",
            data: null
          })
        );
      };
      mainService.onerror = () => {
        MessageBox("服务器通讯失败");
      };
      mainService.onclose = () => {
        MessageBox("连接关闭");
      };
    },
    click_l: function() {},
    click_d: function() {},
    click_r: function() {},
    pic: function() {},
    key: function() {},
    checkSize: function() {
      this.imgHeight = window.screen.availHeight - 40;
      this.imgWidth = window.screen.availWidth - 20;
      if (this.imgHeight > this.imgWidth) {
        var temp = this.imgHeight;
        this.imgHeight = this.imgWidth;
        this.imgWidth = temp;
      }
    }
  }
};
</script>
<style scoped>
@media (orientation: portrait) {
  .content {
    margin-top: -60px;
    -webkit-transform: rotate(90deg); /* Safari and Chrome */
    -moz-transform: rotate(90deg); /* Firefox */
    -ms-transform: rotate(90deg); /* IE 9 */
    -o-transform: rotate(90deg); /* Opera */
    transform: rotate(90deg);
  }
}
@media (orientation: landscape) {
  .content {
    margin-top: -60px;
  }
}
</style>
