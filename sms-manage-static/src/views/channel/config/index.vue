<template>

    <div>

        <h2>渠道配置管理</h2>

        <Table :columns="column" :data="page.items"></Table>

        <pagination :page="page" :pageLoading="pageLoading" @changePage="changePage"  @changePageSize="changePageSize"></pagination>

    </div>
</template>

<script>
  import pagination from '@/components/page'
  import api from '@/components/axios'

  export default {
    name: 'channel-config',
    components: {
      pagination
    },
    data() {
      return {
        pageLoading: false,
        column: [
          {
            title: '名称',
            key: 'name'
          },
          {
            title: '类型',
            key: 'type'
          },
          {
            title: '状态',
            key: 'status'
          }
        ],
        page: {
          currentPage: 1,
          pageSize: 10,
          totalCount: 100,
          items: []
        },
        criteria: {
          name: '',
          type: '',
          currentPage: 1,
          pageSize: 10
        }
      }
    },
    mounted() {
        this.channelConfigs();
    },
    methods: {
      channelConfigs() {
        api.get('/api/channel/configs/' + this.criteria.currentPage + '/' + this.criteria.pageSize, {
          params: {
            name: this.criteria.name,
            type: this.criteria.type
          }
        }).then((res) => {
          console.log(res.data);
          this.page.currentPage = res.data.currentPage;
          this.page.pageSize = res.data.pageSize;
          this.page.totalCount = res.data.totalCount;
          this.page.items = res.data.items;
        }).catch((err) => {
          console.log(err);
        })
      },
      changePage(currentPage) {

      },
      changePageSize(pageSize) {

      }
    }
  }
</script>