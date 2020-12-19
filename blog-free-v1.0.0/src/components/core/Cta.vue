<template>
  <v-btn
    :style="styles"
    color="primary"
    fab
    fixed
    href="https://www.baidu.com/"
    rel="noopener"
    right
    target="_blank"
    top
  >
    <v-icon>
      mdi-vuetify
    </v-icon>
  </v-btn>
</template>

<script>
  export default {
    name: 'CoreCta',

    computed: {
      styles () {
        return {
          marginTop: `${this.$vuetify.application.top}px`,
        }
      },
    },
  }
</script>
