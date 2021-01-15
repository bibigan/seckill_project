<template>
  <section id="feed">
    <feed
      :status="status"
    >
<!--      卡槽--><slot />
    </feed>
  </section>
</template>

<script>
  export default {
    name: 'HomeAbout',
    props: {
      status: {
        // eslint-disable-next-line no-undef
        type: Boolean,
        default: true,
      },
    },
    components: {
      Feed: () => import('@/components/Feed'),
    },
  }
</script>
