<template>
  <!--创建文档弹窗-->
  <el-dialog
      v-model="centerDialogVisible"
      title="创建新项目"
      width="30%"
      destroy-on-close
      center
  >
<!--<span-->
<!--&gt;Notice: before dialog gets opened for the first time this node and the-->
<!--      one bellow will not be rendered</span-->
<!--&gt;-->
    <div>
<!--      <strong>Extra content (Not rendered)</strong>-->
      <el-form-item>
        <el-input type="text" v-model="documentname" placeholder="文档名" />
      </el-form-item>
      <el-form-item>
          <el-input type="text" v-model="userid" placeholder="请输入邀请的用户id" />
      </el-form-item>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="createdocument">创建</el-button>
<!--        <el-button  @click="centerDialogVisible = false"-->
<!--        >Confirm</el-button-->
<!--        >-->
      </span>
    </template>
  </el-dialog>

 <el-row class="tac">
    <el-col :span="12">
      <el-menu
        active-text-color="#ffffff"
        background-color="#0d0d0d"
        class="el-menu-vertical-demo"
        default-active="2"
        text-color="#383838">
<!--        @open="handleOpen"-->
<!--        @close="handleClose"-->
<!--      >-->
        <el-sub-menu index="1">
          <template #title>
            <el-button size="large" class="bigbutton" @click="centerDialogVisible = true">创建新项目</el-button>
          </template>
<!--            <el-menu-item index="1-1"><el-button round size="small">文本</el-button></el-menu-item>-->
<!--            <el-menu-item index="1-2"><el-button round size="small">表格</el-button></el-menu-item>-->
        </el-sub-menu>
        <el-menu-item index="2">
          <span>所有项目</span>
        </el-menu-item>
        <el-menu-item index="3">
          <span>共享项目</span>
        </el-menu-item>
        <el-menu-item index="4">
          <span>已删除项目</span>
        </el-menu-item>
      </el-menu>
    </el-col>
  </el-row>
</template>


<script>
// import { ref } from 'vue'
// const centerDialogVisible = ref(false)
import {instance} from "@/axios/axios";
export default {
  data(){
    return{
      documentid:0,
      documentname:"",
      userid:"",
      centerDialogVisible:false,
      documentok:false,
      userok:false,
      msg:{
        docid:this.documentid,
        docname:this.documentname,
      }
    }
  },
  methods:{
    createdocument(){
      instance.post('/addDocument',null,{
        params:{
          userId:this.$route.query.accountid,
          docName:this.documentname,
        }
      }).then(res=>{
        if(res.data!="") {
          this.documentok = true;
          this.documentid = res.data.documentId;
        }
      }).catch(err=>{
        console.log(err);
      });
      // console.log(this.documentok);
      if(this.documentok){
        instance.post('/invite',null,{
          params:{
            userId:this.userid,
            docId:this.documentid,
          }
        }).then(res=>{
          this.userok=(res.data!="");
        }).catch(err=>{
          console.log(err);
        })
      };
      // this.documentok = true;
      // this.documentid = 5;
      // this.userok=true;
      if(this.documentok&&this.userok){
        this.centerDialogVisible=false;
        this.$emit("save");
      }
    }
  }
};
</script>

<style scoped>
ul{
  width:205px;
  height: 100%;
}
.el-col-12 {
    max-width: 100%;
    flex: 0 0 50%;
}
span{
  text-align: center;
  font-weight:bolder;
  font-size: 16px;
}
.dialog-footer button:first-child {
  margin-right: 10px;
}
input[type="text"],
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
</style>
