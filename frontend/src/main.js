import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'

/* createApp(App).use(store).use(router).use(ElementPlus).mount('#app') */
createApp(App).use(store).use(router).use(ElementPlus).component('QuillEditor', QuillEditor).mount('#app')