<template>
  <div id="order">
    <core-app-bar />

    <core-drawer />

    <articles
      :status="true"
    >
      <order-feed
        :status="false"
        :layout="[1, 1, 1, 1, 1, 1]"
        :count="6"
      >
        <about class="mt-10">
          我的订单
        </about>
      </order-feed>
    </articles>
    <core-footer />

    <core-cta />
  </div>
</template>

<script>
  // eslint-disable-next-line no-unused-vars
  import { mapState, mapMutations, mapGetters } from 'vuex'
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
    mounted () {
      this.initData()
    },
    methods: {
      async initData () {
        // eslint-disable-next-line no-unused-vars
        const data = await this.$H.get('/order')
        // eslint-disable-next-line eqeqeq
        // alert('data:' + data.code + (data.code == '50000'))
        // eslint-disable-next-line eqeqeq
        if (data.code == '50000') { this.$router.push('/login') }
        this.$store.commit('setOrder', data)
      },
    },
    computed: {
      // eslint-disable-next-line no-undef
      ...mapGetters({ getOrder: 'getOrder' }),
      // eslint-disable-next-line no-undef
      ...mapState(['order']),
    },
  }
</script>

<style scoped>

</style>
