<template>

    <Layout class="main">

        <Breadcrumb class="main-breadcrumb">
            <Icon v-on:click="collapsedSider" :class="rotateIcon" type="md-menu" size="25"></Icon>
            <BreadcrumbItem>Home</BreadcrumbItem>
            <BreadcrumbItem>Components</BreadcrumbItem>
            <BreadcrumbItem>Layout</BreadcrumbItem>
        </Breadcrumb>

        <Content class="main-content">
            <router-view></router-view>
        </Content>
    </Layout>

</template>

<script>
  import {TOGGLE_COLLAPSE} from '../../../store/types'
  import {mapGetters} from 'vuex'

  export default {
    name: 'LayoutMain',
    computed: {
      ...mapGetters([
        'siderCollapsed'
      ]),
      rotateIcon() {
        return [
          'main-sider-toggle menu-icon',
          this.siderCollapsed ? 'main-rotate-icon' : ''
        ];
      }
    },
    methods: {
      collapsedSider() {
        this.$store.dispatch(TOGGLE_COLLAPSE);
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .main {
        padding: 0 24px 24px;

        &-breadcrumb {
            margin: 24px 0;
            text-align: left;
        }

        &-content {
            padding: 24px;
            height: -webkit-fill-available;
            background: #fff;
        }

        &-sider-toggle {
            color: #5f6969;
            margin: 0 20px;
        }

        &-rotate-icon {
            transform: rotate(-90deg);
        }
    }
</style>