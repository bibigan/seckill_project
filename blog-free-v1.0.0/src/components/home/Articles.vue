<template>
  <section id="feed">
    <feed>
<!--      卡槽--><slot />
    </feed>
  </section>
</template>

<script>
  export default {
    name: 'HomeAbout',

    components: {
      Feed: () => import('@/components/Feed'),
    },
  }
</script>
