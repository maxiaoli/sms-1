<template>

    <Layout class="main">

        <Breadcrumb class="main-breadcrumb">
            <Icon v-on:click="collapsedSider" :class="rotateIcon" type="md-menu" size="25"></Icon>

            <template v-for="view in cachedViews">
                <BreadcrumbItem v-if="view && view.meta" >{{ view.meta.title }}</BreadcrumbItem>
            </template>

            <BreadcrumbItem>Home</BreadcrumbItem>
        </Breadcrumb>

        <Content class="main-content">
            <!-- 动画过度组件 -->
            <transition name="fade" mode="out-in">
                <!-- 页面缓存组件 -->
                <keep-alive :include="cachedViews">
                    <router-view></router-view>
                </keep-alive>
            </transition>
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
        'siderCollapsed',
        'cachedViews'
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
        height: 100%;

        &-breadcrumb {
            margin: 24px 0;
            text-align: left;
        }

        &-content {
            padding: 24px;
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