<template>
  <div id="home">
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
    },
  }
</script>
