<template>
<div id="container">
  <div id="header">
    <Header />
  </div>
  <div id="main">
    <Info />
    <div class="clear" />
  </div>
  <div id="footer">
    <Footer />
  </div>
</div>
</template>

<script>
import Top from "../Register/Top.vue";
import Info from "./log.vue";
import Footer from "../../components/footer.vue";
import Header from "../../components/header.vue";

export default {
  components: {
    Top,
    Info,
    Footer,
    Header,
  },
};
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
  background-image: url("../../img/back.jpg");
  background-repeat: no-repeat;
}
.clear{
  clear: both;
}
</style>