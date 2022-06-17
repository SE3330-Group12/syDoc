<template>
  <div class="hello">
    <div style="position: absolute;top:0;">
        <a href="javascript:void(0)" @click="downloadExcel">Download source xlsx file</a>
    </div>

    <div
      id="luckysheet"
      style="margin:0px;padding:0px;position:absolute;width:100%;left: 0px;top: 30px;bottom:0px;"
    ></div>

    <div v-show="isMaskShow" style="position: absolute;z-index: 1000000;left: 0px;top: 0px;bottom: 0px;right: 0px; background: rgba(255, 255, 255, 0.8); text-align: center;font-size: 40px;align-items:center;justify-content: center;display:flex;">Downloading</div>

  </div>
</template>

<script>
import LuckyExcel from 'luckyexcel'
//导入库export.js 这个文件是es6的，不能在普通的HTML文件直接引入js文件（虽然都是js文件，但是有区别，具体请百度es6与es5）！需要把es6转es5才可以直接引入使用！
import {exportExcel} from './export'
import {instance} from "@/axios/axios";

export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  data(){
    return {
      selected:"",
      isMaskShow: false,
      title:this.$route.query.docname,
      permission:false
    }

  },
  created(){
    
  },
  mounted() {
    let p = this.permission;
    instance.get('/getPermissionByName',{
      params:{
        docid:this.$route.query.docid,
        username:this.$route.query.username
      }
    }).then(res=>{
      console.log(res.data);
      p=res.data;
      console.log(p);
    }).catch(err=>{
      console.log(err);
    });
    
    var options = {
      container: 'luckysheet',
      allowUpdate: true,
      title:this.title,
      loadUrl:"http://106.15.196.126:8080/load",
      hook:{
        cellUpdateBefore:function(){
          if(!p){
            console.log(p);
            alert("permission denied!");
            return false;
          }
          return true;
        },
      },
      updateUrl: 'ws://106.15.196.126:8081/?docId='+this.$route.query.docid+'&docType=sheet&username='+this.$route.query.username
    }

    $(function () {
      luckysheet.create(options);
    });
  },
  methods:{
    uploadExcel(evt){
        const files = evt.target.files;
        if(files==null || files.length==0){
            alert("No files wait for import");
            return;
        }

        let name = files[0].name;
        let suffixArr = name.split("."), suffix = suffixArr[suffixArr.length-1];
        if(suffix!="xlsx"){
            alert("Currently only supports the import of xlsx files");
            return;
        }
        LuckyExcel.transformExcelToLucky(files[0], function(exportJson, luckysheetfile){

            if(exportJson.sheets==null || exportJson.sheets.length==0){
                alert("Failed to read the content of the excel file, currently does not support xls files!");
                return;
            }
            window.luckysheet.destroy();

            window.luckysheet.create({
                container: 'luckysheet', //luckysheet is the container id
                showinfobar:false,
                data:exportJson.sheets,
                title:exportJson.info.name,
                userInfo:exportJson.info.name.creator
            });
        });
    },
    downloadExcel(){
      exportExcel(luckysheet.getAllSheets(),this.title)
    }

  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
