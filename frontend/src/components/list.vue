<template>
  <!-- 搜索项目栏 -->
  <div class="search_bar">
    <el-input
      @input="searchnow"
      type="text"
      v-model="input"
      placeholder="请输入要查找的项目名称"
    />
    <el-button type="primary">搜索</el-button>
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
    <el-table :data="showData" border>
      <el-table-column type="selection" width="55" />
      <el-table-column prop="name" label="标题" width="250">
        <template v-slot="scope">
          <!-- <el-link type="primary" @click="seeblog(scope.row)">查看详情</el-link> -->
          <a :href="getHref(scope.row.id)">{{ scope.row.name }}</a>
        </template>
      </el-table-column>
      <el-table-column prop="author" label="创建者" width="160" />
      <el-table-column prop="time" label="最后修改时间" width="190" sortable />
      <el-table-column label="操作">
        <template #default="scope">
          <el-tooltip content="下载" placement="top">
            <el-button
              type="primary"
              color="#3F3F3F"
              @click="downloadDialogVisible = true"
              ><img src="../img/下载.png" alt=""
            /></el-button>
          </el-tooltip>
          <el-tooltip content="分享" placement="top">
            <el-button
              type="primary"
              color="#3F3F3F"
              @click="shareDialogVisible = true"
              ><img src="../img/分享.png" alt=""
            /></el-button>
          </el-tooltip>
          <el-tooltip content="编辑历史" placement="top">
            <el-button type="primary" color="#3F3F3F" @click="drawer = true"
              ><img src="../img/播放记录.png" alt=""
            /></el-button>
          </el-tooltip>
          <el-popconfirm
            title="您确定要删除这个文档吗"
            confirm-button-text="确定"
            cancel-button-text="取消"
            @confirm="confirmEvent(scope.row)"
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
    <el-dialog
      v-model="downloadDialogVisible"
      title="下载选项"
      width="30%"
      :before-close="handleClose"
    >
      <span>选择文件类型balabal</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="downloadDialogVisible = false">取消下载</el-button>
          <el-button type="primary" @click="downloadDialogVisible = false"
            >开始下载</el-button
          >
        </span>
      </template>
    </el-dialog>

    <!-- 分享选项 -->
    <el-dialog
      v-model="shareDialogVisible"
      title="文件分享"
      width="50%"
      :before-close="handleClose"
    >
      <el-divider />
      <router-link to="">这里放分享链接</router-link>
      <el-divider />
      <el-row>
        <el-col :span="8">User1</el-col>
        <el-col :span="8">edit&read</el-col>
      </el-row>
      <el-divider border-style="dashed" />
      <el-row>
        <el-col :span="8">User2</el-col>
        <el-col :span="8">edit&read</el-col>
      </el-row>
      <el-divider />
      <el-row>
        <el-col :span="16"></el-col>
        <el-col :span="8">
          <el-select v-model="value" class="m-2" placeholder="选择分享对象">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-col>
      </el-row>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="shareDialogVisible = false">可以编辑</el-button>
          <el-button type="primary" @click="shareDialogVisible = false"
            >分享</el-button
          >
        </span>
      </template>
    </el-dialog>

    <!-- 历史记录抽屉 -->
    <el-drawer v-model="drawer" title="编辑历史" :before-close="handleClose">
      <!-- 插了个描述列表在这里，具体怎么与后端通信写数据暂未实现 -->
      <el-card shadow="hover">
        <el-descriptions title="User Info" column="1">
          <el-descriptions-item label="Username"
            >kooriookami</el-descriptions-item
          >
          <el-descriptions-item label="Telephone"
            >18100000000</el-descriptions-item
          >
          <el-descriptions-item label="Place">Suzhou</el-descriptions-item>
        </el-descriptions>
      </el-card>
      <el-card shadow="hover"> Hover </el-card>
      <el-card shadow="hover"> Hover </el-card>
    </el-drawer>
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
      value: "",
      drawer: false,
      downloadDialogVisible: false,
      shareDialogVisible: false,
      showData: [
        {
          id:1,
          name: "12组在线文档编辑",
          author: "You",
          time: "just now",
        },
        {
          id:2,
          name: "救救我 救救我 救救我",
          author: "You",
          time: "just now",
        },
      ],
      options: [
        {
          value: "Option1",
          label: "Option1",
        },
        {
          value: "Option2",
          label: "Option2",
        },
        {
          value: "Option3",
          label: "Option3",
        },
        {
          value: "Option4",
          label: "Option4",
        },
        {
          value: "Option5",
          label: "Option5",
        },
      ],
      // projects:[
      //   {
      //     id:3,
      //     name:"233333",
      //   },
      //   {
      //     id:4,
      //     name:"help!!!",
      //   }
      // ]
    };
  },
  mounted() {
    // this.$route.params.projects.forEach(item=>{
    //   this.showData.push({name:item.name,author: "You",time: "just now"});
    // })

    this.user.id=this.$route.params.accountid;
    this.user.name=this.$route.params.usename;
    instance.get('/',{
      params:{
        id:this.user.id,
      }
    }).then(res=>{
      res.data.projects.forEach(item=>{
        this.showData.push({id:item.id,name:item.name,author:"You",time:"just now"});
      })
    }).catch(err=>{
      console.log(err);
    })
    // this.projects.forEach(item=>{
    //       this.showData.push({id:item.id,name:item.name,author:"You",time:"just now"});
    //     })
  },
  methods:{
    getHref(val){
      return '/Pages?docid='+val;
    }
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