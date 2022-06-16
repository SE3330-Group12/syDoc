<template>
  <div>
    <Header></Header>
    <div id = 'model'>
      <p>当前模式： {{model}}</p></div>
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

import { reactive, ref, toRefs, toRaw } from "vue";
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
  props: {
    getContent: { type: String, default: "" },
  },
  setup(props) {
    var docId = window.location.href.split("?")[1].split("=")[1].split("&")[0];
    var docName = window.location.href.split("?")[1].split("&")[1].split("=")[1];
    var uid = window.location.href.split("&")[2].split("=")[1];

    let content = ref("");
    content.value = props.getContent;
    const myQuillEditor = ref(null);

    const state = reactive({
      text: "",
      count: 0,
      delta: 0,
      model:"",
    });

    //const ws = useWebSocket(docId, handleMessage);
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
      let delta = {
        
    "ops": [
        {
            "attributes": {
                "color": "#003700"
            },
            "insert": "[报告标题]"
        },
        {
            "attributes": {
                "header": 1
            },
            "insert": "\n"
        },
        {
            "insert": " [你的姓名]                     [日期]                                讲师姓名：[姓名]\n"
        },
        {
            "attributes": {
                "header": 1
            },
            "insert": "\n"
        },
        {
            "insert": "[立即开始]"
        },
        {
            "attributes": {
                "header": 1
            },
            "insert": "\n"
        },
        {
            "insert": "[如果准备好要撰写，只需选中一行文本，开始键入将其替换为自己的内容即可。还可以查看几条快速提示：]\n\n[始终完美呈现]"
        },
        {
            "attributes": {
                "header": 2
            },
            "insert": "\n"
        },
        {
            "insert": "1.   [需要标题？在“开始”选项卡上的“样式”库中，单击所需标题样式即可。]\n            A.   [请注意“样式”库中的其他样式，例如，对于类似这样的引用或编号列表。]\n2.   [若要在选择文本进行复制或编辑时达到最佳效果，所选的字符右侧不要包含空格。]\n\n[标题 3]"
        },
        {
            "attributes": {
                "header": 3
            },
            "insert": "\n"
        },
        {
            "insert": "·      [此样式称为“列表项目符号”。]\n"
        },
        {
            "attributes": {
                "color": "#003700"
            },
            "insert": "[使用此引用样式，以引出直接引用或突出显示关键点。]"
        },
        {
            "insert": "\n"
        }
    ]

      }
           
      quill.updateContents(delta);

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

  },

  components: {
    Header,
  },

  
  data() {
    return {
      content: "",
      editorOption: {
        //ImageResize:{},
        theme: "snow",
        modules: {
          toolbar: toolbarOptions,
          /*      ImageResize:{}, */
        },
      },
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
