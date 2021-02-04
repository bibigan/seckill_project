<template>
  <v-container
    id="about"
    tag="section"
    class="mt-5"
  >
    <base-subheading>
      <slot />
    </base-subheading>

  </v-container>
</template>

<script>
  export default {
    name: 'HomeAbout',
  }
</script>
