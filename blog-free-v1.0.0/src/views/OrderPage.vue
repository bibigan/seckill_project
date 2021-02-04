<template>
  <div id="order">
    <core-app-bar />

    <core-drawer />

    <articles
      :status="true"
    >
      <about />
    </articles>
    <core-footer />

    <core-cta />
  </div>
</template>

<script>
  export default {
    name: 'OrderPage',
    components: {
      About: () => import('@/components/home/About'),
      Articles: () => import('@/components/home/Articles'),
      CoreCta: () => import('@/components/core/Cta'),
      CoreDrawer: () => import('@/components/core/Drawer'),
      CoreFooter: () => import('@/components/core/Footer'),
      CoreAppBar: () => import('@/components/core/AppBar'),
    },
  }
</script>

<style scoped>

</style>
