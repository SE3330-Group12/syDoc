<template>
    <div id="container">
  <div id="header">
    <Header  style="background-color:#0075C2"/>
  </div>
  <div id="main">
    <Info />
  </div>
  <div id="footer">
    <Footer />
  </div>
</div>
</template>

<script>
import Header from '../../components/header.vue';
import Info from './Info.vue';
import Footer from '../../components/footer.vue';

    export default {
        components:{
           Header,
            Info,
            Footer,
        }
    }
</script>

<style scoped>
#container {
  height: 100%;
  width: 100%;
}
#footer {
  position: fixed;
  bottom: 0px;
  width: 100%;
  height: 7vh;
  clear: both;

}
.layout {
  height: 100%;
}
#main {
  position: fixed;
  top:8.5vh;
  height: 91.5vh;
  width:100%;
  margin: 0;
  background-size: cover;
  background-image: url("../../img/newblue.jpg");
  background-repeat: no-repeat;
}
</style>