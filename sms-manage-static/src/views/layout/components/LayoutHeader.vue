<template>

    <Header style="height: 80px;">
        <div class="header">
            <Menu mode="horizontal" theme="dark" active-name="1">
                <router-link to="/home">
                    <span class="header-title">SMS 短信服务</span>
                </router-link>

                <div class="header-user">
                    <Dropdown class="header-user-setting" trigger="click">
                        <a href="javascript:void(0)">
                        <span class="header-user-name">
                            <Avatar size="large" style="background: #2a93a5;">{{name}}</Avatar>
                        </span>
                            <Icon type="ios-arrow-down"></Icon>
                        </a>
                        <DropdownMenu slot="list">
                            <DropdownItem>
                                <span @click="logout">退出</span>
                            </DropdownItem>
                        </DropdownMenu>
                    </Dropdown>
                </div>
            </Menu>
        </div>
    </Header>

</template>

<script>
  import {LOGOUT} from '../../../store/types'
  import {mapGetters} from 'vuex'

  export default {
    name: 'LayoutHeader',
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
    .header {
        height: 100%;

        &-title {
            color: azure;
            float: left;
            font-size: 25px;
        }

        &-user {
            float: right;

            &-name {
                color: #e1eac6;
            }

            &-setting {
                margin-left: 20px;
                margin-top: 3px;
            }
        }
    }

</style>