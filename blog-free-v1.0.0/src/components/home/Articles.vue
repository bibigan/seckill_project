<template>
  <section id="feed">
    <slot />
  </section>
</template>

<script>

  export default {
    name: 'HomeAbout',
    // props: {
    //   status: {
    //     // eslint-disable-next-line no-undef
    //     type: Boolean,
    //     default: true,
    //   },
    //   layout: {
    //     type: Array,
    //     // eslint-disable-next-line vue/require-valid-default-prop
    //     default: [3, 3, 3, 3, 3, 3, 3, 3],
    //   },
    //   count: {
    //     type: Number,
    //     default: 8,
    //   },
    // },
    components: {
      Feed: () => import('@/components/Feed'),
    },
  }
</script>
