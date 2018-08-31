<template>

    <Header style="height: 80px;">
        <div class="header">
            <div class="header-home">
                <router-link to="/home">
                    <span class="header-title">SMS 短信服务</span>
                </router-link>
            </div>

            <Breadcrumb class="header-breadcrumb">
                <BreadcrumbItem>
                    <span style="color: azure">Home</span>
                </BreadcrumbItem>
            </Breadcrumb>

            <div class="header-user">
                <Dropdown trigger="click">
                    <a href="javascript:void(0)">
                        <Avatar size="large" style="background: #2a93a5;">
                            <span class="header-user-name">{{name}}</span>
                        </Avatar>
                        <Icon type="ios-arrow-down"></Icon>
                    </a>
                    <DropdownMenu slot="list" class="header-user-setting" style="text-align: center;">
                        <DropdownItem>
                            <span @click="logout">退出</span>
                        </DropdownItem>
                    </DropdownMenu>
                </Dropdown>
            </div>
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

        &-breadcrumb {
            text-align: left;
        }

        &-user {
            text-align: right;
            margin-top: -64px;
            margin-right: -38px;

            &-name {
                color: #e1eac6;
            }
        }
    }

</style>