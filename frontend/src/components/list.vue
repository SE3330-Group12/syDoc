<template>
  <!-- 搜索项目栏 -->
  <div class="search_bar">
    <el-input
        @input="recoverdata"
        @keyup.enter="searchnow"
        type="text"
        v-model="input"
        placeholder="请输入要查找的项目名称"
    />
    <el-button type="primary" @click="searchnow">搜索</el-button>
    <!-- <el-button type="primary" @click="addBooks">添加</el-button> -->
  </div>

  <!-- 显示数据主体 -->
  <div class="booklist">
    <el-table :data="ShowData" border>
      <el-table-column type="selection" width="55" />
      <el-table-column prop="name" label="标题" width="250">
        <template v-slot="scope">
          <!-- <el-link type="primary" @click="seeblog(scope.row)">查看详情</el-link> -->
          <a :href="getHref(scope.row.id, scope.row.name,scope.row.type)">{{ scope.row.name }}</a>
        </template>
      </el-table-column>
      <el-table-column prop="author" label="创建者" width="200" />
      <el-table-column prop="type" label="类型" width="200" />
      <el-table-column label="操作">
        <template #default="scope">
<!--          <el-tooltip content="下载" placement="top">-->
<!--            <el-button-->
<!--              type="primary"-->
<!--              color="#3F3F3F"-->
<!--              @click="downloadDialogVisible = true"-->
<!--              ><img src="../img/下载.png" alt=""-->
<!--            /></el-button>-->
<!--          </el-tooltip>-->
          <el-tooltip content="分享" placement="top">
            <el-button
              type="primary"
              color="#3F3F3F"
              @click="shareDialogVisible = true;this.showdid=scope.row.id;this.dauthor=scope.row.author;share()"
              ><img src="../img/分享.png" alt=""
            /></el-button>
          </el-tooltip>
          <el-tooltip content="团队管理" placement="top">
            <el-button
                type="primary"
                color="#3F3F3F"
                @click="teamDialogVisible = true;this.showdid=scope.row.id;this.dauthor=scope.row.author;share()"
            ><img src="../img/创作.png" alt=""
            /></el-button>
          </el-tooltip>
<!--          <el-tooltip content="编辑历史" placement="top">-->
<!--            <el-button type="primary" color="#3F3F3F" @click="drawer = true"-->
<!--              ><img src="../img/播放记录.png" alt=""-->
<!--            /></el-button>-->
<!--          </el-tooltip>-->
          <el-popconfirm
            title="您确定要删除这个文档吗"
            confirm-button-text="确定"
            cancel-button-text="取消"
            @confirm="deleteDoc(scope.row)"
          >
            <template #reference>
              <el-button type="primary" color="#3F3F3F"
                ><img src="../img/删除.png" alt=""
              /></el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分享选项 -->
    <el-dialog
      v-model="shareDialogVisible"
      title="文件分享"
      width="50%"
      :before-close="handleClose"
    >
      <el-divider />
      <router-link to="">{{this.code}}</router-link>
      <el-divider />
      <div v-for="user in shareUser">
              <el-row>
                <el-col :span="2"></el-col>
                <el-col :span="10">{{user.username}}</el-col>
                <el-col :span="10">{{user.userpower}}</el-col>
<!--                <el-col :span="4"></el-col>-->
              </el-row>
        <el-divider border-style="dashed" />
      </div>
      <template #footer>
      <el-row>
        <el-col :span="5"></el-col>
        <el-col :span="5">
          <el-select v-model="value" class="m-2" placeholder="选择分享权限">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-col>
        <el-col :span="5"></el-col>
        <span class="dialog-footer">
<!--          <el-button @click="shareDialogVisible = false">可以编辑</el-button>-->
          <el-button type="primary" @click="getInvatationCode()">生成邀请码</el-button>
        </span>
      </el-row>
      </template>
      <br />
    </el-dialog>

<!--团队管理-->
    <el-dialog
        v-model="teamDialogVisible"
        title="团队管理"
        width="50%"
        :before-close="handleClose"
    >
      <el-divider />
      <div v-for="user in shareUser">
        <el-row>
          <el-col :span="7">{{user.username}}</el-col>
          <el-col :span="7">{{user.userpower}}</el-col>
          <el-popconfirm
              title="您确定要修改此成员的权限?"
              confirm-button-text="确定"
              cancel-button-text="取消"
              @confirm="changePower(user.userId)"
          >
            <template #reference>
              <el-button type="primary" color="#3F3F3F"
                         :disabled="(this.dauthor==this.user.name)&&(this.dauthor==user.username)"
              ><img src="../img/创作.png" alt=""
              /></el-button>
            </template>
          </el-popconfirm>
          <el-col :span="1"></el-col>
          <el-tooltip content="删除成员" placement="top">
            <el-button
                type="primary"
                color="#3F3F3F"
                @click="deleteUser(user.userId)"
                :disabled="(this.dauthor==this.user.name)&&(this.dauthor==user.username)"
            ><img src="../img/删除.png" alt=""
            /></el-button>
          </el-tooltip>
        </el-row>
        <el-divider />
      </div>
      <br />
    </el-dialog>

  </div>
</template>

<script>
import { ElMessageBox, ElMessage } from "element-plus";
import { tableData } from "../main.js";
import {instance} from "@/axios/axios";

export default {
  name: "booklist",
  data() {
    return {
      user:{
        id:null,
        name:"",
      },
      input: "",
      shareInput: "",
      value: null,
      drawer: false,
      downloadDialogVisible: false,
      shareDialogVisible: false,
      teamDialogVisible: false,
      showdid:0,
      dauthor:"",
      DocData: [
        // {
        //   id: 1, name: "item.name", author: "You",type:"text", time: "just now"
        // },
        // {
        //   id: 2, name: "item.name", author: "papa",type:"excel", time: "just now"
        // },
        // {
        //   id: 3, name: "item.name", author: "papa",type:"report template", time: "just now"
        // },
        // {
        //   id: 4, name: "item.name", author: "papa",type:"resume template", time: "just now"
        // }
      ],
      ShowData:[
      ],
      tmp:[],
      options: [
        {
          value: 1,
          label: "Edit",
        },
        {
          value: 2,
          label: "Read",
        },
      ],
      shareUser:[
        // {
        //   userId:1,
        //   username:"User1",
        //   userpower:"Edit",
        // },
        // {
        //   userId: 2,
        //   username: "User2",
        //   userpower: "Read",
        // },
        // {
        //   userId: 3,
        //   username: "papa",
        //   userpower: "Creator",
        // },
      ],
      sourceString:"431EYZDOWGVJ5AQMSFCU2TBIRPN796XH0KL",
      code:""
    };
  },
  created() {
    // this.$route.params.projects.forEach(item=>{
    //   this.showData.push({name:item.name,author: "You",time: "just now"});
    // })

    this.user.id = this.$route.query.accountid;
    this.user.name = this.$route.query.usename;
    console.log(this.user);
    instance.get('/getDocList', {
      params: {
        userId: this.user.id,
      }
    }).then(res => {
      res.data.forEach(item => {
        this.DocData.push({id: item.id, name: item.name, author: item.author, type: item.type,time: "just now"});
      })
    }).catch(err => {
      console.log(err);
    })
      // this.projects.forEach(item=>{
      //       this.showData.push({id:item.id,name:item.name,author:"You",time:"just now"});
      //     })
      // //   vmson.$on("addDocument",(val)=>{
      // //   console.log(val);
      // // })
    this.ShowData=this.DocData;
    },
  // },
  methods:{
    getHref(val, val2,type){
      if(type=="text") {
        return '/Pages?docid='+val+'&docname='+val2+'&userid='+this.$route.query.accountid;
      }
      if(type=="excel"){
          return '/Excel?docid='+val+'&docname='+val2+'&username='+this.$route.query.usename;
      }
      if(type=="report template"){
        return '/CovLetter?docid='+val+'&docname='+val2+'&userid='+this.$route.query.accountid;
      }
      if(type=="resume template"){
        return '/Resume?docid='+val+'&docname='+val2+'&userid='+this.$route.query.accountid;
      }
    },
    fresh(){
      // console.log(this.showData);
      this.DocData=[];
      instance.get('/getDocList', {
        params: {
          userId: this.user.id,
        }
      }).then(res => {
        res.data.forEach(item => {
          this.DocData.push({id: item.id, name: item.name, author: item.author,type: item.type, time: "just now"});
        })
      }).catch(err => {
        console.log(err);
      })
      this.ShowData=this.DocData;
    },
    share(){
      this.shareUser=[];
      instance.get('/getUsers',{
        params:{
          documentId:this.showdid
        }
      }).then(res=>{
        res.data.forEach(item =>{
          this.shareUser.push({userId:item.userId,username:item.name, userpower: item.power});
        })
      }).catch(err =>{
        console.log(err);
      })
    },
    getInvatationCode(){
      this.code="";
      let num=this.showdid*10+this.value;
      console.log(num);
      // let pow=this.value;
      while (num>0) {
        let mod = num % 35;
        // console.log(mod);
        num = (num - mod) / 35;
        // console.log(num);
        this.code = this.sourceString.substring(mod,mod+1) + this.code;
        // console.log(this.code);
      };
      // console.log(this.code);
      this.code = '88888888' + this.code;//长度不足8，前面补全
      this.code = this.code.slice(this.code.length - 8,this.code.length);//截取最后8位字符串
    },
    allDoc(){
      this.ShowData=this.DocData;
    },
    createdDoc(){
      this.ShowData=[];
      this.DocData.forEach(item=>{
        if(item.author==this.user.name){
          this.ShowData.push(item);
        }
      })
    },
    sharedDoc(){
      this.ShowData=[];
      this.DocData.forEach(item=>{
        if(item.author!=this.user.name){
          this.ShowData.push(item);
        }
      })
    },
    deleteDoc(Doc){
      instance.post('/deleteDocument',null,{
        params:{
          docId:Doc.id
        }
      }).then(res=>{
        if(res.data){
          this.$options.methods.fresh.bind(this)();
        }
      }).catch(err =>{
        console.log(err);
      })
    },
    changePower(uid){
      // console.log(uid);
      instance.post('/changePower',null,{
        params:{
          userName:uid,
          documentId:this.showdid
        }
      }).then(res =>{
        if(res.data){
          this.$options.methods.share.bind(this)();
        }else{
          this.$message.error("修改成员权限失败")
        }
      }).catch(err =>{
        console.log(err);
      })
    },
    deleteUser(uid){
      instance.post('/deleteUser',null,{
        params:{
          userId:uid,
          documentId:this.showdid
        }
      }).then(res =>{
        if(res.data){
          this.$options.methods.share.bind(this)();
        }else{
          this.$message.error("删除成员失败")
        }
      }).catch(err =>{
        console.log(err);
      })
    },
    //   搜索实现
    searchnow() {
      let searchitem = [];
      this.tmp=this.ShowData;
      var temp = String(this.input).toUpperCase();
      this.ShowData.forEach(function (item) {
        if (String(item.name).toUpperCase().indexOf(temp) > -1) {
          searchitem.push(item);
        }
      });
      this.ShowData = JSON.parse(JSON.stringify(searchitem));
    },
    recoverdata(){
      if(this.input=="")
        this.ShowData =  this.tmp;
    },
  }
};

// showData: null,
// total: 5,
// pageSize: 1,
// form: {
//   bookName: "",
//   bookAuthor: "",
//   bookISBN: "",
//   bookSurplus: "",
//   bookPrice: "",
// }
//     formLabelWidth: "120px",
//     dialogFormVisible: false,
//     rules: {
//       bookName: [
//         { required: true, message: "请输入书籍名称", trigger: "blur" },
//       ],
//     },
//     tabRowIndex:null,
//     tabColumnIndex:null,
//   };
// },
// created: function () {
//   this.showData = JSON.parse(JSON.stringify(tableData));
// },
// methods: {
//   //   搜索实现
//   searchnow(value) {
//     let searchitem = [];
//     var temp = String(value).toUpperCase();
//     tableData.forEach(function (item) {
//       if (item.name.toUpperCase().indexOf(temp) > -1) {
//         searchitem.push(item);
//       }
//     });
//     this.showData = JSON.parse(JSON.stringify(searchitem));
//   },
//   //添加的相关函数
//   addBooks() {
//     this.dialogFormVisible = true;
//   },
//   handleAdd() {
//     var temparray = {
//       img: "",
//       name: this.form.bookName,
//       author: this.form.bookAuthor,
//       isbn: this.form.bookISBN,
//       number: this.form.bookSurplus,
//       price: this.form.bookPrice,
//     };
//     if (this.form.bookName === "") {
//       this.dialogFormVisible = false;
//       return;
//     }
//     tableData.push(temparray);
//     this.showData = JSON.parse(JSON.stringify(tableData));
//     this.dialogFormVisible = false;
//   },
//   // 删除
//   handleDelete(item) {
//     ElMessageBox.confirm("您确定要删除该行吗？", "警告", {
//       confirmButtonText: "确定",
//       cancelButtonText: "取消",
//       type: "warning",
//     })
//       .then(() => {
//         console.log(item);
//         let temp = item.name;
//         tableData.forEach(function (item, index) {
//           if (item.name.indexOf(temp) > -1) {
//             tableData.splice(index, 1);
//           }
//         });
//         this.showData = JSON.parse(JSON.stringify(tableData));
//         ElMessage({
//           type: "success",
//           message: "成功删除",
//         });
//       })
//       .catch(() => {
//         ElMessage({
//           type: "info",
//           message: "取消删除",
//         });
//       });
// },
// },
// components: {
// },
// };
</script>

<style scoped>
.search_bar {
  margin: 10px;
  display: flex;
  justify-content: space-around;
}
.search_bar > button {
  margin-left: 20px;
}
.booklist {
  width: 95%;
  margin: 0 auto;
}
</style>
