<template>

    <el-container style="border: 1px solid #eee">
        <!-- sidebar -->
        <SideBar></SideBar>

        <!-- main -->
        <Main></Main>
    </el-container>

</template>

<script>
    import { SideBar, Main } from './components'

    export default {
      name: 'Layout',
      components: {
        SideBar,
        Main
      }
    }
</script>