<template>
  <v-btn
    :style="styles"
    color="secondary"
    fab
    fixed
    href="https://www.baidu.com/"
    rel="noopener"
    right
    target="_blank"
    top
  >
<!--    <v-icon>-->
<!--      mdi-vuetify-->
<!--    </v-icon>-->
    <v-icon>mdi-account-circle</v-icon>
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
