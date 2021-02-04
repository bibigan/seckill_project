<template>
  <div id="home">
    <core-app-bar />

    <core-drawer />

    <articles
      :status="true"
    >
<!--      引导区-->
      <banner />
      <about />
    </articles>

    <articles
      :status="false"
    >
      <home-soon />
    </articles>

<!--    订阅-->
<!--    <subscribe />-->

<!--    <social />-->
    <core-footer />

    <core-cta />
  </div>
</template>

<script>
  import HomeSoon from '../components/home/Soon'
  export default {
    name: 'Home',

    components: {
      HomeSoon,
      About: () => import('@/components/home/About'),
      Articles: () => import('@/components/home/Articles'),
      Banner: () => import('@/components/home/Banner'),
      Social: () => import('@/components/home/Social'),
      Subscribe: () => import('@/components/home/Subscribe'),
      CoreCta: () => import('@/components/core/Cta'),
      CoreDrawer: () => import('@/components/core/Drawer'),
      CoreFooter: () => import('@/components/core/Footer'),
      CoreAppBar: () => import('@/components/core/AppBar'),
    },
  }
</script>
