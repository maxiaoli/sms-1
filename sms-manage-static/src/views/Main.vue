<template>
    <div class="hello">
        <h2>{{ msg }}</h2>

        <h2>subject: {{ subject }}</h2>
        <h2>Token: {{ token }}</h2>
        <h2>TokenParsed: {{ tokenParsed }}</h2>
        <h2>RealmAccess: {{ realmAccess }}</h2>
        <h2>ResourceAccess: {{ resourceAccess }}</h2>

        <button @click="getIndex">get index</button>
        <button @click="getUser">get user</button>
        <button @click="getAdmin">get admin</button>

        <br/>
        <button @click="logout">log out</button>
    </div>
</template>

<script>
  import request from '../utils/request'
  import keycloak from '../utils/auth'

  export default {
    name: 'Main',
    data() {
      return {
        msg: '',
        subject: '',
        token: '',
        tokenParsed: '',
        realmAccess: '',
        resourceAccess: ''
      }
    },
    mounted() {
      this.subject = keycloak.subject;
      this.token = keycloak.token;
      this.tokenParsed = keycloak.tokenParsed;
      this.realmAccess = keycloak.realmAccess;
      this.resourceAccess = keycloak.resourceAccess;
    },
    methods: {
      getIndex() {
        request({
          url: '/api/index',
          method: 'get'
        }).then((res) => {
          this.msg = res;
        }).catch((err) => {
          console.log(err);
        });
      },
      getUser() {
        request({
          url: '/api/user',
          method: 'get'
        }).then((res) => {
          this.msg = res;
        }).catch((err) => {
          console.log(err);
        });
      },
      getAdmin() {
        request({
          url: '/api/admin',
          method: 'get'
        }).then((res) => {
          this.msg = res;
        }).catch((err) => {
          console.log(err);
        });
      },
      logout() {
        keycloak.logout();
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
