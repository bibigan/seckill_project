<template>
  <v-app>
    <router-view />
  </v-app>
</template>

<script>
  export default {
    name: 'App',

    components: {
      CoreCta: () => import('@/components/core/Cta'),
      CoreDrawer: () => import('@/components/core/Drawer'),
      CoreFooter: () => import('@/components/core/Footer'),
      CoreAppBar: () => import('@/components/core/AppBar'),
      CoreView: () => import('@/components/core/View'),
    },
  }
</script>
