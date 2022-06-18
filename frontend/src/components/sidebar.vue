<template>

  <el-dialog
      v-model="template1"
      title=""
      width="80%"
      height="50%"
      destroy-on-close
      center
  >
    <el-image
        style="width: 100%; height: 100%"
        :src="require('@/img/report.jpg')"
        :fit="fit"></el-image>
  </el-dialog>

  <el-dialog
      v-model="template2"
      title=""
      width="40%"
      height="40%"
      destroy-on-close
      center
  >
    <el-image
        style="width: 100%; height: 100%"
        :src="require('@/img/resume.jpg')"
        :fit="fit"></el-image>
  </el-dialog>

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
<!--      <el-form-item>-->
<!--          <el-input type="text" v-model="userid" placeholder="请输入邀请的用户id" />-->
<!--      </el-form-item>-->
      <el-select v-model="value" placeholder="请选择项目类型" style="margin: 0 auto">
<!--        <el-option-->
<!--            v-for="item in options"-->
<!--            :key="item.value"-->
<!--            :label="item.label"-->
<!--            :value="item.value">-->
<!--          <span style="float: left">{{ item.label }}</span>-->
<!--          <el-button type="info" style="float: right" plain>详情</el-button>-->
<!--        </el-option>-->
        <el-option value="text" label="文档">
          <span style="float: left">文档</span>
        </el-option>
        <el-option value="excel" label="表格">
          <span style="float: left">表格</span>
        </el-option>
        <el-option value="report template" label="模板1: 报告">
          <span style="float: left">模板1: 报告</span>
          <el-button type="info" style="float: right" @click="template1=true" plain>详情</el-button>
        </el-option>
        <el-option value="resume template" label="模板2: 简历">
          <span style="float: left">模板2: 简历</span>
<!--            <el-image-->
<!--                style="width: 10vw; height: 10vh; float:right;"-->
<!--                :src="require('@/img/resume.jpg')"-->
<!--                :preview-src-list="require('@/img/resume.jpg')">-->
<!--            </el-image>-->
          <el-button type="info" style="float: right" @click="template2=true" plain>详情</el-button>
        </el-option>
      </el-select>
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


  <el-dialog
      v-model="addToDoc"
      title="加入项目"
      width="30%"
      destroy-on-close
      center
  >
    <div>
      <el-form-item>
        <el-input type="text" v-model="code" placeholder="请输入邀请码" />
      </el-form-item>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="add()">加入</el-button>
      </span>
    </template>
  </el-dialog>

 <el-row class="tac">
    <el-col :span="12">
      <el-menu
          @select="handleSelect"
        active-text-color="#ffffff"
        background-color="#24354E"
        class="el-menu-vertical-demo"
        :default-active="index"
        text-color="#9A9A9A">
<!--        @open="handleOpen"-->
<!--        @close="handleClose"-->
<!--      >-->
        <el-menu-item index="1">
         <el-button size="large" class="bigbutton" @click="centerDialogVisible = true">创建新项目</el-button>
        </el-menu-item>
        <el-menu-item index="2">
        <el-button size="large" class="bigbutton" @click="addToDoc= true">加入项目</el-button>
        </el-menu-item>
        <el-menu-item index="3">
          <span>所有项目>></span>
        </el-menu-item>
        <el-menu-item index="4">
          <span>你创建的项目>></span>
        </el-menu-item>
        <el-menu-item index="5">
          <span>你加入的项目>></span>
        </el-menu-item>
      </el-menu>
    </el-col>
  </el-row>
</template>


<script>
// import { ref } from 'vue'
// const centerDialogVisible = ref(false)
import {instance} from "@/axios/axios";
// import report from "@/img/report.jpg";
export default {
  data(){
    return{
      documentid:0,
      documentname:"",
      userid:"",
      centerDialogVisible:false,
      addToDoc:false,
      template1:false,
      template2:false,
      // temp1url:report,
      code:"",
      sourceString:'431EYZDOWGVJ5AQMSFCU2TBIRPN796XH0KL',
      docuid:0,
      power:0,
      index:"3",
      // documentok:false,
      // userok:false,
      msg:{
        docid:this.documentid,
        docname:this.documentname,
      },
      // options: [{
      //   value: 'text',
      //   label: '文档'
      // }, {
      //   value: 'excel',
      //   label: '表格'
      // },{
      //   value: 'report template',
      //   label: '模板1: 报告'
      // },
      //   {
      //     value: 'resume template',
      //     label: '模板2: 简历'
      //   }
      // ],
      value:''
    }
  },
  methods:{
    createdocument(){
      if(this.documentname==""){
        this.$message({
          showClose: true,
          message: '文档名不能为空',
          type: 'error'
        });
      };
      if(this.value==""){
        setTimeout(()=>{
          this.$message({
            showClose: true,
            message: '项目类型不能为空',
            type: 'error'
          });
        },1);
      };
       if(this.documentname!=""&&this.value!=""){
      //       setTimeout(()=>{
      //         this.$message({
      //           message: '项目创建成功',
      //           type: 'success'
      //         });
      //       },1);
      //       this.centerDialogVisible = false;
      instance.post('/addDocument',null,{
        params:{
          userId:this.$route.query.accountid,
          docName:this.documentname,
          type:this.value,
        }
      }).then(res=>{
        // console.log(res);
        if(res.data!='') {
          setTimeout(()=>{
            this.$message({
              message: '项目创建成功',
              type: 'success'
            });
          },1);
          this.index="3";
          this.centerDialogVisible = false;
          this.$emit("add");
        }
        else{
          this.$message.error("文档名重复");
        }
        }).catch(err=>{
        console.log(err);
      });
      }
    },
    add(){
      //解码
      // let ecode=this.code;
      this.code = this.code.replace(/8/g,"");//去掉8
      this.docuid = 0;
      for (let i = 0; i < this.code.length; i++) {
        let str = this.code.substring(i,i+1);//获取一个字符
        let num = this.sourceString.indexOf(str);//余数
        this.docuid += num * Math.pow(35,(this.code.length - 1 - i));
      }
      this.power =this.docuid%10;
      this.docuid= (this.docuid-this.power)/10;
      console.log(this.docuid);
      console.log(this.power);
      //add request
      // setTimeout(()=>{
      //         this.$message({
      //           message: '加入项目成功',
      //           type: 'success'
      //         });
      //       },1);
      //       this.addToDoc = false;
      instance.post('/invite',null,{
        params:{
          userId:this.$route.query.accountid,
          docId:this.docuid,
          userPower:this.power,
        }
      }).then(res=>{
        if(res.data!=""){
          this.code="";
          setTimeout(()=>{
            this.$message({
              message: '加入项目成功',
              type: 'success'
            });
          },1);
          this.index="3";
          this.addToDoc = false;
          this.$emit("add");
        }
        else{
          this.$message.error("您已加入该项目或该文档已删除");
          this.code="";
        }
      }).catch(err=>{
        console.log(err);
      })
    },
    handleSelect(index){
      console.log(index);
      if(index=="3") this.$emit("allDoc");
      if(index=="4") this.$emit("createdDoc");
      if(index=="5") this.$emit("sharedDoc")
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
