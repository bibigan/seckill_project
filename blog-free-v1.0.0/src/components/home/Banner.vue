<template>
  <v-carousel
    cycle
    height="400"
    hide-delimiter-background
    show-arrows-on-hover
  >
    <v-carousel-item
      v-for="(slide, i) in slides"
      :key="i"
    >
      <v-sheet height="100%">
        <v-img
          :src="pictures[i]"
          class="grey lighten-2"
          height="400"
          width="100%"
        >
          <!--        <v-row-->
          <!--          class="fill-height"-->
          <!--          align="center"-->
          <!--          justify="center"-->
          <!--        >-->
          <!--          <div class="display-3">-->
          <!--            {{ slide }} Slide-->
          <!--          </div>-->
          <v-row
            class="fill-height pa-3"
            align="center"
          >
            <v-col
              cols="12"
              md="7"
              offset-md="5"
            >
              <h1 class="display-3 font-weight-light mb-2">
                秒杀商城
              </h1>

              <div class="subheading text-uppercase pl-2 mb-4">
                秒杀即将开始，下单尽享优惠
              </div>

              <v-btn
                color="primary"
                depressed
                @click="onClick($event, links[1])"
              >
                <v-icon
                  dark
                  left
                >
                  mdi-arrow-right
                </v-icon>购物
              </v-btn>
            </v-col>
          </v-row>
        </v-img>
      </v-sheet>
    </v-carousel-item>
  </v-carousel>
</template>

<script>
  import { mapGetters, mapMutations } from 'vuex'

  export default {
    name: 'HomeBanner',
    data () {
      return {
        colors: [
          'indigo',
          'warning',
          'pink darken-2',
          'red lighten-1',
          'deep-purple accent-4',
        ],
        slides: [
          'First',
          'Second',
          'Third',
        ],
        pictures: [
          require('@/assets/articles/blurcamera.jpg'),
          require('@/assets/articles/snowcup.jpg'),
          require('@/assets/articles/snowcup.jpg'),
        ],
      }
    },
    computed: {
      ...mapGetters(['links']),
    },
    methods: {
      ...mapMutations(['toggleDrawer']),
      onClick (e, item) {
        e.stopPropagation()

        if (item.to || !item.href) return

        this.$vuetify.goTo(item.href.endsWith('!') ? 0 : item.href)
      },
    },
  }
</script>
