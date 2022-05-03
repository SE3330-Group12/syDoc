<template>
  <div class="bg_header">
    <div class="header">
      <div class="logo">
        <img src="../../img/logo.png" />
      </div>
      <div class="nav">
        <ul class="clear">
          <li>
           <router-link :to="{name:'Login'}">Log In</router-link>
          </li>
          <li>
            <router-link :to="{name:'Register'}">Register</router-link>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {

};
</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
}

li {
    list-style: none;
}

a {
    text-decoration: none;
}

.bg_header {
    height: 80px;
    width: 100%;
    background: rgb(14, 31, 65);
}

.bg_header .header {
    height: 80px;
    margin: 0px 0px;
}

.bg_header .header .logo {
    width: 230px;
    height: 80px;
    float: left;
    overflow: hidden;
    background-color: rgb(14, 31, 65);
}

.bg_header .header .logo img {
    width: 100%;
    height: 100%;
    margin-top: 0px;
}

.bg_header .header .nav {
    position: relative;
    width: 270px;
    height: 80px;
    top: 0px;
    right: 0px;
    float: right;
}

.bg_header .header .nav ul li {
    width: 135px;
    height: 80px;
    float: right;
    text-align: center;
    line-height: 80px;
    font-size: 18px;
    overflow: hidden;
}

.bg_header .header .nav ul li a {
    display: block;
    color: rgb(255, 255, 255);
    text-decoration: none;
}

.bg_header .header .nav ul li a:hover {
    background-color: #fff;
    color: rgb(50, 50, 50);
    font-weight: bold;
}
</style>