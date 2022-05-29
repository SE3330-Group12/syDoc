<template>
  <el-row class="myheader">
        <el-col :span="8">
          <span class="logo">
            <img src="../img/logo.png" />
          </span>
        </el-col>
        <el-col :span="11"></el-col>
        <el-col :span="5">
          <el-button type="plain" size="small" round class="mybutton">帮助</el-button>
          <el-button type="plain" size="small" round class="mybutton">项目</el-button>
          <router-link :to="{path:'/Personal',query:{accountid:this.aId}}">
          <el-button type="plain" size="small" round class="mybutton">账户</el-button>
          </router-link>
<!--          <el-button type="plain" size="small" round class="mybutton" @click="person">账户</el-button>-->
        </el-col>
      </el-row>
</template>

<script>
export default {
  data(){
    return {
      aId:this.$route.query.accountid,
    }
  },
  methods: {
    // person(){
    //   this.$router.push({
    //     path:'/Personal',
    //     query: {
    //       accountId: this.aId,
    //     }
    //   })
    //   console.log(this.aId)
    // }
  },
};
</script>

<style scoped>
.myheader{
  background-color:#000000;
  height:8.5vh;
  width: 100%;
  margin: 0;
}
.mybutton {
  margin-top: 2.5vh;
  margin-left:0.8vw;
}

button{
  font-weight: bolder;
  width: 4.5vw;
  height: 3.5vh;
}

.logo {
  width: 33vh;
  height: 9vh;
  float: left;
  overflow: hidden;
 
}

.logo img {
  width: 100%;
  height: 100%;
  margin-top: 0;
}

/* #header {
  background-color: rgb(12, 10, 44);
  color: black;
  line-height: 6vh;
  height: 6vh;
  width: 98%;
  text-align: right;
  padding: 1vh;
}

.button1 {
  vertical-align: middle;
  background-color: #4caf50;
  border: 0.3vh solid white;
  color: white;
  padding: 0.3vh 1vh;
  border-radius: 3vh;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 2vh;
}

.button1:hover {
  background-color: darkgreen;
}



.dropbtn {
  vertical-align: middle;
  background-color: #4caf50;
  border: 0.3vh solid white;
  color: white;
  padding: 0.3vh 1vh;
  border-radius: 3vh;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 2vh;
}

.dropbtn:hover,
.dropbtn:focus {
  background-color: darkgreen;
}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f1f1f1;
  min-width: 160px;
  overflow: auto;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown a:hover {
  background-color: #ddd;
}

.show {
  display: block;
} */
</style>