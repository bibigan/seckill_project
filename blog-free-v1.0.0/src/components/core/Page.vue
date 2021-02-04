<template>
  <div>
    <core-app-bar />

    <core-drawer />

    <core-view />

    <core-footer />

    <core-cta />
  </div>
</template>

<script>
  export default {
    name: 'PageVue',
    components: {
      CoreCta: () => import('@/components/core/Cta'),
      CoreDrawer: () => import('@/components/core/Drawer'),
      CoreFooter: () => import('@/components/core/Footer'),
      CoreAppBar: () => import('@/components/core/AppBar'),
      CoreView: () => import('@/components/core/View'),
    },
  }
</script>

<style scoped>

</style>
