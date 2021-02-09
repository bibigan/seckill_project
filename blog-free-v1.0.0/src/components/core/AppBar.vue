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
          v-for="(link, i) in links"
          :key="i"
          v-bind="link"
          class="hidden-sm-and-down"
          text
          @click="onClick($event, link)"
        >
          {{ link.text }}
        </v-btn>

        <v-btn
          class="hidden-sm-and-down"
          text
          @click="exit"
        >
          退出登录
        </v-btn>
        <v-btn
          class="hidden-sm-and-down"
          text
          @click="test"
        >
          携带token的测试请求
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
    computed: {
      ...mapGetters(['links']),
      LinkList () {
        return this.links.filter(item => {
          return this.checkLink(item)
        })
      },
    },

    methods: {
      ...mapMutations(['toggleDrawer']),
      onClick (e, item) {
        e.stopPropagation()
        if (item.to || !item.href) return
        console.log('item.href:' + item.href)
        // eslint-disable-next-line eqeqeq
        if (this.$route.path == '/home') {
          console.log('是是是')
          this.$vuetify.goTo(item.href.endsWith('!') ? 0 : item.href)
        } else {
          console.log('否否否')
          this.$router.push('/home' + item.href)
        }
      },
      checkLink (link) {
        // 已登录式屏蔽登录,未登录是屏蔽退出
        // eslint-disable-next-line eqeqeq
        if (this.sessionStorage.getItem('user') != null && link.text == '登录') { return false }
        // eslint-disable-next-line eqeqeq
        if (this.sessionStorage.getItem('user') == null && link.text == '退出') { return false }
      },
      exit () {
        localStorage.removeItem('Authorization')
        this.$router.push('/login')
      },
      test () {
        this.$axios({
          method: 'post',
          url: 'http://127.0.0.1:8088/thymeleaf/test',
        }).then(function (res) {
          console.log('res', res)
        }).catch(function (err) {
          console.log('err', err)
        })
      },
    },
  }
</script>
