<template>
  <div id="order">
    <core-app-bar />

    <core-drawer />

    <articles
      :status="true"
    >
      <order-feed
        :status="false"
        :layout="[1, 1, 1]"
        :count="3"
      >
        <about class="mt-10">我的订单</about>
      </order-feed>
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
      OrderFeed: () => import('@/components/OrderFeed'),
    },
  }
</script>

<style scoped>

</style>
