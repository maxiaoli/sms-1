<template>

    <el-container>
        <el-header style="text-align: right; font-size: 25px">
            <span>{{ username }}</span>
            <button @click="logout">logout</button>
        </el-header>

        <el-main>
            <router-view></router-view>
        </el-main>
    </el-container>

</template>

<script>
  export default {
    name: 'Main',
    data() {
      return {
        username: '无名'
      }
    },
    mounted() {
        this.username = this.getUsername();
    },
    methods: {
      getUsername() {
        return this.$store.getters.username;
      },
      logout() {
        try {
            this.$store.state.keycloak.logout();
            this.$store.dispatch('logout');
        } catch (ex) {
          //存在 Cannot read property 'token' of null 异常，当调用keycloak登出时
        }
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .el-header {
        background-color: #7fafef;
        color: #333;
        line-height: 60px;
    }
</style>