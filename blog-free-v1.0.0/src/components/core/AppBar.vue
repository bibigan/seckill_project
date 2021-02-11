<template>
  <v-app-bar
    app
    flat
  >
    <v-app-bar-nav-icon
      class="hidden-md-and-up"
      @click="toggleDrawer"
    />

    <v-container class="mx-auto py-0">
      <v-row align="center">
        <v-img
          :src="require('@/assets/logo.png')"
          class="mr-5"
          contain
          height="48"
          width="48"
          max-width="48"
          @click="$vuetify.goTo(0)"
        />

        <v-btn
          v-for="(link, i) in linkList"
          :key="i"
          v-bind="link"
          class="hidden-sm-and-down"
          text
          @click="onClick($event, link)"
        >
          {{ link.text }}
        </v-btn>
        <v-spacer />

        <v-text-field
          append-icon="mdi-magnify"
          flat
          hide-details
          solo-inverted
          style="max-width: 300px;"
        />
      </v-row>
    </v-container>
  </v-app-bar>
</template>

<script>
  // Utilities
  import {
    mapGetters,
    mapMutations,
  } from 'vuex'

  export default {
    name: 'CoreAppBar',
    data: () => ({
      str: '',
    }),
    computed: {
      ...mapGetters(['links']),
      userName () {
        return localStorage.getItem('userName')
      },
      // eslint-disable-next-line vue/return-in-computed-property
      linkList () {
        for (var i = 0; i < this.links.length; i++) {
          // eslint-disable-next-line eqeqeq
          if (this.links[i].text == '当前用户：') {
            // eslint-disable-next-line no-unused-vars
            if (localStorage.getItem('userName').length < 6) {
              // eslint-disable-next-line vue/no-side-effects-in-computed-properties
              this.str = '当前用户：' + localStorage.getItem('userName')
            } else {
              // eslint-disable-next-line vue/no-side-effects-in-computed-properties
              this.str = '当前用户：' + localStorage.getItem('userName').toString().substr(0, 4) + '...'
            }
            // eslint-disable-next-line vue/no-side-effects-in-computed-properties,no-undef
            this.links[i].text = this.str
          }
        }
        return this.links
      },
    },

    methods: {
      ...mapMutations(['toggleDrawer']),
      onClick (e, item) {
        e.stopPropagation()
        if (item.to || !item.href) return
        // // eslint-disable-next-line eqeqeq
        // alert('item.text:' + item.text + (item.text == '退出登录'))
        // eslint-disable-next-line eqeqeq
        if (item.text == '退出登录') {
          localStorage.removeItem('Authorization')
          localStorage.removeItem('userName')
        }
        // eslint-disable-next-line eqeqeq
        if (item.text == this.str) {
          alert('用户名：' + localStorage.getItem('userName'))
          // eslint-disable-next-line eqeqeq
        } else if (this.$route.path == '/home') {
          console.log('是 /home')
          this.$vuetify.goTo(item.href.endsWith('!') ? 0 : item.href)
          // eslint-disable-next-line eqeqeq
        } else {
          console.log('不是 /home')
          this.$router.push('/home' + item.href)
        }
      },
      exit () {
        localStorage.removeItem('Authorization')
        this.$router.push('/login')
      },
    },
  }
</script>
