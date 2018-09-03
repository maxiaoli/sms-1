<template>
    <Page show-sizer show-elevator show-total
          :current="page.currentPage"
          :total="page.totalCount"
          :page-size="page.pageSize"
          :page-size-opts="pageSizeOpts"
          :loading="pageLoading"
          @on-change="changePage"
          @on-page-size-change="changePageSize"/>
</template>

<script>
  export default {
    name: 'pagination',
    props: {
      page: {
        default: () => {
          return {
            currentPage: 1,
            pageSize: 10,
            totalCount: 100
          }
        }
      },
      pageLoading: false,
      pageSizeOpts: {
        default: () => {
          return [10, 20, 50, 100]
        }
      }
    },
    methods: {
      changePage(page) {
        console.log(`当前页变为${page}`);
        this.$emit('changePage', page);
      },
      changePageSize(pageSize) {
        console.log(`每页数变为${pageSize}`);
        this.$emit('changePageSize', pageSize);
      }
    }
  }
</script>