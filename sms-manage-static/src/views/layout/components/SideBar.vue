<template>

    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu :default-openeds="['1']">
            <el-submenu index="1">
                <template slot="title"><i class="el-icon-message"></i>菜单</template>
                <el-menu-item index="1-1">选项1</el-menu-item>
                <el-menu-item index="1-2">选项2</el-menu-item>
                <el-menu-item index="1-3">选项3</el-menu-item>
                <el-menu-item index="1-4">选项4</el-menu-item>
            </el-submenu>
        </el-menu>
    </el-aside>

</template>

<script>
    export default {
      name: 'SideBar'
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .el-aside {
        color: #333;
    }
</style>

