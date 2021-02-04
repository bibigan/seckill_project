<template>
  <v-col
    cols="12"
    :md="size === 2 ? 6 : size === 3 ? 3 : undefined"
  >
    <base-card
      id="basedCard"
      :height="value.prominent ? 450 : 300"
      color="grey lighten-1"
      dark
      href="#!"
    >
      <v-img
        :src="require(`@/assets/articles/${value.img}`)"
        height="100%"
        gradient="rgba(0, 0, 0, .42), rgba(0, 0, 0, .42)"
      >
        <v-row
          v-if="!value.prominent"
          class="fill-height text-right ma-0"
        >
          <v-col cols="12">
            <v-chip
              label
              class="mx-0 mb-2 text-uppercase"
              color="grey darken-3"
              text-color="white"
              small
              @click.stop=""
            >
              <base-time-count
                :end-time=" value.seckillEndTime"
                :start-time="value.seckillStartTime"
                style="color: aqua"
                @sendStatus="getStatus"
              />
            </v-chip>

            <h3 class="title font-weight-bold mb-2">
              {{ value.title }}
            </h3>

            <div class="caption">
              ￥{{ value.price }}<br>
              {{show}} {{status}} {{stat}}
            </div>
          </v-col>

          <v-col align-self="end">
            <base-buy-dialog
              :value="value"
              :status="status"
            />
          </v-col>
        </v-row>
      </v-img>
    </base-card>
  </v-col>
</template>

<script>
  import BaseTimeCount from './base/TimeCount'
  import BaseBuyDialog from './base/BuyDialog'
  export default {
    name: 'FeedCard',
    components: {
      BaseBuyDialog,
      BaseTimeCount,

    },
    props: {
      size: {
        type: Number,
        required: true,
      },
      value: {
        type: Object,
        default: () => ({}),
      },
      stat: {
        type: Boolean,
      },
    },
    data () {
      return {
        status: 1,
        show: false,
      }
    },
    computed: {
    },
    mounted () {

    },
    methods: {
      getStatus (data) {
        this.status = data
      },
    },
    watch: {
      // status: {
      //   handler (newVal, oldVal) { // eslint-disable-line no-unused-vars
      //     this.show = true
      //     document.getElementById('basedCard').hidden()
      //   },
      // },
    },
  }
</script>

<style>
.v-image__image {
  transition: .3s linear;
}
</style>
