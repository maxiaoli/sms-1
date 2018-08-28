<template>

    <el-container>
        <el-header style="text-align: right; font-size: 12px">
            <span>张晓峰</span>
        </el-header>

        <el-main>
            <router-view></router-view>
        </el-main>
    </el-container>

</template>

<script>
  export default {
    name: 'Main'
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .el-header {
        background-color: #7fafef;
        color: #333;
        line-height: 60px;
    }
</style>