<template>

    <div class="layout">
        <Layout>

            <Layout>
                <LayoutHeader></LayoutHeader>
            </Layout>

            <Layout>
                <Sider hide-trigger :style="{background: '#fff'}">
                    <LayoutSider></LayoutSider>
                </Sider>

                <LayoutMain></LayoutMain>
            </Layout>
        </Layout>
    </div>
</template>

<script>
  import {LayoutHeader, LayoutSider, LayoutMain} from './components'

  export default {
    name: 'LayoutIndex',
    components: {
      LayoutHeader,
      LayoutSider,
      LayoutMain
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss">
    .layout {
        border: 1px solid #d7dde4;
        background: #f5f7f9;
        position: relative;
        border-radius: 4px;
        overflow: hidden;

        &-logo {
            width: 100px;
            height: 30px;
            border-radius: 3px;
            float: left;
            position: relative;
            top: 15px;
            left: 20px;
        }

        &-nav {
            width: 420px;
            margin: 0 auto;
            margin-right: 20px;
        }
    }

</style>