<template>

    <Layout :style="{padding: '0 24px 24px'}">
        <Breadcrumb :style="{margin: '24px 0'}">
            <BreadcrumbItem>Home</BreadcrumbItem>
            <BreadcrumbItem>Components</BreadcrumbItem>
            <BreadcrumbItem>Layout</BreadcrumbItem>
        </Breadcrumb>

        <Content :style="{padding: '24px', minHeight: '280px', background: '#fff'}">
            <router-view></router-view>
        </Content>
    </Layout>

</template>

<script>
  export default {
    name: 'LayoutMain'
  }
</script>