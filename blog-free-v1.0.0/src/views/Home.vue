<template>
  <div id="home">
    <core-app-bar />

    <core-drawer />

    <articles>
      <feed
        :status="true"
        :layout="[3, 3, 3, 3, 3, 3, 3, 3]"
        :count="8"
      >
        <!--      引导区-->
        <banner />
        <about>正在秒杀</about>
      </feed>
    </articles>

    <articles>
      <feed
        :status="false"
        :layout="[3, 3, 3, 3, 3, 3, 3, 3]"
        :count="8"
      >
        <home-soon />
      </feed>
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
      Feed: () => import('@/components/Feed'),
    },
  }
</script>
