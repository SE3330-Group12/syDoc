<template>
  <div>
    <Header></Header>
    <Info></Info>
    <Footer></Footer>
  </div>
</template>

<script>
import Header from '../../components/header.vue';
import Footer from '../../components/footer.vue';
import Info from './personal center'
export default {
  name: "index",
  components:{
    Header,
    Info,
    Footer,
  }
}
</script>

<style scoped>

</style>