<template>
  <div class="bg_intro">
    <div class="intro">
      <ul class='info'>
        <li>
          <input type="text"  id='name' placeholder="用户名" @blur="usenameDuplicate">
<!--          <div v-show="useshow"><p type="text" style="color: white">该用户名已被使用 </p></div>-->
        </li>
        <li>
          <input type="email" id='email' placeholder="电子邮件">
        </li>
        <li>
          <input type="password" id='password' placeholder="密码">
        </li>
        <li>
          <input type="password" id='SecPass' placeholder="再次确认密码" @blur="secpass">
<!--          <div v-show="passshow"><p type="text" style="color: white">两次密码不同 </p></div>-->
        </li>
      </ul>
      <button id='btn' style="margin: auto" @click="register">创建账户</button>
      <router-link :to="{name:'Login'}"><a>Already have an account? Click here to Log In</a></router-link>
<!--      <div v-show="successShow"><p type="text" style="color: white">注册成功!</p></div>-->
<!--      <div v-show="failShow"><p type="text" style="color: white">注册失败!</p></div>-->
    </div>
  </div>
  <router-view></router-view>
</template>

<script>
import {instance} from "@/axios/axios";
export default {
  data(){
    return{
      // useshow:false,
      // passshow:false,
      // successShow:false,
      // failShow:false,
    }
  },
  methods:{
    usenameDuplicate (){
      let usename=document.getElementById('name').value;
      instance.get('/checkDup',{
        params:{
          name:usename
        }
      }).then(res=>{
        if(res.data)
          this.$message.error('该用户名已被使用');
        // this.useshow=!res.data;
      }).catch(err=>{
        console.log(err);
      })
    },
    secpass(){
      let oldpass=document.getElementById('password').value;
      let newpass=document.getElementById('SecPass').value;
      // this.passshow=!(newpass==oldpass);
      if(newpass!=oldpass)
        this.$message.error('两次密码不同');
    },
    register(){
      var usn=document.getElementById('name').value;
      var em=document.getElementById('email').value;
      var opass=document.getElementById('password').value;
      var npass=document.getElementById('SecPass').value;
      if(opass==npass){
        instance.post('/addAccount',null,{
          params:{
            name:usn,
            email:em,
            password:opass,
          }
        }).then(res=>{
          if(res.data){
            // this.useshow=false;
            // this.passshow=false;
            // this.failShow=false;
            // this.successShow=true;
            this.$message({
              message: '注册成功!',
              type: 'success'
            });
            this.$router.push({
              path:'/Login',
              query: {
                username:usn,
              }
            })
          }
          else{
            // this.successShow=false;
            // this.failShow=true;
            this.$message.error('注册失败!');
          }
          // console.log(res.data);
        }).catch(err=>{
          console.log(err);
        })
      }
      else
        // this.passshow=true;
        this.$message.error('两次密码不同');
    }
    // register(){
    //   var usn=document.getElementById('name').value;
    //   this.$message({
    //               message: '注册成功!',
    //               type: 'success'
    //             });
    //   this.$router.push({
    //     path:'/Login',
    //     query: {
    //       username:usn,
    //     }
    //   })
    // }
  }
}
</script>

<style scoped>
*{
  padding:0;
  margin:0;
}
li {
    list-style: none;
}
.bg_intro{
   position: fixed;
    /* background: url("../../img/") no-repeat; */
    background-size: cover;
    width: 100%;
    height: 100%;
}
.bg_intro .intro{
  position: relative;
  background: #E2E4E44A;
  border-radius: 20px;
  width:40%;
  height: 70%;
  margin:0 auto;
  margin-top:5%;
}
.bg_intro .intro .info{
  position: relative;
  width: 80%;
  height: 300px;
  margin:auto auto;
  padding:30px 0px 0px 0px;
}
input[type="text"],
input[type="password"],
input[type="email"],
textarea {
    display: block;
    width: 300px;
    height: 20px;
    padding: 12px;
    border-radius: 5px;
    background: #fff;
    border: none;
    outline: none;
    margin: 20px auto;
    text-transform: none;
    color: #383838;
    font-size: 14px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.4);
    font-family: 'roboto', sans-serif;
}

::placeholder {
    color: rgba(46, 31, 31, 0.6);
}
#btn{
  width: 300px;
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
    margin: 30px 0px;
}
a{
  position: relative;
  font-size: 15px;
  text-align: center;
  display: inline-block;
  text-transform: capitalize;
  color: #1e2f92;
  font-weight: bold;
  padding: 10px 0px 0px 45px;
}
</style>