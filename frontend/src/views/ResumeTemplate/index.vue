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

    const ws = useWebSocket(docId, handleMessage);

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
                "size": "huge"
            },
            "insert": "[姓氏] [名字]"
        },
        {
            "attributes": {
                "align": "center"
            },
            "insert": "\n"
        },
        {
            "insert": "[地址] · [电话]"
        },
        {
            "attributes": {
                "align": "center"
            },
            "insert": "\n"
        },
        {
            "attributes": {
                "color": "windowtext"
            },
            "insert": "[电子邮件] · [领英档案] · [Twitter/博客/作品集]"
        },
        {
            "attributes": {
                "align": "center"
            },
            "insert": "\n"
        },
        {
            "insert": "\n"
        },
        {
            "attributes": {
                "color": "windowtext"
            },
            "insert": "[如果你准备撰写，只需选择此提示文本并开始键入即可将其替换为自己的文本。为达到最佳效果，请不要在选中的字符右侧或左侧包含空格。简要说明你的职业目标，或概述能使你脱颖而出的技能。将工作描述中的措词用作关键词。]"
        },
        {
            "insert": "\n\n工作经验"
        },
        {
            "attributes": {
                "header": 1
            },
            "insert": "\n"
        },
        {
            "insert": "            [开始日期] - [结束日期] "
        },
        {
            "attributes": {
                "header": 3
            },
            "insert": "\n"
        },
        {
            "insert": "         [职务]，"
        },
        {
            "attributes": {
                "color": "windowtext"
            },
            "insert": "[公司]"
        },
        {
            "attributes": {
                "header": 2
            },
            "insert": "\n"
        },
        {
            "insert": "              [描述你的职责和在影响力和成果方面取得的成就。举例说明，但尽量保持简洁。]\n\n           [开始日期] - [结束日期] "
        },
        {
            "attributes": {
                "header": 3
            },
            "insert": "\n"
        },
        {
            "insert": "        [职务]，"
        },
        {
            "attributes": {
                "color": "windowtext"
            },
            "insert": "[公司]"
        },
        {
            "attributes": {
                "header": 2
            },
            "insert": "\n"
        },
        {
            "insert": "            [描述你的职责和在影响力和成果方面取得的成就。举例说明，但尽量保持简洁。]\n\n教育背景"
        },
        {
            "attributes": {
                "header": 1
            },
            "insert": "\n"
        },
        {
            "insert": "            [年/月]"
        },
        {
            "attributes": {
                "header": 3
            },
            "insert": "\n"
        },
        {
            "insert": "         [学位名称]，"
        },
        {
            "attributes": {
                "color": "windowtext"
            },
            "insert": "[学校]"
        },
        {
            "attributes": {
                "header": 2
            },
            "insert": "\n"
        },
        {
            "insert": "             [你可以尽情展示你的 GPA、获得的奖项和荣誉。也可以随意添加课程作业。]\n\n           [年/月]"
        },
        {
            "attributes": {
                "header": 3
            },
            "insert": "\n"
        },
        {
            "insert": "        [学位名称]，"
        },
        {
            "attributes": {
                "color": "windowtext"
            },
            "insert": "[学校]"
        },
        {
            "attributes": {
                "header": 2
            },
            "insert": "\n"
        },
        {
            "insert": "            [你可以尽情展示你的 GPA、获得的奖项和荣誉。也可以随意添加课程作业。]\n  \n技能"
        },
        {
            "attributes": {
                "header": 1
            },
            "insert": "\n"
        },
        {
            "insert": "              "
        },
        {
            "attributes": {
                "color": "#1d824c"
            },
            "insert": "·      "
        },
        {
            "insert": "[列出你申请该岗位的相关优势]\n"
        },
        {
            "attributes": {
                "color": "#1d824c"
            },
            "insert": "              ·      "
        },
        {
            "insert": "[列出你的优点之一]\n              "
        },
        {
            "attributes": {
                "color": "#1d824c"
            },
            "insert": "·      "
        },
        {
            "insert": "[列出你的优点之一]\n"
        },
        {
            "attributes": {
                "color": "#1d824c"
            },
            "insert": "              ·      "
        },
        {
            "insert": "[列出你的优点之一]\n"
        },
        {
            "attributes": {
                "color": "#1d824c"
            },
            "insert": "              ·      "
        },
        {
            "insert": "[列出你的优点之一]\n\n活动"
        },
        {
            "attributes": {
                "header": 1
            },
            "insert": "\n"
        },
        {
            "insert": "              [使用此部分突出展示你喜爱的相关兴趣和活动，以及你希望如何回馈社会。最好包含体现你的领导能力和志愿者经验的活动。或展示重要的附加作品，如出版物、证书、语言能力等。]\n  \n  \n"
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
