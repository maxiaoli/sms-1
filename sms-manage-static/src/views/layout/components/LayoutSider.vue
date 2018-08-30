<template>

    <div class="sider">
        <Menu accordion theme="dark" width="auto" :class="menuitemClasses">
            <Submenu name="1">
                <template slot="title">
                    <Icon type="ios-navigate" v-on:click="noCollapsed"></Icon>
                    <span>Item 1</span>
                </template>
                <MenuItem name="1-1">
                    <router-link to="/home">
                        <span>Option 1</span>
                    </router-link>
                </MenuItem>
                <MenuItem name="1-2"><span>Option 2</span></MenuItem>
                <MenuItem name="1-3"><span>Option 3</span></MenuItem>
            </Submenu>
            <Submenu name="2">
                <template slot="title">
                    <Icon type="ios-keypad"></Icon>
                    <span>Item 2</span>
                </template>
                <MenuItem name="2-1"><span>Option 1</span></MenuItem>
                <MenuItem name="2-2"><span>Option 2</span></MenuItem>
            </Submenu>
        </Menu>
    </div>

</template>

<script>
  import {mapGetters} from 'vuex'
  import {TOGGLE_COLLAPSE} from '../../../store/types'

  export default {
    name: 'LayoutSider',
    computed: {
      ...mapGetters([
        'siderCollapsed'
      ]),
      menuitemClasses() {
        return [
          'menu-item',
          this.siderCollapsed ? 'collapsed-menu' : ''
        ]
      }
    },
    methods: {
      noCollapsed() {
        if (this.siderCollapsed) {
          console.log('1111');
          this.$store.dispatch(TOGGLE_COLLAPSE, false);
        }
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss">
    .menu-icon {
        transition: all .3s;
    }

    .menu-item {
        span {
            display: inline-block;
            overflow: hidden;
            width: 70px;
            text-overflow: ellipsis;
            white-space: nowrap;
            vertical-align: bottom;
            transition: width .2s ease .2s;
        }

        i {
            transform: translateX(0px);
            transition: font-size .2s ease, transform .2s ease;
            vertical-align: middle;
            font-size: 16px;
        }
    }

    .collapsed-menu {
        span {
            width: 0px;
            transition: width .2s ease;
        }

        i {
            transform: translateX(5px);
            transition: font-size .2s ease .2s, transform .2s ease .2s;
            vertical-align: middle;
            font-size: 22px;
        }

        .ivu-menu-submenu-title-icon {
            display: none;
        }
    }

</style>

