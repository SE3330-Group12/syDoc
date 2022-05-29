<template>
<!--  <h>person</h>-->
  <div>
    <br />
    <br />
    <div  style="width:10%;height: 10%;margin:  auto;">
      <img
          style="width:100%;height: 100%;border-radius: 100px"
          :src="imageUrl"
          class="image"
      >
    </div>

    <br>
    <br>
    <el-descriptions :column="1" style="width: 1200px;margin: auto" border>
      <el-descriptions-item label="用户ID" v-model="userID" class="message"><p class="message">{{userID}}</p></el-descriptions-item>
      <el-descriptions-item label="用户名" v-model="username" ><p class="message">{{username}}</p></el-descriptions-item>
      <el-descriptions-item label="邮箱号" v-model="email" class="message"><p class="message">{{email}}</p></el-descriptions-item>
<!--      <el-descriptions-item label="座右铭" v-model="motto" class="message" ><p class="message">{{motto}}</p></el-descriptions-item>-->
<!--      <el-descriptions-item label="居住地" v-model="city" class="message"><p class="message">{{city}}</p></el-descriptions-item>-->

    </el-descriptions>
    <br />
    <router-link to="Login">
    <el-button round style="width: 5%;height: 3%;font-size: 18px;font-weight: bold">退出登录</el-button>
    </router-link>
  </div>
</template>

<script>
import {instance} from "@/axios/axios";

export default {
  name: "personal center",
  data(){
    return{
      userID:1,
      username:"papa",
      email:"papa@sjtu.edu.cn",
      imageUrl:"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
    }
  },
  mounted() {
    instance.get('/getAccount',{
      params:{
        accountID:this.$route.query.accountid
      }
    }).then(res=>{
      this.userID=res.data.accountId;
      this.username=res.data.name;
      this.email=res.data.email;
    }).catch(err=>{
      console.log(err);
    })
  },
}
</script>

<style scoped>
.message{
  width: 20em;
  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
  font-size: 20px;
}
</style>