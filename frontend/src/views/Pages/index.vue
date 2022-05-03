<template>
  <div>
    <Header></Header>
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
    />
    <div id="count">当前字数：{{ count }}</div>
  </div>
</template>

<script>
import Header from "../../components/header.vue";

import { reactive, ref, toRefs, toRaw } from "vue";
import { useWebSocket } from "../../hooks";

export default {
  props: {
    getContent: { type: String, default: "" },
  },
  setup(props) {
    var docId = window.location.href.split("?")[1].split("=")[1];
    console.log("docID", docId);

    let content = ref("");
    content.value = props.getContent;
    const myQuillEditor = ref(null);

    const state = reactive({
      text: "",
      count: 0,
      delta:0,
    });

    const ws = useWebSocket(docId,handleMessage);

    function handleMessage(e) {
      let quill = toRaw(myQuillEditor.value).getQuill();
      let deltas=e.data.split('\n');
      deltas.forEach(element => {
        console.log(element);
        let obj=JSON.parse(element);
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
      quill.on("text-change", (delta) => {
        console.log("change", delta);
        const txt = quill.getText();
        const char = txt.replace(/\s/g, "");
        state.count = char.length;

        //将docId和delta传递给后端
        if(state.delta==0){
            ws.send(
            JSON.stringify({
              _delta: delta,
            })
          );
        }
        else{state.delta--;}
      });
    };

    return {
      ...toRefs(state),
      content,
      myQuillEditor,
      onEditorChange,
      onEditorFocus,
      onEditorReady,
      onEditorBlur,
    };
  },
  components: {
    Header,
  },
};
</script>

<style scoped>
</style>
