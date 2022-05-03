<template>
  <div>
    <Header />
    <el-container class="main">
      <Sidebar @save="save"></Sidebar>
        <el-main class="content">
      <List ref="listContainer"/>
        </el-main>
    </el-container>

    <div class="footer">
      <Footer />
    </div>
  </div>
</template>

<script>
import Header from "../../components/header.vue";
import Footer from "../../components/footer.vue";
import Sidebar from "../../components/sidebar.vue";
import List from'../../components/list.vue'
export default {
  components: {
    Header,
    Sidebar,
    Footer,
    List,
  },
  // created() {
  //   console.log(this.$route.params);
  // }
  methods: {

    save(){

      this.$refs.listContainer.fresh();

    }

  }
};
</script>

<style scoped>
.footer {
  background-color: #a2a2a298;
  position: fixed;
  bottom: 0px;
  height: 8vh;
  width: 100%;
}
.main {
  height: 83.5vh;
  width: 100%;
}
.side{
    height: 83.5vh;
}
el-mian{
    --el-main-padding: 30px;
    width: 70%;
}
</style>