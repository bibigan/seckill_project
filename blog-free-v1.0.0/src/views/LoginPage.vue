<template>
  <v-content id="loginPage">
    <login />
  </v-content>
</template>

<script>
  export default {
    name: 'LoginPage',
    components: {
      Login: () => import('@/components/page/Login'),
    },
  }
</script>

<style scoped>

</style>
