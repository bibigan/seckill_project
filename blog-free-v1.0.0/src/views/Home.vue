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
        <about>秒杀商品</about>
      </feed>
    </articles>

<!--    <articles>-->
<!--      <feed-->
<!--        :status="false"-->
<!--        :layout="[3, 3, 3, 3, 3, 3, 3, 3]"-->
<!--        :count="8"-->
<!--      >-->
<!--        <home-soon />-->
<!--      </feed>-->
<!--    </articles>-->

    <!--    订阅-->
    <!--    <subscribe />-->

    <!--    <social />-->
    <core-footer />

    <core-cta />
  </div>
</template>

<script>
  import HomeSoon from '../components/home/Soon'
  import { mapGetters, mapState } from 'vuex'
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
    mounted () {
      // this.getItems()
      // this.$store.dispatch('setArticles')
      this.initData()
    },
    methods: {
      getItems () {
        this.$axios({
          method: 'get',
          url: 'http://192.168.189.130:8088/thymeleaf/items',
          data: null,
        }).then(function (res) {
          const data = res
          this.$store.commit('setValues', data)
        }).catch(function (err) {
          console.log('err', err)
        })
      },
      async initData () {
        // eslint-disable-next-line no-unused-vars
        const data = await this.$H.get('/items')
        // eslint-disable-next-line eqeqeq
        // alert('data:' + data.code + (data.code == '50000'))
        // eslint-disable-next-line eqeqeq
        if (data.code == '50000') { this.$router.push('/login') }
        this.$store.commit('setValues', data)
      },
    },
    computed: {
      // eslint-disable-next-line no-undef
      ...mapGetters({ getValues: 'getValues' }),
      // eslint-disable-next-line no-undef
      ...mapState(['articles']),
    },
  }
</script>
