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

  <!-- 添加商品对话框
  <el-dialog v-model="dialogFormVisible" title="请录入书籍信息">
    <el-form :model="form" :rules="rules">
      <el-form-item label="封面:" :label-width="formLabelWidth">
        <el-upload
          class="upload-demo"
          action="https://jsonplaceholder.typicode.com/posts/"
          :on-preview="handlePreview"
          multiple
          :limit="1"
          :file-list="fileList"
        >
          <el-button type="primary">上传封面</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="书名:" :label-width="formLabelWidth">
        <el-input v-model="form.bookName" autocomplete="off" />
      </el-form-item>
      <el-form-item label="作者:" :label-width="formLabelWidth">
        <el-input v-model="form.bookAuthor" autocomplete="off" />
      </el-form-item>
      <el-form-item label="ISBN编号:" :label-width="formLabelWidth">
        <el-input v-model="form.bookISBN" autocomplete="off" />
      </el-form-item>
      <el-form-item label="库存:" :label-width="formLabelWidth">
        <el-input v-model="form.bookSurplus" autocomplete="off" />
      </el-form-item>
      <el-form-item label="单价:" :label-width="formLabelWidth">
        <el-input v-model="form.bookPrice" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAdd">确定</el-button>
      </span>
    </template>
  </el-dialog> -->

  <!-- 显示数据主体 -->
  <div class="booklist">
    <el-table :data="ShowData" border>
      <el-table-column type="selection" width="55" />
      <el-table-column prop="name" label="标题" width="250">
        <template v-slot="scope">
          <!-- <el-link type="primary" @click="seeblog(scope.row)">查看详情</el-link> -->
          <a :href="getHref(scope.row.id)">{{ scope.row.name }}</a>
        </template>
      </el-table-column>
      <el-table-column prop="author" label="创建者" width="160" />
      <el-table-column prop="type" label="类型" width="160" />
      <el-table-column prop="time" label="最后修改时间" width="250" sortable />
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

    <!-- 下载选项弹框 -->
<!--    <el-dialog-->
<!--      v-model="downloadDialogVisible"-->
<!--      title="下载选项"-->
<!--      width="30%"-->
<!--      :before-close="handleClose"-->
<!--    >-->
<!--      <span>选择文件类型balabal</span>-->
<!--      <template #footer>-->
<!--        <span class="dialog-footer">-->
<!--          <el-button @click="downloadDialogVisible = false">取消下载</el-button>-->
<!--          <el-button type="primary" @click="downloadDialogVisible = false"-->
<!--            >开始下载</el-button-->
<!--          >-->
<!--        </span>-->
<!--      </template>-->
<!--    </el-dialog>-->

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
                <el-col :span="7">{{user.username}}</el-col>
                <el-col :span="7">{{user.userpower}}</el-col>

<!--                <el-tooltip content="修改成员权限" placement="top">-->
<!--                  <el-button-->
<!--                      type="primary"-->
<!--                      color="#3F3F3F"-->
<!--                      @click=""-->
<!--                  ><img src="../img/创作.png" alt=""-->
<!--                  /></el-button>-->
<!--                </el-tooltip>-->
                <el-popconfirm
                    title="您确定要修改此成员的权限?"
                    confirm-button-text="确定"
                    cancel-button-text="取消"
                    @confirm="changePower(user.userId)"
                >
                  <template #reference>
                    <el-button type="primary" color="#3F3F3F" :disabled="this.dauthor!=this.user.name"
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
                        :disabled="this.dauthor!=this.user.name"
                    ><img src="../img/删除.png" alt=""
                    /></el-button>
                  </el-tooltip>
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
      showdid:0,
      dauthor:"",
      DocData: [
        // {
        //   id: 1, name: "item.name", author: "You",type:"文档", time: "just now"
        // },
        // {
        //   id: 2, name: "item.name", author: "papa",type:"文档", time: "just now"
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
    getHref(val){
      return '/Pages?docid='+val;
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