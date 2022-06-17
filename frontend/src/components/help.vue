<template>
  <div>
    <h1>--水源文档使用指南--</h1>
    <div class="main">
      <h2>1.创建文档</h2>
      <p>点击侧边栏按钮“创建新项目”，在弹出的对话框中输入项目名称并选择项目类型，点击确定。</p>
      <p>我们为您预设的项目类型有文档和表格，您还可以选择系统预先为您写好的模板，更加节省工作哦</p>
      <img src="../img/newpoj.png" alt="" style="height:50vh">
      <p>恭喜您！您已经成功创建了一个文档项目了！现在开始对它进行编辑吧！</p>
      <h2>2.邀请成员</h2>
      <p>点击你想要分享的文档项目对应的“操作”一栏中的分享图标，在弹出的对话框中输入邀请的权限并点击生成邀请码，将该邀请码发送给邀请的对象。</p>
      <img src="../img/clickshare.png" alt="" style="height:60vh">
      <img src="../img/share.png" alt="" style="height:60vh">
      <p>邀请对象登录账号后在侧边栏点击加入项目后填入邀请码即可加入项目。</p>
      <h2>3.团队管理</h2>
      <p>注意：文档的创建者才有权力删除和更改成员权限</p>
      <p>如果你是文档的创建者，击你想要管理的文档项目对应的“操作”一栏中的成员管理图标。</p>
      <img src="../img/clickadmin.png" alt="" style="height:60vh">
      <p>在弹出的对话框中可以看到该项目所有的成员和他们对应的权限，点击对应按钮可以删除成员或者修改他们的权限。</p>
      <img src="../img/admin.png" alt="" style="height:60vh">
      <h2>4.导出与删除文档</h2>
      <p>在主页中点击一个文档项目操作栏中的删除图标可以删除一个文档，点击确定后文档永久删除无法恢复哦！</p>
      <p>在文档的编辑界面下方点击“PDF”按钮可自动下载文档对应的pfd文件，文件名与项目中的文档名保持一致。</p>
      <h3 class="end">以上就是全部的帮助信息，如在使用软件过程中遇到任何问题，欢迎您随时反馈！</h3>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {},
};
</script>

<style scoped>
h2 {
  text-align: left;
}
p{
    text-align: left;
    margin-left: 3vw;
}
.main{
    width: 85vw;
    margin: auto;
}
.end{
    /* margin: 50px; */
    margin-bottom: 3vh;
    text-align: left;
}
</style>
