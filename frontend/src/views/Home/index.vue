<template>
  <div>
    <Header />
    <el-container class="main">
      <Sidebar @add="add"
               @allDoc="allDoc"
               @createdDoc="createdDoc"
               @sharedDoc="sharedDoc"></Sidebar>
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
    add(){
      this.$refs.listContainer.fresh();
    },
    allDoc(){
      this.$refs.listContainer.allDoc();
    },
    createdDoc(){
      this.$refs.listContainer.createdDoc();
    },
    sharedDoc(){
      this.$refs.listContainer.sharedDoc();
    },
  }
};
</script>

<style scoped>
.footer {
  background-color: #92A4BD;
  position: fixed;
  bottom: 0px;
  height: 5vh;
  width: 100%;
}
.main {
  height: 86.5vh;
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