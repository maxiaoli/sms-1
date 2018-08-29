<template>
    <div class="dashboard-container">
        <div class="dashboard-text">{{name}} 欢迎访问短信服务管理系统=。=</div>
    </div>
</template>

<script>
  export default {
    name: 'dashboard',
    data() {
      return {
        name: '无名'
      }
    },
    mounted() {
        this.name = this.getName();
    },
    methods: {
      getName() {
        return this.$store.getters.name;
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .dashboard {
        &-container {
            margin: 30px;
        }
        &-text {
            font-size: 30px;
            line-height: 46px;
        }
    }
</style>
