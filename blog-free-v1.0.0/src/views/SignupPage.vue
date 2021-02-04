<template>
  <v-content id="signupPage">
    <signup />
  </v-content>
</template>

<script>
  export default {
    name: 'SignupVue',
    components: {
      Signup: () => import('@/components/page/Signup'),
    },
  }
</script>

<style scoped>

</style>
