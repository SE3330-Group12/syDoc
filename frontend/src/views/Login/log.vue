<template>
    <div class="bg_intro">
      <div class="intro">
        <ul class="info">
          <li>
            <input type="text" id="name" v-model="usname" placeholder="用户名" @keyup="checkusernull"/>
<!--              <el-input v-model="input" type="text" placeholder="用户名" @blur="checkusernull"/>-->
          </li>
          <!-- <li>
          <input type="email" id='email' placeholder="电子邮件">
        </li> -->
          <li>
            <input type="password" id="password" placeholder="密码" @keyup="checkpasswordnull"/>
          </li>
          <!-- <li>
          <input type="password" id='SecPass' placeholder="再次确认密码">
        </li> -->
        </ul>
<!--        <router-link :to="{ name: 'home' }"-->
<!--          ><button id="btn">登 &nbsp;&nbsp; 录</button>-->
<!--        </router-link>-->
        <button id="btn" @click="login()" style="margin: 0 auto">登 &nbsp;&nbsp; 录</button>
        <router-link :to="{ name: 'Register' }"
          ><a style="margin: 0 auto">No account? Click here to Register</a></router-link
        >
        <br />
<!--        <div v-show="usshow"><p type="text" style="color: white">用户名不能为空</p></div>-->
<!--        <div v-show="passshow"><p type="text" style="color: white">密码不能为空</p></div>-->
<!--        <div v-show="passwrong"><p type="text" style="color: white">用户名或密码错误！</p></div>-->
      </div>  
    </div>
    <!-- <router-view></router-view> -->
</template>

<script>
// import useWebSocket from '../../webSocket/websocket';
// import { useRouter } from 'vue-router';
import {instance} from "@/axios/axios";
export default {
  data(){
    return {
      // usshow: false,
      // passshow: false,
      // passwrong:false,
      input:"",
      usname:this.$route.query.username,
    }
  },
  methods:{
    login() {
      // let usname = document.getElementById('name').value;
      let password = document.getElementById('password').value;
      // this.usshow = (usname == "");
      // this.passshow = (password == "");
      if(this.usname=="") this.checkusernull();
      if(password=="") {
        setTimeout(()=>{
          this.checkpasswordnull();
        },1);
      }
      if (this.usname != "" || password != "") {
        instance.get('/checkAccount', {
          params: {
            name: this.usname,
            password: password,
          }
        }).then(res => {
          if (res.data == "") {
            setTimeout(()=>{
              this.$message({
                showClose: true,
                message: '用户名或密码错误',
                type: 'error'
              });
            },1);
          }
          else {
            this.$router.push({
              path:'/Home',
              query: {
                accountid: res.data.accountId,
                usename: res.data.name,
                // projects:res.data.projects
              }
            })
          }
        }).catch(err => {
          console.log(err);
        })
        //     this.$router.push({
        //       //name: 'home',
        //       path:'/',
        //       query: {
        //         accountid:1,
        //         usename:"papa",
        //       }
        //     })
      }
    },
    checkusernull(){
      // let usname = document.getElementById('name').value;
      if(this.usname==""){
        this.$message({
          showClose: true,
          message: '用户名不能为空',
          type: 'error'
        });
      }
    },
    checkpasswordnull(){
      let password = document.getElementById('password').value;
      if(password==""){
        this.$message({
          showClose: true,
          message: '密码不能为空',
          type: 'error'
        });
      }
    }
  }
  // setup () {
  //   const ws = useWebSocket(handleMessage);
  //   const router = useRouter();
  //   const login = () => {
  //     let uname = document.getElementById("name");
  //     let pw = document.getElementById("password");
  //     ws.send(JSON.stringify({
  //       usename: uname,
  //       password: pw
  //     }));
  //   }
  //   function handleMessage(e) {
  //     router.push('/');
  //   }
  //   return{
  //     login
  //   }
  // }
};
</script>

<style scoped>
* {
  padding: 0;
  margin: 0;
}
li {
  list-style: none;
}
template,
body {
  text-align: center;
}
.bg_intro {
  height: 100%;
  width: 100%;
}
.bg_intro .intro {
  position: relative;
  background: #a2a2a298;
  border-radius: 20px;
  width: 30%;
  height: 65%;
  margin: 0 auto;
  margin-top: 5%;
}
.bg_intro .intro .info {
  position: relative;
  width: 80%;
  height: 300px;
  margin: auto auto;
  padding: 30px 0px 0px 0px;
}
input[type="text"],
input[type="password"],
textarea {
  display: block;
  width: 80%;
  height: 15px;
  padding: 12px;
  border-radius: 5px;
  background: #fff;
  border: none;
  outline: none;
  margin: 60px auto;
  text-transform: none;
  color: #383838;
  font-size: 14px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.4);
  font-family: "roboto", sans-serif;
}

::placeholder {
  color: rgba(46, 31, 31, 0.6);
}
#btn {
  /* position: relative; */
  width: 80%;
  height: 40px;
  line-height: 40px;
  margin: auto;
  background: #383838;
  color: #fff;
  font-weight: bold;
  border-radius: 5px;
  text-transform: capitalize;
  border: none;
  cursor: pointer;
  display: block;
  margin: -10px 45px;
}
a {
  /* position: relative; */
  font-size: 15px;
  text-align: center;
  display: inline-block;
  text-transform: capitalize;
  color: #1e2f92;
  font-weight: bold;
  padding: 10px 0px 0px 60px;
}
</style>