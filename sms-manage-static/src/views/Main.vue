<template>
    <div class="hello">
        <h2>{{ msg }}</h2>
        <h2>{{ subject }}</h2>
        <h2>{{ token }}</h2>

        <button @click="getIndex">get index</button>

        <button @click="logout">log out</button>
    </div>
</template>

<script>
  import request from '../utils/request'
  import keycloak from '../utils/auth'

  export default {
    name: 'Index',
    data() {
      return {
        msg: '',
        subject: '',
        token: ''
      }
    },
    mounted() {
      this.subject = keycloak.subject;
      this.token = keycloak.token;
    },
    methods: {
      getIndex() {
        request({
          url: '/api/index',
          method: 'get'
        }).then((res) => {
          console.log(res.data);
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
