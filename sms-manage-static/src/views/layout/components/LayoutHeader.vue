<template>

    <Header>
        <Menu mode="horizontal" theme="dark" active-name="1">

            <div class="user">
                <Dropdown class="user-setting" trigger="click" style="margin-left: 20px">
                    <a href="javascript:void(0)">
                        <span class="user-name">{{name}}</span>
                        <Icon type="ios-arrow-down"></Icon>
                    </a>
                    <DropdownMenu slot="list">
                        <DropdownItem>
                            <span @click="logout" >退出</span>
                        </DropdownItem>
                    </DropdownMenu>
                </Dropdown>
            </div>
        </Menu>
    </Header>

</template>

<script>
  import {LOGOUT} from '../../../store/types'
  import {mapGetters} from 'vuex'

  export default {
    name: 'LayoutHeader',
    data() {
      return {}
    },
    computed: {
      ...mapGetters([
        'name'
      ])
    },
    methods: {
      logout() {
        try {
          this.$store.getters.keycloak.logout();
          this.$store.dispatch(LOGOUT);
        } catch (ex) {
          //存在 Cannot read property 'token' of null 异常，当调用keycloak登出时
        }
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .user {
        float: right;

        &-name {
            color: #e1eac6;
        }
    }

</style>