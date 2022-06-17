<template>
  <div>
    <Header></Header>
    <div id = 'model'>
    <p>{{docName}} （{{model}}）</p></div>
    <QuillEditor
      id="editorId"
      ref="myQuillEditor"
      theme="snow"
      v-model:content="content"
      contentType="html"
      @blur="onEditorBlur($event)"
      @focus="onEditorFocus($event)"
      @textChange="onEditorChange($event)"
      @ready="onEditorReady($event)"
      style="min-height: 500px"
      :options="editorOption"
    />
    <div id="count">当前字数：{{ count }}</div>
    <div>
      <button id="cmd" @click="toPDF()">导出为PDF</button>
    </div>
  </div>
</template>

<script>
import {instance} from "@/axios/axios";
import Header from "../../components/header.vue";
import { saveAs } from "file-saver";
import { pdfExporter } from "quill-to-pdf";

import { reactive, ref, toRefs, toRaw, readOnly } from "vue";
import { useWebSocket } from "../../hooks";

const toolbarOptions = [
  ["bold", "italic", "underline", "strike"], // 加粗，斜体，下划线，删除线
  ["blockquote", "code-block"], //引用，代码块
  [{ header: 1 }, { header: 2 }], // 几级标题
  [{ list: "ordered" }, { list: "bullet" }], // 有序列表，无序列表
  [{ script: "sub" }, { script: "super" }], // 下角标，上角标
  [{ indent: "-1" }, { indent: "+1" }], // 缩进
  [{ direction: "rtl" }], // 文字输入方向
  [{ size: ["small", false, "large", "huge"] }], // 字体大小
  [{ color: [] }], // 颜色选择
  [{ font: [] }], // 字体
  [{ align: [] }], // 居中
  ["clean"], // 清除样式,
  ["link", "image"], // 上传图片、上传视频
];

export default {
  created(){
    setInterval(()=>{
      setTimeout(()=>{
        instance.get('/heartbeat')
            .then(res=>{
              if(res.data&&this.isfresh){
                //fresh
                location.reload();
                this.isfresh=false;
                console.log(this.isfresh)
              }
            }).catch(err=>{
          //box out
          if(!this.isfresh)
          {
            // console.log(this.isfresh);
            alert("Network connection error!");
            this.isfresh=true;
          }
          console.log(err)
        })
      },0)
    },1000)//1 s
  },
  props: {
    getContent: { type: String, default: "" },
  },
  setup(props) {
    var docId = window.location.href.split("?")[1].split("=")[1].split("&")[0];
    var docName = window.location.href
      .split("?")[1]
      .split("&")[1]
      .split("=")[1];
    var uid = window.location.href.split("&")[2].split("=")[1];

    let content = ref("");
    content.value = props.getContent;
    const myQuillEditor = ref(null);

    const state = reactive({
      text: "",
      count: 0,
      delta: 0,
      model:"",
      docName:"",
    });
    var username="nzy";
    var docType="doc";
    const ws = useWebSocket(docId,docType,username,handleMessage);

    function handleMessage(e) {
      let quill = toRaw(myQuillEditor.value).getQuill();
      let deltas = e.data.split("\n");
      deltas.forEach((element) => {
        console.log(element);
        let obj = JSON.parse(element);
        state.delta++;
        quill.updateContents(obj._delta.ops);
      });
    }

    const onEditorChange = (e) => {
      console.log("Editor changed", e);
    };

    const onEditorFocus = (e) => {
      console.log("Editor Focused.", e);
    };

    const onEditorBlur = (e) => {
      console.log("Editor blured.", e);
    };

    const onEditorReady = (e) => {
      console.log("Editor ready.", e);

      let quill = toRaw(myQuillEditor.value).getQuill();
      state.docName = docName;

      instance
        .get('/getPermission', {
          params: {
            documentId: docId,
            userId: uid,
          },
        })
        .then((res) => {
          if(res.data == 2) {
            quill.enable(false);
            state.model = "阅读模式";
          }
          else {
            state.model = "编辑模式";
          }
        })
        .catch((err) => {
          console.log(err);
        });


      quill.on("text-change", (delta) => {
        console.log("change", delta);
        const txt = quill.getText();
        const char = txt.replace(/\s/g, "");
        state.count = char.length;

        //将docId和delta传递给后端
        if (state.delta == 0) {
          ws.send(
            JSON.stringify({
              _delta: delta,
            })
          );
        } else {
          state.delta--;
        }
      });
    };

    async function toPDF() {
      let quill = toRaw(myQuillEditor.value).getQuill();
      const delta = quill.getContents(); //gets the Quill delta
      const pdfAsBlob = await pdfExporter.generatePdf(delta); //converts to PDF
      //saveAs(pdfAsBlob, 'pdf-export.pdf'); //downloads from the browser
      saveAs(pdfAsBlob, docName);
    }

    return {
      ...toRefs(state),
      content,
      myQuillEditor,
      onEditorChange,
      onEditorFocus,
      onEditorReady,
      onEditorBlur,
      toPDF,
    };
  },

  methods: {
    // weberror() {
    //   this.$alert('网络连接异常，请检查网络后重试', '网络错误', {
    //     confirmButtonText: '确定',
    //     callback: action => {
    //       this.$message({
    //         type: 'info',
    //         message: `action: ${ action }`
    //       });
    //     }
    //   });
    // }
  },

  components: {
    Header,
  },

  data() {
    return {
      content: "",
      //disabled:false,
      editorOption: {
        theme: "snow",
        modules: {
          toolbar: toolbarOptions,
        },
        placeholder: "Start here....",
      },
      isfresh:false,
    };
  },
};
</script>

<style scoped>
#model {
  height: 40px;
}
p {
  width: 200px;
  font: bold;
}
</style>
