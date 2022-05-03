<template>
 <el-row class="tac">
    <el-col :span="12">
      <el-menu
        active-text-color="#ffffff"
        background-color="#0d0d0d"
        class="el-menu-vertical-demo"
        default-active="2"
        text-color="#383838"
        @open="handleOpen"
        @close="handleClose"
      >
        <el-sub-menu index="1">
          <template #title>
            <el-button size="large" class="bigbutton">创建新项目</el-button>
          </template>
            <el-menu-item index="1-1"><el-button round size="small">文本</el-button></el-menu-item>
            <el-menu-item index="1-2"><el-button round size="small">表格</el-button></el-menu-item>
        </el-sub-menu>
        <el-menu-item index="2">
          <span>所有项目</span>
        </el-menu-item>
        <el-menu-item index="3">
          <span>共享项目</span>
        </el-menu-item>
        <el-menu-item index="4">
          <span>已删除项目</span>
        </el-menu-item>
      </el-menu>
    </el-col>
  </el-row>
</template>

<script>

export default {

};
</script>

<style scoped>
ul{
  width:205px;
  height: 100%;
}
.el-col-12 {
    max-width: 100%;
    flex: 0 0 50%;
}
span{
  text-align: center;
  font-weight:bolder;
  font-size: 16px;
}
</style>
