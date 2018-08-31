<template>

    <div class="tags-bar">
        <Tabs v-model="currentTab" closable type="card">
            <TabPane label="标签一" name="name1"></TabPane>
            <TabPane label="标签二" name="name2"></TabPane>
            <TabPane label="标签三" name="name3"></TabPane>
            <!--
                   <router-link ref='tag' class="tags-view-item"
                   :class="isActive(tag)?'active':''"
                   v-for="tag in Array.from(visitedViews)"
                   :to="tag.path"
                   :key="tag.path"
                   @contextmenu.prevent.native="openMenu(tag,$event)">
                     {{generateTitle(tag.title)}}
                    <span class='el-icon-close' @click.prevent.stop='closeSelectedTag(tag)'></span>
                  </router-link>
            -->

            <template v-for="view in visitedViews">
                <TabPane :label="view.title" :name="view.name"></TabPane>
            </template>

        </Tabs>
    </div>

</template>

<script>
  import {mapGetters} from 'vuex'
  import {ADD_VISITED_VIEW, DEL_VISITED_VIEW} from '../../store/types'

  export default {
    name: 'TagsBar',
    data() {
      return {
        currentTab: 'name1'
      }
    },
    computed: {
      ...mapGetters([
        'visitedViews'
      ])
    },
    mounted() {
      this.addViewTags();
    },
    watch: {
      $route() {
        this.addViewTags();
        this.moveToCurrentTag();
      }
    },
    methods: {
      generateRoute() {
        if (this.$route.name) {
          return this.$route
        }
        return false
      },
      isActive(route) {
        return route.path === this.$route.path || route.name === this.$route.name;
      },
      addViewTags() {
        const route = this.generateRoute();
        if (!route) {
          return false
        }
        this.$store.dispatch(ADD_VISITED_VIEW, route);
      },
      moveToCurrentTag() {

      },
      closeSelectedTag(view) {
        this.$store.dispatch(DEL_VISITED_VIEW, view).then((views) => {
          if (this.isActive(view)) {
            const latestView = views.slice(-1)[0];
            if (latestView) {
              this.$router.push(latestView.path);
            } else {
              this.$router.push('/');
            }
          }
        })
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .tags-bar {
        float: left;
        margin-left: 19px;
        margin-top: 8px;
    }

</style>