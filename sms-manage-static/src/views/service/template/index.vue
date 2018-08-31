<template>

    <div class="service-template">

        <h2>模板管理</h2>

        <h3>{{msg}}</h3>

        <input v-model="msg" type="text"/>

        <button v-on:click="delAllViews">shanchu</button>

    </div>

</template>

<script>
import { ADD_VISITED_VIEW,DEL_ALL_VIEW } from '../../../store/types'

  export default {
    name: 'service-template',
    data() {
      return {
        msg: ''
      }
    },
    mounted() {
      this.addVisitedView();
    },
    methods: {
      generateRoute() {
        if (this.$route.name) {
          return this.$route;
        }
        return false;
      },
      addVisitedView() {
        const route = this.generateRoute();
        if (!route) {
          return false;
        }
        this.$store.dispatch(ADD_VISITED_VIEW, route);
      },
      delAllViews() {
        this.$store.dispatch(DEL_ALL_VIEW);
      }
    }
  }
</script>