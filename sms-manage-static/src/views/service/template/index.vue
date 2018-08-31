<template>

    <div class="service-template">

        <h2>模板管理</h2>

        <h3>{{msg}}</h3>

        <input v-model="msg" type="text"/>

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
    }
  }
</script>